package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.order.OrderMap;

/**
 * Panel containing the list of orders.
 */
public class OrderListPanel extends UiPart<Region> {
    private static final String FXML = "OrderListPanel.fxml";

    @FXML
    private ListView<OrderMap> orderListView;

    /**
     * Creates an {@code OrderListPanel} with the given {@code ObservableList}.
     */
    public OrderListPanel(ObservableList<OrderMap> orderList) {
        super(FXML);
        orderListView.setItems(orderList);
        orderListView.setCellFactory(listView -> new OrderListViewCell());
    }


    /**
     * Custom {@code ListCell} that displays an {@code OrderMap} using an {@code OrderCard}.
     */
    class OrderListViewCell extends ListCell<OrderMap> {
        @Override
        protected void updateItem(OrderMap order, boolean empty) {
            super.updateItem(order, empty);

            if (empty || order == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new OrderCard(order, getIndex() + 1).getRoot());
            }
        }
    }
}
