package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
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
import seedu.address.testutil.TypicalPersons;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        var maybeData = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE, JsonSerializableAddressBook.class);
        assertTrue(maybeData.isPresent());
        JsonSerializableAddressBook dataFromFile = maybeData.get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalPersonsAddressBook = TypicalPersons.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        var maybeData = JsonUtil.readJsonFile(INVALID_PERSON_FILE, JsonSerializableAddressBook.class);
        assertTrue(maybeData.isPresent());
        JsonSerializableAddressBook dataFromFile = maybeData.get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        var maybeData = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE, JsonSerializableAddressBook.class);
        assertTrue(maybeData.isPresent());
        JsonSerializableAddressBook dataFromFile = maybeData.get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_nullPersonsAndOrders_success() throws Exception {
        JsonSerializableAddressBook jsonAb = new JsonSerializableAddressBook(null, null);

        assertEquals(new AddressBook(), jsonAb.toModelType());
    }

    @Test
    public void toModelType_withPersons_success() throws Exception {
        AddressBook ab = new AddressBook();

        Person person = new Person(
                new Name("Alice"),
                new Phone("98765432"),
                new Address("123456"),
                new Region("W"),
                new HashSet<>()
        );
        ab.addPerson(person);


        Set<ProductQuantityPair> orderItems = new HashSet<>();
        orderItems.add(new ProductQuantityPair("1 1"));

        OrderMap order = new OrderMap(
                1,
                person,
                orderItems,
                OrderStatus.PENDING,
                new OrderDateTime(LocalDateTime.parse("2026-03-10T10:15:30"))
        );
        ab.addOrder(order);

        JsonSerializableAddressBook jsonAb = new JsonSerializableAddressBook(ab);
        AddressBook converted = jsonAb.toModelType();

        assertEquals(1, converted.getPersonList().size());
        assertEquals("Alice", converted.getPersonList().get(0).getName().toString());
        assertEquals("98765432", converted.getPersonList().get(0).getPhone().toString());
        assertEquals("123456", converted.getPersonList().get(0).getAddress().toString());
        assertEquals("W", converted.getPersonList().get(0).getRegion().toString());

        assertEquals(1, converted.getOrderList().size());
        assertEquals(1, converted.getOrderList().get(0).getOrderId());
        assertEquals("Alice", converted.getOrderList().get(0).getPerson().getName().toString());
        assertEquals(orderItems, converted.getOrderList().get(0).getProductQuantityPairs());
        assertEquals("2026-03-10T10:15:30", converted.getOrderList().get(0).getOrderDatetime().toString());
    }

    @Test
    public void toModelType_orderMatchedByPhone_success() throws Exception {
        Person person = new Person(
                new Name("Alice"),
                new Phone("98765432"),
                new Address("123456"),
                new Region("W"),
                new HashSet<>());
        JsonAdaptedOrder adaptedOrder = new JsonAdaptedOrder(
                "1", "Wrong Name", "98765432", "PENDING", LocalDateTime.now().toString(), List.of("1 1"));

        JsonSerializableAddressBook jsonAb = new JsonSerializableAddressBook(
                List.of(new JsonAdaptedPerson(person)), List.of(adaptedOrder));

        AddressBook converted = jsonAb.toModelType();
        assertEquals(1, converted.getOrderList().size());
        assertEquals("Alice", converted.getOrderList().get(0).getPerson().getName().toString());
    }

    @Test
    public void toModelType_orderMatchedByNameWhenPhoneMissing_success() throws Exception {
        Person person = new Person(
                new Name("Alice"),
                new Phone("98765432"),
                new Address("123456"),
                new Region("W"),
                new HashSet<>());
        JsonAdaptedOrder adaptedOrder = new JsonAdaptedOrder(
                "1", "Alice", null, "PENDING", LocalDateTime.now().toString(), List.of("1 1"));

        JsonSerializableAddressBook jsonAb = new JsonSerializableAddressBook(
                List.of(new JsonAdaptedPerson(person)), List.of(adaptedOrder));

        AddressBook converted = jsonAb.toModelType();
        assertEquals(1, converted.getOrderList().size());
        assertEquals("Alice", converted.getOrderList().get(0).getPerson().getName().toString());
    }

    @Test
    public void toModelType_missingPersonForOrder_throwsIllegalValueException() {
        Person person = new Person(
                new Name("Alice"),
                new Phone("98765432"),
                new Address("123456"),
                new Region("W"),
                new HashSet<>());
        JsonAdaptedOrder adaptedOrder = new JsonAdaptedOrder(
                "1", "Unknown", "99999999", "PENDING", LocalDateTime.now().toString(), List.of("1 1"));

        JsonSerializableAddressBook jsonAb = new JsonSerializableAddressBook(
                List.of(new JsonAdaptedPerson(person)), List.of(adaptedOrder));

        assertThrows(IllegalValueException.class, "Person not found for order: Unknown", jsonAb::toModelType);
    }

    @Test
    public void toModelType_duplicateOrders_throwsIllegalValueException() {
        Person person = new Person(
                new Name("Alice"),
                new Phone("98765432"),
                new Address("123456"),
                new Region("W"),
                new HashSet<>());
        JsonAdaptedOrder order = new JsonAdaptedOrder(
                "1", "Alice", "98765432", "PENDING", LocalDateTime.now().toString(), List.of("1 1"));

        JsonSerializableAddressBook jsonAb = new JsonSerializableAddressBook(
                List.of(new JsonAdaptedPerson(person)), List.of(order, order));

        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_ORDER,
                jsonAb::toModelType);
    }

}
