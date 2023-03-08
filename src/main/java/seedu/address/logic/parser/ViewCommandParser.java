package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input commands and creates a View Command.
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param userInput
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public ViewCommand parse(String userInput) throws ParseException {
        // shows user information
        if (userInput.isEmpty()) {
            return new ViewCommand();
        }
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(
                userInput, PREFIX_NAME, PREFIX_INDEX);
        // show user by querying name
        if (arePrefixesPresent(argumentMultimap, PREFIX_NAME)) {
            String name = argumentMultimap.getValue(PREFIX_NAME).get();
            return new ViewCommand(name);
        }
        // show user by querying index
        if (arePrefixesPresent(argumentMultimap, PREFIX_INDEX)) {
            try {
                int index = Integer.parseInt(argumentMultimap.getValue(PREFIX_INDEX).get());
                return new ViewCommand(Index.fromZeroBased(index));
            } catch (Exception e) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.USAGE));
            }
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.USAGE));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
