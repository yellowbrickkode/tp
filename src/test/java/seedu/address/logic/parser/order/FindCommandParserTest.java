package seedu.address.logic.parser.order;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.person.FindPersonCommand;
import seedu.address.logic.parser.person.FindCommandParser;
import seedu.address.model.person.RegionContainsKeywordsPredicate;

public class FindCommandParserTest {

    private seedu.address.logic.parser.person.FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindPersonCommand expectedFindCommand =
                new FindPersonCommand(new RegionContainsKeywordsPredicate(Arrays.asList("N", "E")));
        assertParseSuccess(parser, "N E", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n N \n \t E  \t", expectedFindCommand);
    }

}
