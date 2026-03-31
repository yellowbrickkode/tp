//@@author Achiack
package seedu.address.model.order;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;

/**
 * Represents an OrderDate of an Order.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class OrderDateTime {
    public final LocalDateTime value;

    /**
     * Constructs an {@code OrderDateTime}.
     *
     * @param datetime A valid datetime.
     */
    public OrderDateTime(LocalDateTime datetime) {
        requireNonNull(datetime);
        value = datetime;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof OrderDateTime
                && value.equals(((OrderDateTime) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
