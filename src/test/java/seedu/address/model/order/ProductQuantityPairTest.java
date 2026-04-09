package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.person.Phone;
import seedu.address.testutil.Assert;

public class ProductQuantityPairTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    void constructor_invalidValue_throwsIllegalArgumentException() {
        String invalidValue = "";
        assertThrows(IllegalArgumentException.class, () -> new ProductQuantityPair(invalidValue));
    }

    @Test
    void constructor_invalidProduct_throwsIllegalArgumentException() {
        String invalidItem = "0 1";
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_MENU_ITEM, 0);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> new ProductQuantityPair(invalidItem));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void constructor_invalidQuantity_throwsIllegalArgumentException() {
        String invalidQuantity = "1 -1";
        String expectedMessage = Quantity.MESSAGE_CONSTRAINTS;
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> new ProductQuantityPair(invalidQuantity));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void isValidProductQuantityPair_invalidValue_throwsIllegalArgumentException() {
        // null pair
        Assert.assertThrows(NullPointerException.class, () -> ProductQuantityPair.isValidProductQuantityPair(null));
    }

    @Test
    void getProduct() {
        ProductQuantityPair pair = new ProductQuantityPair("1 1");
        Product expectedProduct = Messages.MENU.getItem(1);
        assertEquals(expectedProduct, pair.getProduct());
    }

    @Test
    void getQuantity() {
        ProductQuantityPair pair = new ProductQuantityPair("1 1");
        Quantity expectedQuantity = new Quantity("1");
        assertEquals(expectedQuantity, pair.getQuantity());
    }

    @Test
    void toStringMethod() {
        String value = "1 1";
        ProductQuantityPair pair = new ProductQuantityPair(value);
        assertEquals(value, pair.toString());
    }

    @Test
    void equals_sameObject_returnsTrue() {
        ProductQuantityPair pair = new ProductQuantityPair("1 1");
        assertEquals(pair, pair);
    }

    @Test
    void equals_null_returnsFalse() {
        ProductQuantityPair pair = new ProductQuantityPair("1 1");
        assertNotEquals(pair, null);
    }

    @Test
    void equals_returnsTrue() {
        ProductQuantityPair pair1 = new ProductQuantityPair("1 1");
        ProductQuantityPair pair2 = new ProductQuantityPair("1 1");
        assertEquals(pair1, pair2);
    }

    @Test
    void equals_hashSetSameOrder_returnsTrue() {
        Set<ProductQuantityPair> set1 = new HashSet<>();
        set1.add(new ProductQuantityPair("1 1"));
        set1.add(new ProductQuantityPair("2 2"));
        set1.add(new ProductQuantityPair("3 3"));

        Set<ProductQuantityPair> set2 = new HashSet<>();
        set2.add(new ProductQuantityPair("1 1"));
        set2.add(new ProductQuantityPair("2 2"));
        set2.add(new ProductQuantityPair("3 3"));

        assertEquals(set1, set2);
    }

    @Test
    void equals_hashSetDifferentOrder_returnsTrue() {
        Set<ProductQuantityPair> set1 = new HashSet<>();
        set1.add(new ProductQuantityPair("1 1"));
        set1.add(new ProductQuantityPair("2 2"));
        set1.add(new ProductQuantityPair("3 3"));

        Set<ProductQuantityPair> set2 = new HashSet<>();
        set2.add(new ProductQuantityPair("3 3"));
        set2.add(new ProductQuantityPair("2 2"));
        set2.add(new ProductQuantityPair("1 1"));

        assertEquals(set1, set2);
    }

    @Test
    void equals_differentProduct_returnsFalse() {
        ProductQuantityPair pair1 = new ProductQuantityPair("1 1");
        ProductQuantityPair pair2 = new ProductQuantityPair("2 1");
        assertNotEquals(pair1, pair2);
    }

    @Test
    void equals_differentQuantity_returnsFalse() {
        ProductQuantityPair pair1 = new ProductQuantityPair("1 1");
        ProductQuantityPair pair2 = new ProductQuantityPair("1 2");
        assertNotEquals(pair1, pair2);
    }

    @Test
    void compareTo() {
        ProductQuantityPair pair1 = new ProductQuantityPair("1 1");
        ProductQuantityPair pair2 = new ProductQuantityPair("2 2");
        assertEquals(-1, pair1.compareTo(pair2));
        assertEquals(1, pair2.compareTo(pair1));

        ProductQuantityPair pair3 = new ProductQuantityPair("1 10");
        assertEquals(0, pair1.compareTo(pair3));
    }
}
