package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Product(null));
    }

    @Test
    void constructor_blank_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Product(""));
        assertThrows(IllegalArgumentException.class, () -> new Product("   "));
    }

    @Test
    void constructor_validProduct_success() {
        Product product = new Product("Laptop");
        assertEquals("Laptop", product.value);
    }

    @Test
    void equals_sameValue_returnsTrue() {
        Product a = new Product("Phone");
        Product b = new Product("Phone");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void equals_differentValue_returnsFalse() {
        Product a = new Product("Phone");
        Product b = new Product("Tablet");
        assertNotEquals(a, b);
    }

    @Test
    void toString_returnsValue() {
        Product product = new Product("Headphones");
        assertEquals("Headphones", product.toString());
    }
}
