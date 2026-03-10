package seedu.address.model.order;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

import java.util.Objects;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Order {

    private final OrderId orderId;
    private final Person person;
    private final Product product;
    private final Quantity quantity;
    private final Price price;
    private final OrderStatus status;
    private final OrderDate orderDate;

    /**
     * Every field must be present and not null.
     */
    public Order(OrderId orderId, Person person, Product product, Quantity quantity, Price price, OrderStatus status, OrderDate orderDate) {
        requireAllNonNull(orderId, product, quantity, price, status, orderDate);
        this.orderId = orderId;
        this.person = person;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.orderDate = orderDate;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public Person getPerson() {
        return person;
    }

    public Product getProduct() {
        return product;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Price getPrice() {
        return price;
    }

    public OrderStatus getOrderStatus() {
        return status;
    }

    public OrderDate getDate() {
        return orderDate;
    }

    /**
     * Returns true if both orders have the same name.
     * This defines a weaker notion of equality between two orders.
     */
    public boolean isSameOrder(Order otherOrder) {
        if (otherOrder == this) {
            return true;
        }

        return otherOrder != null
                && otherOrder.getOrderId().equals(getOrderId());
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

        if (!(other instanceof Order)) {
            return false;
        }

        Order otherOrder = (Order) other;
        return orderId.equals(otherOrder.orderId)
                && product.equals(otherOrder.product)
                && quantity.equals(otherOrder.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, product, quantity);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("orderId", orderId)
                .add("person", person)
                .add("product", product)
                .add("quantity", quantity)
                .add("price", price)
                .add("status", status)
                .add("orderDate", orderDate)
                .toString();
    }
}