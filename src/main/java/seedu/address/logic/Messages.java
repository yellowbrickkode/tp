package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.order.OrderMap;
import seedu.address.model.order.Product;
import seedu.address.model.order.ProductList;
import seedu.address.model.order.ProductQuantityPair;
import seedu.address.model.order.Quantity;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INVALID_ORDER_DISPLAYED_INDEX = "The order index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d person(s) listed!";
    public static final String MESSAGE_ORDERS_LISTED_OVERVIEW = "%1$d order(s) listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_MENU_ITEM = "Invalid menu item index: %d";

    public static final ProductList MENU = new ProductList();

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Region: ")
                .append(person.getRegion())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code ordermap} for display to the user.
     */
    public static String format(OrderMap orderMap) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Order ID: ")
                .append(orderMap.getOrderId())
                .append("; Customer: ")
                .append(orderMap.getPerson().getName())
                .append("; Date/Time: ")
                .append(orderMap.getOrderDatetime().toString())
                .append("; Status: ")
                .append(orderMap.getStatus())
                .append("; Items: ");

        boolean first = true;

        for (ProductQuantityPair entry : orderMap.getProductQuantityPairs()) {
            if (!first) {
                builder.append(", ");
            }
            first = false;

            Product product = entry.getProduct();
            Quantity quantity = entry.getQuantity();
            builder.append(String.format(
                    "%s [%d] [$%.2f] ",
                    product.getName(),
                    quantity.getValue(),
                    product.getPrice()
            ));
        }
        return builder.toString();
    }

}
