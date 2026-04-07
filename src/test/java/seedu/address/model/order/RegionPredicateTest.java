package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.person.Region;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.PersonBuilder;

public class RegionPredicateTest {

    @Test
    public void test_regionMatchesPendingOrder_returnsTrue() {
        RegionPredicate predicate = new RegionPredicate(new Region("N"));
        Person person = new PersonBuilder().withRegion("N").build();
        OrderMap order = new OrderBuilder().withPerson(person).build();
        assertTrue(predicate.test(order));
    }

    @Test
    public void test_regionDoesNotMatch_returnsFalse() {
        RegionPredicate predicate = new RegionPredicate(new Region("N"));
        Person person = new PersonBuilder().withRegion("E").build();
        OrderMap order = new OrderBuilder().withPerson(person).build();
        assertFalse(predicate.test(order));
    }

    @Test
    public void test_completedOrder_returnsFalse() {
        RegionPredicate predicate = new RegionPredicate(new Region("N"));
        Person person = new PersonBuilder().withRegion("N").build();
        OrderMap completedOrder = new OrderBuilder().withPerson(person).build().markAsCompleted();
        assertFalse(predicate.test(completedOrder));
    }

    @Test
    public void equals_returnsTrueWhenSameRegion() {
        RegionPredicate first = new RegionPredicate(new Region("N"));
        RegionPredicate second = new RegionPredicate(new Region("N"));
        assertTrue(first.equals(second));
    }

    @Test
    public void equals_returnsFalseWhenDifferentRegion() {
        RegionPredicate first = new RegionPredicate(new Region("N"));
        RegionPredicate second = new RegionPredicate(new Region("E"));
        assertFalse(first.equals(second));
    }

    @Test
    public void equals_returnsFalseWhenDifferentType() {
        RegionPredicate predicate = new RegionPredicate(new Region("N"));
        assertFalse(predicate.equals(1));
    }

    @Test
    public void toString_returnsExpected() {
        RegionPredicate predicate = new RegionPredicate(new Region("N"));
        String expected = RegionPredicate.class.getCanonicalName() + "{region=" + new Region("N") + "}";
        assertTrue(predicate.toString().equals(expected));
    }
}
