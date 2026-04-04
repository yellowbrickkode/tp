package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.order.AddOrderCommand;
import seedu.address.logic.commands.order.CompleteOrderCommand;
import seedu.address.logic.commands.order.DeleteOrderCommand;
import seedu.address.logic.commands.order.EditOrderCommand;
import seedu.address.logic.commands.order.FindOrderByPhoneNumberCommand;
import seedu.address.logic.commands.order.ListOrderCommand;
import seedu.address.logic.commands.person.AddPersonCommand;
import seedu.address.logic.commands.person.DeletePersonCommand;
import seedu.address.logic.commands.person.EditPersonCommand;
import seedu.address.logic.commands.person.FindPersonCommand;
import seedu.address.logic.commands.person.ListPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.order.AddOrderCommandParser;
import seedu.address.logic.parser.order.CompleteOrderCommandParser;
import seedu.address.logic.parser.order.DeleteOrderCommandParser;
import seedu.address.logic.parser.order.EditOrderCommandParser;
import seedu.address.logic.parser.order.FindOrderByPhoneNumberCommandParser;
import seedu.address.logic.parser.person.AddPersonCommandParser;
import seedu.address.logic.parser.person.DeletePersonCommandParser;
import seedu.address.logic.parser.person.EditPersonCommandParser;
import seedu.address.logic.parser.person.FindPersonCommandParser;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddPersonCommand.COMMAND_WORD:
            return new AddPersonCommandParser().parse(arguments);

        case AddOrderCommand.COMMAND_WORD:
            return new AddOrderCommandParser().parse(arguments);

        case EditPersonCommand.COMMAND_WORD:
            return new EditPersonCommandParser().parse(arguments);

        case EditOrderCommand.COMMAND_WORD:
            return new EditOrderCommandParser().parse(arguments);

        case DeletePersonCommand.COMMAND_WORD:
            return new DeletePersonCommandParser().parse(arguments);

        case DeleteOrderCommand.COMMAND_WORD:
            return new DeleteOrderCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindPersonCommand.COMMAND_WORD:
            return new FindPersonCommandParser().parse(arguments);

        case FindOrderByPhoneNumberCommand.COMMAND_WORD:
            return new FindOrderByPhoneNumberCommandParser().parse(arguments);

        case ListPersonCommand.COMMAND_WORD:
            return new ListPersonCommand();

        case ListOrderCommand.COMMAND_WORD:
            return new ListOrderCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case CompleteOrderCommand.COMMAND_WORD:
            return new CompleteOrderCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
