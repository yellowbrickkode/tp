package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class OrderDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new OrderDate(null));
    }

    @Test
    void toString_returnsCorrectValue() {
        LocalDate date = LocalDate.of(2026, 3, 11);
        OrderDate orderDate = new OrderDate(date);
        assertEquals("2026-03-11", orderDate.toString());
    }

    @Test
    void equals_sameValue_returnsTrue() {
        LocalDate date = LocalDate.of(2026, 3, 11);
        OrderDate a = new OrderDate(date);
        OrderDate b = new OrderDate(date);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void equals_differentValue_returnsFalse() {
        OrderDate a = new OrderDate(LocalDate.of(2026, 3, 11));
        OrderDate b = new OrderDate(LocalDate.of(2026, 3, 12));
        assertNotEquals(a, b);
    }
}
