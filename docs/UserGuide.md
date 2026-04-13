---
layout: page
title: User Guide
---

Food Bridge is a desktop app that helps restaurant delivery workers keep track of customer details and orders quickly and efficiently.
By using simple typed commands, you can manage everything faster than traditional point-and-click apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed on your computer. Use these installation guides to ensure you have the correct version:

   * [Windows installation guide](https://se-education.org/guides/tutorials/javaInstallationWindows.html)
   * [Mac installation guide](https://se-education.org/guides/tutorials/javaInstallationMac.html)
   * [Linux installation guide](https://se-education.org/guides/tutorials/javaInstallationLinux.html)

2. Download the latest version of the app from [here](https://github.com/AY2526S2-CS2103T-T12-4/tp/releases) (under Assets, look for the file ending with `.jar`). 

3. In your computer's file manager (e.g. File Explorer or Finder), create a new folder and copy the downloaded `.jar` file into it.

4. Open a command terminal.

   * **Windows users**: Press Win + R, type "cmd", and press Enter.
   * **Mac users**: Press Command + Space to open Spotlight search, type "Terminal", and press Enter.

5. Type <code>cd </code> (make sure to include the space), then drag the folder containing the `.jar` file into the terminal window. Press Enter to navigate to the folder.

6. Type `java -jar food-bridge-[version].jar` and press Enter to launch the application.<br>
    E.g. If you downloaded version 1.5, you should type `java -jar food-bridge-1.5.jar`.<br>
     A GUI similar to the below should appear. Note how the app contains some sample data.<br>
         ![Ui](images/Ui.png)

7. Type a command into the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.
    
8. If you are new to CLI apps, read [Command format](#command-format) first to understand how Food Bridge commands are structured.

9. Refer to the [Example Workflow](#example-workflow) for a quick introduction to the commands.

10. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Example Workflow

Here is an example workflow for a new user getting to know Food Bridge.

1. [**Clear existing data**](#clearing-all-entries--clear): Use `clear` to remove the sample data.

2. [**Add a customer**](#adding-a-customer-addperson): A new order comes from a customer:
   * Name: Jenny Tan
   * Phone number: 98765432
   * Postal code: 111111
   * Unit number: #01-01
   * Region: North.<br>
   Use `addperson n/Jenny Tan p/98765432 a/111111 u/#01-01 r/N` to add the new customer to the contact list.
   
3. [**Edit a customer**](#editing-a-customer--editperson): You realise the unit number is wrong. It should be #02-01.<br>
    Use `editperson 1 u/#02-01` to edit the contact. (`1` refers to the first customer in the list, i.e. Jenny Tan.)

4. [**Add an order**](#adding-an-order-addorder): The customer orders: 
   * Caesar Salad x1
   * Cafe Latte x2
   From the [menu](#menu), you check the item numbers:
   * Caesar Salad: item 5
   * Cafe Latte: item 8
   Use `addorder c/1 o/5 1 o/8 2` to add the order.
   
5. [**List all orders**](#listing-all-orders--listorder): Use `listorder` to see all orders.

6. [**Edit an order**](#editing-an-order--editorder): The customer changes Caesar Salad (item 5) to Smoked Salmon Bagel (item 6).<br>
    Use `editorder 1 o/5 0 o/6 1` to edit the order. (`1` refers to the first order in the list.)

7. [**Delete an order**](#deleting-an-order--deleteorder): Once the order is completed, use `deleteorder` to delete the order.

8. [**Find orders**](#finding-orders-by-phone-number-or-region--findorder): Use `findorder r/N` to list all orders (active and inactive) from the North region, or `findorder p/98765432` to list all orders (active and inactive) by phone number.

9. [**Complete orders by region**](#completing-all-orders-in-a-region--completeregion): Use `completeregion r/N` to mark all North region orders as completed.

10. [**Exit**](#exiting-the-program--exit): Use `exit` to close the application.

--------------------------------------------------------------------------------------------------------------------

## Features

#### Command format

The commands used in Food Bridge consist of the following parts:

`command [INDEX] [prefix/VALUE]…​`

1. The `command`, which controls the behaviour.
2. The `INDEX`, which identifies the relevant item in the list.
3. The `prefix`, which identifies a field in a customer or order.
4. The `VALUE`, which provides information for the field identified by the `prefix`.

Notes about the format:

* **Parameters**: Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `addperson n/NAME`, `NAME` is a parameter which can be used as `addperson n/John Doe`.

* **Optionals**: Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/member` or as `n/John Doe`.

* **Multiple parameters**: Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as `t/member`, `t/member t/staff`, or simply ignored.

* **Parameter order**: Apart from the `INDEX`, parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* **Commands with no parameters**: Extraneous parameters for commands that do not take in parameters (such as `help`, `listperson`, `listorder`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

--------------------------------------------------------------------------------------------------------------------

### **Customer Management Commands**

#### Customer contacts

Information about a customer is stored in a customer contact. Each contact has the following fields:

* **Name**: The name of the customer.
* **Phone number**: The phone number of the customer.
* **Address**: The address of the customer, identified by its postal code.
* **Unit number**: The optional unit number of the customer's address.
* **Region**: One of the five regions in Singapore, i.e. North, North East, West, East, and Central.
* **Tags**: Additional information about the customer, e.g. `member`

Below are some common parameters and their required formats.

| Parameter        | Format requirements                                              |
|------------------|------------------------------------------------------------------|
| `CUSTOMER_INDEX` | Must be a positive integer, e.g. 1, 2, 3, …​                     |
| `NAME`           | Must contain only alphabetical letters, numbers, and spaces.     |
| `PHONE_NUMBER`   | Must be exactly 8 digits long and start with either 6, 8, or 9.  |
| `POSTAL_CODE`    | Must be exactly 6 digits long.                                   |
| `UNIT`           | Must be in the form `#XX-XX` or `#XX-XXX`, where `X` is a digit. |
| `REGION`         | Must be either: `N`, `NE`, `W`, `E`, or `C`.                     |
| `TAG`            | Must contain only alphabetical letters and numbers.              |

#### Adding a customer: `addperson`

You can add a customer to the contact list.

Format: `addperson n/NAME p/PHONE_NUMBER a/POSTAL_CODE [u/UNIT_NUMBER] r/REGION [t/TAG]…​`

* Adding a unit number is optional.
* You can add zero or more tags to a customer.

Examples:
* `addperson n/John Doe p/98765432 a/111111 u/#01-01 r/N` adds a customer named `John Doe` with phone number `98765432`, postal code `111111`, unit number `#01-01`, in the `N` region to the contact list.
* `addperson n/Betsy Crowe p/87243155 a/110022 r/C t/member` adds a customer named `Betsy Crowe`, with phone number `87243155`, postal code `110022`, in the `C` region, tagged as a `member` to the contact list. 

![Adding a customer](images/addPersonExample.png)
*Figure: The new customer appears in the customer list after executing the command `addperson n/John Doe p/98765432 a/111111 u/#01-01 r/N`.*

#### Deleting a customer: `deleteperson`

You can delete the specified customer from the contact list.

Format: `deleteperson CUSTOMER_INDEX`

* Deletes the customer at the specified `CUSTOMER_INDEX`.
  * The index refers to the index number shown in the displayed contact list.
* **Warning:** This is a destructive action. If the customer has any existing orders, those associated orders are deleted together with the customer.

Examples:
* `deleteperson 2` deletes the 2nd customer in the contact list.
* `findperson N` followed by `deleteperson 1` deletes the 1st person displayed in the results of the `findperson` command.

#### Listing all customers: `listperson`

You can view a list of all customers in the contact list.

Format: `listperson`

#### Editing a customer: `editperson`

You can edit an existing customer in the contact list.

Format: `editperson CUSTOMER_INDEX [n/NAME] [p/PHONE_NUMBER] [a/POSTAL_CODE] [u/UNIT_NUMBER] [r/REGION] [t/TAG]…​`

* This will edit the customer at the specified `CUSTOMER_INDEX`. 
  * The index refers to the index number shown in the displayed contact list.
* You must provide at least one of the optional fields.
* Existing values will be updated with your input values.
* If you edit the customer's phone number, that customer's existing orders will be updated to use the new phone number.

Editing tags:
* If you edit the customer's tags, the existing tags will be overridden.
* You can remove all of the specified customer’s tags by using `t/` without
    specifying any tags after it.

Examples:
* `editperson 1 p/91234567 r/E` edits the phone number and region of the 1st person in the list to be `91234567` and `East` respectively.
* `editperson 2 n/Betsy Crower t/` edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.
* `editperson 3 a/123456 u/` edits the postal code of the 3rd person to be `123456` and clears the existing unit number.

#### Finding customers by region: `findperson`

You can search for customers who live in one of the given regions.

Format: `findperson REGION [MORE_REGIONS]…`

* The search is case-insensitive.<br/> e.g. `n` will match `N`
* The order of the regions does not matter.<br/> e.g. `NE W` will match both `W` and `NE`
* Only full region keywords will be matched.<br/> e.g. `N` will not match `NE`

Examples:
* `findperson N` displays customers who live in region `N`.
* `findperson NE W` displays customers who live in either region `NE` or `W`.

--------------------------------------------------------------------------------------------------------------------

### **Order Management Commands** 

#### Orders

Each order has the following fields:

* **Customer**: The customer who placed the order.
* **Items**: A list of items in the order. Each item includes:
  * **Menu item**: The food item, identified by its index in the menu.
  * **Quantity**: The number of units ordered for that item.

Below are some common parameters and their required formats.

| Parameter     | Format requirements                                                     |
|---------------|-------------------------------------------------------------------------|
| `ORDER_INDEX` | Must be a positive integer, e.g. 1, 2, 3, …​                            |
| `MENU_ITEM`   | Must be a positive integer and correspond to an item index on the menu. |
| `QUANTITY`    | Must be a positive integer.                                             |
| `PHONE_NUMBER`| Must be exactly 8 digits long and start with either 6, 8, or 9.         |

#### Menu

Below is the menu used in Food Bridge, consisting of each item's name and price.

1. Chicken Rice, $4.50
2. Mixed Rice, $4.50
3. Beef Udon, $7.00
4. Ice Cream, $2.50
5. Caesar Salad, $5.50
6. Smoked Salmon Bagel, $8.50
7. Apple Juice, $1.50
8. Cafe Latte, $3.00

#### Adding an order: `addorder`

You can add an order to the order list.

Format: `addorder c/CUSTOMER_INDEX o/MENU_ITEM QUANTITY [o/MENU_ITEM QUANTITY]…​`

* Adds an order for the customer at `CUSTOMER_INDEX` in the displayed contact list.
* You can add one or more menu items to the order.
  * To add multiple items in the same order, simply repeat the `o/` prefix.

Examples:
* `addorder c/1 o/2 5` adds an order of 5 units of menu item 2 for the first customer in the contact list.
* `addorder c/2 o/1 1 o/2 3 o/4 2` adds an order for the second customer, consisting of 1 unit of menu item 1, 3 units of menu item 2, and 2 units of menu item 4.

![Adding an order](images/addOrderExample.png)
*Figure: The new order appears in the order list after executing the command `addorder c/1 o/2 5`.*

#### Deleting an order: `deleteorder`

You can delete an order from the order list.

Format: `deleteorder ORDER_INDEX`

* Deletes the order at `ORDER_INDEX`.
  * The index refers to the index number shown in the displayed order list.

Examples:
* `deleteorder 3` deletes the 3rd order in the order list.

#### Listing all orders: `listorder`

You can view a list of all orders in the order list.

Format: `listorder`

#### Viewing current orders: `listcurrorder`

You can view a list of all incomplete (active) orders.

Format: `listcurrorder`

#### Viewing past orders: `listpastorder`

You can view a list of all completed orders.

Format: `listpastorder`

#### Finding orders by phone number or region: `findorder`

You can find orders by either customer phone number or customer region.

Format: `findorder [p/PHONE_NUMBER] [r/REGION]`

* Exactly one filter must be provided (either `p/PHONE_NUMBER` or `r/REGION`).
* The phone filter matches orders whose phone number exactly equals `PHONE_NUMBER`.
* The region filter matches orders whose customer's region exactly equals `REGION`.
* `findorder` works for both active and inactive orders.
* You can use `listorder` to show all orders again after filtering.

Examples:
* `findorder p/98765432` displays all orders (active and inactive) made by the customer with phone number `98765432`.
* `findorder r/N` displays all orders (active and inactive) made by customers in region `N`.

#### Deleting orders of a specific person: `deleteorderbyphone`

You can delete orders matching a specific customer phone number.

Format: `deleteorderbyphone PHONE_NUMBER`

* Deletes all orders whose customer's phone number exactly matches `PHONE_NUMBER`.

Examples:
* `deleteorderbyphone 98765432` deletes all orders made by the customer with phone number `98765432`.

#### Editing an order: `editorder`

You can edit an existing order in the order list.

Format: `editorder ORDER_INDEX o/MENU_ITEM QUANTITY [o/MENU_ITEM QUANTITY]…​` 

* Edits the order at the specified `ORDER_INDEX`. 
  * The index refers to the index number shown in the displayed order list.
* You can repeat the `o/` prefix to modify multiple menu items.
* For each specified menu item:
  * If it does not exist in the order, it will be added. 
  * If it already exists, its quantity will be updated. 
  * If the specified quantity is `0`, the item will be removed from the order.
* You cannot edit already completed orders.

Examples:
*  `editorder 1 o/1 1 o/2 4` edits the 1st order in the list to include 1 unit of menu item 1 and 4 units of menu item 2.
*  `editorder 2 o/3 0` edits the 2nd order to remove menu item 3 from the order.

#### Mark an order as completed: `complete`

You can mark an existing order as completed.

Format: `complete ORDER_INDEX`

* Marks the order at the specified `ORDER_INDEX` as completed.
  * The index refers to the index number shown in the displayed order list.

Examples:
* `complete 1` marks the 1st order in the list as completed.

![Marking an order as completed](images/completeOrderExample.png)

#### Completing all orders in a region : `completeregion`

You can mark all orders from one region as completed.

Format: `completeregion r/REGION`

* Marks all orders made by customers in the specified `REGION` as completed.
* This will not affect orders that are already completed.

Examples:
* `completeregion r/E` marks all orders made by customers who live in the East region as completed.

![Marking an order as completed by region](images/completeOrderRegionExample.png)
*Figure: Completing all orders for customers in the specified region marks the matching orders as completed in the order list.*

#### Clear Order List: `clearorder`

You can clear all orders from the order list.

Format: `clearorder`

--------------------------------------------------------------------------------------------------------------------

### **General Commands**

#### Viewing help: `help`

You can view a summary of commands.

![help message](images/helpWindow.png)

* You can also view the help window by pressing F1, or clicking the "Help" button in the menu bar.

Format: `help`

#### Undoing the last change: `undo`

You can undo the most recent change to the contact or order lists.

Format: `undo`

* Only commands that modify data can be undone.
* You can perform `undo` multiple times until there is no more history to undo.

Examples:
* `deleteperson 2` followed by `undo` restores the deleted person.

#### Redoing the last undone change: `redo`

You can redo the most recently undone change.

Format: `redo`

* You can only use `redo` after `undo` is used.
  * If there are no undone changes, `redo` will fail.
* You can perform `redo` multiple times until the latest state is reached.
* The redo history is cleared when new changes are made.

Examples:
* `deleteperson 2` followed by `undo` then `redo` deletes the 2nd person again.

#### Clearing all entries: `clear`

You can clear all entries from the contact list and order list.

Format: `clear`

#### Exiting the program: `exit`

You can close the application.

Format: `exit`

### **Data Storage**

#### Saving customer and order data

Food Bridge data is saved automatically after any command that modifies data. There is no need to save manually.

#### (Advanced) Updating the customer and order data file  

Food Bridge data is stored as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users may update the data directly by editing this file.

<div markdown="span" class="alert alert-warning"> :exclamation: **Caution:**
If your changes are invalid, Food Bridge will discard all data and start with an empty data file on the next run. Hence, it is recommended to back up the file before editing it.<br>
Furthermore, certain edits may cause Food Bridge to behave in unexpected ways (e.g., if a value is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Food Bridge home folder.

**Q**: Can I add multiple products in one order?<br>
**A**: Yes. Use the `addorder` command and add as many menu items as you want with the `o/` prefix.

**Q**: Can I use both `p/` and `r/` in one `findorder` command?<br>
**A**: No. `findorder` accepts only one filter at a time, either `p/PHONE_NUMBER` or `r/REGION`.

**Q**: Why does `deleteorderbyphone` not use `p/` like `findorder`?<br>
**A**: `deleteorderbyphone` takes a plain `PHONE_NUMBER` argument without a prefix. Example: `deleteorderbyphone 98765432`.

**Q**: What happens if I delete a customer with existing orders?<br>
**A**: Deleting a customer automatically deletes their corresponding orders.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.
3. **Double-clicking the `.jar` file may not work on some systems**, depending on system Java/file association settings. The workaround is to launch the app from a terminal using `java -jar food-bridge-[version].jar`.
4. **If the app is placed in a write-protected folder**, it may fail to save data and preferences correctly. The workaround is to move the app to a folder with write permissions.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action                             | Format, Examples                                                                                                                                              |
|------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add Customer**                   | `addperson n/NAME p/PHONE_NUMBER a/POSTAL_CODE [u/UNIT_NUMBER] r/REGION [t/TAG]…​` <br> e.g. `addperson n/John Doe p/98765432 a/111111 u/#01-01 r/N t/member` |
| **Delete Customer**                | `deleteperson CUSTOMER_INDEX` (deletes the customer and all associated orders) <br> e.g. `deleteperson 2`                                                    |
| **List Customers**                 | `listperson`                                                                                                                                                  |
| **Edit Customer**                  | `editperson CUSTOMER_INDEX [n/NAME] [p/PHONE_NUMBER] [a/POSTAL_CODE] [u/UNIT_NUMBER] [r/REGION] [t/TAG]…​` <br> e.g. `editperson 1 p/91234567 r/E`            |
| **Find Customers by Region**       | `findperson REGION [MORE_REGIONS]…` <br> e.g. `findperson N`                                                                                                  |
| **Add Order**                      | `addorder c/CUSTOMER_INDEX o/MENU_ITEM QUANTITY [o/MENU_ITEM QUANTITY]…​` <br> e.g. `addorder c/1 o/2 5`                                                      |
| **Delete Order**                   | `deleteorder ORDER_INDEX` <br> e.g. `deleteorder 3`                                                                                                           |
| **List Orders**                    | `listorder`                                                                                                                                                   |
| **List Current Orders**            | `listcurrorder`                                                                                                                                                |
| **List Past Orders**               | `listpastorder`                                                                                                                                                |
| **Edit Order**                     | `editorder ORDER_INDEX o/MENU_ITEM QUANTITY [o/MENU_ITEM QUANTITY]…​` <br> e.g. `editorder 1 o/1 1 o/2 4`                                                     |
| **Mark Order as Completed**        | `complete ORDER_INDEX` <br> e.g. `complete 1`                                                                                                                 |
| **Find Orders by Region or Phone** | `findorder [p/PHONE_NUMBER] [r/REGION]` (exactly one field must be provided; works for active and inactive orders) <br> e.g. `findorder r/N`, `findorder p/98765432`                       |
| **Complete Orders by Region**      | `completeregion r/REGION` <br> e.g. `completeregion r/NE`                                                                                                     |
| **Delete Orders by Phone**         | `deleteorderbyphone PHONE_NUMBER` <br> e.g. `deleteorderbyphone 98765432`                                                                                     |
| **Clear All Orders**               | `clearorder`                                                                                                                                                  |
| **View Help**                      | `help`                                                                                                                                                        |
| **Undo**                           | `undo`                                                                                                                                                        |
| **Redo**                           | `redo`                                                                                                                                                        |
| **Clear**                          | `clear`                                                                                                                                                       |
| **Exit**                           | `exit`                                                                                                                                                        |
