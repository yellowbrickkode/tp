package seedu.address.logic.commands.order;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ORDERS;
import static seedu.address.testutil.TypicalOrders.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.OrderMap;
import seedu.address.model.order.OrderStatus;

public class CompleteOrderCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndex_success() {
        // get first order in the filtered list
        List<OrderMap> lastShownList = model.getFilteredOrderList();
        OrderMap orderToComplete = lastShownList.get(0);

        CompleteOrderCommand command = new CompleteOrderCommand(Index.fromOneBased(1));

        OrderMap completedOrder = orderToComplete.markAsCompleted();
        expectedModel.setOrder(orderToComplete, completedOrder);
        expectedModel.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(CompleteOrderCommand.MESSAGE_SUCCESS,
                        completedOrder.getOrderId(),
                        completedOrder.getPerson().getName()));

        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        CompleteOrderCommand command = new CompleteOrderCommand(outOfBoundIndex);

        assertCommandFailure(command, model, "No order with that index was found");
    }

    @Test
    public void execute_alreadyCompleted_throwsCommandException() {
        OrderMap orderToComplete = model.getFilteredOrderList().get(0);
        // mark it completed first
        OrderMap completedOrder = orderToComplete.markAsCompleted();
        model.setOrder(orderToComplete, completedOrder);

        int completedOrderIndex = findDisplayedIndexByOrderId(completedOrder.getOrderId());
        CompleteOrderCommand command = new CompleteOrderCommand(Index.fromZeroBased(completedOrderIndex));

        assertCommandFailure(command, model, "Order is already completed");
    }

    @Test
    public void execute_undoRedo_modelUpdated() throws Exception {
        OrderMap orderToComplete = model.getFilteredOrderList().get(0);
        CompleteOrderCommand command = new CompleteOrderCommand(Index.fromOneBased(1));
        command.execute(model);

        int updatedOrderIndex = findDisplayedIndexByOrderId(orderToComplete.getOrderId());
        assert(model.getFilteredOrderList().get(updatedOrderIndex).getStatus() == OrderStatus.COMPLETED);
        assertTrue(command.shouldRecordInHistory());
        assertTrue(command.mutatesModel());
    }

    private int findDisplayedIndexByOrderId(int orderId) {
        List<OrderMap> displayedOrders = model.getFilteredOrderList();
        for (int i = 0; i < displayedOrders.size(); i++) {
            if (displayedOrders.get(i).getOrderId() == orderId) {
                return i;
            }
        }
        throw new AssertionError("Expected order id not found in displayed list: " + orderId);
    }
}
