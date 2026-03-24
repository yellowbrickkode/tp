package seedu.address.logic.parser.order;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.order.EditOrderCommand;

public class EditOrderCommandParserTest {

    @Test
    public void parse_returnsCommandInstance() throws Exception {
        assertTrue(new EditOrderCommandParser().parse("1") instanceof EditOrderCommand);
    }
}
