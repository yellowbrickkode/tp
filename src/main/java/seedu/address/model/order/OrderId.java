package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class OrderId {
    public static final String MESSAGE_CONSTRAINTS =
            "Order ID should only contain positive integers.";

    public static final String VALIDATION_REGEX = "\\d+";

    public final String value;

    /**
     * Constructs an {@code OrderId}.
     *
     * @param orderId A valid OrderId.
     */
    public OrderId(String orderId) {
        requireNonNull(orderId);
        checkArgument(isValidOrderId(orderId), MESSAGE_CONSTRAINTS);
        value = orderId;
    }

    /**
     * Returns true if a given string is a valid orderId.
     */
    public static boolean isValidOrderId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof OrderId
                && value.equals(((OrderId) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
