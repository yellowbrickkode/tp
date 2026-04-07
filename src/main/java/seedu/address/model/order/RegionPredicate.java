package seedu.address.model.order;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Region;

/**
 * Tests that an {@code OrderMap}'s person's region matches the specified region.
 */
public class RegionPredicate implements Predicate<OrderMap> {
    private final Region region;

    public RegionPredicate(Region region) {
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

        if (!(other instanceof RegionPredicate)) {
            return false;
        }

        RegionPredicate otherPredicate = (RegionPredicate) other;
        return region.equals(otherPredicate.region);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("region", region).toString();
    }
}

