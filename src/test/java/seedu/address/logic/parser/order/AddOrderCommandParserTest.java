package seedu.address.logic.parser.order;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.order.AddOrderCommand;

public class AddOrderCommandParserTest {

    @Test
    public void parse_returnsCommandInstance() throws Exception {
        assertTrue(new AddOrderCommandParser().parse("anything") instanceof AddOrderCommand);
    }
}
