package seedu.address.model.order;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents the Product ordered.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Product {
    public static final String MESSAGE_CONSTRAINTS =
            "Product names should not be blank.";

    private static int idx = 1;
    public final int index;
    public final String name;
    private final double price;

    /**
     * Constructs an {@code Product}.
     *
     * @param product A valid Product.
     */
    public Product(String product, double price) {
        requireAllNonNull(product, price);
        checkArgument(!product.trim().isEmpty(), MESSAGE_CONSTRAINTS);
        this.index = idx;
        name = product;
        this.price = price;
        idx++;
    }

    public String getName() { return name; }

    public double getPrice() { return price; }

    @Override
    public String toString() {
        return name + ", $" + price;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Product
                && name.equals(((Product) other).name)
                && price == (((Product) other).price));
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
