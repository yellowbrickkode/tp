package seedu.address.testutil;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.order.OrderDateTime;
import seedu.address.model.order.OrderMap;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.order.ProductQuantityPair;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Region;

/**
 * A utility class to help with building Order objects.
 */
public class OrderBuilder {

    public static final String DEFAULT_ORDERID = "1";
    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_ADDRESS = "123456";
    public static final String DEFAULT_REGION = "N";
    public static final String DEFAULT_UNITNO = "#01-01";
    public static final String DEFAULT_STATUS = "PENDING";
    public static final String DEFAULT_ORDERDATETIME = "2007-12-03T10:15:30";

    private static final Person DEFAULT_PERSON = new Person(
            new Name(DEFAULT_NAME),
            new Phone(DEFAULT_PHONE),
            new Address(DEFAULT_ADDRESS, DEFAULT_UNITNO),
            new Region(DEFAULT_REGION),
            new HashSet<>());

    private static final Set<ProductQuantityPair> DEFAULT_ORDERMAP = new HashSet<>() {{
            add(new ProductQuantityPair("1 1"));
            add(new ProductQuantityPair("5 2"));
        }};

    private final Set<ProductQuantityPair> defaultOrderMap = new HashSet<>();

    private int orderId;
    private Person person;
    private Set<ProductQuantityPair> orders;
    private OrderStatus status;
    private OrderDateTime orderDateTime;
    private boolean useExplicitId;

    /**
     * Creates a {@code OrderBuilder} with the default details.
     */
    public OrderBuilder() {
        person = DEFAULT_PERSON;
        orders = DEFAULT_ORDERMAP;
        orderId = Integer.parseInt(DEFAULT_ORDERID);
        status = OrderStatus.valueOf(DEFAULT_STATUS);
        orderDateTime = new OrderDateTime(LocalDateTime.parse(DEFAULT_ORDERDATETIME));
        useExplicitId = false;
    }

    /**
     * Initializes the OrderBuilder with the data of {@code orderToCopy}.
     */
    public OrderBuilder(OrderMap orderToCopy) {
        person = orderToCopy.getPerson();
        orders = new HashSet<>(orderToCopy.getProductQuantityPairs());
        orderId = orderToCopy.getOrderId();
        status = orderToCopy.getStatus();
        orderDateTime = orderToCopy.getOrderDatetime();
        useExplicitId = true;
    }

    /**
     * Sets the {@code orderId} of the {@code OrderMap} we are building.
     */
    public OrderBuilder withOrderId(int orderId) {
        this.orderId = orderId;
        this.useExplicitId = true;
        return this;
    }

    /**
     * Sets the {@code person} of the {@code OrderMap} we are building.
     */
    public OrderBuilder withPerson(Person person) {
        this.person = person;
        return this;
    }

    /**
     * Parses the {@code orders} into a {@code Set<OrderMap>} and set it to the {@code Person} that we are building.
     */
    public OrderBuilder withOrderMap(String ... orders) {
        Set<ProductQuantityPair> map = new HashSet<>();
        for (String order : orders) {
            map.add(new ProductQuantityPair(order));
        }
        this.orders = map;
        return this;
    }

    /**
     * Sets the {@code status} of the {@code OrderMap} we are building.
     */
    public OrderBuilder withStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    /** Gets DEFAULT_PERSON. */
    public Person getDefaultPerson() {
        return DEFAULT_PERSON;
    }

    /** Gets DEFAULT_ORDERMAP. */
    public Set<ProductQuantityPair> getDefaultOrderMap() {
        return DEFAULT_ORDERMAP;
    }

    /**
     * Builds an {@code OrderMap} with the current builder state.
     */
    public OrderMap build() {
        if (useExplicitId) {
            return new OrderMap(orderId, person, orders, status, orderDateTime);
        }
        return new OrderMap(person, orders);
    }

}
