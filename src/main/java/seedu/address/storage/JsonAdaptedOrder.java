package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.OrderDateTime;
import seedu.address.model.order.OrderMap;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.order.ProductQuantityPair;
import seedu.address.model.person.Person;

/**
 * Jackson-friendly version of {@link OrderMap}.
 */
public class JsonAdaptedOrder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Order's %s field is missing!";

    private final String orderId;
    private final String personName;
    private final String personPhone;
    private final String status;
    private final String orderDatetime;
    private final List<String> itemList = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedOrder} with the given order details.
     */
    @JsonCreator
    public JsonAdaptedOrder(
            @JsonProperty("orderId") String orderId,
            @JsonProperty("personName") String personName,
            @JsonProperty("personPhone") String personPhone,
            @JsonProperty("status") String status,
            @JsonProperty("orderDatetime") String orderDatetime,
            @JsonProperty("itemList") List<String> itemList) {

        this.orderId = orderId;
        this.personName = personName;
        this.personPhone = personPhone;
        this.status = status;
        this.orderDatetime = orderDatetime;
        if (itemList != null) {
            this.itemList.addAll(itemList);
        }
    }

    /**
     * Converts a given {@code Order} into this class for Jackson use.
     */
    public JsonAdaptedOrder(OrderMap source) {
        this.orderId = Integer.toString(source.getOrderId());
        this.personName = source.getPerson().getName().toString();
        this.personPhone = source.getPerson().getPhone().toString();
        this.status = source.getStatus().name();
        this.itemList.addAll(source.getProductQuantityPairs().stream()
                .map(ProductQuantityPair::toString)
                .toList());
        this.orderDatetime = source.getOrderDatetime().toString();
    }

    public String getPersonName() {
        return personName;
    }

    public String getPersonPhone() {
        return personPhone;
    }

    /**
     * Converts this Jackson-friendly adapted order object into the model's {@code Order} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted order.
     */
    public OrderMap toModelType(Person person) throws IllegalValueException {
        if (orderId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "orderId"));
        }
        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "status"));
        }
        if (orderDatetime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "orderDatetime"));
        }
        if (person == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "person"));
        }

        final Set<ProductQuantityPair> itemList = new HashSet<>();
        for (String item : this.itemList) {
            try {
                ProductQuantityPair productQuantityPair = new ProductQuantityPair(item);
                itemList.add(productQuantityPair);
            } catch (IllegalArgumentException e) {
                throw new IllegalValueException(e.getMessage());
            }
        }

        final int parsedOrderId;
        try {
            parsedOrderId = Integer.parseInt(orderId);
        } catch (NumberFormatException e) {
            throw new IllegalValueException("Invalid orderId: " + orderId);
        }

        final OrderStatus parsedStatus;
        try {
            parsedStatus = OrderStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException("Invalid order status: " + status);
        }

        LocalDateTime parsedDateTime;
        try {
            parsedDateTime = LocalDateTime.parse(orderDatetime);
        } catch (java.time.format.DateTimeParseException e) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            try {
                parsedDateTime = LocalDateTime.parse(orderDatetime, formatter);
            } catch (java.time.format.DateTimeParseException ex) {
                throw new IllegalValueException("Invalid order datetime: " + orderDatetime);
            }
        }
        return new OrderMap(
                parsedOrderId,
                person,
                itemList,
                parsedStatus,
                new OrderDateTime(parsedDateTime));
    }
}
