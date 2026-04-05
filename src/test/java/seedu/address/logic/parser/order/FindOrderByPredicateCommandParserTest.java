package seedu.address.logic.parser.order;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.order.FindOrderByPhoneNumberCommand;
import seedu.address.model.order.PhoneNumberPredicate;
import seedu.address.model.person.Phone;

public class FindOrderByPredicateCommandParserTest {

    private final FindOrderByPredicateCommandParser parser = new FindOrderByPredicateCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOrderByPhoneNumberCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPhone_throwsParseException() {
        assertParseFailure(parser, "123",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOrderByPhoneNumberCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " p/123", Phone.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " p/9435 1253", Phone.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validArgs_returnsFindOrderByPhoneNumberCommand() {
        FindOrderByPhoneNumberCommand expectedFindCommand =
                new FindOrderByPhoneNumberCommand(new PhoneNumberPredicate("94351253"));
        assertParseSuccess(parser, " p/94351253", expectedFindCommand);
        assertParseSuccess(parser, " \n p/94351253 \t", expectedFindCommand);
    }
}
