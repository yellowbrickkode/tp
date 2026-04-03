package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.control.Label;
import seedu.address.model.order.OrderMap;
import seedu.address.model.person.Person;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.PersonBuilder;

public class OrderCardTest {

    private static final AtomicBoolean TOOLKIT_INITIALIZED = new AtomicBoolean(false);

    @BeforeAll
    public static void initToolkit() {
        if (TOOLKIT_INITIALIZED.compareAndSet(false, true)) {
            System.setProperty("javafx.platform", "Monocle");
            System.setProperty("monocle.platform", "Headless");
            System.setProperty("prism.order", "sw");
            try {
                Platform.startup(() -> { });
            } catch (IllegalStateException | UnsupportedOperationException e) {
                // Toolkit already initialized or not available in this environment.
            }
        }
    }

    @Test
    public void constructor_setsRegionLabel() throws Exception {
        Person person = new PersonBuilder().withName("Order Person").withRegion("N").build();
        OrderMap order = new OrderBuilder().withPerson(person).withOrderId(1).build();

        OrderCard orderCard = new OrderCard(order, 1);
        Label region = (Label) getPrivateField(orderCard, "region");
        assertEquals("Region: N", region.getText());
    }

    private static Object getPrivateField(Object target, String fieldName) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(target);
    }
}
