package seedu.address.logic.commands.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_CURR_ORDERS;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.OrderMap;

/**
 * Lists all incomplete orders in the address book to the user.
 */
public class ListCurrOrderCommand extends Command {

    public static final String COMMAND_WORD = "listcurrorder";

    public static final String MESSAGE_SUCCESS = "Listed all incomplete orders";

    public static final String MESSAGE_NO_ORDERS = "No active orders found";

    public ListCurrOrderCommand() {}

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateFilteredOrderList(PREDICATE_SHOW_CURR_ORDERS);

        ObservableList<OrderMap> orderList = model.getAddressBook().getOrderList();
        if (orderList.isEmpty()) {
            return new CommandResult(MESSAGE_NO_ORDERS, false, false, false, true);
        }

        return new CommandResult(MESSAGE_SUCCESS, false, false, false, true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || other instanceof ListCurrOrderCommand;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
