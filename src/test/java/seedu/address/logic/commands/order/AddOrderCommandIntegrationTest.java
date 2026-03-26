package seedu.address.logic.commands.order;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.OrderMap;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) for {@code AddOrderCommand}.
 */
public class AddOrderCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newOrder_success() {
        int index = 1;

        Map<Integer, Integer> order = new HashMap<>();
        order.put(1, 2);
        order.put(2, 3);

        Person person = model.getFilteredPersonList().get(index - 1);
        OrderMap orderToAdd = new OrderMap(person, order);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addOrder(orderToAdd);

        assertCommandSuccess(new AddOrderCommand(index, order), model,
                String.format(AddOrderCommand.MESSAGE_SUCCESS, Messages.format(orderToAdd)),
                expectedModel);
    }
}
