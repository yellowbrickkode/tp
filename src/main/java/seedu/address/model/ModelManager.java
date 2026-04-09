package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.order.OrderMap;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<OrderMap> filteredOrders;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.versionedAddressBook = new VersionedAddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.versionedAddressBook.getPersonList());
        filteredOrders = new FilteredList<>(this.versionedAddressBook.getOrderList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.versionedAddressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return this.versionedAddressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return this.versionedAddressBook.hasPerson(person);
    }

    @Override
    public boolean hasOrder(OrderMap order) {
        requireNonNull(order);
        return versionedAddressBook.hasOrder(order);
    }

    @Override
    public void deletePerson(Person target) {
        this.versionedAddressBook.removeOrdersForPerson(target);
        this.versionedAddressBook.removePerson(target);
    }

    //@@author Achiack
    @Override
    public void deleteOrder(OrderMap target) {
        versionedAddressBook.removeOrder(target);
    }

    @Override
    public void deleteOrderByPredicate(Predicate<OrderMap> predicate) {
        versionedAddressBook.removeOrderByPredicate(predicate);
    }

    //@@author
    @Override
    public void addPerson(Person person) {
        this.versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addOrder(OrderMap order) {
        versionedAddressBook.addOrder(order);
        updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        this.versionedAddressBook.setPerson(target, editedPerson);
    }
    //@@author Achiack
    @Override
    public void setOrder(OrderMap target, OrderMap editedOrder) {
        requireAllNonNull(target, editedOrder);

        this.versionedAddressBook.setOrder(target, editedOrder);
    }

    //@@author
    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }
    //@@author Achiack
    @Override
    public ObservableList<OrderMap> getFilteredOrderList() {
        return filteredOrders;
    }
    //@@author
    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //@@author Achiack
    @Override
    public void updateFilteredOrderList(Predicate<OrderMap> predicate) {
        requireNonNull(predicate);
        filteredOrders.setPredicate(predicate);
    }
    //@@author

    //=========== Undo/Redo =================================================================================
    //@@author wangyida-reused
    //Reused from https://github.com/se-edu/addressbook-level4
    //with minor modifications

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
    }

    @Override
    public String getUndoCommandText() {
        return versionedAddressBook.getUndoCommandText();
    }

    @Override
    public String getRedoCommandText() {
        return versionedAddressBook.getRedoCommandText();
    }

    @Override
    public void commitAddressBook(String commandText) {
        versionedAddressBook.commit(commandText);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;

        boolean one = this.versionedAddressBook.equals(otherModelManager.versionedAddressBook);
        boolean two = userPrefs.equals(otherModelManager.userPrefs);
        boolean three = filteredPersons.equals(otherModelManager.filteredPersons);
        boolean four = filteredOrders.equals(otherModelManager.filteredOrders);

        return this.versionedAddressBook.equals(otherModelManager.versionedAddressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredOrders.equals(otherModelManager.filteredOrders);
    }

}
