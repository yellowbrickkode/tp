package seedu.address.logic.parser.order;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ORDER;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_ONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.order.EditOrderCommand;
import seedu.address.model.order.OrderMap;
import seedu.address.model.order.ProductQuantityPair;
import seedu.address.model.order.Quantity;

public class EditOrderCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditOrderCommand.MESSAGE_USAGE);

    private EditOrderCommandParser parser = new EditOrderCommandParser();

    @Test
    public void parse_returnsCommandInstance() throws Exception {
        assertTrue(new EditOrderCommandParser().parse(" 1 o/1 1") instanceof EditOrderCommand);
    }

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_ORDER_ONE, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditOrderCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidProduct_failure() {
        Index targetIndex = INDEX_FIRST_ORDER;
        String preamble = String.valueOf(targetIndex.getOneBased());

        assertParseFailure(parser, preamble + " o/0 1",
                String.format(Messages.MESSAGE_INVALID_MENU_ITEM, 0));
        assertParseFailure(parser, preamble + " o/9999 1",
                String.format(Messages.MESSAGE_INVALID_MENU_ITEM, 9999));
        assertParseFailure(parser, preamble + " o/-1 1",
                ProductQuantityPair.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, preamble + " o/A 1",
                ProductQuantityPair.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidQuantity_failure() {
        Index targetIndex = INDEX_FIRST_ORDER;
        String preamble = String.valueOf(targetIndex.getOneBased());

        assertParseFailure(parser, preamble + " o/1 -1",
                Quantity.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, preamble + " o/1",
                ProductQuantityPair.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, preamble + " o/1 A",
                ProductQuantityPair.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1 o/" + INVALID_ORDER, OrderMap.MESSAGE_CONSTRAINTS); // invalid order
    }
}
