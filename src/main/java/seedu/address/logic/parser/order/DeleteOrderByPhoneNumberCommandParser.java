package seedu.address.logic.parser.order;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.order.DeleteOrderByPhoneNumberCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.PhoneNumberPredicate;
import seedu.address.model.person.Phone;

/**
 * Parses input arguments and creates a new DeleteOrderByPhoneNumberCommand object
 */
public class DeleteOrderByPhoneNumberCommandParser implements Parser<DeleteOrderByPhoneNumberCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteOrderByPhoneNumberCommand
     * and returns a DeleteOrderByPhoneNumberCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteOrderByPhoneNumberCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteOrderByPhoneNumberCommand.MESSAGE_USAGE));
        }

        Phone phone = ParserUtil.parsePhone(trimmedArgs);
        return new DeleteOrderByPhoneNumberCommand(new PhoneNumberPredicate(phone.value));
    }

}
