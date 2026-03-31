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
    public static final String MESSAGE_CONSTRAINTS =
            "Orders should be in the form \"MENU_ITEM PRODUCT_QUANTITY\".";
    public static final String VALIDATION_REGEX = "^\\d+ \\d+$";

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
     * Creates a new OrderMap.
     * @param orderId The order ID
     * @param person The customer
     * @param orderMap The items ordered
     * @param status The status of the order
     * @param orderDatetime The timestamp
     */
    public OrderMap(int orderId, Person person, Map<Integer, Integer> orderMap,
                    OrderStatus status, OrderDateTime orderDatetime) {
        this.orderId = orderId;
        this.person = person;
        this.orderMap = orderMap;
        this.status = status;
        this.orderDatetime = orderDatetime;
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
     * Returns the id of the next order to be created.
     */
    public static int getNextId() {
        return idx;
    }

    /**
     * Resets the static order id counter (test helper).
     */
    public static void cleanIdx() {
        idx = 1;
    }

    /**
     * Returns true if both orders have the same order ID.
     * This defines a weaker notion of equality between two orders.
     */
    public boolean isSameOrder(OrderMap otherOrder) {
        if (otherOrder == this) {
            return true;
        }

        return otherOrder != null
                && otherOrder.getOrderId() == getOrderId();
    }

    /**
     * Sets the OrderStatus of an OrderMap as Completed.
     */
    public OrderMap markAsCompleted() {
        if (status == OrderStatus.COMPLETED) {
            throw new IllegalStateException("Order is already completed");
        }
        if (status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Cannot complete a cancelled order");
        }

        return new OrderMap(
                orderId,
                person,
                orderMap,
                OrderStatus.COMPLETED,
                orderDatetime
        );
    }

    /**
     * Returns true if a given string is a valid product + quantity pair.
     */
    public static boolean isValidProductQuantityPair(String test) {
        return test.matches(VALIDATION_REGEX);
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
