package seedu.address.logic.commands.order;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.order.PhoneNumberPredicate;

/**
 * Finds and lists all orders in address book whose customer's phone number matches the given phone number.
 */
public class FindOrderByPhoneNumberCommand extends Command {

    public static final String COMMAND_WORD = "findorderbyphone";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all orders whose customer's phone number matches the given phone number "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: PHONE\n"
            + "Example: " + COMMAND_WORD + " 90813212";

    private final PhoneNumberPredicate predicate;

    public FindOrderByPhoneNumberCommand(PhoneNumberPredicate predicate) {
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
