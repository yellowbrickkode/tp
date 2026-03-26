package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.order.OrderMap;
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
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalPersonsAddressBook = TypicalPersons.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
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

        OrderMap order = new OrderMap(
                person,
                (Map<Integer, Integer>) new HashMap<>().put(1, 1)
        );
        ab.addOrder(order);

        JsonSerializableAddressBook jsonAb = new JsonSerializableAddressBook(ab);
        AddressBook converted = jsonAb.toModelType();

        assertEquals(1, converted.getPersonList().size());
        assertEquals("Alice", converted.getPersonList().get(0).getName().toString());
        assertEquals("98765432", converted.getPersonList().get(0).getPhone().toString());
        assertEquals("123456", converted.getPersonList().get(0).getAddress().toString());
        assertEquals("W", converted.getPersonList().get(0).getRegion().toString());

        assertEquals("Alice", converted.getOrderList().get(0).getPerson().getName().toString());
        assertEquals((Map<Integer, Integer>) new HashMap<>().put(1, 1),
                converted.getOrderList().get(0).getOrderMap());
    }

}
