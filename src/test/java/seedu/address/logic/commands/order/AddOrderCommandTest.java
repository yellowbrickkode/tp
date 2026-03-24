package seedu.address.logic.commands.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.ModelManager;

public class AddOrderCommandTest {

    @Test
    public void execute_returnsTodoMessage() throws Exception {
        CommandResult result = new AddOrderCommand().execute(new ModelManager());
        assertEquals("TODO: Implement add order", result.getFeedbackToUser());
    }

    @Test
    public void equals_sameType_returnsTrue() {
        assertTrue(new AddOrderCommand().equals(new AddOrderCommand()));
    }
}
