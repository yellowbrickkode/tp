package seedu.address.storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.OrderMap;
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
            @JsonProperty("orders") List<String> orders) {

        this.orderId = orderId;
        this.personName = personName;
        this.status = status;
        this.orderDatetime = orderDatetime;
        this.orders = new HashMap<>();
        for (String order : orders) {
            String[] parts = order.split(" ");
            this.orders.put(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        }
    }

    /**
     * Converts a given {@code Order} into this class for Jackson use.
     */
    public JsonAdaptedOrder(OrderMap source) {
        this.orderId = Integer.toString(source.getOrderId());
        this.personName = source.getPerson().getName().toString();
        this.status = source.getStatus().toString();
        this.orders = source.getOrderMap();
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
        return new OrderMap(person, this.orders);
    }
}
