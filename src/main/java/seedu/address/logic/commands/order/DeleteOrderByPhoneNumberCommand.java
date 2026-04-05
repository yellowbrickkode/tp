package seedu.address.logic.commands.order;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.PhoneNumberPredicate;

/**
 * Deletes all orders in address book whose customer's phone number matches the given phone number.
 */
public class DeleteOrderByPhoneNumberCommand extends Command {

    public static final String COMMAND_WORD = "deleteorderbyphone";

    public static final String MESSAGE_DELETE_ORDERS_BY_PHONE_SUCCESS = "Deleted %1$d order(s) with given phone "
            + "number.";
    public static final String MESSAGE_NO_ORDER_WITH_PHONE = "No orders found with given phone number.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes all orders matching the provided customer phone number.\n"
            + "Parameters: PHONE\n"
            + "Example: " + COMMAND_WORD + " 90813212";

    private final PhoneNumberPredicate predicate;

    /**
     * Creates a command that deletes orders matching the given phone number predicate.
     */
    public DeleteOrderByPhoneNumberCommand(PhoneNumberPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        int orderCountBeforeDeletion = model.getAddressBook().getOrderList().size();
        if (model.getAddressBook().getOrderList().stream().filter(predicate).findAny().isEmpty()) {
            throw new CommandException(MESSAGE_NO_ORDER_WITH_PHONE);
        }
        model.deleteOrderByPredicate(this.predicate);
        int deletedOrderCount = orderCountBeforeDeletion - model.getAddressBook().getOrderList().size();
        return new CommandResult(String.format(MESSAGE_DELETE_ORDERS_BY_PHONE_SUCCESS, deletedOrderCount),
                false, false, false, true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteOrderByPhoneNumberCommand)) {
            return false;
        }

        DeleteOrderByPhoneNumberCommand otherDeleteByPhoneNumCommand = (DeleteOrderByPhoneNumberCommand) other;
        return predicate.equals(otherDeleteByPhoneNumCommand.predicate);
    }

    @Override
    public boolean shouldRecordInHistory() {
        return true;
    }

    @Override
    public boolean mutatesModel() {
        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
