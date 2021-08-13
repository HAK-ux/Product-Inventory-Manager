# My Personal Project

## Product Inventory Manager Application
**Proposal:** This application is a product inventory manager which would be used by anyone who wants to organize their product inventory in various ways. This application could be useful for manufacturing companies, stores and other businesses which have an inventory of products they want to sell. This project is of interest to me as it would allow me to apply my programming skills to create a desktop application which has practical use in the real world. 

### User Stories
- As a user, I want to be able to add a product to my product inventory.
- As a user, I want to be able to remove a product from my inventory.
- As a user, I want to be able to view my whole inventory.
- As a user, I want to be able to view the total value of my inventory.
- As a user, I want to be able to edit the price and quantity of any product in the inventory.
- As a user, I want to be able to save my product inventory to file.
- As a user, I want to be able to load my product inventory from file.

### Phase 4: Task 2
- Test and design a class in your model package that is robust.  You must have at least one method that throws a checked exception.  You must have one test for the case where the exception is expected and another where the exception is not expected:

In the Inventory class, the addProduct(Product product) method throws an InvalidIdException. This exception is caught in the InventoryEditor class (gui) in the addProduct() method and also in the InventoryManager class (console based application) in the processAddProduct(Inventory inventory) method (depending on which ui is used in the main class). This exception is tested in the InventoryTest class in the invalidIdExceptionTestExpected() method and in the invalidIdExceptionTestNotExpected() method.

### Phase 4: Task 3
If I had more time I would definitely refactor my code to be more cohesive and readable. Instead of implementing every aspect of the gui in the InventoryEditor class, I would instead make different classes for different tasks, and use the InventoryEditor class to compile all of these classes and make them work together. Some changes I would make include:
- I would make separate files for JFrame, JButton, JTextField, JTable and I would move the code relating to these classes from the InventoryEditor class to these classes.
- I also noticed some duplicated code in each of my actionPerformed methods for my JButttons. I would work to minimize this duplicated code as well as other duplicated code I can find.
- I would work to refactor my code and implement the iterator pattern in my Inventory and Product classes. Right now I am using a getInventory() method to return an ArrayList of products and I have now learnt that implementing the iterator pattern would be a better solution if I want to access the products in my inventory.


