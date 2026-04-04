package seedu.address.logic.commands.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static seedu.address.logic.Messages.MESSAGE_ORDERS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalOrders.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.PhoneNumberPredicate;

public class FindOrderByPhoneNumberCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        PhoneNumberPredicate firstPredicate = new PhoneNumberPredicate("94351253");
        PhoneNumberPredicate secondPredicate = new PhoneNumberPredicate("98765432");

        FindOrderByPhoneNumberCommand findFirstCommand = new FindOrderByPhoneNumberCommand(firstPredicate);
        FindOrderByPhoneNumberCommand findSecondCommand = new FindOrderByPhoneNumberCommand(secondPredicate);

        assertSame(findFirstCommand, findFirstCommand);
        assertEquals(findFirstCommand, new FindOrderByPhoneNumberCommand(firstPredicate));
        assertNotEquals(findFirstCommand, 1);
        assertNotEquals(findFirstCommand, null);
        assertNotEquals(findFirstCommand, findSecondCommand);
    }

    @Test
    public void execute_noMatchingPhone_noOrdersFound() {
        String expectedMessage = String.format(MESSAGE_ORDERS_LISTED_OVERVIEW, 0);
        PhoneNumberPredicate predicate = preparePredicate("99999999");
        FindOrderByPhoneNumberCommand command = new FindOrderByPhoneNumberCommand(predicate);
        expectedModel.updateFilteredOrderList(predicate);
        assertCommandSuccess(command, model,
                new CommandResult(expectedMessage, false, false, false, true), expectedModel);
        assertEquals(expectedModel.getFilteredOrderList(), model.getFilteredOrderList());
    }

    @Test
    public void execute_matchingPhone_oneOrderFound() {
        String expectedMessage = String.format(MESSAGE_ORDERS_LISTED_OVERVIEW, 1);
        PhoneNumberPredicate predicate = preparePredicate("94351253");
        FindOrderByPhoneNumberCommand command = new FindOrderByPhoneNumberCommand(predicate);
        expectedModel.updateFilteredOrderList(predicate);
        assertCommandSuccess(command, model,
                new CommandResult(expectedMessage, false, false, false, true), expectedModel);
        assertEquals(1, model.getFilteredOrderList().size());
    }

    @Test
    public void toStringMethod() {
        PhoneNumberPredicate predicate = new PhoneNumberPredicate("94351253");
        FindOrderByPhoneNumberCommand findCommand = new FindOrderByPhoneNumberCommand(predicate);
        String expected = FindOrderByPhoneNumberCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    private PhoneNumberPredicate preparePredicate(String userInput) {
        return new PhoneNumberPredicate(userInput.trim());
    }
}
