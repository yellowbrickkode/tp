package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's region in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRegion(String)}
 */
public class Region {

    public static final String MESSAGE_CONSTRAINTS = "Regions should be one of the following: N, NE, W, E, C";

    public final String value;

    /**
     * Constructs a {@code Region}.
     *
     * @param region A valid region.
     */
    public Region(String region) {
        requireNonNull(region);
        checkArgument(isValidRegion(region), MESSAGE_CONSTRAINTS);
        value = region;
    }

    /**
     * Returns true if a given string is a valid region.
     */
    public static boolean isValidRegion(String test) {
        return test.matches("^(N|NE|W|E|C)$");
    }

    @Override
    public String toString() {
        return value;
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
        return value.equals(otherRegion.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

