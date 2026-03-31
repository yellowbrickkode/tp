//@@author Achiack
package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class OrderDateTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new OrderDateTime(null));
    }

    @Test
    void toString_returnsCorrectValue() {
        //@@author
        LocalDateTime datetime = LocalDateTime.of(2026, 3, 11, 23, 59);
        OrderDateTime orderDateTime = new OrderDateTime(datetime);
        assertEquals("2026-03-11T23:59", orderDateTime.toString());
        //@@author Achiack
    }

    @Test
    void equals_sameValue_returnsTrue() {
        //@@author
        LocalDateTime datetime = LocalDateTime.of(2026, 3, 11, 23, 59);
        OrderDateTime a = new OrderDateTime(datetime);
        OrderDateTime b = new OrderDateTime(datetime);
        //@@author Achiack
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void equals_differentValue_returnsFalse() {
        OrderDateTime a = new OrderDateTime(LocalDateTime.of(2026, 3, 11, 23, 59));
        OrderDateTime b = new OrderDateTime(LocalDateTime.of(2026, 3, 11, 23, 58));
        assertNotEquals(a, b);
    }
}
