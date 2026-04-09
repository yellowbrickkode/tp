package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import seedu.address.model.order.OrderDateTime;
import seedu.address.model.order.OrderMap;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.order.ProductQuantityPair;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class OrderListPanelTest {

    @BeforeAll
    public static void initToolkit() {
        System.setProperty("javafx.platform", "Monocle");
        System.setProperty("monocle.platform", "Headless");
        System.setProperty("prism.order", "sw");
        try {
            Platform.startup(() -> { });
        } catch (UnsupportedOperationException e) {
            org.junit.jupiter.api.Assumptions.assumeTrue(false, "JavaFX not supported in this environment");
        } catch (IllegalStateException e) {
            // JavaFX runtime already initialized.
        }
    }

    @Test
    public void constructor_displaysOrdersInProvidedOrder() throws Exception {
        Person person = new PersonBuilder().withName("Order Person").build();
        HashSet<ProductQuantityPair> items = new HashSet<>();
        items.add(new ProductQuantityPair("1 1"));

        OrderMap older = new OrderMap(1, person, items, OrderStatus.PENDING,
                new OrderDateTime(LocalDateTime.of(2026, 3, 10, 10, 0)));
        OrderMap newer = new OrderMap(2, person, items, OrderStatus.PENDING,
                new OrderDateTime(LocalDateTime.of(2026, 3, 11, 10, 0)));

        ObservableList<OrderMap> orders = FXCollections.observableArrayList(older, newer);
        OrderListPanel panel = createPanel(orders);

        ListView<OrderMap> listView = getListView(panel);
        assertNotNull(listView);
        assertEquals(older, listView.getItems().get(0));
        assertEquals(newer, listView.getItems().get(1));
    }

    private OrderListPanel createPanel(ObservableList<OrderMap> orders) throws Exception {
        final OrderListPanel[] panelRef = new OrderListPanel[1];
        runOnFxThreadAndWait(() -> panelRef[0] = new OrderListPanel(orders));
        return panelRef[0];
    }

    private ListView<OrderMap> getListView(OrderListPanel panel) throws Exception {
        return (ListView<OrderMap>) panel.getRoot().lookup("#orderListView");
    }

    private void runOnFxThreadAndWait(Runnable action) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                action.run();
            } finally {
                latch.countDown();
            }
        });
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new AssertionError("JavaFX action timed out");
        }
    }
}

