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

In the Inventory class, the addProduct(Product product) method throws an InvalidIdException. This exception among many other exceptions, is caught in the InventoryEditor class (gui) in the addProduct() method and also in the InventoryManager class (console based application) in the processAddProduct(Inventory inventory) method. This exception is tested in the InventoryTest class in the invalidIdExceptionTestExpected() method and in the invalidIdExceptionTestNotExpected() method.


