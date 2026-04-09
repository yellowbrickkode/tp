package seedu.address;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.OrderMap;
import seedu.address.model.order.ProductQuantityPair;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.Storage;

public class MainAppTest {

    @Test
    public void initModelManager_noAddressBook_usesSampleDataAndSaves() throws Exception {
        MainApp mainApp = new MainApp();
        StorageStub storage = StorageStub.withNoData();
        ReadOnlyUserPrefs userPrefs = new UserPrefs();

        Model model = invokeInitModelManager(mainApp, storage, userPrefs);

        ReadOnlyAddressBook expectedSample = SampleDataUtil.getSampleAddressBook();
        ReadOnlyAddressBook actual = model.getAddressBook();
        assertEquals(expectedSample.getPersonList(), actual.getPersonList());
        assertEquals(buildOrderSignatures(expectedSample), buildOrderSignatures(actual));
        assertEquals(buildOrderSignatures(expectedSample), buildOrderSignatures(storage.savedAddressBook));
    }

    @Test
    public void initModelManager_dataLoadingException_usesEmptyAndDoesNotSave() throws Exception {
        MainApp mainApp = new MainApp();
        StorageStub storage = StorageStub.withDataLoadingException();
        ReadOnlyUserPrefs userPrefs = new UserPrefs();

        Model model = invokeInitModelManager(mainApp, storage, userPrefs);

        assertEquals(new AddressBook(), new AddressBook(model.getAddressBook()));
        assertNull(storage.savedAddressBook);
    }

    private Model invokeInitModelManager(MainApp mainApp, Storage storage, ReadOnlyUserPrefs userPrefs)
            throws Exception {
        Method method = MainApp.class.getDeclaredMethod("initModelManager", Storage.class, ReadOnlyUserPrefs.class);
        method.setAccessible(true);
        return (Model) method.invoke(mainApp, storage, userPrefs);
    }

    private Set<OrderSignature> buildOrderSignatures(ReadOnlyAddressBook addressBook) {
        Set<OrderSignature> signatures = new HashSet<>();
        for (OrderMap order : addressBook.getOrderList()) {
            signatures.add(new OrderSignature(order.getPerson(), order.getProductQuantityPairs()));
        }
        return signatures;
    }

    private static class OrderSignature {
        private final seedu.address.model.person.Person person;
        private final Set<ProductQuantityPair> items;

        private OrderSignature(seedu.address.model.person.Person person, Set<ProductQuantityPair> items) {
            this.person = person;
            this.items = new HashSet<>(items);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof OrderSignature)) {
                return false;
            }
            OrderSignature otherSignature = (OrderSignature) other;
            return person.equals(otherSignature.person)
                    && items.equals(otherSignature.items);
        }

        @Override
        public int hashCode() {
            return java.util.Objects.hash(person, items);
        }
    }

    private static class StorageStub implements Storage {
        private static final Path DEFAULT_PATH = Paths.get("data", "addressbook.json");

        private final ReadOnlyAddressBook addressBook;
        private final boolean hasAddressBookData;
        private DataLoadingException exceptionToThrow;
        private ReadOnlyAddressBook savedAddressBook;

        private StorageStub(ReadOnlyAddressBook addressBook, boolean hasAddressBookData) {
            this.addressBook = addressBook;
            this.hasAddressBookData = hasAddressBookData;
        }

        private static StorageStub withNoData() {
            return new StorageStub(null, false);
        }

        private static StorageStub withDataLoadingException() {
            StorageStub stub = new StorageStub(null, false);
            stub.exceptionToThrow = new DataLoadingException(new Exception("unreadable data"));
            return stub;
        }

        @Override
        public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
            if (exceptionToThrow != null) {
                throw exceptionToThrow;
            }
            if (!hasAddressBookData) {
                return Optional.empty();
            }
            return Optional.of(addressBook);
        }

        @Override
        public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
            return readAddressBook();
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook) {
            savedAddressBook = addressBook;
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) {
            savedAddressBook = addressBook;
        }

        @Override
        public Path getAddressBookFilePath() {
            return DEFAULT_PATH;
        }

        @Override
        public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
            throw new AssertionError("Not used in these tests");
        }

        @Override
        public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
            throw new AssertionError("Not used in these tests");
        }

        @Override
        public Path getUserPrefsFilePath() {
            return DEFAULT_PATH;
        }
    }
}
