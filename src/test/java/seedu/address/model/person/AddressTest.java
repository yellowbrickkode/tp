package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Address(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> Address.isValidAddress(null));

        // invalid addresses
        assertFalse(Address.isValidAddress("")); // empty string
        assertFalse(Address.isValidAddress(" ")); // spaces only
        assertFalse(Address.isValidAddress("12345")); // 5 digits
        assertFalse(Address.isValidAddress("1234567")); // 7 digits
        assertFalse(Address.isValidAddress("abcdef")); // letters

        // valid addresses
        assertTrue(Address.isValidAddress("123456")); // 6 digit postal code
        assertTrue(Address.isValidAddress("000000")); // all zeros
        assertTrue(Address.isValidAddress("999999")); // all nines
    }

    @Test
    public void equals() {
        Address address = new Address("123456");

        // same values -> returns true
        assertTrue(address.equals(new Address("123456")));

        // same object -> returns true
        assertTrue(address.equals(address));

        // null -> returns false
        assertFalse(address.equals(null));

        // different types -> returns false
        assertFalse(address.equals(5.0f));

        // different values -> returns false
        assertFalse(address.equals(new Address("654321")));
    }
}
