package seedu.address.logic.commands.order;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.order.OrderMap;

/**
 * Finds and lists all orders in address book that match the given predicate.
 */
public class FindOrderByPhoneNumberCommand extends Command {

    public static final String COMMAND_WORD = "findorder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all orders that match the given criteria and displays them as a list "
            + "with index numbers.\n"
            + "Parameters: [p/PHONE] [r/REGION] (exactly one field must be provided)\n"
            + "Example: " + COMMAND_WORD + " p/90813212\n"
            + "Example: " + COMMAND_WORD + " r/N";

    private final Predicate<OrderMap> predicate;

    /**
     * Creates a command to find orders by predicate.
     */
    public FindOrderByPhoneNumberCommand(Predicate<OrderMap> predicate) {
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

        // instanceof handles nulls
        if (!(other instanceof FindOrderByPhoneNumberCommand)) {
            return false;
        }

        FindOrderByPhoneNumberCommand otherFindCommand = (FindOrderByPhoneNumberCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
