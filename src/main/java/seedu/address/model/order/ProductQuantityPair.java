package seedu.address.model.order;

import java.util.Objects;

import seedu.address.logic.Messages;

/**
 * Represents a product + quantity pair.
 */
public class ProductQuantityPair implements Comparable<ProductQuantityPair> {
    public static final String MESSAGE_CONSTRAINTS =
            "Orders should be in the form \"MENU_ITEM PRODUCT_QUANTITY\".";
    public static final String VALIDATION_REGEX = "^[0-9]\\d* -?\\d+$";
    private final Product product;
    private final Quantity quantity;

    /**
     * Constructs a {@code ProductQuantityPair}.
     *
     * @param productQuantityPair A valid product + quantity pair.
     */
    public ProductQuantityPair(String productQuantityPair) {
        if (!isValidProductQuantityPair(productQuantityPair)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }

        String[] pair = productQuantityPair.split(" ", 2);
        int product = Integer.parseInt(pair[0]);
        String quantity = pair[1];

        if (!new ProductList().isValidItem(product)) {
            throw new IllegalArgumentException(String.format(Messages.MESSAGE_INVALID_MENU_ITEM, product));
        }

        if (!Quantity.isValidQuantity(quantity)) {
            throw new IllegalArgumentException(Quantity.MESSAGE_CONSTRAINTS);
        }

        this.product = Messages.MENU.getItem(product);
        this.quantity = new Quantity(quantity);
    }

    /**
     * Returns true if a given string is a valid product + quantity pair.
     */
    public static boolean isValidProductQuantityPair(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the Product of a ProductQuantityPair.
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Returns the Quantity of a ProductQuantityPair.
     */
    public Quantity getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return String.format("%d %d", Messages.MENU.getIndex(product), quantity.getValue());
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (!(object instanceof ProductQuantityPair other)) {
            return false;
        }

        return product.equals(other.product) && quantity.equals(other.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity);
    }

    @Override
    public int compareTo(ProductQuantityPair o) {
        return product.compareTo(o.product);
    }
}
