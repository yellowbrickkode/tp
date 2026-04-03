package seedu.address.logic.commands.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.ActiveOrderInRegionPredicate;
import seedu.address.model.order.OrderDateTime;
import seedu.address.model.order.OrderMap;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.person.Person;
import seedu.address.model.person.Region;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.PersonBuilder;

public class FindOrderCommandTest {

    @Test
    public void execute_filtersActiveOrdersByRegion() throws Exception {
        Person northPerson = new PersonBuilder().withName("North Person").withRegion("N").build();
        Person eastPerson = new PersonBuilder().withName("East Person").withRegion("E").build();

        OrderMap pendingNorth = new OrderBuilder().withPerson(northPerson).withOrderId(1).build();
        OrderMap completedNorth = new OrderMap(2, northPerson,
                new OrderBuilder().getDefaultOrderMap(),
                OrderStatus.COMPLETED,
                new OrderDateTime(LocalDateTime.now()));
        OrderMap pendingEast = new OrderBuilder().withPerson(eastPerson).withOrderId(3).build();

        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(northPerson);
        addressBook.addPerson(eastPerson);
        addressBook.addOrder(pendingNorth);
        addressBook.addOrder(completedNorth);
        addressBook.addOrder(pendingEast);

        Model model = new ModelManager(addressBook, new UserPrefs());

        FindOrderCommand command = new FindOrderCommand(new ActiveOrderInRegionPredicate(new Region("N")));
        CommandResult result = command.execute(model);

        assertEquals(1, model.getFilteredOrderList().size());
        assertEquals(pendingNorth, model.getFilteredOrderList().get(0));
        assertEquals(String.format(Messages.MESSAGE_ORDERS_LISTED_OVERVIEW, 1), result.getFeedbackToUser());
        assertTrue(result.isShowOrders());
    }

    @Test
    public void equals() {
        FindOrderCommand command = new FindOrderCommand(new ActiveOrderInRegionPredicate(new Region("N")));
        FindOrderCommand sameCommand = new FindOrderCommand(new ActiveOrderInRegionPredicate(new Region("N")));
        FindOrderCommand differentCommand = new FindOrderCommand(new ActiveOrderInRegionPredicate(new Region("E")));

        assertTrue(command.equals(command));
        assertTrue(command.equals(sameCommand));
        assertFalse(command.equals(differentCommand));
        assertFalse(command.equals(null));
        assertFalse(command.equals(1));
    }

    @Test
    public void toStringMethod() {
        ActiveOrderInRegionPredicate predicate = new ActiveOrderInRegionPredicate(new Region("N"));
        FindOrderCommand command = new FindOrderCommand(predicate);
        String expected = FindOrderCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, command.toString());
    }
}
