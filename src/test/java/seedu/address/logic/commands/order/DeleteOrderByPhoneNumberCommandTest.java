package seedu.address.logic.commands.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalOrders.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.OrderMap;
import seedu.address.model.order.PhoneNumberPredicate;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.TypicalOrders;

public class DeleteOrderByPhoneNumberCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_matchingPhone_deletesAllMatchingOrders() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        OrderMap completedAliceOrder = TypicalOrders.ALICE_ORDER.markAsCompleted();
        model.setOrder(TypicalOrders.ALICE_ORDER, completedAliceOrder);
        expectedModel.setOrder(TypicalOrders.ALICE_ORDER, completedAliceOrder);

        OrderMap extraOrder = new OrderBuilder(TypicalOrders.ALICE_ORDER).withOrderId(99).build();
        model.addOrder(extraOrder);
        expectedModel.addOrder(extraOrder);

        PhoneNumberPredicate predicate = new PhoneNumberPredicate("94351253");
        long expectedDeletedCount = model.getAddressBook().getOrderList().stream().filter(predicate).count();
        DeleteOrderByPhoneNumberCommand command = new DeleteOrderByPhoneNumberCommand(predicate);
        expectedModel.deleteOrderByPredicate(predicate);
        CommandResult expectedResult = new CommandResult(
                String.format(DeleteOrderByPhoneNumberCommand.MESSAGE_DELETE_ORDERS_BY_PHONE_SUCCESS,
                        expectedDeletedCount), false, false, false, true);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
        assertEquals(2, expectedDeletedCount);
        assertEquals(0, model.getAddressBook().getOrderList().stream().filter(predicate).count());
    }

    @Test
    public void execute_noMatchingPhone_throwsCommandException() {
        PhoneNumberPredicate predicate = new PhoneNumberPredicate("61234567");
        DeleteOrderByPhoneNumberCommand command = new DeleteOrderByPhoneNumberCommand(predicate);

        assertCommandFailure(command, model, DeleteOrderByPhoneNumberCommand.MESSAGE_NO_ORDER_WITH_PHONE);
    }

    @Test
    public void equals() {
        PhoneNumberPredicate predicate = new PhoneNumberPredicate("94351253");
        PhoneNumberPredicate otherPredicate = new PhoneNumberPredicate("98765432");
        DeleteOrderByPhoneNumberCommand deleteCommand = new DeleteOrderByPhoneNumberCommand(predicate);
        DeleteOrderByPhoneNumberCommand deleteCommandCopy = new DeleteOrderByPhoneNumberCommand(predicate);
        DeleteOrderByPhoneNumberCommand otherCommand = new DeleteOrderByPhoneNumberCommand(otherPredicate);

        assertTrue(deleteCommand.equals(deleteCommand));
        assertTrue(deleteCommand.equals(deleteCommandCopy));
        assertFalse(deleteCommand.equals(1));
        assertFalse(deleteCommand.equals(null));
        assertFalse(deleteCommand.equals(otherCommand));
    }

    @Test
    public void toStringMethod() {
        PhoneNumberPredicate predicate = new PhoneNumberPredicate("94351253");
        DeleteOrderByPhoneNumberCommand deleteCommand = new DeleteOrderByPhoneNumberCommand(predicate);
        String expected = DeleteOrderByPhoneNumberCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    @Test
    public void mutabilityFlags_returnsTrue() {
        PhoneNumberPredicate predicate = new PhoneNumberPredicate("94351253");
        DeleteOrderByPhoneNumberCommand deleteCommand = new DeleteOrderByPhoneNumberCommand(predicate);
        assertTrue(deleteCommand.shouldRecordInHistory());
        assertTrue(deleteCommand.mutatesModel());
    }
}
