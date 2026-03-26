package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of orders that enforces uniqueness between its elements and does not allow nulls.
 * An order is considered unique by comparing using {@code Order#isSameOrder(Order)}. As such, adding and updating of
 * orders uses Order#isSameOrder(Order) for equality so as to ensure that the order being added or updated is
 * unique in terms of orderId in the UniqueOrderList. However, the removal of a order uses Order#equals(Object) so
 * as to ensure that the order with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see OrderMap#isSameOrder(OrderMap)
 */
public class UniqueOrderList implements Iterable<OrderMap> {
    private final ObservableList<OrderMap> internalList = FXCollections.observableArrayList();
    private final ObservableList<OrderMap> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(OrderMap toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameOrder);
    }

    /**
     * Adds an order to the list.
     * The order must not already exist in the list.
     */
    public void add(OrderMap toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the order {@code target} in the list with {@code editedOrder}.
     * {@code target} must exist in the list.
     * The orderId of {@code editedOrder} must not be the same as another existing order in the list.
     */
    public void setOrder(OrderMap target, OrderMap editedOrder) {
        requireAllNonNull(target, editedOrder);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSameOrder(editedOrder) && contains(editedOrder)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedOrder);
    }

    /**
     * Removes the equivalent order from the list.
     * The order must exist in the list.
     */
    public void remove(OrderMap toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setOrders(UniqueOrderList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code orders}.
     * {@code orders} must not contain duplicate persons.
     */
    public void setOrders(List<OrderMap> orders) {
        requireAllNonNull(orders);
        if (!ordersAreUnique(orders)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(orders);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<OrderMap> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<OrderMap> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueOrderList)) {
            return false;
        }

        UniqueOrderList otherUniqueOrderList = (UniqueOrderList) other;
        return internalList.equals(otherUniqueOrderList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code orders} contains only unique orders.
     */
    private boolean ordersAreUnique(List<OrderMap> orders) {
        for (int i = 0; i < orders.size() - 1; i++) {
            for (int j = i + 1; j < orders.size(); j++) {
                if (orders.get(i).isSameOrder(orders.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
