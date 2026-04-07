package seedu.address.logic.commands.order;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.order.RegionPredicate;

/**
 * Finds and lists all active orders in address book whose customer's region matches the given region.
 */
public class FindOrderByRegionCommand extends Command {

    public static final String COMMAND_WORD = "findorder";

    private final RegionPredicate predicate;

    /**
     * Creates a command to find active orders by customer region.
     */
    public FindOrderByRegionCommand(RegionPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredOrderList(this.predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_ORDERS_LISTED_OVERVIEW, model.getFilteredOrderList().size()),
                false, false, false, true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindOrderByRegionCommand)) {
            return false;
        }

        FindOrderByRegionCommand otherFindCommand = (FindOrderByRegionCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}

