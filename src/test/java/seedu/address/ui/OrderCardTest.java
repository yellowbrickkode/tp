package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import seedu.address.model.order.OrderMap;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.PersonBuilder;

public class OrderCardTest {

    @BeforeAll
    public static void initToolkit() {
        if (System.getenv("CI") != null) {
            org.junit.jupiter.api.Assumptions.assumeTrue(false, "Skip UI tests on CI");
        }
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
    public void constructor_displaysRegionLabel() throws Exception {
        OrderMap order = new OrderBuilder()
                .withPerson(new PersonBuilder().withRegion("N").build())
                .build();
        OrderCard card = createOrderCard(order, 1);
        Parent root = card.getRoot();
        Label regionLabel = (Label) root.lookup("#region");
        assertNotNull(regionLabel);
        String expected = order.getPerson().getRegion().toLabel();
        assertEquals(expected, regionLabel.getText());
    }

    @Test
    public void constructor_displaysStatusTagItemsAndDatetime() throws Exception {
        java.time.LocalDateTime time = java.time.LocalDateTime.of(2026, 3, 11, 10, 15);
        java.util.HashSet<seedu.address.model.order.ProductQuantityPair> items = new java.util.HashSet<>();
        items.add(new seedu.address.model.order.ProductQuantityPair("1 1"));
        items.add(new seedu.address.model.order.ProductQuantityPair("2 3"));
        seedu.address.model.person.Person person = new PersonBuilder().withRegion("N").build();
        OrderMap order = new OrderMap(1, person, items, seedu.address.model.order.OrderStatus.PENDING,
                new seedu.address.model.order.OrderDateTime(time));

        OrderCard card = createOrderCard(order, 1);
        Parent root = card.getRoot();

        javafx.scene.layout.FlowPane statusTags =
                (javafx.scene.layout.FlowPane) root.lookup("#statusTags");
        assertNotNull(statusTags);
        assertEquals(1, statusTags.getChildren().size());
        Label statusLabel = (Label) statusTags.getChildren().get(0);
        assertEquals("PENDING", statusLabel.getText());

        Label itemsLabel = (Label) root.lookup("#items");
        assertNotNull(itemsLabel);
        String itemsText = itemsLabel.getText();
        org.junit.jupiter.api.Assertions.assertTrue(itemsText.contains(", "));

        Label datetimeLabel = (Label) root.lookup("#datetime");
        assertNotNull(datetimeLabel);
        assertEquals("At: 2026-03-11 10:15", datetimeLabel.getText());
    }

    private OrderCard createOrderCard(OrderMap order, int displayedIndex) throws Exception {
        final OrderCard[] cardRef = new OrderCard[1];
        runOnFxThreadAndWait(() -> cardRef[0] = new OrderCard(order, displayedIndex));
        return cardRef[0];
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
