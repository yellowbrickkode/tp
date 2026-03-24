package seedu.address.logic.parser.order;

import seedu.address.logic.commands.order.AddOrderCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddOrderCommand object.
 */
public class AddOrderCommandParser implements Parser<AddOrderCommand> {

    @Override
    public AddOrderCommand parse(String args) throws ParseException {
        return new AddOrderCommand();
    }
}
