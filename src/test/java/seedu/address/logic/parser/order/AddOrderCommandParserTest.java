package seedu.address.logic.parser.order;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMERIDX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.order.AddOrderCommand;
import seedu.address.model.order.ProductQuantityPair;
import seedu.address.model.order.Quantity;

public class AddOrderCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE);

    private final AddOrderCommandParser parser = new AddOrderCommandParser();

    @Test
    public void parse_returnsCommandInstance() throws Exception {
        assertTrue(new AddOrderCommandParser().parse(" c/1 o/1 1") instanceof AddOrderCommand);
    }

    @Test
    public void parse_allFieldsPresent_success() {
        Set<ProductQuantityPair> order = new HashSet<>();
        order.add(new ProductQuantityPair("1 2"));
        order.add(new ProductQuantityPair("2 3"));

        assertParseSuccess(parser, " c/1 o/1 2 o/2 3", new AddOrderCommand(1, order));
    }

    @Test
    public void parse_leadingWhitespace_success() {
        Set<ProductQuantityPair> order = new HashSet<>();
        order.add(new ProductQuantityPair("1 1"));

        assertParseSuccess(parser, "   \n\t c/1 o/1 1", new AddOrderCommand(1, order));
    }

    @Test
    public void parse_missingParts_failure() {
        // no customer index
        assertParseFailure(parser, " o/1 1", MESSAGE_INVALID_FORMAT);

        // no order items
        assertParseFailure(parser, " c/1", MESSAGE_INVALID_FORMAT);

        // non-empty preamble
        assertParseFailure(parser, "1 c/1 o/1 1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_duplicateCustomerIndex_failure() {
        assertParseFailure(parser, " c/1 c/2 o/1 1",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CUSTOMERIDX));
    }

    @Test
    public void parse_invalidProduct_failure() {
        assertParseFailure(parser, " c/1 o/0 1",
                String.format(Messages.MESSAGE_INVALID_MENU_ITEM, 0));
    }

    @Test
    public void parse_invalidQuantity_failure() {
        assertParseFailure(parser, " c/1 o/1 0", Quantity.MESSAGE_CONSTRAINTS_POSITIVE);
    }

    @Test
    public void parse_invalidOrderFormat_failure() {
        assertParseFailure(parser, " c/1 o/1", ProductQuantityPair.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " c/1 o/1 A", ProductQuantityPair.MESSAGE_CONSTRAINTS);
    }
}
