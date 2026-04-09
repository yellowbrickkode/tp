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
 * Contains integration tests (interaction with the Model) for {@code RedoCommand}.
 */
public class RedoCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_noCommandToRedo_failure() {
        // Nothing has been undone yet – canRedoAddressBook() should return false
        assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_singleRedoableState_success() {
        // Commit a state, then undo it so that it can be redone
        Person person = new PersonBuilder().withName("Redo Test Person")
                .withPhone("91234567").withAddress("123456").withRegion("N").build();
        String commandText = "addperson n/Redo Test Person p/91234567 a/123456 r/N";

        model.addPerson(person);
        model.commitAddressBook(commandText);

        // Expected model after redo should have the person re-added
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addPerson(person);
        expectedModel.commitAddressBook(commandText);

        model.undoAddressBook(); // Undo so there is something to redo

        String expectedMessage = String.format(RedoCommand.MESSAGE_SUCCESS, commandText);
        assertCommandSuccess(new RedoCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleRedoableStates_success() {
        // Commit two states, undo both, then redo once
        Person firstPerson = new PersonBuilder().withName("First Redo Person")
                .withPhone("91111111").withAddress("111111").withRegion("N").build();
        Person secondPerson = new PersonBuilder().withName("Second Redo Person")
                .withPhone("92222222").withAddress("222222").withRegion("N").build();
        String firstCommandText = "addperson n/First Redo Person p/91111111 a/111111 r/N";
        String secondCommandText = "addperson n/Second Redo Person p/92222222 a/222222 r/N";

        model.addPerson(firstPerson);
        model.commitAddressBook(firstCommandText);
        model.addPerson(secondPerson);
        model.commitAddressBook(secondCommandText);

        // Undo both commits
        model.undoAddressBook();
        model.undoAddressBook();

        // Redo once – should go to state with only firstPerson added
        // expectedModel must mirror the exact same version history as model after the redo:
        // state list: [typicals, typicals+first, typicals+first+second], pointer at 1
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addPerson(firstPerson);
        expectedModel.commitAddressBook(firstCommandText);
        expectedModel.addPerson(secondPerson);
        expectedModel.commitAddressBook(secondCommandText);
        expectedModel.undoAddressBook();
        expectedModel.undoAddressBook();
        expectedModel.redoAddressBook();

        String expectedMessage = String.format(RedoCommand.MESSAGE_SUCCESS, firstCommandText);
        assertCommandSuccess(new RedoCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_redoAfterNewCommit_failure() {
        // Undo history is cleared when a new commit is made, so redo should fail
        Person person = new PersonBuilder().withName("Redo Test Person")
                .withPhone("91234567").withAddress("123456").withRegion("N").build();
        String commandText = "addperson n/Redo Test Person p/91234567 a/123456 r/N";

        model.addPerson(person);
        model.commitAddressBook(commandText);
        model.undoAddressBook(); // Undo to create a redoable state

        // Now make a new commit, which should clear the redo history
        Person newPerson = new PersonBuilder().withName("New Person After Undo")
                .withPhone("93333333").withAddress("333333").withRegion("N").build();
        String newCommandText = "addperson n/New Person After Undo p/93333333 a/333333 r/N";
        model.addPerson(newPerson);
        model.commitAddressBook(newCommandText);

        // Redo should now fail since the redo history was cleared by the new commit
        assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_redoAddPersonCommand_success() {
        // Add a person, commit, undo, then redo – person should be re-added
        Person personToAdd = new PersonBuilder().withName("Hoon Meier")
                .withPhone("84824240").withAddress("500001").withRegion("N").build();
        String commandText = "addperson n/Hoon Meier p/84824240 a/500001 r/N";

        model.addPerson(personToAdd);
        model.commitAddressBook(commandText);
        model.undoAddressBook();

        // expectedModel mirrors: add → commit (pointer at 1 after redo)
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addPerson(personToAdd);
        expectedModel.commitAddressBook(commandText);

        String expectedMessage = String.format(RedoCommand.MESSAGE_SUCCESS, commandText);
        assertCommandSuccess(new RedoCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_redoDeleteCommand_success() {
        // Delete a person, commit, undo, then redo – person should be removed again
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String commandText = "deleteperson 1";

        model.deletePerson(personToDelete);
        model.commitAddressBook(commandText);
        model.undoAddressBook();

        // expectedModel mirrors: delete → commit (pointer at 1 after redo)
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        expectedModel.commitAddressBook(commandText);

        String expectedMessage = String.format(RedoCommand.MESSAGE_SUCCESS, commandText);
        assertCommandSuccess(new RedoCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_redoEditCommand_success() {
        // Edit a person, commit, undo, then redo – edit should be reapplied
        Person original = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person edited = new PersonBuilder(original).withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();
        String commandText = "editperson 1 n/Bob Choo p/92222222";

        model.setPerson(original, edited);
        model.commitAddressBook(commandText);
        model.undoAddressBook();

        // expectedModel mirrors: edit → commit (pointer at 1 after redo)
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPerson(original, edited);
        expectedModel.commitAddressBook(commandText);

        String expectedMessage = String.format(RedoCommand.MESSAGE_SUCCESS, commandText);
        assertCommandSuccess(new RedoCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_redoClearCommand_success() {
        // Clear the address book, commit, undo, then redo – book should be empty again
        String commandText = "clear";
        model.setAddressBook(new AddressBook());
        model.commitAddressBook(commandText);
        model.undoAddressBook();

        // expectedModel mirrors: clear → commit (pointer at 1 after redo)
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());
        expectedModel.commitAddressBook(commandText);

        String expectedMessage = String.format(RedoCommand.MESSAGE_SUCCESS, commandText);
        assertCommandSuccess(new RedoCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_consecutiveRedos_success() {
        // Commit two states, undo both, then redo through all the way to the last state
        Person firstPerson = new PersonBuilder().withName("Hoon Meier")
                .withPhone("84824240").withAddress("500001").withRegion("N").build();
        Person secondPerson = new PersonBuilder().withName("Ida Mueller")
                .withPhone("84821310").withAddress("600001").withRegion("N").build();
        String firstCommandText = "addperson n/Hoon Meier p/84824240 a/500001 r/N";
        String secondCommandText = "addperson n/Ida Mueller p/84821310 a/600001 r/N";

        model.addPerson(firstPerson);
        model.commitAddressBook(firstCommandText);
        model.addPerson(secondPerson);
        model.commitAddressBook(secondCommandText);
        model.undoAddressBook();
        model.undoAddressBook();

        // First redo – forward to state with only firstPerson
        Model expectedAfterFirstRedo = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedAfterFirstRedo.addPerson(firstPerson);
        expectedAfterFirstRedo.commitAddressBook(firstCommandText);
        expectedAfterFirstRedo.addPerson(secondPerson);
        expectedAfterFirstRedo.commitAddressBook(secondCommandText);
        expectedAfterFirstRedo.undoAddressBook();
        expectedAfterFirstRedo.undoAddressBook();
        expectedAfterFirstRedo.redoAddressBook();
        String expectedFirstMessage = String.format(RedoCommand.MESSAGE_SUCCESS, firstCommandText);
        assertCommandSuccess(new RedoCommand(), model, expectedFirstMessage, expectedAfterFirstRedo);

        // Second redo – forward to state with both persons
        Model expectedAfterSecondRedo = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedAfterSecondRedo.addPerson(firstPerson);
        expectedAfterSecondRedo.commitAddressBook(firstCommandText);
        expectedAfterSecondRedo.addPerson(secondPerson);
        expectedAfterSecondRedo.commitAddressBook(secondCommandText);
        expectedAfterSecondRedo.undoAddressBook();
        expectedAfterSecondRedo.undoAddressBook();
        expectedAfterSecondRedo.redoAddressBook();
        expectedAfterSecondRedo.redoAddressBook();
        String expectedSecondMessage = String.format(RedoCommand.MESSAGE_SUCCESS, secondCommandText);
        assertCommandSuccess(new RedoCommand(), model, expectedSecondMessage, expectedAfterSecondRedo);

        // Third redo should fail – at the latest state already
        assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void mutabilityFlags_mutatesModelWithoutHistoryCommit() {
        RedoCommand redoCommand = new RedoCommand();
        assertFalse(redoCommand.shouldRecordInHistory());
        assertTrue(redoCommand.mutatesModel());
    }
}
