package seedu.address.logic.commands.order;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.ActiveOrderInRegionPredicate;

/**
 * Finds and lists active orders in the address book for a given region.
 */
public class FindOrderCommand extends Command {

    public static final String COMMAND_WORD = "findorder";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds active orders for a region. "
            + "Parameters: r/REGION\n"
            + "Example: " + COMMAND_WORD + " r/N";

    private final ActiveOrderInRegionPredicate predicate;

    /**
     * Creates a FindOrderCommand with the specified predicate.
     */
    public FindOrderCommand(ActiveOrderInRegionPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredOrderList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_ORDERS_LISTED_OVERVIEW, model.getFilteredOrderList().size()),
                false,
                false,
                false,
                true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindOrderCommand)) {
            return false;
        }

        FindOrderCommand otherCommand = (FindOrderCommand) other;
        return predicate.equals(otherCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
