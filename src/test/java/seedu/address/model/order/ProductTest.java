//@@author Achiack
package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    void constructor_null_throwsNullPointerException() {
        //@@author
        assertThrows(NullPointerException.class, () -> new Product(null, 12.00));
        //@@author Achiack
    }

    @Test
    void constructor_blank_throwsIllegalArgumentException() {
        //@@author
        assertThrows(IllegalArgumentException.class, () -> new Product("", 12.00));
        assertThrows(IllegalArgumentException.class, () -> new Product("   ", 12.00));
        //@@author Achiack
    }

    @Test
    void constructor_validProduct_success() {
        //@@author
        Product product = new Product("Laptop", 200.00);
        assertEquals("Laptop", product.getName());
        //@@author Achiack
    }

    @Test
    void equals_sameValue_returnsTrue() {
        //@@author
        Product a = new Product("Phone", 200.00);
        Product b = new Product("Phone", 200.00);
        //@@author Achiack
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void equals_differentValue_returnsFalse() {
        //@@author
        Product a = new Product("Phone", 200.00);
        Product b = new Product("Tablet", 200.00);
        //@@author Achiack
        assertNotEquals(a, b);
    }

    @Test
    void toString_returnsValue() {
        //@@author
        Product product = new Product("Headphones", 100.00);
        assertEquals("Headphones, $100.00", product.toString());
    }
}
