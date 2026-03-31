package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.order.OrderMap;
import seedu.address.model.person.Person;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();
    //@@author Achiack
    /**
     * Returns an unmodifiable view of the orders list.
     * This list will not contain any duplicate orders.
     */
    ObservableList<OrderMap> getOrderList();

}
