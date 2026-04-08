---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* Base project structure and several core patterns were adapted from [se-edu AddressBook-Level3 (AB3)](https://github.com/se-edu/addressbook-level3).
* Undo/redo state-management logic was reused and adapted from [se-edu AddressBook-Level4 (AB4)](https://github.com/se-edu/addressbook-level4), primarily in `VersionedAddressBook` and related model integration.
* Some JavaFX-related code ideas were adapted from Marco Jakob's tutorial: [JavaFX 8 Tutorial](http://code.makery.ch/library/javafx-8-tutorial/).
* Libraries used: JavaFX, Jackson, JUnit5.

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

Given below is a quick overview of the main components and how they interact with each other.

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

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `deleteperson 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class, which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class, which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside components from being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts, e.g., `CommandBox`, `ResultDisplay`, `PersonListPanel`, `OrderListPanel`, `StatusBarFooter`, etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class, which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFX UI framework. The layout of these UI parts is defined in matching `.fxml` files in the `src/main/resources/view` folder. For example, the layout of the `MainWindow` is specified in `MainWindow.fxml`.

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("deleteperson 1")` API call as an example.

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
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name, e.g., `AddPersonCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddPersonCommand`), which the `AddressBookParser` returns as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddPersonCommandParser`, `DeletePersonCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible, e.g., during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="650" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object) and all `OrderMap` objects (which are contained in a `UniqueOrderList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list, which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be observed. For example, the UI can be bound to this list so that it automatically updates when the data in the list change.
* stores the currently 'selected' `OrderMap` objects (e.g., results of a search query) as a separate _filtered_ list, which is exposed to outsiders as an unmodifiable `ObservableList<OrderMap>` that can be observed. For example, the UI can be bound to this list so that it automatically updates when the data in the list change.
* stores a `UserPrefs` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPrefs` object.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components).

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefsStorage`, which means it can be treated as either one (if only one set of functionality is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`).

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, DevOps**

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
| -------- | ---------------------------------------------------------- | ------------------------------------------------------- | --------------------------------------------------------------------------- |
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

* **Mainstream OS**: Windows, Linux, Unix, macOS
* **Contact**: An individual or business entity stored in the application for reference. In this project, a contact refers to a customer.
* **Orders**: In this project, customer orders are stored in a different list than the contact list.
* **Region Tag**: A geographical area used to categorise delivery locations. In this project, region refers specifically to one of the following values: North, North-East, West, East, Central.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Effort**

Compared with AB3, this project required substantially more effort in domain modeling and command behavior because we support multiple entity types (`Person` and `OrderMap`) and their interactions, rather than a single main entity.

The overall difficulty was moderate-to-high: beyond CRUD-style commands, we needed to maintain consistency between contact and order data, support additional filtering flows, and keep UI behavior intuitive when switching between person and order views.

Main challenges included designing data structures for orders (status, datetime, product-quantity pairs), preventing invalid cross-entity states, and updating parser/logic/model/UI layers in a synchronized way without regressions.

Estimated effort was front-loaded into architecture adaptation and integration testing, then shifted to incremental feature delivery and bug fixing. A significant portion of effort (well above 5%) was saved through reuse:
* AB3 scaffold and architecture conventions accelerated setup, component boundaries, and documentation style.
* AB4 undo/redo approach reduced implementation risk; our adaptation work is mainly in `VersionedAddressBook`, `ModelManager`, and undo/redo command integration.

Key achievements include extending the model to support orders with richer attributes, preserving clean layering across components, and delivering a working CLI-driven workflow for both contact and order management while keeping documentation and diagrams aligned with implementation.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file.<br>
      Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `deleteperson 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `deleteperson 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `deleteperson`, `deleteperson x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
