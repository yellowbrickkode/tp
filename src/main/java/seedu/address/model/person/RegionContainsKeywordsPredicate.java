package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Region} matches any of the keywords given.
 */
public class RegionContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public RegionContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream().anyMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(person.getRegion().getValue().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RegionContainsKeywordsPredicate)) {
            return false;
        }

        RegionContainsKeywordsPredicate otherRegionContainsKeywordsPredicate =
                (RegionContainsKeywordsPredicate) other;
        return keywords.equals(otherRegionContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}

