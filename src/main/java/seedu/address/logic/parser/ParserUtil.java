package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.order.AddOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.ProductQuantityPair;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Region;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String postalCode, String unit) throws ParseException {
        String trimmedPostalCode = parsePostalCode(postalCode);
        if (unit.isBlank()) {
            return new Address(trimmedPostalCode);
        } else {
            String trimmedUnit = unit.trim();
            if (!Address.isValidUnit(trimmedUnit)) {
                throw new ParseException(Address.MESSAGE_CONSTRAINTS_UNIT);
            }
            return new Address(trimmedPostalCode, trimmedUnit);
        }
    }

    /**
     * Parses a {@code String postalCode} into a valid {@code String postalCode}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code postalCode} is invalid.
     */
    public static String parsePostalCode(String postalCode) throws ParseException {
        requireNonNull(postalCode);
        String trimmedPostalCode = postalCode.trim();
        if (!Address.isValidAddress(trimmedPostalCode)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return trimmedPostalCode;
    }

    /**
     * Parses a {@code String unitNo} into a valid {@code String unitNo}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code unitNo} is invalid.
     */
    public static String parseUnitNo(String unitNo) throws ParseException {
        requireNonNull(unitNo);
        if (unitNo.isBlank()) {
            return "";
        }
        String trimmedUnitNo = unitNo.trim();
        if (!Address.isValidUnit(trimmedUnitNo)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS_UNIT);
        }
        return trimmedUnitNo;
    }

    /**
     * Parses a {@code String region} into a {@code Region}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code region} is invalid.
     */
    public static Region parseRegion(String region) throws ParseException {
        requireNonNull(region);
        String trimmedRegion = region.trim();
        if (!Region.isValidRegion(trimmedRegion)) {
            throw new ParseException(Region.MESSAGE_CONSTRAINTS);
        }
        return new Region(trimmedRegion);
    }

    /**
     * Parses a single {@code String order} into a {@code Map<Integer, Integer> orderMap}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Map<Integer, Integer> parseOrder(String order) {
        requireNonNull(order);
        Map<Integer, Integer> orderMap = new HashMap<>();
        String trimmedOrder = order.trim();
        int menuItem = Integer.parseInt(trimmedOrder.split(" ")[0]);
        int quantity = Integer.parseInt(trimmedOrder.split(" ")[1]);
        orderMap.put(menuItem, quantity);
        return orderMap;
    }
    /**
     * Parses a List of {@code String order} into a {@code Set<ProductQuantityPair> itemSet}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Set<ProductQuantityPair> parseOrders(List<String> orders) throws ParseException {
        requireNonNull(orders);
        Set<ProductQuantityPair> itemSet = new HashSet<>();
        for (String order : orders) {
            String trimmedOrder = order.trim();

            if (!ProductQuantityPair.isValidProductQuantityPair(trimmedOrder)) {
                throw new ParseException(ProductQuantityPair.MESSAGE_CONSTRAINTS);
            }

            ProductQuantityPair productQuantityPair;

            try {
                productQuantityPair = new ProductQuantityPair(trimmedOrder);
            } catch (IllegalArgumentException e) {
                throw new ParseException(e.getMessage());
            }

            itemSet.add(productQuantityPair);
        }
        return itemSet;
    }

    /**
     * Parses a List of {@code String order} into a {@code Set<ProductQuantityPair> itemSet}.
     * Quantities should be positive.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Set<ProductQuantityPair> parseOrdersPositiveQuantity(List<String> orders) throws ParseException {
        Set<ProductQuantityPair> itemSet = parseOrders(orders);
        for (ProductQuantityPair item : itemSet) {
            if (item.getQuantity().getValue() <= 0) {
                throw new ParseException(AddOrderCommand.MESSAGE_ZERO_QUANTITY);
            }
        }
        return itemSet;
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
