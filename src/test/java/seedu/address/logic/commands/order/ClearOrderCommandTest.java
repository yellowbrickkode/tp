package seedu.address.logic.commands.order;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalOrders.getTypicalAddressBook;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearOrderCommandTest {

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        AddressBook clearedAddressBook = new AddressBook(model.getAddressBook());
        clearedAddressBook.setOrders(Collections.emptyList());
        expectedModel.setAddressBook(clearedAddressBook);

        assertCommandSuccess(new ClearOrderCommand(), model,
                new CommandResult(ClearOrderCommand.MESSAGE_SUCCESS, false, false, false, false), expectedModel);
    }

    @Test
    public void execute_emptyOrderList_success() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs());

        assertCommandSuccess(new ClearOrderCommand(), model,
                new CommandResult(ClearOrderCommand.MESSAGE_SUCCESS, false, false, false, false), expectedModel);
    }

    @Test
    public void mutabilityFlags_returnsTrue() {
        ClearOrderCommand clearOrderCommand = new ClearOrderCommand();
        assertTrue(clearOrderCommand.shouldRecordInHistory());
        assertTrue(clearOrderCommand.mutatesModel());
    }
}

