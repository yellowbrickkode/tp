package seedu.address.logic.parser.order;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGION;

import java.util.stream.Stream;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.order.FindOrderByPhoneNumberCommand;
import seedu.address.logic.commands.order.FindOrderByRegionCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.PhoneNumberPredicate;
import seedu.address.model.order.RegionPredicate;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Region;

/**
 * Parses input arguments for the {@code findorder} command and creates a
 * {@link seedu.address.logic.commands.order.FindOrderByPhoneNumberCommand} when {@code p/} is supplied.
 */
public class FindOrderByPredicateCommandParser implements Parser<Command> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code findorder} command
     * and returns a FindOrderByPhoneNumberCommand or FindOrderByRegionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PHONE, PREFIX_REGION);

        if (!isAnyPrefixPresent(argMultimap, PREFIX_PHONE, PREFIX_REGION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOrderByPhoneNumberCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PHONE, PREFIX_REGION);
        boolean hasPhone = argMultimap.getValue(PREFIX_PHONE).isPresent();
        boolean hasRegion = argMultimap.getValue(PREFIX_REGION).isPresent();

        if (hasPhone && hasRegion) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOrderByPhoneNumberCommand.MESSAGE_USAGE));
        }

        if (hasPhone) {
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
            return new FindOrderByPhoneNumberCommand(new PhoneNumberPredicate(phone.value));
        }

        if (hasRegion) {
            Region region = ParserUtil.parseRegion(argMultimap.getValue(PREFIX_REGION).get());
            return new FindOrderByRegionCommand(new RegionPredicate(region));
        }

        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOrderByPhoneNumberCommand.MESSAGE_USAGE));
    }

    /**
     * Returns true if at least one prefix contains a non-empty {@code Optional} value in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isAnyPrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
