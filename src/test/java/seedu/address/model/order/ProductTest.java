package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Product(null, 0));
    }

    @Test
    void constructor_blank_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Product("", 0));
        assertThrows(IllegalArgumentException.class, () -> new Product("   ", 0));
    }

    @Test
    void constructor_validProduct_success() {
        Product product = new Product("Laptop", 0);
        assertEquals("Laptop", product.getName());
    }

    @Test
    void equals_sameValue_returnsTrue() {
        Product a = new Product("Phone", 0);
        Product b = new Product("Phone", 0);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void equals_differentValue_returnsFalse() {
        Product a = new Product("Phone", 0);
        Product b = new Product("Tablet", 0);
        assertNotEquals(a, b);
    }

    @Test
    void toString_returnsValue() {
        Product product = new Product("Headphones", 4.00);
        assertEquals("Headphones, $4.00", product.toString());
    }
}
