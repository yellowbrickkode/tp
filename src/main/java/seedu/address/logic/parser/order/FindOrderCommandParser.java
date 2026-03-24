package seedu.address.logic.parser.order;

import seedu.address.logic.commands.order.FindOrderCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindOrderCommand object.
 */
public class FindOrderCommandParser implements Parser<FindOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindOrderCommand
     * and returns a FindOrderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public FindOrderCommand parse(String args) throws ParseException {
        return new FindOrderCommand();
    }
}
