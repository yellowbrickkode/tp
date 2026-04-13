package seedu.address.logic.commands.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDERS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ORDERS;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.OrderMap;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.order.Product;
import seedu.address.model.order.ProductQuantityPair;

/**
 * Edits the details of an existing order in the address book.
 */
public class EditOrderCommand extends Command {

    public static final String COMMAND_WORD = "editorder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the order identified "
            + "by the index number used in the displayed order list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ORDERS + "MENU_ITEM PRODUCT_QUANTITY...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ORDERS + "5 2 "
            + PREFIX_ORDERS + "8 0";

    public static final String MESSAGE_EDIT_ORDER_SUCCESS = "Order edited successfully.\n%1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one menu item to edit must be provided.";
    public static final String MESSAGE_EMPTY_ORDER = "An order must have at least one item.";
    public static final String MESSAGE_COMPLETED_ORDER = "Cannot edit a completed order.";

    private final Index index;
    private final EditOrderDescriptor editOrderDescriptor;

    /**
     * @param index of the order in the filtered person list to edit
     * @param editOrderDescriptor details to edit the order with
     */
    public EditOrderCommand(Index index, EditOrderDescriptor editOrderDescriptor) {
        requireNonNull(index);
        requireNonNull(editOrderDescriptor);

        this.index = index;
        this.editOrderDescriptor = new EditOrderDescriptor(editOrderDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<OrderMap> lastShownList = model.getFilteredOrderList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        OrderMap orderToEdit = lastShownList.get(index.getZeroBased());
        if (orderToEdit.getStatus().equals(OrderStatus.COMPLETED)) {
            throw new CommandException(MESSAGE_COMPLETED_ORDER);
        }
        OrderMap editedOrder = createEditedOrderMap(orderToEdit, editOrderDescriptor);

        model.setOrder(orderToEdit, editedOrder);
        model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        return new CommandResult(String.format(MESSAGE_EDIT_ORDER_SUCCESS, Messages.format(editedOrder)));
    }

    /**
     * Creates and returns an {@code Order} with the details of {@code orderToEdit}
     * edited with {@code editOrderDescriptor}.
     */
    private static OrderMap createEditedOrderMap(
            OrderMap orderToEdit, EditOrderDescriptor editOrderDescriptor) throws CommandException {
        assert orderToEdit != null;

        Set<ProductQuantityPair> updatedOrderMap = getUpdatedOrderMap(orderToEdit, editOrderDescriptor);

        if (updatedOrderMap.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_ORDER);
        }

        return new OrderMap(orderToEdit.getOrderId(), orderToEdit.getPerson(), updatedOrderMap,
                orderToEdit.getStatus(), orderToEdit.getOrderDatetime());
    }

    /**
     * Returns the updated order map based on the {@code EditOrderDescriptor}.
     * @param orderToEdit The original order
     * @param editOrderDescriptor The order descriptor with the new order items
     */
    private static Set<ProductQuantityPair> getUpdatedOrderMap(
            OrderMap orderToEdit, EditOrderDescriptor editOrderDescriptor) throws CommandException {
        Set<ProductQuantityPair> enteredOrders = editOrderDescriptor.getProductQuantityPairs();
        Set<ProductQuantityPair> updateditemSet = new HashSet<>();

        for (ProductQuantityPair item : enteredOrders) {
            if (item.getQuantity().getValue() != 0) {
                updateditemSet.add(item);
            }
        }

        for (ProductQuantityPair item : orderToEdit.getProductQuantityPairs()) {
            if (!doesItemSetContainProduct(enteredOrders, item.getProduct())) {
                updateditemSet.add(item);
            }
        }
        return updateditemSet;
    }

    /**
     * Returns true if {@code itemSet} contains an entry for {@code product}.
     */
    private static boolean doesItemSetContainProduct(Set<ProductQuantityPair> itemSet, Product product) {
        return itemSet.stream()
                .anyMatch(pair -> pair.getProduct().equals(product));
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

        // instanceof handles nulls
        if (!(other instanceof EditOrderCommand)) {
            return false;
        }

        EditOrderCommand otherEditCommand = (EditOrderCommand) other;
        return index.equals(otherEditCommand.index)
                && editOrderDescriptor.equals(otherEditCommand.editOrderDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editOrderDescriptor", editOrderDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditOrderDescriptor {
        private Set<ProductQuantityPair> productQuantityPairs;

        public EditOrderDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditOrderDescriptor(EditOrderDescriptor toCopy) {
            setProductQuantityPairs(toCopy.productQuantityPairs);
        }

        public void setProductQuantityPairs(Set<ProductQuantityPair> productQuantityPairs) {
            this.productQuantityPairs = productQuantityPairs;
        }

        public Set<ProductQuantityPair> getProductQuantityPairs() {
            return productQuantityPairs;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditOrderDescriptor)) {
                return false;
            }

            EditOrderDescriptor otherEditOrderDescriptor = (EditOrderDescriptor) other;
            return Objects.equals(productQuantityPairs, otherEditOrderDescriptor.productQuantityPairs);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("orderMap", productQuantityPairs)
                    .toString();
        }
    }
}
