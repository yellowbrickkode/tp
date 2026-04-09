package seedu.address.logic.parser.order;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGION;

import java.util.stream.Stream;

import seedu.address.logic.commands.order.CompleteRegionOrdersCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Region;

/**
 * Parses input arguments and creates a new {@link CompleteRegionOrdersCommand} object.
 */
public class CompleteRegionOrdersCommandParser implements Parser<CompleteRegionOrdersCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CompleteRegionOrdersCommand
     * and returns a CompleteRegionOrdersCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CompleteRegionOrdersCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REGION);

        if (!arePrefixesPresent(argMultimap, PREFIX_REGION) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CompleteRegionOrdersCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_REGION);
        Region region = ParserUtil.parseRegion(argMultimap.getValue(PREFIX_REGION).get());

        return new CompleteRegionOrdersCommand(region);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

