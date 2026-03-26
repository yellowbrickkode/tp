package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code UndoCommand}.
 */
public class UndoCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_noCommandToUndo_failure() {
        // No commits beyond the initial state – canUndoAddressBook() should return false
        assertCommandFailure(new UndoCommand(), model, UndoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_singleUndoableState_success() {
        // Simulate a modifying command by adding a person and committing
        Person person = new PersonBuilder().withName("Undo Test Person")
                .withPhone("91234567").withAddress("123456").build();

        model.addPerson(person);
        model.commitAddressBook();

        // Expected model should be in the state before the commit (no extra person)
        // expectedModel must mirror the exact same version history as model after the undo:
        // state list: [typicals, typicals+person], pointer at 0
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addPerson(person);
        expectedModel.commitAddressBook();
        expectedModel.undoAddressBook();

        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_multipleUndoableStates_success() {
        // Commit two modifying states
        Person firstPerson = new PersonBuilder().withName("First Undo Person")
                .withPhone("91111111").withAddress("111111").build();
        Person secondPerson = new PersonBuilder().withName("Second Undo Person")
                .withPhone("92222222").withAddress("222222").build();

        model.addPerson(firstPerson);
        model.commitAddressBook();
        model.addPerson(secondPerson);
        model.commitAddressBook();

        // Undo once – should go back to state with only firstPerson added
        // expectedModel must mirror the exact same version history as model after the undo:
        // state list: [typicals, typicals+first, typicals+first+second], pointer at 1
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addPerson(firstPerson);
        expectedModel.commitAddressBook();
        expectedModel.addPerson(secondPerson);
        expectedModel.commitAddressBook();
        expectedModel.undoAddressBook();

        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_undoAfterUndoingAll_failure() {
        // Commit one state, undo it manually, then the UndoCommand should fail
        Person person = new PersonBuilder().withName("Undo Test Person")
                .withPhone("91234567").withAddress("123456").build();

        model.addPerson(person);
        model.commitAddressBook();

        model.undoAddressBook(); // Manually undo to exhaust the history

        assertCommandFailure(new UndoCommand(), model, UndoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_undoAddPersonCommand_success() {
        // Execute AddPersonCommand, commit, then undo – person should be removed
        Person personToAdd = new PersonBuilder().withName("Hoon Meier")
                .withPhone("84824240").withAddress("500001").build();

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addPerson(personToAdd);
        expectedModel.commitAddressBook();
        expectedModel.undoAddressBook();

        model.addPerson(personToAdd);
        model.commitAddressBook();

        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_undoDeleteCommand_success() {
        // Execute DeleteCommand, commit, then undo – deleted person should be restored
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        expectedModel.commitAddressBook();
        expectedModel.undoAddressBook();

        model.deletePerson(personToDelete);
        model.commitAddressBook();

        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_undoEditCommand_success() {
        // Execute EditCommand, commit, then undo – person should revert to original
        Person original = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person edited = new PersonBuilder(original).withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPerson(original, edited);
        expectedModel.commitAddressBook();
        expectedModel.undoAddressBook();

        model.setPerson(original, edited);
        model.commitAddressBook();

        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_undoClearCommand_success() {
        // Execute ClearCommand, commit, then undo – address book should be restored
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());
        expectedModel.commitAddressBook();
        expectedModel.undoAddressBook();

        model.setAddressBook(new AddressBook());
        model.commitAddressBook();

        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_consecutiveUndos_success() {
        // Commit three states, then undo all the way back to the initial state
        Person firstPerson = new PersonBuilder().withName("Hoon Meier")
                .withPhone("84824240").withAddress("500001").build();
        Person secondPerson = new PersonBuilder().withName("Ida Mueller")
                .withPhone("84821310").withAddress("600001").build();

        model.addPerson(firstPerson);
        model.commitAddressBook();
        model.addPerson(secondPerson);
        model.commitAddressBook();

        // First undo – back to state with only firstPerson
        Model expectedAfterFirstUndo = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedAfterFirstUndo.addPerson(firstPerson);
        expectedAfterFirstUndo.commitAddressBook();
        expectedAfterFirstUndo.addPerson(secondPerson);
        expectedAfterFirstUndo.commitAddressBook();
        expectedAfterFirstUndo.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedAfterFirstUndo);

        // Second undo – back to initial state (typicals only)
        Model expectedAfterSecondUndo = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedAfterSecondUndo.addPerson(firstPerson);
        expectedAfterSecondUndo.commitAddressBook();
        expectedAfterSecondUndo.addPerson(secondPerson);
        expectedAfterSecondUndo.commitAddressBook();
        expectedAfterSecondUndo.undoAddressBook();
        expectedAfterSecondUndo.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS, expectedAfterSecondUndo);

        // Third undo should fail – no more history
        assertCommandFailure(new UndoCommand(), model, UndoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void mutabilityFlags_mutatesModelWithoutHistoryCommit() {
        UndoCommand undoCommand = new UndoCommand();
        assertFalse(undoCommand.shouldRecordInHistory());
        assertTrue(undoCommand.mutatesModel());
    }
}
