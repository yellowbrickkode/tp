package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.order.OrderDateTime;
import seedu.address.model.order.OrderMap;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.order.ProductQuantityPair;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Region;

public class JsonAdaptedOrderTest {

    private static final Person PERSON = new Person(
            new Name("Alice"),
            new Phone("98765432"),
            new Address("123456"),
            new Region("W"),
            new HashSet<>()
    );

    private static final Set<ProductQuantityPair> ITEMS = Set.of(new ProductQuantityPair("1 1"));

    private static final String ORDER_DATETIME = "2026-03-10T10:15:30";

    @Test
    public void saveAndLoadOrders() throws Exception {
        AddressBook ab = new AddressBook();

        ab.addPerson(PERSON);
        OrderMap order = new OrderMap(
                1,
                PERSON,
                ITEMS,
                OrderStatus.PENDING,
                new OrderDateTime(LocalDateTime.parse(ORDER_DATETIME))
        );
        ab.addOrder(order);

        JsonSerializableAddressBook json = new JsonSerializableAddressBook(ab);

        AddressBook loaded = json.toModelType();
        assertEquals(1, loaded.getOrderList().size());
    }

    @Test
    public void toModelType_nullItemList_success() throws Exception {
        JsonAdaptedOrder adaptedOrder = new JsonAdaptedOrder(
                "1", "Alice", "98765432", "PENDING", ORDER_DATETIME, null);

        OrderMap order = adaptedOrder.toModelType(PERSON);

        assertEquals(1, order.getOrderId());
        assertEquals(PERSON, order.getPerson());
        assertEquals(0, order.getProductQuantityPairs().size());
    }

    @Test
    public void toModelType_nullOrderId_throwsIllegalValueException() {
        JsonAdaptedOrder adaptedOrder = new JsonAdaptedOrder(
                null, "Alice", "98765432", "PENDING", ORDER_DATETIME, List.of("1 1"));

        IllegalValueException thrown = assertThrows(IllegalValueException.class, () ->
                adaptedOrder.toModelType(PERSON));
        assertEquals(String.format(JsonAdaptedOrder.MISSING_FIELD_MESSAGE_FORMAT, "orderId"), thrown.getMessage());
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        JsonAdaptedOrder adaptedOrder = new JsonAdaptedOrder(
                "1", "Alice", "98765432", null, ORDER_DATETIME, List.of("1 1"));

        IllegalValueException thrown = assertThrows(IllegalValueException.class, () ->
                adaptedOrder.toModelType(PERSON));
        assertEquals(String.format(JsonAdaptedOrder.MISSING_FIELD_MESSAGE_FORMAT, "status"), thrown.getMessage());
    }

    @Test
    public void toModelType_nullOrderDatetime_throwsIllegalValueException() {
        JsonAdaptedOrder adaptedOrder = new JsonAdaptedOrder(
                "1", "Alice", "98765432", "PENDING", null, List.of("1 1"));

        IllegalValueException thrown = assertThrows(IllegalValueException.class, () ->
                adaptedOrder.toModelType(PERSON));
        assertEquals(String.format(JsonAdaptedOrder.MISSING_FIELD_MESSAGE_FORMAT, "orderDatetime"),
                thrown.getMessage());
    }

    @Test
    public void toModelType_nullPerson_throwsIllegalValueException() {
        JsonAdaptedOrder adaptedOrder = new JsonAdaptedOrder(
                "1", "Alice", "98765432", "PENDING", ORDER_DATETIME, List.of("1 1"));

        IllegalValueException thrown = assertThrows(IllegalValueException.class, () ->
                adaptedOrder.toModelType(null));
        assertEquals(String.format(JsonAdaptedOrder.MISSING_FIELD_MESSAGE_FORMAT, "person"), thrown.getMessage());
    }

    @Test
    public void toModelType_invalidItemList_throwsIllegalValueException() {
        JsonAdaptedOrder adaptedOrder = new JsonAdaptedOrder(
                "1", "Alice", "98765432", "PENDING", ORDER_DATETIME, List.of("abc"));

        IllegalValueException thrown = assertThrows(IllegalValueException.class, () ->
                adaptedOrder.toModelType(PERSON));
        assertEquals(ProductQuantityPair.MESSAGE_CONSTRAINTS, thrown.getMessage());
    }

    @Test
    public void toModelType_invalidOrderId_throwsIllegalValueException() {
        JsonAdaptedOrder adaptedOrder = new JsonAdaptedOrder(
                "abc", "Alice", "98765432", "PENDING", ORDER_DATETIME, List.of("1 1"));

        IllegalValueException thrown = assertThrows(IllegalValueException.class, () ->
                adaptedOrder.toModelType(PERSON));
        assertEquals("Invalid orderId: abc", thrown.getMessage());
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedOrder adaptedOrder = new JsonAdaptedOrder(
                "1", "Alice", "98765432", "UNKNOWN", ORDER_DATETIME, List.of("1 1"));

        IllegalValueException thrown = assertThrows(IllegalValueException.class, () ->
                adaptedOrder.toModelType(PERSON));
        assertEquals("Invalid order status: UNKNOWN", thrown.getMessage());
    }

    @Test
    public void toModelType_invalidOrderDatetime_throwsIllegalValueException() {
        JsonAdaptedOrder adaptedOrder = new JsonAdaptedOrder(
                "1", "Alice", "98765432", "PENDING", "invalid-datetime", List.of("1 1"));

        IllegalValueException thrown = assertThrows(IllegalValueException.class, () ->
                adaptedOrder.toModelType(PERSON));
        assertEquals("Invalid order datetime: invalid-datetime", thrown.getMessage());
    }
}
