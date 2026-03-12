package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class OrderIdTest {

    @Test
    void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new OrderId(null));
    }

    @Test
    void constructor_invalidOrderId_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new OrderId(""));
        assertThrows(IllegalArgumentException.class, () -> new OrderId("abc"));
        assertThrows(IllegalArgumentException.class, () -> new OrderId("-1"));
    }

    @Test
    void constructor_validOrderId_success() {
        OrderId id = new OrderId("123");
        assertEquals("123", id.value);
    }

    @Test
    void isValidOrderId() {
        assertTrue(OrderId.isValidOrderId("1"));
        assertTrue(OrderId.isValidOrderId("123456"));
        assertFalse(OrderId.isValidOrderId(""));
        assertFalse(OrderId.isValidOrderId("abc"));
        assertFalse(OrderId.isValidOrderId("-1"));
        assertFalse(OrderId.isValidOrderId("12a3"));
    }

    @Test
    void equals_sameValue_returnsTrue() {
        OrderId a = new OrderId("42");
        OrderId b = new OrderId("42");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void equals_differentValue_returnsFalse() {
        OrderId a = new OrderId("42");
        OrderId b = new OrderId("43");
        assertNotEquals(a, b);
    }

    @Test
    void equals_otherObject_returnsFalse() {
        OrderId a = new OrderId("42");
        assertNotEquals(a, null);
        assertNotEquals(a, "42");
    }

    @Test
    void toString_returnsValue() {
        OrderId id = new OrderId("99");
        assertEquals("99", id.toString());
    }
}
