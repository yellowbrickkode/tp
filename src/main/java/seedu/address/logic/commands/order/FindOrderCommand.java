package seedu.address.logic.commands.order;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Finds and lists orders in the address book matching keywords.
 */
public class FindOrderCommand extends Command {

    public static final String COMMAND_WORD = "findorder";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds orders by keywords. TODO";

    public FindOrderCommand() {}

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult("TODO: Implement find order");
    }

    @Override
    public boolean equals(Object other) {
        return other == this || other instanceof FindOrderCommand;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
