package seedu.address.logic.commands.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMERIDX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDERS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.OrderMap;

/**
 * Marks an order as completed using its displayed index from the address book.
 */
public class CompleteOrderCommand extends Command {
    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_SUCCESS = "Order Marked as Completed: %s";

    public static final String MESSAGE_INVALID_INDEX = "No order with that index was found";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the order as completed by the index number used in the displayed order list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index targetIndex;

    /**
     * Creates an CompleteOrderCommand to mark the order as completed.
     */
    public CompleteOrderCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    /**
     * Indicates that CompleteOrderCommand mutates the model.
     */
    @Override
    public boolean mutatesModel() {
        return true;
    }

    /** Indicates that AddOrderCommand should be recorded. */
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

        return new CommandResult(String.format(MESSAGE_SUCCESS, completedOrder));
    }
}
