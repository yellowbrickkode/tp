package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PriceTest {

    @Test
    void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Price(null));
    }

    @Test
    void constructor_invalidPrice_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Price(""));
        assertThrows(IllegalArgumentException.class, () -> new Price("abc"));
        assertThrows(IllegalArgumentException.class, () -> new Price("-1.23"));
    }

    @Test
    void constructor_validPrice_success() {
        Price price = new Price("12.50");
        assertEquals(12.50, price.value);
    }

    @Test
    void isValidPrice() {
        assertTrue(Price.isValidPrice("0"));
        assertTrue(Price.isValidPrice("0.0"));
        assertTrue(Price.isValidPrice("123.45"));
        assertFalse(Price.isValidPrice("-0.01"));
        assertFalse(Price.isValidPrice("abc"));
        assertFalse(Price.isValidPrice(""));
    }

    @Test
    void equals_sameValue_returnsTrue() {
        Price a = new Price("10.0");
        Price b = new Price("10.0");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void equals_differentValue_returnsFalse() {
        Price a = new Price("10.0");
        Price b = new Price("15.0");
        assertNotEquals(a, b);
    }

    @Test
    void equals_otherObject_returnsFalse() {
        Price a = new Price("10.0");
        assertNotEquals(a, null);
        assertNotEquals(a, "10.0");
    }

    @Test
    void toString_returnsFormattedValue() {
        Price price = new Price("12.5");
        assertEquals("12.50", price.toString());

        Price price2 = new Price("3");
        assertEquals("3.00", price2.toString());
    }
}
