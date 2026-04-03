package seedu.address.model.order;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that an {@code OrderMap}'s person's phone matches the specified phone number.
 */
public class PhoneNumberPredicate implements Predicate<OrderMap> {
    private final String phoneNum;

    public PhoneNumberPredicate(String phoneNum) {
        requireNonNull(phoneNum);
        this.phoneNum = phoneNum;
    }

    @Override
    public boolean test(OrderMap order) {
        return order.getPerson().getPhone().toString().equals(phoneNum);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PhoneNumberPredicate)) {
            return false;
        }

        PhoneNumberPredicate otherPredicate = (PhoneNumberPredicate) other;
        return phoneNum.equals(otherPredicate.phoneNum);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("phoneNum", phoneNum).toString();
    }
}
