package seedu.address.model.order;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Represents an order made by a customer in the system.
 */
public class OrderMap {
    private static int idx = 1;
    private final int orderId;
    private final Person person;
    private final Map<Integer, Integer> orderMap;
    private final OrderStatus status;
    private final OrderDateTime orderDatetime;

    /**
     * Constructs a new {@code OrderMap} for the given customer with the specified order items.
     *
     * @param person The customer who placed the order.
     * @param orderMap A mapping of menu item IDs to quantities representing the order.
     */
    public OrderMap(Person person, Map<Integer, Integer> orderMap) {
        this.orderId = idx;
        this.person = person;
        this.orderMap = orderMap;
        idx++;
        this.status = OrderStatus.PENDING;
        this.orderDatetime = new OrderDateTime(LocalDateTime.now());
    }

    /**
     * Returns the {@link Person} associated with this order.
     */
    public Person getPerson() {
        return person;
    }

    /** Returns the unique OrderId of this order. */
    public int getOrderId() {
        return orderId;
    }

    /** Returns the mapping of menu item IDs to quantities for this order. */
    public Map<Integer, Integer> getOrderMap() {
        return orderMap;
    }

    /** Returns the current status of this order. */
    public OrderStatus getStatus() {
        return status;
    }

    /** Returns the timestamp when the order was created. */
    public OrderDateTime getOrderDatetime() {
        return orderDatetime;
    }

    /**
     * Checks whether this order is the same as another order based on the unique order ID.
     *
     * @param otherOrder The other {@code OrderMap} to compare with.
     * @return True if both orders have the same order ID, false otherwise.
     */
    public boolean isSameOrder(OrderMap otherOrder) {
        if (otherOrder == this) {
            return true;
        }

        return otherOrder != null
                && otherOrder.getOrderId() == getOrderId();
    }

    /**
     * Returns true if both orders have the same identity and data fields.
     * This defines a stronger notion of equality between two orders.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof OrderMap)) {
            return false;
        }

        OrderMap otherOrder = (OrderMap) other;
        return orderId == otherOrder.getOrderId()
                && orderMap.equals(otherOrder.getOrderMap())
                && person.equals(otherOrder.getPerson())
                && orderDatetime.equals(otherOrder.getOrderDatetime())
                && status.equals(otherOrder.getStatus());
    }

    /**
     * Returns an integer hash code for this order based on order ID, customer, and order items.
     */
    @Override
    public int hashCode() {
        return Objects.hash(orderId, person, orderMap);
    }

    /**
     * Returns a string representation of this order.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("orderId", orderId)
                .add("person", person)
                .add("status", status)
                .add("orderDatetime", orderDatetime)
                .add("orderMap", orderMap.toString())
                .toString();
    }
}
