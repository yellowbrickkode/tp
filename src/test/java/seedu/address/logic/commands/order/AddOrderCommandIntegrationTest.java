package seedu.address.logic.commands.order;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.ModelManager;

public class AddOrderCommandIntegrationTest {

    @Test
    public void execute_returnsTodoMessage() throws Exception {
        CommandResult result = new AddOrderCommand().execute(new ModelManager());
        assertEquals("TODO: Implement add order", result.getFeedbackToUser());
    }
}
