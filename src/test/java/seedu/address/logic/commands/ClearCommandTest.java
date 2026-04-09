package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalOrders;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBookWithOrders_success() {
        Model model = new ModelManager(TypicalOrders.getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(TypicalOrders.getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_filteredListsToNone_success() {
        Model model = new ModelManager(TypicalOrders.getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(TypicalOrders.getTypicalAddressBook(), new UserPrefs());

        model.updateFilteredPersonList(person -> false);
        model.updateFilteredOrderList(order -> false);
        expectedModel.updateFilteredPersonList(person -> false);
        expectedModel.updateFilteredOrderList(order -> false);
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void mutabilityFlags_returnsTrue() {
        ClearCommand clearCommand = new ClearCommand();
        assertTrue(clearCommand.shouldRecordInHistory());
        assertTrue(clearCommand.mutatesModel());
    }

}
