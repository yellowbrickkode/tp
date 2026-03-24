package seedu.address.logic.parser.order;

import seedu.address.logic.commands.order.EditOrderCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditOrderCommand object.
 */
public class EditOrderCommandParser implements Parser<EditOrderCommand> {

    @Override
    public EditOrderCommand parse(String args) throws ParseException {
        return new EditOrderCommand();
    }
}
