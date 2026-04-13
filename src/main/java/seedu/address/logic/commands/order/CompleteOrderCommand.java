package seedu.address.logic.commands.order;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.OrderMap;

/**
 * Marks an order as completed using its displayed index from the order list.
 */
public class CompleteOrderCommand extends Command {
    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_SUCCESS = "Order %d for %s marked as completed.";

    public static final String MESSAGE_INVALID_INDEX = "No order with that index was found";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the order identified by the index number used in the displayed order list as completed.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index targetIndex;

    /**
     * Creates an CompleteOrderCommand to mark the order as completed.
     */
    public CompleteOrderCommand(Index targetIndex) {
        assert targetIndex != null : "Index cannot be null";
        this.targetIndex = targetIndex;
    }

    /**
     * Indicates that CompleteOrderCommand mutates the model.
     */
    @Override
    public boolean mutatesModel() {
        return true;
    }

    /** Indicates that CompleteOrderCommand should be recorded. */
    @Override
    public boolean shouldRecordInHistory() {
        return true;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<OrderMap> lastShownList = model.getFilteredOrderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        OrderMap orderToMark = lastShownList.get(targetIndex.getZeroBased());

        OrderMap completedOrder;
        try {
            completedOrder = orderToMark.markAsCompleted();
        } catch (IllegalStateException e) {
            throw new CommandException(e.getMessage());
        }

        model.setOrder(orderToMark, completedOrder);

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                completedOrder.getOrderId(),
                completedOrder.getPerson().getName()), false, false, false, true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CompleteOrderCommand otherCommand)) {
            return false;
        }

        return targetIndex.equals(otherCommand.targetIndex);
    }
}
