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
        String expected = "Region: " + order.getPerson().getRegion();
        assertEquals(expected, regionLabel.getText());
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
