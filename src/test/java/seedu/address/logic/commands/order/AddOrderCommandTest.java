package seedu.address.logic.commands.order;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.order.OrderMap;
import seedu.address.model.order.ProductQuantityPair;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddOrderCommandTest {

    @BeforeEach
    public void setUp() {
        OrderMap.cleanIdx();
    }

    @Test
    public void constructor_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddOrderCommand(1, null));
    }

    @Test
    public void execute_orderAcceptedByModel_addSuccessful() {
        ModelStubAcceptingOrderAdded modelStub = new ModelStubAcceptingOrderAdded();

        Set<ProductQuantityPair> order = new HashSet<>();
        order.add(new ProductQuantityPair("1 2"));

        CommandResult commandResult = new AddOrderCommand(1, order).execute(modelStub);

        OrderMap addedOrder = modelStub.ordersAdded.get(0);

        assertEquals(String.format(AddOrderCommand.MESSAGE_SUCCESS, Messages.format(addedOrder)),
                commandResult.getFeedbackToUser());
        assertEquals(1, modelStub.ordersAdded.size());
    }

    @Test
    public void equals() {
        Set<ProductQuantityPair> order1 = new HashSet<>();
        order1.add(new ProductQuantityPair("1 1"));

        Set<ProductQuantityPair> order2 = new HashSet<>();
        order2.add(new ProductQuantityPair("2 2"));

        AddOrderCommand addOrderCommand1 = new AddOrderCommand(1, order1);
        AddOrderCommand addOrderCommand2 = new AddOrderCommand(2, order2);

        // same object -> returns true
        assertEquals(addOrderCommand1, addOrderCommand1);

        // same values -> returns true
        AddOrderCommand addOrderCommandCopy = new AddOrderCommand(1, order1);
        assertEquals(addOrderCommand1, addOrderCommandCopy);

        // different types -> returns false
        assertNotEquals(addOrderCommand1, 1);

        // null -> returns false
        assertNotEquals(addOrderCommand1, null);

        // different order -> returns false
        assertNotEquals(addOrderCommand1, addOrderCommand2);
    }

    @Test
    public void mutabilityFlags_returnsTrue() {
        Set<ProductQuantityPair> order = new HashSet<>();
        order.add(new ProductQuantityPair("1 1"));

        AddOrderCommand addCommand = new AddOrderCommand(1, order);
        assertTrue(addCommand.shouldRecordInHistory());
        assertTrue(addCommand.mutatesModel());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private static class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError();
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError();
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError();
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError();
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError();
        }

        @Override
        public void setAddressBookFilePath(Path path) {
            throw new AssertionError();
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError();
        }

        @Override
        public void addOrder(OrderMap order) {
            throw new AssertionError();
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError();
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError();
        }

        @Override
        public boolean hasOrder(OrderMap order) {
            throw new AssertionError();
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError();
        }

        @Override
        public void deleteOrder(OrderMap target) {
            throw new AssertionError();
        }

        @Override
        public void deleteOrderByPredicate(Predicate<OrderMap> predicate) {
            throw new AssertionError();
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError();
        }

        @Override
        public void setOrder(OrderMap target, OrderMap editedOrder) {
            throw new AssertionError();
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError();
        }

        @Override
        public ObservableList<OrderMap> getFilteredOrderList() {
            throw new AssertionError();
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError();
        }

        @Override
        public void updateFilteredOrderList(Predicate<OrderMap> predicate) {
            throw new AssertionError();
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError();
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError();
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError();
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError();
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError();
        }

        @Override
        public seedu.address.model.VersionedAddressBook.Snapshot createAddressBookSnapshot() {
            throw new AssertionError();
        }

        @Override
        public void restoreAddressBookSnapshot(seedu.address.model.VersionedAddressBook.Snapshot snapshot) {
            throw new AssertionError();
        }
    }

    /**
     * A Model stub that always accepts the order being added.
     */
    private class ModelStubAcceptingOrderAdded extends ModelStub {
        final ArrayList<OrderMap> ordersAdded = new ArrayList<>();
        final Person person = new PersonBuilder().build();

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableArrayList(person);
        }

        @Override
        public void addOrder(OrderMap order) {
            requireNonNull(order);
            ordersAdded.add(order);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
