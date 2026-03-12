package seedu.address.model.order;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

/**
 * Represents an OrderDate of an Order.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class OrderDate {
    public final LocalDate value;

    /**
     * Constructs an {@code OrderDate}.
     *
     * @param date A valid date.
     */
    public OrderDate(LocalDate date) {
        requireNonNull(date);
        value = date;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof OrderDate
                && value.equals(((OrderDate) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
