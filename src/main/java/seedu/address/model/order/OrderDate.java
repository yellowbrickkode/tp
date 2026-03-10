package seedu.address.model.order;

import java.time.LocalDate;

import static java.util.Objects.requireNonNull;

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
