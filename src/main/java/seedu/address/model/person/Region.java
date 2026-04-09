package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's region in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRegion(String)}
 */
public class Region {
    public static final String MESSAGE_CONSTRAINTS =
            "Regions should be one of: N, NE, W, E, C. They should not be blank.";
    public static final String VALIDATION_REGEX = "^(N|NE|W|E|C)$";

    /**
     * An enumeration for all possible region types.
     */
    public enum RegionType {
        N("North", "#2C7542"),
        NE("North East", "#B87F23"),
        W("West", "#32458A"),
        E("East", "#8C3B3B"),
        C("Central", "#7D4B8C");

        public final String label;
        public final String colour;

        RegionType(String label, String colour) {
            this.label = label;
            this.colour = colour;
        }
    }

    private final RegionType value;

    /**
     * Constructs a {@code Region}.
     *
     * @param region A valid region.
     */
    public Region(String region) {
        requireNonNull(region);
        checkArgument(isValidRegion(region), MESSAGE_CONSTRAINTS);
        this.value = RegionType.valueOf(region);
    }

    /**
     * Returns the value of the region.
     */
    public RegionType getValue() {
        return value;
    }

    /**
     * Returns true if a given string is a valid region.
     */
    public static boolean isValidRegion(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the user-facing label of the Region.
     */
    public String toLabel() {
        return getValue().label;
    }

    /**
     * Returns the label colour of the Region.
     */
    public String getColour() {
        return getValue().colour;
    }

    @Override
    public String toString() {
        return getValue().toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Region)) {
            return false;
        }

        Region otherRegion = (Region) other;
        return getValue().equals(otherRegion.getValue());
    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }

}
