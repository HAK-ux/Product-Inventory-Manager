package ui;

import exceptions.InvalidIdException;
import exceptions.InvalidPriceException;
import exceptions.InvalidQtyException;
import exceptions.ZeroNameLengthException;
import model.Inventory;
import model.Product;

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
        String command = null;
        input = new Scanner(System.in);

        while (running) {
            displayMenu();
            command = input.next();

            if (command.equals("q")) {
                running = false;
            }
        }

    }

    private void displayMenu() {
        System.out.println("\nChoose one of the following:");
        System.out.println("\ti -> View your inventory.");
        System.out.println("\ta -> Add product to inventory.");
        System.out.println("\tr -> Remove product from inventory.");
        System.out.println("\tv -> View the total value of your inventory");
        System.out.println("\tq -> quit");
    }

    private void processViewInventory() {
        System.out.println(inventory);
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
            System.out.println("Please enter a valid name.");
        } catch (InvalidQtyException e) {
            System.out.println("Please enter a valid quantity.");
        } catch (InvalidPriceException e) {
            System.out.println("Please enter a valid price.");
        }
        return null;

    }

    private void processAddProduct()  {
        Product product = getProduct();
        try {
            Inventory inventory = new Inventory();
            inventory.addProduct(product);
        } catch (InvalidIdException e) {
            System.out.println("Product ID already exists, please enter a unique ID number.");
        }




    }

    private void processRemoveProduct() {

    }

    private void processViewValue() {

    }


}
