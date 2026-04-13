//@@author Achiack
package seedu.address.model.order;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.AppUtil;

/**
 * Represents a quantity in an order.
 */
public class Quantity {
    public static final String MESSAGE_CONSTRAINTS = "Quantity should be a non-negative integer.";
    public static final String MESSAGE_MAXIMUM = String.format("Quantity cannot exceed 500");
    private static final int MAX_VALUE = 500;
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
        if (quantity.length() > 9) {
            throw new IllegalArgumentException(MESSAGE_MAXIMUM);
        }
        this.value = Integer.parseInt(quantity);
        if (this.value > MAX_VALUE) {
            throw new IllegalArgumentException(MESSAGE_MAXIMUM);
        }
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

