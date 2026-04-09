package seedu.address.logic.commands.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.address.testutil.TypicalOrders.getTypicalAddressBook;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.OrderDateTime;
import seedu.address.model.order.OrderMap;
import seedu.address.model.order.OrderStatus;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteOrderCommand}.
 */
public class DeleteOrderCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        OrderMap orderToDelete = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        DeleteOrderCommand deleteCommand = new DeleteOrderCommand(INDEX_FIRST_ORDER);

        String expectedMessage = String.format(DeleteOrderCommand.MESSAGE_DELETE_ORDER_SUCCESS,
                Messages.format(orderToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteOrder(orderToDelete);

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false, false, true);
        assertCommandSuccess(deleteCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredListWithDisplaySorting_success() {
        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(TypicalPersons.ALICE);
        addressBook.addPerson(TypicalPersons.BENSON);

        OrderMap olderPendingOrder = new OrderMap(1, TypicalPersons.ALICE, new OrderBuilder().getDefaultOrderMap(),
                OrderStatus.PENDING, new OrderDateTime(LocalDateTime.of(2026, 1, 1, 12, 0)));
        OrderMap newerPendingOrder = new OrderMap(2, TypicalPersons.BENSON, new OrderBuilder().getDefaultOrderMap(),
                OrderStatus.PENDING, new OrderDateTime(LocalDateTime.of(2026, 1, 2, 12, 0)));
        addressBook.addOrder(olderPendingOrder);
        addressBook.addOrder(newerPendingOrder);

        Model localModel = new ModelManager(addressBook, new UserPrefs());
        DeleteOrderCommand deleteCommand = new DeleteOrderCommand(INDEX_FIRST_ORDER);
        OrderMap displayedFirstOrder = localModel.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());

        String expectedMessage = String.format(DeleteOrderCommand.MESSAGE_DELETE_ORDER_SUCCESS,
                Messages.format(displayedFirstOrder));

        ModelManager expectedModel = new ModelManager(localModel.getAddressBook(), new UserPrefs());
        expectedModel.deleteOrder(displayedFirstOrder);

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false, false, true);
        assertCommandSuccess(deleteCommand, localModel, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        DeleteOrderCommand deleteCommand = new DeleteOrderCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);

        OrderMap orderToDelete = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        DeleteOrderCommand deleteCommand = new DeleteOrderCommand(INDEX_FIRST_ORDER);

        String expectedMessage = String.format(DeleteOrderCommand.MESSAGE_DELETE_ORDER_SUCCESS,
                Messages.format(orderToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteOrder(orderToDelete);
        showNoOrder(expectedModel);

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false, false, true);
        assertCommandSuccess(deleteCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);

        Index outOfBoundIndex = INDEX_SECOND_ORDER;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getOrderList().size());

        DeleteOrderCommand deleteCommand = new DeleteOrderCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteOrderCommand deleteFirstCommand = new DeleteOrderCommand(INDEX_FIRST_ORDER);
        DeleteOrderCommand deleteSecondCommand = new DeleteOrderCommand(INDEX_SECOND_ORDER);

        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        DeleteOrderCommand deleteFirstCommandCopy = new DeleteOrderCommand(INDEX_FIRST_ORDER);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        assertFalse(deleteFirstCommand.equals(1));

        assertFalse(deleteFirstCommand.equals(null));

        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteOrderCommand deleteCommand = new DeleteOrderCommand(targetIndex);
        String expected = DeleteOrderCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    @Test
    public void mutabilityFlags_returnsTrue() {
        DeleteOrderCommand deleteCommand = new DeleteOrderCommand(INDEX_FIRST_ORDER);
        assertTrue(deleteCommand.shouldRecordInHistory());
        assertTrue(deleteCommand.mutatesModel());
    }

    private void showOrderAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredOrderList().size());

        OrderMap order = model.getFilteredOrderList().get(targetIndex.getZeroBased());
        int orderId = order.getOrderId();
        model.updateFilteredOrderList(orderItem -> orderItem.getOrderId() == orderId);

        assertEquals(1, model.getFilteredOrderList().size());
    }

    private void showNoOrder(Model model) {
        model.updateFilteredOrderList(order -> false);

        assertTrue(model.getFilteredOrderList().isEmpty());
    }
}
