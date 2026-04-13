package seedu.address.logic.commands.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalOrders.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.order.EditOrderCommand.EditOrderDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.OrderMap;
import seedu.address.model.order.OrderStatus;
import seedu.address.testutil.EditOrderDescriptorBuilder;
import seedu.address.testutil.OrderBuilder;

public class EditOrderCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_newMenuItem_success() {
        Index indexLastOrder = Index.fromOneBased(model.getFilteredOrderList().size());
        OrderMap lastOrder = model.getFilteredOrderList().get(indexLastOrder.getZeroBased());

        OrderBuilder orderInList = new OrderBuilder(lastOrder);
        OrderMap editedOrder = orderInList.withOrderMap("2 2", "4 2", "7 1").build();

        EditOrderCommand.EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withOrderMap("7 1").build();
        EditOrderCommand editCommand = new EditOrderCommand(indexLastOrder, descriptor);

        String expectedMessage = String.format(EditOrderCommand.MESSAGE_EDIT_ORDER_SUCCESS,
                Messages.format(editedOrder));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setOrder(lastOrder, editedOrder);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editQuantity_success() {
        Index indexLastOrder = Index.fromOneBased(model.getFilteredOrderList().size());
        OrderMap lastOrder = model.getFilteredOrderList().get(indexLastOrder.getZeroBased());

        OrderBuilder orderInList = new OrderBuilder(lastOrder);
        OrderMap editedOrder = orderInList.withOrderMap("2 2", "4 10").build();

        EditOrderCommand.EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withOrderMap("4 10").build();
        EditOrderCommand editCommand = new EditOrderCommand(indexLastOrder, descriptor);

        String expectedMessage = String.format(EditOrderCommand.MESSAGE_EDIT_ORDER_SUCCESS,
                Messages.format(editedOrder));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setOrder(lastOrder, editedOrder);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteMenuItem_success() {
        Index indexLastOrder = Index.fromOneBased(model.getFilteredOrderList().size());
        OrderMap lastOrder = model.getFilteredOrderList().get(indexLastOrder.getZeroBased());

        OrderBuilder orderInList = new OrderBuilder(lastOrder);
        OrderMap editedOrder = orderInList.withOrderMap("4 2").build();

        EditOrderCommand.EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withOrderMap("2 0").build();
        EditOrderCommand editCommand = new EditOrderCommand(indexLastOrder, descriptor);

        String expectedMessage = String.format(EditOrderCommand.MESSAGE_EDIT_ORDER_SUCCESS,
                Messages.format(editedOrder));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setOrder(lastOrder, editedOrder);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyOrder_failure() {
        Index indexLastOrder = Index.fromOneBased(model.getFilteredOrderList().size());

        EditOrderCommand.EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withOrderMap("2 0", "4 0").build();
        EditOrderCommand editCommand = new EditOrderCommand(indexLastOrder, descriptor);

        assertCommandFailure(editCommand, model, EditOrderCommand.MESSAGE_EMPTY_ORDER);
    }

    @Test
    public void execute_invalidOrderIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withOrderMap("1 0").build();
        EditOrderCommand editCommand = new EditOrderCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_completeOrder_failure() {
        model.addOrder(new OrderBuilder()
                .withOrderId(model.getFilteredOrderList().size() + 1)
                .withStatus(OrderStatus.COMPLETED).build());
        Index index = Index.fromOneBased(model.getFilteredOrderList().size());
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withOrderMap("1 0").build();
        EditOrderCommand editCommand = new EditOrderCommand(index, descriptor);

        assertCommandFailure(editCommand, model, EditOrderCommand.MESSAGE_COMPLETED_ORDER);
    }

    @Test
    public void equals() {
        EditOrderCommand editOrderCommand = new EditOrderCommand(
                Index.fromOneBased(1), new EditOrderDescriptor());

        assertEquals(editOrderCommand, editOrderCommand);

        assertEquals(editOrderCommand, new EditOrderCommand(Index.fromOneBased(1),
                new EditOrderDescriptor()));

        assertNotEquals(new Object(), editOrderCommand);

        assertNotEquals(editOrderCommand, new EditOrderCommand(Index.fromOneBased(2),
                new EditOrderDescriptor()));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditOrderDescriptor editOrderDescriptor = new EditOrderCommand.EditOrderDescriptor();
        EditOrderCommand editOrderCommand = new EditOrderCommand(index, editOrderDescriptor);
        String expected = EditOrderCommand.class.getCanonicalName() + "{index=" + index + ", editOrderDescriptor="
                + editOrderDescriptor + "}";
        assertEquals(expected, editOrderCommand.toString());
    }

    @Test
    public void mutabilityFlags_returnsTrue() {
        EditOrderCommand command = new EditOrderCommand(INDEX_FIRST_PERSON, new EditOrderCommand.EditOrderDescriptor());
        assertTrue(command.shouldRecordInHistory());
        assertTrue(command.mutatesModel());
    }

}
