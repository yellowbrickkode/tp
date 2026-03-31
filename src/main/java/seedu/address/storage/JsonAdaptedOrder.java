package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.OrderDateTime;
import seedu.address.model.order.OrderMap;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.person.Person;

/**
 * Jackson-friendly version of {@link OrderMap}.
 */
public class JsonAdaptedOrder {

    private final String orderId;
    private final String personName;
    private final String status;
    private final String orderDatetime;
    private final Map<Integer, Integer> orders;

    /**
     * Constructs a {@code JsonAdaptedOrder} with the given order details.
     */
    @JsonCreator
    public JsonAdaptedOrder(
            @JsonProperty("orderId") String orderId,
            @JsonProperty("personName") String personName,
            @JsonProperty("status") String status,
            @JsonProperty("orderDatetime") String orderDatetime,
            @JsonProperty("orders") Map<String, Integer> orders) {

        this.orderId = orderId;
        this.personName = personName;
        this.status = status;
        this.orderDatetime = orderDatetime;
        this.orders = new HashMap<>();
        if (orders != null) {
            for (Map.Entry<String, Integer> entry : orders.entrySet()) {
                this.orders.put(Integer.parseInt(entry.getKey()), entry.getValue());
            }
        }
    }

    /**
     * Converts a given {@code Order} into this class for Jackson use.
     */
    public JsonAdaptedOrder(OrderMap source) {
        this.orderId = Integer.toString(source.getOrderId());
        this.personName = source.getPerson().getName().toString();
        this.status = source.getStatus().name();
        this.orders = new HashMap<>(source.getOrderMap());
        this.orderDatetime = source.getOrderDatetime().toString();
    }

    public String getPersonName() {
        return personName;
    }

    /**
     * Converts this Jackson-friendly adapted order object into the model's {@code Order} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted order.
     */
    public OrderMap toModelType(Person person) throws IllegalValueException {
        LocalDateTime parsedDateTime;
        try {
            parsedDateTime = LocalDateTime.parse(orderDatetime);
        } catch (java.time.format.DateTimeParseException e) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            parsedDateTime = LocalDateTime.parse(orderDatetime, formatter);
        }
        return new OrderMap(
                Integer.parseInt(orderId),
                person,
                this.orders,
                OrderStatus.valueOf(status),
                new OrderDateTime(parsedDateTime));
    }
}
