package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.PersonBuilder;

public class PhoneNumberPredicateTest {

    @Test
    public void test_phoneMatchesPendingOrder_returnsTrue() {
        PhoneNumberPredicate predicate = new PhoneNumberPredicate("94351253");
        Person person = new PersonBuilder().withPhone("94351253").build();
        OrderMap order = new OrderBuilder().withPerson(person).build();
        assertTrue(predicate.test(order));
    }

    @Test
    public void test_phoneDoesNotMatch_returnsFalse() {
        PhoneNumberPredicate predicate = new PhoneNumberPredicate("94351253");
        Person person = new PersonBuilder().withPhone("98765432").build();
        OrderMap order = new OrderBuilder().withPerson(person).build();
        assertFalse(predicate.test(order));
    }

    @Test
    public void test_completedOrder_returnsFalse() {
        PhoneNumberPredicate predicate = new PhoneNumberPredicate("94351253");
        Person person = new PersonBuilder().withPhone("94351253").build();
        OrderMap completedOrder = new OrderBuilder().withPerson(person).build().markAsCompleted();
        assertFalse(predicate.test(completedOrder));
    }

    @Test
    public void equals_returnsTrueWhenSamePhone() {
        PhoneNumberPredicate first = new PhoneNumberPredicate("94351253");
        PhoneNumberPredicate second = new PhoneNumberPredicate("94351253");
        assertTrue(first.equals(second));
    }

    @Test
    public void equals_returnsFalseWhenDifferentPhone() {
        PhoneNumberPredicate first = new PhoneNumberPredicate("94351253");
        PhoneNumberPredicate second = new PhoneNumberPredicate("98765432");
        assertFalse(first.equals(second));
    }

    @Test
    public void equals_returnsFalseWhenDifferentType() {
        PhoneNumberPredicate predicate = new PhoneNumberPredicate("94351253");
        assertFalse(predicate.equals(1));
    }

    @Test
    public void toString_returnsExpected() {
        PhoneNumberPredicate predicate = new PhoneNumberPredicate("94351253");
        String expected = PhoneNumberPredicate.class.getCanonicalName() + "{phoneNum=94351253}";
        assertTrue(predicate.toString().equals(expected));
    }
}

