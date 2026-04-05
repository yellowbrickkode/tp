//@@author Achiack
package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.ModelManager;
import seedu.address.model.order.exceptions.DuplicateOrderException;
import seedu.address.model.order.exceptions.OrderNotFoundException;
import seedu.address.testutil.OrderBuilder;

public class UniqueOrderListTest {

    private UniqueOrderList uniqueOrderList;
    //@@author
    private OrderMap order1;
    private OrderMap order2;
    //@@author Achiack

    @Test
    public void addOrder_orderAdded() {
        ModelManager model = new ModelManager();
        //@@author
        OrderMap order = new OrderBuilder().build();
        //@@author Achiack

        model.addOrder(order);

        assertTrue(model.hasOrder(order));
    }

    @BeforeEach
    void setUp() {
        //@@author
        OrderMap.cleanIdx();

        //@@author Achiack
        uniqueOrderList = new UniqueOrderList();
        //@@author
        order1 = new OrderBuilder().build();
        order2 = new OrderBuilder().build();
        //@@author Achiack
    }

    @Test
    void contains_orderInList_returnsTrue() {
        uniqueOrderList.add(order1);
        assertTrue(uniqueOrderList.contains(order1));
    }

    @Test
    void contains_orderNotInList_returnsFalse() {
        assertFalse(uniqueOrderList.contains(order1));
    }

    @Test
    void contains_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueOrderList.contains(null));
    }

    @Test
    void add_order_success() {
        uniqueOrderList.add(order1);
        assertTrue(uniqueOrderList.contains(order1));
    }

    @Test
    void add_duplicateOrder_throwsDuplicateOrderException() {
        uniqueOrderList.add(order1);
        //@@author
        OrderMap duplicate = new OrderBuilder(order1).build();
        //@@author Achiack
        assertThrows(DuplicateOrderException.class, () -> uniqueOrderList.add(duplicate));
    }

    @Test
    void add_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueOrderList.add(null));
    }

    @Test
    void setOrder_editDuplicate_throwsDuplicateOrderException() {
        uniqueOrderList.add(order1);
        uniqueOrderList.add(order2);
        //@@author
        OrderMap edited = order1;

        //@@author Achiack
        assertThrows(DuplicateOrderException.class, () -> uniqueOrderList.setOrder(order2, edited));
    }

    @Test
    void remove_existingOrder_success() {
        uniqueOrderList.add(order1);
        uniqueOrderList.remove(order1);
        assertFalse(uniqueOrderList.contains(order1));
    }

    @Test
    void remove_orderNotInList_throwsOrderNotFoundException() {
        assertThrows(OrderNotFoundException.class, () -> uniqueOrderList.remove(order1));
    }

    @Test
    void remove_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueOrderList.remove(null));
    }

    @Test
    void setOrders_uniqueOrderList_success() {
        UniqueOrderList replacement = new UniqueOrderList();
        replacement.add(order1);
        uniqueOrderList.setOrders(replacement);
        assertTrue(uniqueOrderList.contains(order1));
    }

    @Test
    void setOrders_listWithDuplicates_throwsDuplicateOrderException() {
        List<OrderMap> orders = Arrays.asList(order1, order1);
        assertThrows(DuplicateOrderException.class, () -> uniqueOrderList.setOrders(orders));
    }

    @Test
    void asUnmodifiableObservableList_cannotModify() {
        uniqueOrderList.add(order1);
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueOrderList.asUnmodifiableObservableList().add(order2));
    }

    @Test
    void equals_sameList_returnsTrue() {
        uniqueOrderList.add(order1);
        UniqueOrderList copy = new UniqueOrderList();
        copy.add(order1);
        assertEquals(uniqueOrderList, copy);
    }

    @Test
    void equals_differentList_returnsFalse() {
        uniqueOrderList.add(order1);
        UniqueOrderList copy = new UniqueOrderList();
        copy.add(order2);
        assertNotEquals(uniqueOrderList, copy);
    }
}
