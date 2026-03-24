package seedu.address.logic.commands.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.ModelManager;

public class EditOrderCommandTest {

    @Test
    public void execute_returnsTodoMessage() throws Exception {
        CommandResult result = new EditOrderCommand().execute(new ModelManager());
        assertEquals("TODO: Implement edit order", result.getFeedbackToUser());
    }

    @Test
    public void equals_sameType_returnsTrue() {
        assertTrue(new EditOrderCommand().equals(new EditOrderCommand()));
    }
}
