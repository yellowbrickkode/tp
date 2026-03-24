package seedu.address.logic.commands.order;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Adds an order to the address book.
 */
public class AddOrderCommand extends Command {

    public static final String COMMAND_WORD = "addorder";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an order to the address book. TODO";

    public AddOrderCommand() {}

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult("TODO: Implement add order");
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
        return other == this || other instanceof AddOrderCommand;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
