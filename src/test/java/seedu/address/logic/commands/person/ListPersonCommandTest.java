package seedu.address.logic.commands.person;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalOrders;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListPersonCommand.
 */
public class ListPersonCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListPersonCommand(), model,
                new CommandResult(ListPersonCommand.MESSAGE_SUCCESS, false, false, true, false), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListPersonCommand(), model,
                new CommandResult(ListPersonCommand.MESSAGE_SUCCESS, false, false, true, false), expectedModel);
    }

    @Test
    public void execute_listIsEmpty_showsNothing() {
        Model emptyModel = new ModelManager();
        assertCommandSuccess(new ListPersonCommand(), emptyModel,
                new CommandResult(ListPersonCommand.MESSAGE_NO_CONTACTS, false, false, true, false), emptyModel);
    }

    @Test
    public void execute_listIsFilteredToNone_showsEverything() {
        model.updateFilteredPersonList(person -> false);
        assertCommandSuccess(new ListPersonCommand(), model,
                new CommandResult(ListPersonCommand.MESSAGE_SUCCESS, false, false, true, false), expectedModel);
    }

    @Test
    public void execute_doesNotAffectOrderFilter() {
        Model modelWithOrders = new ModelManager(TypicalOrders.getTypicalAddressBook(), new UserPrefs());
        Model expectedModelWithOrders = new ModelManager(modelWithOrders.getAddressBook(), new UserPrefs());

        modelWithOrders.updateFilteredOrderList(order -> false);
        expectedModelWithOrders.updateFilteredOrderList(order -> false);

        assertCommandSuccess(new ListPersonCommand(), modelWithOrders,
                new CommandResult(ListPersonCommand.MESSAGE_SUCCESS, false, false, true, false),
                expectedModelWithOrders);
    }
}
