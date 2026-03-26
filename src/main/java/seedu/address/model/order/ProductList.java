package seedu.address.model.order;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a menu of products available for ordering.
 */
public class ProductList {

    private List<Product> menu;

    /**
     * Constructs a new {@code ProductList} with a predefined set of products.
     */
    public ProductList() {
        this.menu = new ArrayList<>();
        menu.add(new Product("Chicken Rice", 4.50));
        menu.add(new Product("Mixed Rice", 4.50));
        menu.add(new Product("Beef Udon", 7.00));
        menu.add(new Product("Ice Cream", 2.50));
        menu.add(new Product("Caesar Salad", 5.50));
        menu.add(new Product("Smoked Salmon Bagel", 8.50));
        menu.add(new Product("Apple Juice", 1.50));
        menu.add(new Product("Cafe Latte", 3.00));
    }

    /**
     * Returns the product at the specified 1-based index in the menu.
     * @param index 1-based index in the menu.
     * @return menu item.
     */
    public Product getItem(int index) {
        return menu.get(index - 1);
    }
}
