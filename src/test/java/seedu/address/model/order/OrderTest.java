package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class OrderTest {

    private final Person person = new PersonBuilder().build();
    private final OrderId orderId = new OrderId("1");
    private final Product product = new Product("Laptop");
    private final Quantity quantity = new Quantity("2");
    private final Price price = new Price("1500");
    private final OrderStatus status = OrderStatus.PENDING;
    private final OrderDate orderDate = new OrderDate(java.time.LocalDate.of(2026, 3, 11));

    @Test
    void constructor_andGetters_success() {
        Order order = new Order(orderId, person, product, quantity, price, status, orderDate);
        assertEquals(orderId, order.getOrderId());
        assertEquals(person, order.getPerson());
        assertEquals(product, order.getProduct());
        assertEquals(quantity, order.getQuantity());
        assertEquals(price, order.getPrice());
        assertEquals(status, order.getOrderStatus());
        assertEquals(orderDate, order.getDate());
    }

    @Test
    void isSameOrder_sameObject_returnsTrue() {
        Order order = new Order(orderId, person, product, quantity, price, status, orderDate);
        assertTrue(order.isSameOrder(order));
    }

    @Test
    void isSameOrder_differentId_returnsFalse() {
        Order order1 = new Order(orderId, person, product, quantity, price, status, orderDate);
        Order order2 = new Order(new OrderId("2"), person, product, quantity, price, status, orderDate);
        assertFalse(order1.isSameOrder(order2));
    }

    @Test
    void isSameOrder_null_returnsFalse() {
        Order order = new Order(orderId, person, product, quantity, price, status, orderDate);
        assertFalse(order.isSameOrder(null));
    }

    @Test
    void equals_sameValue_returnsTrue() {
        Order order1 = new Order(orderId, person, product, quantity, price, status, orderDate);
        Order order2 = new Order(orderId, person, product, quantity, price, status, orderDate);
        assertEquals(order1, order2);
        assertEquals(order1.hashCode(), order2.hashCode());
    }

    @Test
    void equals_differentValue_returnsFalse() {
        Order order1 = new Order(orderId, person, product, quantity, price, status, orderDate);
        Order order2 = new Order(new OrderId("2"), person, product, quantity, price, status, orderDate);
        assertNotEquals(order1, order2);
    }

    @Test
    void equals_otherObject_returnsFalse() {
        Order order = new Order(orderId, person, product, quantity, price, status, orderDate);
        assertNotEquals(order, null);
        assertNotEquals(order, "not an order");
    }

    @Test
    void toString_containsAllFields() {
        Order order = new Order(orderId, person, product, quantity, price, status, orderDate);
        String str = order.toString();
        assertTrue(str.contains("orderId"));
        assertTrue(str.contains("person"));
        assertTrue(str.contains("product"));
        assertTrue(str.contains("quantity"));
        assertTrue(str.contains("price"));
        assertTrue(str.contains("status"));
        assertTrue(str.contains("orderDate"));
    }
}
