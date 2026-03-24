package seedu.address.logic.commands.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.ModelManager;

public class DeleteOrderCommandTest {

    @Test
    public void execute_returnsTodoMessage() throws Exception {
        CommandResult result = new DeleteOrderCommand().execute(new ModelManager());
        assertEquals("TODO: Implement delete order", result.getFeedbackToUser());
    }

    @Test
    public void equals_sameType_returnsTrue() {
        assertTrue(new DeleteOrderCommand().equals(new DeleteOrderCommand()));
    }
}
