//@@author Achiack
package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class OrderMapTest {

    private final Person person = new PersonBuilder().build();
    private final Map<Integer, Integer> ordermap = new HashMap<>();

    public OrderMapTest() {
        ordermap.put(1, 2);
    }

    @Test
    void constructor_andGetters_success() {
        OrderMap order = new OrderMap(person, ordermap);
        assertEquals(person, order.getPerson());
        assertEquals(ordermap, order.getOrderMap());
    }

    @Test
    void isSameOrder_sameObject_returnsTrue() {
        OrderMap order = new OrderMap(person, ordermap);
        assertTrue(order.isSameOrder(order));
    }

    @Test
    void isSameOrder_differentId_returnsFalse() {
        OrderMap order1 = new OrderMap(person, ordermap);
        OrderMap order2 = new OrderMap(person, ordermap);
        assertFalse(order1.isSameOrder(order2));
    }

    @Test
    void isSameOrder_null_returnsFalse() {
        OrderMap order = new OrderMap(person, ordermap);
        assertFalse(order.isSameOrder(null));
    }

    @Test
    void equals_sameValue_returnsTrue() {
        //@@author
        OrderMap order = new OrderMap(person, ordermap);
        assertEquals(order, order);
        assertEquals(order.hashCode(), order.hashCode());
        LocalDateTime now = LocalDateTime.now();
        OrderMap order1 = new OrderMap(1, person, ordermap, OrderStatus.PENDING, new OrderDateTime(now));
        OrderMap order2 = new OrderMap(1, person, ordermap, OrderStatus.PENDING, new OrderDateTime(now));
        //@@author Achiack
        assertEquals(order1, order2);
        assertEquals(order1.hashCode(), order2.hashCode());
    }

    @Test
    void equals_differentValue_returnsFalse() {
        //@@author
        Map<Integer, Integer> otherMap = new HashMap<>();
        otherMap.put(2, 1);
        OrderMap order1 = new OrderMap(person, ordermap);
        OrderMap order2 = new OrderMap(person, otherMap);
        //@@author Achiack
        assertNotEquals(order1, order2);
    }

    @Test
    void equals_otherObject_returnsFalse() {
        //@@author
        OrderMap order = new OrderMap(person, ordermap);
        assertNotEquals(null, order);
        assertNotEquals("not an order", order);
    }
    //@@author Achiack
    @Test
    void toString_containsAllFields() {
        OrderMap order = new OrderMap(person, ordermap);
        String str = order.toString();
        assertTrue(str.contains("orderId"));
        assertTrue(str.contains("person"));
        assertTrue(str.contains("status"));
        assertTrue(str.contains("orderDatetime"));
    }
}
