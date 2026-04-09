package seedu.address.ui;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.order.OrderMap;
import seedu.address.model.order.Product;
import seedu.address.model.order.ProductQuantityPair;
import seedu.address.model.order.Quantity;

/**
 * An UI component that displays information of an {@code OrderMap}.
 */
public class OrderCard extends UiPart<Region> {

    private static final String FXML = "OrderListCard.fxml";
    private static final DateTimeFormatter DISPLAY_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public final OrderMap order;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label customer;
    @FXML
    private Label datetime;
    @FXML
    private Label region;
    @FXML
    private Label items;
    @FXML
    private Label phone;
    @FXML
    private FlowPane statusTags;


    /**
     * Creates an {@code OrderCard} with the given {@code OrderMap} and index to display.
     */
    public OrderCard(OrderMap order, int displayedIndex) {
        super(FXML);
        this.order = order;
        id.setText(displayedIndex + ". ");
        customer.setText(order.getPerson().getName().fullName);
        region.setText(order.getPerson().getRegion().toLabel());
        region.setStyle("-fx-background-color: " + order.getPerson().getRegion().getColour());
        status.setText("Status: " + order.getStatus());
        phone.setText("Phone number: " + order.getPerson().getPhone().value);
        datetime.setText("At: " + order.getOrderDatetime().value.format(DISPLAY_FORMATTER));

        Label statusLabel = new Label(order.getStatus().toString());
        statusLabel.getStyleClass().add("cell_small_label");
        statusLabel.setStyle("-fx-background-color: " + getStatusColor(order.getStatus()) + ";");
        statusTags.getChildren().add(statusLabel);

        StringBuilder itemList = new StringBuilder();
        for (ProductQuantityPair entry : order.getProductQuantityPairs()) {
            if (itemList.length() > 0) {
                itemList.append(", ");
            }
            Product product = entry.getProduct();
            Quantity quantity = entry.getQuantity();
            itemList.append(product.getName()).append(" x").append(quantity);
        }
        items.setText(itemList.toString());
    }

    private String getStatusColor(seedu.address.model.order.OrderStatus status) {
        switch (status) {
        case COMPLETED:
            return "#2C7542";
        case CANCELLED:
            return "#8C3B3B";
        default:
            return "#B87F23";
        }
    }
}
