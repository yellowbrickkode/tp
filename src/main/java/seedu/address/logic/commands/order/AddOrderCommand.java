package seedu.address.logic.commands.order;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMERIDX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDERS;

import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.order.OrderMap;
import seedu.address.model.order.ProductQuantityPair;
import seedu.address.model.person.Person;

/**
 * Adds an {@code OrderMap} to the address book.
 */
public class AddOrderCommand extends Command {

    public static final String COMMAND_WORD = "addorder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": adds a new order made by a returning customer. "
            + "Parameters: " + PREFIX_CUSTOMERIDX + "CUSTOMER_INDEX (must be a positive integer) "
            + "o/ [MENU_ITEM PRODUCT_QUANTITY]\n"
            + "Example: " + COMMAND_WORD + PREFIX_CUSTOMERIDX + " 1 " + PREFIX_ORDERS + "1 1" + PREFIX_ORDERS + "2 4\n"
            + "This means customer 1 ordered 1 of menu item 1 and 4 of menu item 2.";

    public static final String MESSAGE_SUCCESS = "New order added: %1$s.";
    public static final String MESSAGE_ZERO_QUANTITY = "Quantity cannot be zero";

    private final int index;
    private final Set<ProductQuantityPair> order;

    /**
     * Constructor for AddOrderCommand.
     *
     * @param index Index of person to tag the order to.
     * @param order Map of all orders made.
     */
    public AddOrderCommand(int index, Set<ProductQuantityPair> order) {
        requireAllNonNull(index, order);
        this.index = index;
        this.order = order;
    }

    /**
     * Executes the AddOrderCommand.
     *
     * @param model {@code Model} which the command should operate on.
     * @return CommandResult with confirmation message.
     */
    @Override
    public CommandResult execute(Model model) {
        Person person = model.getFilteredPersonList().get(index - 1);
        OrderMap toAdd = new OrderMap(person, this.order);
        model.addOrder(toAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    /** Returns the index of the person specified. */
    public int getIndex() {
        return index;
    }

    /** Returns the Map of all orders made. */
    public Set<ProductQuantityPair> getOrder() {
        return order;
    }

    /** Checks if two AddOrderCommands are equal. */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddOrderCommand)) {
            return false;
        }

        AddOrderCommand e = (AddOrderCommand) other;
        return index == e.getIndex() && order.equals(e.getOrder());
    }

    /** Indicates that AddOrderCommand should be recorded. */
    @Override
    public boolean shouldRecordInHistory() {
        return true;
    }

    /** Indicates that AddOrderCommand mutates the model. */
    @Override
    public boolean mutatesModel() {
        return true;
    }

    /** Represents the AddOrderCommand as a String. */
    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
