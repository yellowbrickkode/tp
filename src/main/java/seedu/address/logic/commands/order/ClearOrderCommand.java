package seedu.address.logic.commands.order;

import static java.util.Objects.requireNonNull;

import java.util.Collections;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears all orders in the address book without affecting persons.
 */
public class ClearOrderCommand extends Command {

    public static final String COMMAND_WORD = "clearorder";
    public static final String MESSAGE_SUCCESS = "Orders have been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        AddressBook clearedAddressBook = new AddressBook(model.getAddressBook());
        clearedAddressBook.setOrders(Collections.emptyList());
        model.setAddressBook(clearedAddressBook);

        return new CommandResult(MESSAGE_SUCCESS, false, false, false, false);
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
        return other instanceof ClearOrderCommand;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
