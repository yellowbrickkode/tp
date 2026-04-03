package seedu.address.logic.parser.order;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.order.CompleteRegionOrdersCommand;
import seedu.address.model.person.Region;

public class CompleteRegionOrdersCommandParserTest {

    private final CompleteRegionOrdersCommandParser parser = new CompleteRegionOrdersCommandParser();

    @Test
    public void parse_validArgs_returnsCommand() {
        assertParseSuccess(parser, " r/N",
                new CompleteRegionOrdersCommand(new Region("N")));
    }

    @Test
    public void parse_missingPrefix_failure() {
        assertParseFailure(parser, " N",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        CompleteRegionOrdersCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        assertParseFailure(parser, " extra r/N",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        CompleteRegionOrdersCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidRegion_failure() {
        assertParseFailure(parser, " r/NEE", Region.MESSAGE_CONSTRAINTS);
    }
}
