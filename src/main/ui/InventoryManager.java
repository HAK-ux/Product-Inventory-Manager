package ui;

import exceptions.*;
import model.Inventory;
import model.Product;

import java.util.Arrays;
import java.util.Scanner;

// Inventory manager application
public class InventoryManager {
    private Scanner input;
    private Inventory inventory;

    // EFFECTS: Runs the inventory manager application.
    public InventoryManager() {
        runInventoryManager();
    }

    // MODIFIES: this
    // EFFECTS: Processes user input.
    private void runInventoryManager() {
        boolean running = true;

        input = new Scanner(System.in);
        inventory = new Inventory();

        while (running) {
            displayMenu();
            String command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                running = false;
            } else {
                processCommand(command);
            }
        }
    }

    private void displayMenu() {
        System.out.println("\nChoose one of the following:");
        System.out.println("\ti -> View your inventory.");
        System.out.println("\ta -> Add product to inventory.");
        System.out.println("\tr -> Remove product from inventory.");
        System.out.println("\tv -> View the total value of your inventory.");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        switch (command) {
            case "i":
                processViewInventory();
                break;
            case "a":
                processAddProduct(inventory);
                break;
            case "r":
                processRemoveProduct(inventory);
                break;
            case "v":
                processViewValue();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    private void processViewInventory() {
        System.out.println("Name | ID | Ctg. | Price | Qty.");
        for (Product product : inventory.getInventory()) {
            System.out.println("\n" + product.getName() + " | " + product.getId() + " | " + product.getCtg() + " | "
                    + product.getPrice() + " | " + product.getQty());
        }



    }

    // EFFECTS: Gets the product from the user.
    private Product getProduct() {
        input = new Scanner(System.in);

        System.out.println("Product Name: ");
        String name = input.next();
        System.out.println("Unique ID Number: ");
        int id = input.nextInt();
        System.out.println("Product Category: ");
        String ctg = input.next();
        System.out.println("Product Price: ");
        double price = input.nextDouble();
        System.out.println("Product Quantity: ");
        int qty = input.nextInt();

        try {
            return new Product(name, id, ctg, price, qty);
        } catch (ZeroNameLengthException e) {
            System.err.println("Please enter a valid name.");
        } catch (InvalidQtyException e) {
            System.err.println("Please enter a valid quantity.");
        } catch (InvalidPriceException e) {
            System.err.println("Please enter a valid price.");
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: Processes the adding of a product to the inventory.
    private void processAddProduct(Inventory inventory)  {
        Product product = getProduct();
        try {
            inventory.addProduct(product);
        } catch (InvalidIdException e) {
            System.err.println("Product ID already exists, please enter a unique ID number.");
        }
    }

    // MODIFIES: this
    // EFFECTS: Processes the adding of a product to the inventory.
    private void processRemoveProduct(Inventory inventory) {
        input = new Scanner(System.in);

        System.out.println("Product Unique ID Number: ");
        int id = input.nextInt();

        try {
            inventory.removeProduct(inventory.getProductGivenId(id));
        } catch (InvalidProductException e) {
            System.err.println("This product does not exist.");
        }

    }

    // EFFECTS: Processes and prints the value of the inventory.
    private void processViewValue() {
        System.out.println("The total value of your inventory is " + inventory.getValue() + ".");
    }


}
