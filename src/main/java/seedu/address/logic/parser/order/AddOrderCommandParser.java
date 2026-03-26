package seedu.address.logic.parser.order;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMERIDX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDERS;

import java.util.Map;
import java.util.stream.Stream;

import seedu.address.logic.commands.order.AddOrderCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@link AddOrderCommand} object.
 */
public class AddOrderCommandParser implements Parser<AddOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddOrderCommand
     * and returns an {@link AddOrderCommand} object for execution.
     *
     * @param args The input arguments provided by the user.
     * @return A new {@code AddOrderCommand} containing the parsed customer index and order map.
     * @throws ParseException If the user input does not conform to the expected format or contains invalid values.
     */
    public AddOrderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args,
                        PREFIX_CUSTOMERIDX, PREFIX_ORDERS);

        if (!arePrefixesPresent(
                argMultimap,
                PREFIX_CUSTOMERIDX, PREFIX_ORDERS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CUSTOMERIDX);
        int index = Integer.parseInt(argMultimap.getValue(PREFIX_CUSTOMERIDX).get());
        Map<Integer, Integer> order = ParserUtil.parseOrders(argMultimap.getAllValues(PREFIX_ORDERS));

        return new AddOrderCommand(index, order);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

