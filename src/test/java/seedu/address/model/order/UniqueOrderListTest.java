package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class UniqueOrderListTest {

    private UniqueOrderList uniqueOrderList;
    private Order order1;
    private Order order2;

    @Test
    public void addOrder_orderAdded() {
        ModelManager model = new ModelManager();
        Person person = new PersonBuilder().build();

        Order order = new Order(
                new OrderId("1"),
                person,
                new Product("Laptop"),
                new Quantity("2"),
                new Price("1500"),
                OrderStatus.PENDING,
                new OrderDate(LocalDate.parse("2026-03-10"))
        );

        model.addOrder(order);

        assertTrue(model.hasOrder(order));
    }

    @BeforeEach
    void setUp() {
        uniqueOrderList = new UniqueOrderList();
        Person person1 = new PersonBuilder().build();
        Person person2 = new PersonBuilder().build();
        order1 = new Order(new OrderId("1"), person1, new Product("Laptop"),
                new Quantity("2"), new Price("1500"), OrderStatus.PENDING, new OrderDate(java.time.LocalDate.now()));
        order2 = new Order(new OrderId("2"), person2, new Product("Phone"),
                new Quantity("1"), new Price("500"), OrderStatus.PENDING, new OrderDate(java.time.LocalDate.now()));
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
    void add_duplicateOrder_throwsDuplicatePersonException() {
        uniqueOrderList.add(order1);
        Order duplicate = new Order(new OrderId("1"), order1.getPerson(), order1.getProduct(),
                order1.getQuantity(), order1.getPrice(), order1.getOrderStatus(), order1.getDate());
        assertThrows(DuplicatePersonException.class, () -> uniqueOrderList.add(duplicate));
    }

    @Test
    void add_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueOrderList.add(null));
    }

    @Test
    void setOrder_editDuplicate_throwsDuplicatePersonException() {
        uniqueOrderList.add(order1);
        uniqueOrderList.add(order2);
        Order edited = new Order(order1.getOrderId(), order2.getPerson(), order2.getProduct(),
                order2.getQuantity(), order2.getPrice(), order2.getOrderStatus(), order2.getDate());
        assertThrows(DuplicatePersonException.class, () -> uniqueOrderList.setOrder(order2, edited));
    }

    @Test
    void remove_existingOrder_success() {
        uniqueOrderList.add(order1);
        uniqueOrderList.remove(order1);
        assertFalse(uniqueOrderList.contains(order1));
    }

    @Test
    void remove_orderNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueOrderList.remove(order1));
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
    void setOrders_listWithDuplicates_throwsDuplicatePersonException() {
        List<Order> orders = Arrays.asList(order1, order1);
        assertThrows(DuplicatePersonException.class, () -> uniqueOrderList.setOrders(orders));
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
