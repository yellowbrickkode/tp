---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams are in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `deleteperson 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeletePersonCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeletePersonCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddPersonCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddPersonCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddPersonCommandParser`, `DeletePersonCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object) and all `OrderMap` objects (which are contained in a `UniqueOrderList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores the currently 'selected' `OrderMap` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<OrderMap>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `addperson n/David …​` to add a new person. The `addperson` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Logic.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

Similarly, how an undo operation goes through the `Model` component is shown below:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Model.png)

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `listperson`. Commands that do not modify the address book, such as `listperson`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

This product is for delivery workers of a restaurant in Central Singapore.

**Value proposition**:

Our platform streamlines logistics by tagging orders by region for efficient batch lookups, while providing visual analytics for trending items. While the app identifies customers within the same area for convenience, it does not provide specific route planning for delivery workers.

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                               | I want to …​                                       | So that I can…​                                                        |
| ------ |----------------------------------------------------------|-------------------------------------------------------|---------------------------------------------------------------------------|
| `* * *` | user                                                     | add a new contact with name, address and phone number | record the customer's details to the system.                              |
| `* * *` | user                                                     | delete a contact                                      | remove contacts from the system that no longer order from the restaurant. |
| `* * *` | user                                                     | add an order                                          | save the details of an order and the customer who ordered it.             |
| `* * *` | user                                                     | delete an order                                       | remove wrongly-keyed orders in the system.                                |
| `* * *` | restaurant employee                                      | mark an order as completed                            | keep track of which orders are completed.                                 |
| `* *`  | new user                                                 | follow the walkthrough                                | learn to use the main features of the app.                                |
| `* *`  | user                                                     | select an existing contact while adding an order      | save time by not retyping the same details.                               |
| `* *`  | user                                                     | add a scheduled order                                 | add an order for later, ahead of time.                                    |
| `* *`  | careless user                                            | undo deletion or edition of an order                  | restore the data.                                                         |
| `* *`  | restaurant delivery worker troubled by regional delivery | find all active orders with a specific region tag     | filter the orders and find all the orders in specific regions.            |
| `* *`  | restaurant delivery worker                               | sort orders by waiting time                           | prioritize deliveries that have been waiting too long.                    |
| `* *`  | restaurant manager                                       | see the statistics of the restaurant's orders         | see which menu items are the most popular.                                |
| `* *`  | restaurant manager                                       | keep track of the number of food items ordered        | know which items are favoured by customers.                               |
| `*`    | experienced user                                         | pin important orders                                  | easily view them later if required.                                       |
| `*`    | experienced user                                         | copy the customer's phone number                      | easily paste the number to Whatsapp to call the customer.                 |
| `*`    | user                                                     | be alerted if I add an identical contact              | avoid adding the same customer twice by mistake.                          |

*{More to be added}*


### Use cases

(For all use cases below, the **System** is the `Food Bridge` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Add a contact**

**MSS**

1.  User requests to add a contact, providing relevant information.
2.  Food Bridge adds the contact to the list.
3.  Food Bridge shows the list of persons.

    Use case ends.

**Extensions**

* 2a. The number given is invalid.

  * 2a1. Food Bridge shows an error message.

    Use case resumes at step 1.


* 2b. The address given is invalid.

  * 2b1. Food Bridge shows an error message.

    Use case resumes at step 1.


* 2c. No order is given.

  * 2c1. Food Bridge shows an error message.

    Use case resumes at step 1.


* 2d. Contact is a duplicate - all information exactly matches a contact already in the list.

  * 2d1. Food Bridge adds the order to the already-stored contact.

    Use case ends.



**Use case: Delete a contact**

**MSS**

1.  User requests to list persons.
2.  Food Bridge shows a list of persons.
3.  User requests to delete a specific person in the list.
4.  Food Bridge deletes the person.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. Food Bridge shows an error message.

      Use case resumes at step 2.


**Use case: List all contacts**

**MSS**

1.  User requests to list persons.
2.  Food Bridge shows a list of persons.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.


**Use case: List all orders**

**MSS**

1.  User requests to list orders.
2.  Food Bridge shows a list of orders, and the details of the customer who made the order.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

**Use case: Mark an order as completed**

**MSS**

1.  User requests to list orders.
2.  Food Bridge shows a list of orders, and the details of the customer who made the order.
3.  User requests to mark a specific order in the list as completed.
4.  Food Bridge marks the order as completed.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. Food Bridge shows an error message.

      Use case resumes at step 2.

**Use case: Clear all orders**

**MSS**

1.  User requests to clear all orders.
2.  Food Bridge removes all orders from the order list.
3.  Food Bridge shows the list of orders.

    Use case ends.

**Extensions**

* 2a. The order list is already empty.

    * 2a1. Food Bridge shows a message that there are no orders to clear.

      Use case ends.

*{More to be added}*

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  The application must be able to function completely offline as a standalone executable .jar file, without requiring a connection to an external database or the internet.
5.  The system should handle malformed commands (e.g., a delivery worker typing a wrong region format) by providing clear, actionable error messages rather than crashing.
6.  The system should safeguard against data corruption. If the application is closed abruptly, previously saved order data must remain intact.
7.  The system should be designed so that adding a new region (e.g., expanding from Central Singapore to the East) or a new delivery status does not require modifying the core execution logic.
8.  The application must not upload customer addresses or order details to any external cloud service or third-party server. All data must remain strictly on the delivery worker's local machine.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Contact**: An individual or business entity stored in the application for reference. In this project, a contact refers to a customer.
* **Orders**: In this project, customer orders are stored in a different list than the contact list.
* **Region Tag**: A geographical area used to categorise delivery locations. In this project, region refers specifically to one of the following values: North, North-East, West, East, Central.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample customers.
   
1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Viewing the Help Page
1. Viewing the help page
   1. Test case: `help`<br>
      Expected: A new window opens showing the help page.

### Adding a person

1. Adding a person while all customers are being shown
   1. Prerequisites: List all customers using the `listperson` command. Multiple customers in the list.
   2. Test case: `addperson n/John Doe p/98765432 a/111111 u/#01-01 r/N`<br>
      Expected: New contact is added to the list. Details of the new contact shown in the status message.
   3. Test case: `addperson n/John Doe p/98765432`<br>
      Expected: No new contact is added. Error details shown in the status message. Status bar remains the same.
   
### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `deleteperson 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `deleteperson 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `deleteperson`, `deleteperson x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

### Listing all persons
1. Listing all persons
   1. Prerequisites: App is launched with multiple persons in the list.
   2. Test case: `listperson`<br>
   Expected: All customers are displayed in the list.

### Editing a person
1. Editing a person with valid inputs
    1. Prerequisites: List all persons using the `listperson` command. Multiple persons in the list.
    2. Test case: `editperson 1 p/91234567 r/E`<br>
   Expected: First person’s phone number and region are updated.
    3. Test case: `editperson 2 n/Betsy Crower t/`<br>
   Expected: Second person’s name is updated and all tags are cleared.
    4. Test case: `editperson 3 a/123456 u/`<br>
     Expected: Third person’s address is updated and unit is cleared.
2. Editing a person with invalid inputs<br>
    1. Test case: `editperson 0 n/John`<br>
    Expected: No person is edited. Error message shown.
    2. Test case: `editperson 1 p/123`<br>
    Expected: No change. Error message due to invalid phone number.
    3. Other incorrect commands to try:
    `editperson`, `editperson x`, `editperson 1`<br>
    Expected: Similar to above.

### Finding persons by region
1. Finding persons with valid regions
    1. Prerequisites: List contains persons from different regions.
    2. Test case: `findperson N`<br>
    Expected: Persons in region N are displayed.
    3. Test case: `findperson N NE`<br>
    Expected: Persons in region N and NE are displayed.
2. Finding persons with invalid input
    1. Test case: `findperson X`<br>
    Expected: No persons found or error message shown.

### Adding an order
1. Adding an order with valid inputs
    1. Prerequisites: At least one person exists.
    2. Test case: `addorder c/1 o/2 5`<br>
    Expected: Order is added for customer 1.
    3. Test case: `addorder c/2 o/1 1 o/2 3 o/4 2`<br>
    Expected: Multiple items added to the order.
2. Adding an order with invalid inputs
    1. Test case: `addorder c/0 o/1 1`<br>
    Expected: No order added. Error message shown.
    2. Other incorrect commands to try:
    `addorder`, `addorder c/x`, `addorder c/1`<br>
    Expected: Similar to above.

### Deleting an order
1. Deleting an order
    1. Prerequisites: List all orders using `listorder`. Multiple orders exist.
    2. Test case: `deleteorder 1`<br>
    Expected: First order is deleted.
    3. Test case: `deleteorder 0`<br>
    Expected: No order deleted. Error message shown.
    4. Other incorrect commands to try:
    `deleteorder`, `deleteorder x`, `deleteorder 999`<br>
    Expected: Similar to above.

### Listing all orders
1. Listing all orders
    1. Prerequisites: Orders exist in the system.
    Test case: `listorder`<br>
    Expected: All orders are displayed.

### Editing an order
1. Editing an order with valid inputs
    1. Prerequisites: List all orders using listorder.
    2. Test case: `editorder 1 o/1 1 o/2 4`<br>
    Expected: Order is updated with new quantities.
    3. Test case: `editorder 2 o/2 0`<br>
    Expected: Menu item 2 is removed from the order.
2. Editing an order with invalid inputs
    1. Test case: `editorder 0 o/1 1`<br>
    Expected: No changes made. Error shown.
    2. Other incorrect commands:
    `editorder`, `editorder x`, `editorder 1`<br>
    Expected: Similar to above.

### Finding order by region
1. Finding orders with valid regions
    1. Prerequisites: List contains orders from different regions.
    2. Test case: `findorder r/N`<br>
    Expected: Orders in region N are displayed.

### Finding order by phone number
1. Finding orders with valid phone numbers
    1. Prerequisites: List contains orders from different customers.
    2. Test case: `findorder p/98765432`<br>
    Expected: Orders for customer with phone number 98765432 are displayed.

### Mark an order as completed
1. Marking an order as completed
    1. Prerequisites: List all orders using the `listorder` command. Multiple orders in the list.
    2. Test case: `complete 1`<br>
    Expected: First order is marked as completed. Status message reflects the update.
    3. Test case: `complete 0`<br>
    Expected: No order is updated. Error message shown.
    4. Other incorrect commands to try:
    `complete`, `complete x`, `complete 999`<br>
    Expected: Similar to above.

### Mark an order as completed by region
1. Marking orders as completed by region
    1. Prerequisites: List all orders using the `listorder` command. Multiple orders from different regions in the list.
    2. Test case: `completeregion r/N`<br>
    Expected: All orders in region N are marked as completed. Status message reflects the update.
    3. Test case: `completeregion r/X`<br>
    Expected: No orders updated. Error message shown.

### Clearing all orders
1. Clearing the order list
    1. Prerequisites: Orders exist in the system.
    2. Test case: `clearorder`<br>
    Expected: All orders are removed from the order list. Status message confirms clearing.
    3. Test case: `clearorder` when order list is already empty<br>
    Expected: No change to the list. Application remains stable

### Undoing changes
1. Undo last action
    1. Prerequisites: Perform a modifying command (e.g. `deleteperson 1`).
    2. Test case: `undo`<br>
    Expected: Last change is reverted.
    3. Test case: `undo` repeatedly<br>
    Expected: Steps back through history until no more actions to undo.

### Redoing changes
1. Redo last undone action
    1. Prerequisites: Perform `undo` first.
    2. Test case: `redo`<br>
    Expected: Last undone action is reapplied.
    3. Test case: `redo` with no undo history<br>
    Expected: Error message shown.

### Clearing all entries
1. Clearing all data
    1. Prerequisites: App contains persons and/or orders.
    2. Test case: `clear`<br>
    Expected: All entries are removed.

### Exiting the program
1. Exiting the program
    1. Prerequisites: The applcation is running.
    2. Test case: `exit`<br>
    Expected: The application closes.

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_

