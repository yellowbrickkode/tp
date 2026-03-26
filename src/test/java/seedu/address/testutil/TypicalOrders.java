package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.order.OrderMap;

/**
 * A utility class containing a list of {@code Order} objects to be used in tests.
 */

public class TypicalOrders {

    public static final OrderMap ALICE_ORDER;

    static {
        try {
            ALICE_ORDER = new OrderBuilder().withPerson(TypicalPersons.ALICE)
                    .withOrderMap(List.of("1 2", "2 4")).build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static final OrderMap BENSON_ORDER;

    static {
        try {
            BENSON_ORDER = new OrderBuilder().withPerson(TypicalPersons.BENSON)
                    .withOrderMap(List.of("2 1", "3 6", "1 1")).build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static final OrderMap CARL_ORDER;

    static {
        try {
            CARL_ORDER = new OrderBuilder().withPerson(TypicalPersons.CARL)
                    .withOrderMap(List.of("1 1")).build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static final OrderMap DANIEL_ORDER;

    static {
        try {
            DANIEL_ORDER = new OrderBuilder().withPerson(TypicalPersons.DANIEL)
                    .withOrderMap(List.of("4 1")).build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static final OrderMap ELLE_ORDER;

    static {
        try {
            ELLE_ORDER = new OrderBuilder().withPerson(TypicalPersons.ELLE)
                    .withOrderMap(List.of("4 4")).build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static final OrderMap FIONA_ORDER;

    static {
        try {
            FIONA_ORDER = new OrderBuilder().withPerson(TypicalPersons.FIONA)
                    .withOrderMap(List.of("5 1")).build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static final OrderMap GEORGE_ORDER;

    static {
        try {
            GEORGE_ORDER = new OrderBuilder().withPerson(TypicalPersons.GEORGE)
                    .withOrderMap(List.of("2 2", "4 2")).build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    // Manually added
    public static final OrderMap HOON_ORDER;

    static {
        try {
            HOON_ORDER = new OrderBuilder().withPerson(TypicalPersons.HOON)
                    .withOrderMap(List.of("1 1", "2 2")).build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static final OrderMap IDA_ORDER;

    static {
        try {
            IDA_ORDER = new OrderBuilder().withPerson(TypicalPersons.IDA)
                    .withOrderMap(List.of("3 2", "4 3")).build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final OrderMap AMY_ORDER;

    static {
        try {
            AMY_ORDER = new OrderBuilder().withPerson(TypicalPersons.AMY)
                    .withOrderMap(List.of("3 2", "4 4")).build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static final OrderMap BOB_ORDER;

    static {
        try {
            BOB_ORDER = new OrderBuilder().withPerson(TypicalPersons.BOB)
                    .withOrderMap(List.of("1 2", "3 1")).build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private TypicalOrders() {} // prevents instantiation

    /**
     * Returns an AddressBook with typical orders.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();

        ab.addPerson(TypicalPersons.ALICE);
        ab.addPerson(TypicalPersons.BENSON);
        ab.addPerson(TypicalPersons.CARL);
        ab.addPerson(TypicalPersons.DANIEL);

        for (OrderMap order : getTypicalOrders()) {
            ab.addOrder(order);
        }

        return ab;
    }

    public static List<OrderMap> getTypicalOrders() {
        return new ArrayList<>(
                Arrays.asList(
                        ALICE_ORDER,
                        BENSON_ORDER,
                        CARL_ORDER,
                        DANIEL_ORDER,
                        ELLE_ORDER,
                        FIONA_ORDER,
                        GEORGE_ORDER
                )
        );
    }
}
