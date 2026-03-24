package seedu.address.logic.parser.order;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.order.FindOrderCommand;

public class FindOrderCommandParserTest {

    @Test
    public void parse_returnsCommandInstance() throws Exception {
        assertTrue(new FindOrderCommandParser().parse("keyword") instanceof FindOrderCommand);
    }
}
