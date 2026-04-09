package seedu.address.logic.commands.order;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.order.OrderMap;
import seedu.address.model.person.Region;

/**
 * Marks all orders belonging to customers in a given region as completed.
 */
public class CompleteRegionOrdersCommand extends Command {

    public static final String COMMAND_WORD = "completeregion";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks all orders made by customers in a region as completed. "
            + "Parameters: r/REGION\n"
            + "Example: " + COMMAND_WORD + " r/N";

    public static final String MESSAGE_SUCCESS =
            "Marked %d order(s) as completed for region %s. Skipped %d order(s).";
    public static final String MESSAGE_NO_MATCHING_ORDERS = "No orders found for region %s.";

    private final Region region;

    /**
     * Creates a CompleteRegionOrdersCommand to complete orders for the given region.
     */
    public CompleteRegionOrdersCommand(Region region) {
        requireNonNull(region);
        this.region = region;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        List<OrderMap> orders = new ArrayList<>(model.getAddressBook().getOrderList());
        int matched = 0;
        int completed = 0;
        int skipped = 0;
        Set<Integer> completedOrderIds = new HashSet<>();

        for (OrderMap order : orders) {
            if (!order.getPerson().getRegion().equals(region)) {
                continue;
            }
            matched++;
            try {
                OrderMap completedOrder = order.markAsCompleted();
                model.setOrder(order, completedOrder);
                completed++;
                completedOrderIds.add(completedOrder.getOrderId());
            } catch (IllegalStateException e) {
                skipped++;
            }
        }

        model.updateFilteredOrderList(order -> completedOrderIds.contains(order.getOrderId()));

        if (matched == 0) {
            return new CommandResult(String.format(MESSAGE_NO_MATCHING_ORDERS, region));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, completed, region, skipped));
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
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CompleteRegionOrdersCommand)) {
            return false;
        }

        CompleteRegionOrdersCommand otherCommand = (CompleteRegionOrdersCommand) other;
        return region.equals(otherCommand.region);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("region", region)
                .toString();
    }
}
