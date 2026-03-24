package seedu.address.logic.commands.order;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Edits the details of an existing order in the address book.
 */
public class EditOrderCommand extends Command {

    public static final String COMMAND_WORD = "editorder";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits an order by index. TODO";

    public EditOrderCommand() {}

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult("TODO: Implement edit order");
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
        return other == this || other instanceof EditOrderCommand;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
