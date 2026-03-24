package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.person.EditPersonCommand.EditPersonDescriptor;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Region;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setPostalCode(person.getAddress().getPostalCode());
        descriptor.setUnitNo(person.getAddress().getUnit());
        descriptor.setRegion(person.getRegion());
        descriptor.setOrder(new ArrayList<>(person.getOrders()));
        descriptor.setTags(person.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code String postalCode} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPostalCode(String postalCode) {
        descriptor.setPostalCode(postalCode);
        return this;
    }

    /**
     * Sets the {@code String unitNo} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withUnitNo(String unitNo) {
        descriptor.setUnitNo(unitNo);
        return this;
    }

    /**
     * Sets the {@code Region} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withRegion(String region) {
        descriptor.setRegion(new Region(region));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    /**
     * Sets the {@code orders} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withOrders(String... orders) {
        descriptor.setOrder(new ArrayList<>(Arrays.asList(orders)));
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
