package seedu.address.logic.parser.order;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.order.CompleteOrderCommand;

public class CompleteOrderCommandParserTest {

    private final CompleteOrderCommandParser parser = new CompleteOrderCommandParser();

    @Test
    public void parse_validArgs_returnsCompleteOrderCommand() {
        // user types "complete 1" → index 1
        assertParseSuccess(parser, "1", new CompleteOrderCommand(Index.fromOneBased(1)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // no index
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CompleteOrderCommand.MESSAGE_USAGE));

        // invalid index (non-numeric)
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CompleteOrderCommand.MESSAGE_USAGE));
    }
}
