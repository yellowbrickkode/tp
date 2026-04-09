//@@author Achiack
package seedu.address.model.order;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.AppUtil;

/**
 * Represents a quantity in an order.
 */
public class Quantity {
    public static final String MESSAGE_CONSTRAINTS = "Quantity should be a non-negative integer.";
    private static final String VALIDATION_REGEX_NONNEGATIVE = "^[0-9]\\d*$";

    private final int value;

    /**
     * Constructs a {@code Quantity}.
     *
     * @param quantity A valid quantity string.
     */
    public Quantity(String quantity) {
        requireNonNull(quantity);
        AppUtil.checkArgument(isValidQuantity(quantity), MESSAGE_CONSTRAINTS);
        this.value = Integer.parseInt(quantity);
    }

    public int getValue() {
        return value;
    }

    /**
     * Returns true if a given string is a valid and non-negative quantity.
     */
    public static boolean isValidQuantity(String test) {
        return test != null && test.matches(VALIDATION_REGEX_NONNEGATIVE);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Quantity && value == ((Quantity) other).value);
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}

