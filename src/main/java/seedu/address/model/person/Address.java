package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import javafx.util.Pair;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Address {

    public static final String MESSAGE_CONSTRAINTS =
            "Addresses should be entered as postal codes, and should not be blank.";
    public static final String MESSAGE_CONSTRAINTS_UNIT =
            "Unit numbers should follow the convention #XX-XX or #XX-XXX.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "\\d{6}";
    public static final String VALIDATION_REGEX_UNIT = "[#]\\d{2}[-]\\d{2,3}";

    public final Pair<String, String> value;

    /**
     * Constructs an {@code Address}.
     *
     * @param address A valid address.
     */
    public Address(String address) {
        requireNonNull(address);
        checkArgument(isValidAddress(address), MESSAGE_CONSTRAINTS);
        value = new Pair<>(address, "");
    }

    /**
     * Constructs an {@code Address} with a unit number.
     *
     * @param address A valid address.
     * @param unit A valid unit number.
     */
    public Address(String address, String unit) {
        requireNonNull(address);
        checkArgument(isValidAddress(address), MESSAGE_CONSTRAINTS);
        requireNonNull(unit);
        checkArgument(isValidUnit(unit), MESSAGE_CONSTRAINTS_UNIT);
        value = new Pair<>(address, unit);
    }

    /**
     * Returns true if a given string is a valid address.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid unit number.
     */
    public static boolean isValidUnit(String test) {
        return test.matches(VALIDATION_REGEX_UNIT);
    }

    @Override
    public String toString() {
        if (getUnit().isEmpty()) {
            return getPostalCode();
        }
        return getPostalCode() + ", " + getUnit();
    }

    public String getPostalCode() {
        return value.getKey();
    }

    public String getUnit() {
        return value.getValue();
    }

    public Pair<String, String> getAddress() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Address)) {
            return false;
        }

        Address otherAddress = (Address) other;
        boolean samePostalCode = getPostalCode().equals(otherAddress.getPostalCode());
        boolean sameUnitNo = getUnit().equals(otherAddress.getUnit());
        return samePostalCode && sameUnitNo;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
