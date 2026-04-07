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
import seedu.address.model.order.RegionPredicate;
import seedu.address.model.person.Region;

public class FindOrderByRegionCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        RegionPredicate firstPredicate = new RegionPredicate(new Region("N"));
        RegionPredicate secondPredicate = new RegionPredicate(new Region("E"));

        FindOrderByRegionCommand findFirstCommand = new FindOrderByRegionCommand(firstPredicate);
        FindOrderByRegionCommand findSecondCommand = new FindOrderByRegionCommand(secondPredicate);

        assertSame(findFirstCommand, findFirstCommand);
        assertEquals(findFirstCommand, new FindOrderByRegionCommand(firstPredicate));
        assertNotEquals(findFirstCommand, 1);
        assertNotEquals(findFirstCommand, null);
        assertNotEquals(findFirstCommand, findSecondCommand);
    }

    @Test
    public void execute_noMatchingRegion_noOrdersFound() {
        String expectedMessage = String.format(MESSAGE_ORDERS_LISTED_OVERVIEW, 0);
        RegionPredicate predicate = preparePredicate("C");
        FindOrderByRegionCommand command = new FindOrderByRegionCommand(predicate);
        expectedModel.updateFilteredOrderList(predicate);
        assertCommandSuccess(command, model,
                new CommandResult(expectedMessage, false, false, false, true), expectedModel);
        assertEquals(expectedModel.getFilteredOrderList(), model.getFilteredOrderList());
    }

    @Test
    public void execute_matchingRegion_ordersFound() {
        RegionPredicate predicate = preparePredicate("N");
        FindOrderByRegionCommand command = new FindOrderByRegionCommand(predicate);
        expectedModel.updateFilteredOrderList(predicate);
        String expectedMessage = String.format(MESSAGE_ORDERS_LISTED_OVERVIEW,
                expectedModel.getFilteredOrderList().size());
        assertCommandSuccess(command, model,
                new CommandResult(expectedMessage, false, false, false, true), expectedModel);
        assertEquals(expectedModel.getFilteredOrderList(), model.getFilteredOrderList());
    }

    @Test
    public void toStringMethod() {
        RegionPredicate predicate = preparePredicate("N");
        FindOrderByRegionCommand findCommand = new FindOrderByRegionCommand(predicate);
        String expected = FindOrderByRegionCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    private RegionPredicate preparePredicate(String userInput) {
        return new RegionPredicate(new Region(userInput.trim()));
    }
}

