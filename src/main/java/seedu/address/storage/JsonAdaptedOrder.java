package seedu.address.storage;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderDate;
import seedu.address.model.order.OrderId;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.order.Price;
import seedu.address.model.order.Product;
import seedu.address.model.order.Quantity;
import seedu.address.model.person.Person;

/**
 * Jackson-friendly version of {@link Order}.
 */
public class JsonAdaptedOrder {

    private final String orderId;
    private final String personName;
    private final String product;
    private final String quantity;
    private final String price;
    private final String status;
    private final String orderDate;

    /**
     * Constructs a {@code JsonAdaptedOrder} with the given order details.
     */
    @JsonCreator
    public JsonAdaptedOrder(
            @JsonProperty("orderId") String orderId,
            @JsonProperty("personName") String personName,
            @JsonProperty("product") String product,
            @JsonProperty("quantity") String quantity,
            @JsonProperty("price") String price,
            @JsonProperty("status") String status,
            @JsonProperty("orderDate") String orderDate) {

        this.orderId = orderId;
        this.personName = personName;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.orderDate = orderDate;
    }

    /**
     * Converts a given {@code Order} into this class for Jackson use.
     */
    public JsonAdaptedOrder(Order source) {
        orderId = source.getOrderId().toString();
        personName = source.getPerson().getName().toString();
        product = source.getProduct().toString();
        quantity = source.getQuantity().toString();
        price = source.getPrice().toString();
        status = source.getOrderStatus().toString();
        orderDate = source.getDate().toString();
    }

    public String getPersonName() {
        return personName;
    }

    /**
     * Converts this Jackson-friendly adapted order object into the model's {@code Order} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted order.
     */
    public Order toModelType(Person person) throws IllegalValueException {
        return new Order(
                new OrderId(orderId),
                person,
                new Product(product),
                new Quantity(quantity),
                new Price(price),
                OrderStatus.valueOf(status),
                new OrderDate(LocalDate.parse(orderDate))
        );
    }
}
