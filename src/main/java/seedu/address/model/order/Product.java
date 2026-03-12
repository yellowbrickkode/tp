package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the Product ordered.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Product {
    public static final String MESSAGE_CONSTRAINTS =
            "Product names should not be blank.";

    public final String value;

    /**
     * Constructs an {@code Product}.
     *
     * @param product A valid Product.
     */
    public Product(String product) {
        requireNonNull(product);
        checkArgument(!product.trim().isEmpty(), MESSAGE_CONSTRAINTS);
        value = product;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Product
                && value.equals(((Product) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
