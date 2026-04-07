package seedu.address.ui;

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

    public final OrderMap order;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label customer;
    @FXML
    private Label status;
    @FXML
    private Label datetime;
    @FXML
    private Label region;
    @FXML
    private FlowPane items;

    /**
     * Creates an {@code OrderCard} with the given {@code OrderMap} and index to display.
     */
    public OrderCard(OrderMap order, int displayedIndex) {
        super(FXML);
        this.order = order;
        id.setText(displayedIndex + ". ");
        customer.setText(order.getPerson().getName().fullName);
        status.setText("Status: " + order.getStatus());
        region.setText("Region: " + order.getPerson().getRegion());
        datetime.setText("At: " + order.getOrderDatetime());

        for (ProductQuantityPair entry : order.getProductQuantityPairs()) {
            Product product = entry.getProduct();
            Quantity quantity = entry.getQuantity();
            String itemLabel;
            itemLabel = product.getName() + " x" + quantity;
            items.getChildren().add(new Label(itemLabel));
        }
    }
}
