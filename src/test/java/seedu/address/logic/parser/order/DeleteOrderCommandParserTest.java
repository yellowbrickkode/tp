package seedu.address.logic.parser.order;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.order.DeleteOrderCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteOrderCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteOrderCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteOrderCommandParserTest {

    @Test
    public void parse_returnsCommandInstance() throws Exception {
        assertTrue(new DeleteOrderCommandParser().parse("1") instanceof DeleteOrderCommand);
    }
}
