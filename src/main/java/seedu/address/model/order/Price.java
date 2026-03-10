package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Price {
    public static final String MESSAGE_CONSTRAINTS =
            "Price must be a positive number.";

    public final double value;

    /**
     * Constructs an {@code Price}.
     *
     * @param price A valid Price.
     */
    public Price(String price) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), MESSAGE_CONSTRAINTS);
        value = Double.parseDouble(price);
    }

    /**
     * Returns true if a given string is a valid price.
     */
    public static boolean isValidPrice(String test) {
        try {
            return Double.parseDouble(test) >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("%.2f", value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Price
                && value == ((Price) other).value);
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }
}
