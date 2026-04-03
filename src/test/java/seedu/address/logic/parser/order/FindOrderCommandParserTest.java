package seedu.address.logic.parser.order;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.order.FindOrderCommand;
import seedu.address.model.order.ActiveOrderInRegionPredicate;
import seedu.address.model.person.Region;

public class FindOrderCommandParserTest {

    private final FindOrderCommandParser parser = new FindOrderCommandParser();

    @Test
    public void parse_validArgs_returnsCommand() {
        assertParseSuccess(parser, " r/N",
                new FindOrderCommand(new ActiveOrderInRegionPredicate(new Region("N"))));
    }

    @Test
    public void parse_missingPrefix_failure() {
        assertParseFailure(parser, " N",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOrderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidRegion_failure() {
        assertParseFailure(parser, " r/NEE", Region.MESSAGE_CONSTRAINTS);
    }
}
