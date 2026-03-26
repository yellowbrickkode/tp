package seedu.address.testutil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.OrderMap;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Region;

/**
 * A utility class to help with building Order objects.
 */
public class OrderBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_ADDRESS = "123456";
    public static final String DEFAULT_REGION = "N";
    public static final String DEFAULT_UNITNO = "#01-01";
    public static final int DEFAULT_PRODUCT = 1;
    public static final int DEFAULT_QUANTITY = 2;

    private final Person DefaultPerson = new Person(
            new Name(DEFAULT_NAME),
            new Phone(DEFAULT_PHONE),
            new Address(DEFAULT_ADDRESS, DEFAULT_UNITNO),
            new Region(DEFAULT_REGION),
            new HashSet<>());

    private Map<Integer, Integer> DefaultOrderMap = new HashMap<>(DEFAULT_PRODUCT, DEFAULT_QUANTITY);

    private Person person;
    private Map<Integer, Integer> orders;

    /**
     * Creates a {@code OrderBuilder} with the default details.
     */
    public OrderBuilder() {
        person = getDefaultPerson();
        orders = getDefaultOrderMap();
    }

    /**
     * Initializes the OrderBuilder with the data of {@code orderToCopy}.
     */
    public OrderBuilder(OrderMap orderToCopy) {
        person = orderToCopy.getPerson();
        orders = new HashMap<>(orderToCopy.getOrderMap());
    }

    /**
     * Sets the {@code Person} of the {@code OrderMap} that we are building.
     */
    public OrderBuilder withPerson(String name, String phone, String address, String unit, String region, String tags) {
        this.person = new Person(
                new Name(name),
                new Phone(phone),
                new Address(address, unit),
                new Region(region),
                new HashSet<>());
        return this;
    }

    /**
     * Sets the {@code Person} of the {@code OrderMap} that we are building.
     */
    public OrderBuilder withPerson(Person person) {
        this.person = person;
        return this;
    }

    /**
     * Sets the OrderMap of the OrderMap that OrderBuilder is building.
     */
    public OrderBuilder withOrderMap(List<String> orders) throws ParseException {
        this.orders = ParserUtil.parseOrders(orders);
        return this;
    }

    /** Gets DEFAULT_PERSON. */
    public Person getDefaultPerson() {
        return DefaultPerson;
    }


    /** Gets DEFAULT_ORDERMAP. */
    public Map<Integer, Integer> getDefaultOrderMap() {
        return DefaultOrderMap;
    }

    public OrderMap build() {
        return new OrderMap(person, orders);
    }

}
