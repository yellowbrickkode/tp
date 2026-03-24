package seedu.address.logic.commands.order;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Deletes an order identified using its displayed index from the address book.
 */
public class DeleteOrderCommand extends Command {

    public static final String COMMAND_WORD = "deleteorder";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an order by index. TODO";

    public DeleteOrderCommand() {}

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult("TODO: Implement delete order");
    }

    @Override
    public boolean shouldRecordInHistory() {
        return true;
    }

    @Override
    public boolean mutatesModel() {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this || other instanceof DeleteOrderCommand;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
