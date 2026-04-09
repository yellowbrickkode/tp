package seedu.address.model;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class VersionedAddressBookTest {

    @Test
    public void getUndoCommandText_noUndo_throwsNoUndoableStateException() {
        VersionedAddressBook addressBook = new VersionedAddressBook(new AddressBook());
        assertThrows(VersionedAddressBook.NoUndoableStateException.class, addressBook::getUndoCommandText);
    }

    @Test
    public void getRedoCommandText_noRedo_throwsNoRedoableStateException() {
        VersionedAddressBook addressBook = new VersionedAddressBook(new AddressBook());
        assertThrows(VersionedAddressBook.NoRedoableStateException.class, addressBook::getRedoCommandText);
    }
}
