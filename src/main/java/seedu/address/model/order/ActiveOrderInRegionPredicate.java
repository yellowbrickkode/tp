package seedu.address.model.order;

import java.util.Objects;
import java.util.function.Predicate;

import seedu.address.model.person.Region;

/**
 * Tests that an {@code OrderMap} is active and belongs to the specified region.
 */
public class ActiveOrderInRegionPredicate implements Predicate<OrderMap> {

    private final Region region;

    /**
     * Creates a predicate that matches active orders for the given region.
     */
    public ActiveOrderInRegionPredicate(Region region) {
        this.region = region;
    }

    @Override
    public boolean test(OrderMap order) {
        return order.getPerson().getRegion().equals(region)
                && order.getStatus() == OrderStatus.PENDING;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ActiveOrderInRegionPredicate)) {
            return false;
        }

        ActiveOrderInRegionPredicate otherPredicate = (ActiveOrderInRegionPredicate) other;
        return region.equals(otherPredicate.region);
    }

    @Override
    public int hashCode() {
        return Objects.hash(region);
    }
}

