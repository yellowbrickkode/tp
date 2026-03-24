package seedu.address.logic.commands.order;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists all orders in the address book.
 */
public class ListOrderCommand extends Command {

    public static final String COMMAND_WORD = "listorders";
    public static final String MESSAGE_SUCCESS = "Listed all orders. TODO";

    public ListOrderCommand() {}

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult("TODO: Implement list orders");
    }

    @Override
    public boolean equals(Object other) {
        return other == this || other instanceof ListOrderCommand;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
