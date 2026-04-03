package seedu.address.logic.commands.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.OrderDateTime;
import seedu.address.model.order.OrderMap;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.person.Person;
import seedu.address.model.person.Region;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.PersonBuilder;

public class CompleteRegionOrdersCommandTest {

    @Test
    public void constructor_nullRegion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CompleteRegionOrdersCommand(null));
    }

    @Test
    public void execute_matchingRegion_marksOrdersCompleted() {
        Person northPerson = new PersonBuilder().withName("North Person").withRegion("N").build();
        Person eastPerson = new PersonBuilder().withName("East Person").withRegion("E").build();

        OrderMap northOrder = new OrderBuilder().withPerson(northPerson).withOrderId(1).build();
        OrderMap eastOrder = new OrderBuilder().withPerson(eastPerson).withOrderId(2).build();

        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(northPerson);
        addressBook.addPerson(eastPerson);
        addressBook.addOrder(northOrder);
        addressBook.addOrder(eastOrder);

        Model model = new ModelManager(addressBook, new UserPrefs());

        CompleteRegionOrdersCommand command = new CompleteRegionOrdersCommand(new Region("N"));
        CommandResult result = command.execute(model);

        List<OrderMap> updatedOrders = model.getAddressBook().getOrderList();
        OrderMap updatedNorth = updatedOrders.stream()
                .filter(order -> order.getPerson().equals(northPerson))
                .findFirst()
                .orElseThrow();
        OrderMap updatedEast = updatedOrders.stream()
                .filter(order -> order.getPerson().equals(eastPerson))
                .findFirst()
                .orElseThrow();

        assertEquals(OrderStatus.COMPLETED, updatedNorth.getStatus());
        assertEquals(OrderStatus.PENDING, updatedEast.getStatus());
        assertEquals(String.format(CompleteRegionOrdersCommand.MESSAGE_SUCCESS, 1, "N", 0),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_noMatchingRegion_returnsNoOrdersMessage() {
        Person northPerson = new PersonBuilder().withName("North Person").withRegion("N").build();
        OrderMap northOrder = new OrderBuilder().withPerson(northPerson).withOrderId(1).build();

        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(northPerson);
        addressBook.addOrder(northOrder);

        Model model = new ModelManager(addressBook, new UserPrefs());

        CompleteRegionOrdersCommand command = new CompleteRegionOrdersCommand(new Region("E"));
        CommandResult result = command.execute(model);

        assertEquals(String.format(CompleteRegionOrdersCommand.MESSAGE_NO_MATCHING_ORDERS, "E"),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_skipsAlreadyCompletedOrders() {
        Person northPerson = new PersonBuilder().withName("North Person").withRegion("N").build();

        OrderMap completedOrder = new OrderMap(1, northPerson,
                new OrderBuilder().getDefaultOrderMap(),
                OrderStatus.COMPLETED,
                new OrderDateTime(LocalDateTime.now()));

        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(northPerson);
        addressBook.addOrder(completedOrder);

        Model model = new ModelManager(addressBook, new UserPrefs());

        CompleteRegionOrdersCommand command = new CompleteRegionOrdersCommand(new Region("N"));
        CommandResult result = command.execute(model);

        assertEquals(String.format(CompleteRegionOrdersCommand.MESSAGE_SUCCESS, 0, "N", 1),
                result.getFeedbackToUser());
        assertTrue(model.getAddressBook().getOrderList().get(0).getStatus() == OrderStatus.COMPLETED);
    }

    @Test
    public void mutabilityFlags_returnsTrue() {
        CompleteRegionOrdersCommand command = new CompleteRegionOrdersCommand(new Region("N"));
        assertTrue(command.shouldRecordInHistory());
        assertTrue(command.mutatesModel());
    }

    @Test
    public void equals() {
        CompleteRegionOrdersCommand command = new CompleteRegionOrdersCommand(new Region("N"));
        CompleteRegionOrdersCommand sameCommand = new CompleteRegionOrdersCommand(new Region("N"));
        CompleteRegionOrdersCommand differentCommand = new CompleteRegionOrdersCommand(new Region("E"));

        assertTrue(command.equals(command));
        assertTrue(command.equals(sameCommand));
        assertFalse(command.equals(differentCommand));
        assertFalse(command.equals(null));
        assertFalse(command.equals(1));
    }

    @Test
    public void toStringMethod() {
        CompleteRegionOrdersCommand command = new CompleteRegionOrdersCommand(new Region("N"));
        String expected = CompleteRegionOrdersCommand.class.getCanonicalName() + "{region="
                + new Region("N") + "}";
        assertEquals(expected, command.toString());
    }
}
