package seedu.address.logic.parser.order;

import seedu.address.logic.commands.order.DeleteOrderCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteOrderCommand object.
 */
public class DeleteOrderCommandParser implements Parser<DeleteOrderCommand> {

    @Override
    public DeleteOrderCommand parse(String args) throws ParseException {
        return new DeleteOrderCommand();
    }
}
