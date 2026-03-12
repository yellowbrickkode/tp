package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class QuantityTest {

    @Test
    void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Quantity(null));
    }

    @Test
    void constructor_invalidQuantity_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Quantity(""));
        assertThrows(IllegalArgumentException.class, () -> new Quantity("abc"));
        assertThrows(IllegalArgumentException.class, () -> new Quantity("0"));
        assertThrows(IllegalArgumentException.class, () -> new Quantity("-5"));
    }

    @Test
    void constructor_validQuantity_success() {
        Quantity quantity = new Quantity("5");
        assertEquals(5, quantity.value);
    }

    @Test
    void isValidQuantity() {
        assertTrue(Quantity.isValidQuantity("1"));
        assertTrue(Quantity.isValidQuantity("100"));
        assertFalse(Quantity.isValidQuantity("0"));
        assertFalse(Quantity.isValidQuantity("-1"));
        assertFalse(Quantity.isValidQuantity("abc"));
        assertFalse(Quantity.isValidQuantity("1.5"));
        assertFalse(Quantity.isValidQuantity(""));
    }

    @Test
    void equals_sameValue_returnsTrue() {
        Quantity a = new Quantity("10");
        Quantity b = new Quantity("10");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void equals_differentValue_returnsFalse() {
        Quantity a = new Quantity("10");
        Quantity b = new Quantity("20");
        assertNotEquals(a, b);
    }

    @Test
    void toString_returnsValue() {
        Quantity quantity = new Quantity("7");
        assertEquals("7", quantity.toString());
    }
}
