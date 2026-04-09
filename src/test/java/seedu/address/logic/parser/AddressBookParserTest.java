package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.order.ClearOrderCommand;
import seedu.address.logic.commands.order.DeleteOrderByPhoneNumberCommand;
import seedu.address.logic.commands.order.DeleteOrderCommand;
import seedu.address.logic.commands.order.FindOrderByPhoneNumberCommand;
import seedu.address.logic.commands.order.FindOrderByRegionCommand;
import seedu.address.logic.commands.order.ListOrderCommand;
import seedu.address.logic.commands.person.AddPersonCommand;
import seedu.address.logic.commands.person.DeletePersonCommand;
import seedu.address.logic.commands.person.EditPersonCommand;
import seedu.address.logic.commands.person.EditPersonCommand.EditPersonDescriptor;
import seedu.address.logic.commands.person.FindPersonCommand;
import seedu.address.logic.commands.person.ListPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.PhoneNumberPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.RegionContainsKeywordsPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddPersonCommand command = (AddPersonCommand) parser.parseCommand(PersonUtil.getAddPersonCommand(person));
        assertEquals(new AddPersonCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
        assertTrue(parser.parseCommand(PREAMBLE_WHITESPACE + ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + "   ") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_clearOrder() throws Exception {
        assertTrue(parser.parseCommand(ClearOrderCommand.COMMAND_WORD) instanceof ClearOrderCommand);
        assertTrue(parser.parseCommand(ClearOrderCommand.COMMAND_WORD + " 3") instanceof ClearOrderCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeletePersonCommand command = (DeletePersonCommand) parser.parseCommand(
                DeletePersonCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeletePersonCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_deleteOrder() throws Exception {
        DeleteOrderCommand command = (DeleteOrderCommand) parser.parseCommand(
                DeleteOrderCommand.COMMAND_WORD + " " + INDEX_FIRST_ORDER.getOneBased());
        assertEquals(new DeleteOrderCommand(INDEX_FIRST_ORDER), command);
    }

    @Test
    public void parseCommand_deleteOrderByPhone() throws Exception {
        DeleteOrderByPhoneNumberCommand command = (DeleteOrderByPhoneNumberCommand) parser.parseCommand(
                DeleteOrderByPhoneNumberCommand.COMMAND_WORD + " 94351253");
        assertEquals(new DeleteOrderByPhoneNumberCommand(new PhoneNumberPredicate("94351253")), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditPersonCommand command = (EditPersonCommand) parser.parseCommand(EditPersonCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditPersonCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("N", "E", "W");
        FindPersonCommand command = (FindPersonCommand) parser.parseCommand(
                FindPersonCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindPersonCommand(new RegionContainsKeywordsPredicate(keywords)), command);
    }


    @Test
    public void parseCommand_findOrder() throws Exception {
        assertTrue(parser.parseCommand("findorder p/94351253")
                instanceof FindOrderByPhoneNumberCommand);
        assertTrue(parser.parseCommand("findorder r/N")
                instanceof FindOrderByRegionCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListPersonCommand.COMMAND_WORD) instanceof ListPersonCommand);
        assertTrue(parser.parseCommand(ListPersonCommand.COMMAND_WORD + " 3") instanceof ListPersonCommand);
        assertTrue(parser.parseCommand(PREAMBLE_WHITESPACE + ListPersonCommand.COMMAND_WORD)
                instanceof ListPersonCommand);
    }

    @Test
    public void parseCommand_listOrder() throws Exception {
        assertTrue(parser.parseCommand(ListOrderCommand.COMMAND_WORD) instanceof ListOrderCommand);
        assertTrue(parser.parseCommand(ListOrderCommand.COMMAND_WORD + " 3") instanceof ListOrderCommand);
        assertTrue(parser.parseCommand(PREAMBLE_WHITESPACE + ListOrderCommand.COMMAND_WORD)
                instanceof ListOrderCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
