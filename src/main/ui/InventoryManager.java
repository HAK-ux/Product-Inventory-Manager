package ui;

import exceptions.*;
import model.Inventory;
import model.Product;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Inventory manager application
public class InventoryManager {
    private static final String JSON_STORE = "./data/inventory.json";
    private Scanner input;
    private static Inventory inventory;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: Runs the inventory manager application.
    public InventoryManager() throws InvalidPriceException, ZeroNameLengthException, InvalidQtyException,
                                     InvalidIdException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runInventoryManager();
    }

    // MODIFIES: this
    // EFFECTS: Processes user input.
    private void runInventoryManager() throws InvalidPriceException, ZeroNameLengthException, InvalidQtyException,
                                              InvalidIdException {
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

    // EFFECTS: Displays menu of options to user.
    private void displayMenu() {
        System.out.println("\nChoose one of the following:");
        System.out.println("\ti -> View your inventory.");
        System.out.println("\ts -> Save your inventory.");
        System.out.println("\tl -> Load your inventory.");
        System.out.println("\ta -> Add product to inventory.");
        System.out.println("\tr -> Remove product from inventory.");
        System.out.println("\te -> Edit a product in your inventory.");
        System.out.println("\tv -> View the total value of your inventory.");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: Displays menu of options to edit a product for the user.
    private void editMenu() {
        System.out.println("\nChoose one of the following:");
        System.out.println("\ti -> Increase price");
        System.out.println("\tr -> Reduce price");
        System.out.println("\ta -> Add quantity");
        System.out.println("\td -> Decrease quantity");
        System.out.println("\tq -> quit");

    }

    // MODIFIES: this
    // EFFECTS: Processes user command in displayMenu.
    private void processCommand(String command) throws InvalidPriceException, ZeroNameLengthException,
                                                       InvalidQtyException, InvalidIdException {
        if (command.equals("i")) {
            processViewInventory();
        } else if (command.equals("s")) {
            saveInventory();
        } else if (command.equals("l")) {
            loadInventory();
        } else if (command.equals("a")) {
            processAddProduct(inventory);
        } else if (command.equals("r")) {
            processRemoveProduct(inventory);
        } else if (command.equals("e")) {
            editMenu();
            String editCommand = input.next().toLowerCase();
            if (editCommand.equals("q")) {
                displayMenu();
            } else {
                processEditCommand(editCommand);
            }
        } else if (command.equals("v")) {
            processViewValue();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: Processes user command in the editMenu.
    private void processEditCommand(String command) {
        switch (command) {
            case "i":
                processAddPrice(inventory);
                break;
            case "r":
                processReducePrice(inventory);
                break;
            case "a":
                processAddQuantity(inventory);
                break;
            case "d":
                processReduceQuantity(inventory);
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    // EFFECTS: Prints out the inventory with each attribute of the product separated by a "|".
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
            System.err.println("Please enter a valid name. \n");
            return getProduct();
        } catch (InvalidQtyException e) {
            System.err.println("Please enter a valid quantity. \n");
            return getProduct();
        } catch (InvalidPriceException e) {
            System.err.println("Please enter a valid price. \n");
            return getProduct();
        }
    }

    // MODIFIES: this
    // EFFECTS: Processes the adding of a product to the inventory.
    private void processAddProduct(Inventory inventory) {
        Product product = getProduct();
        try {
            inventory.addProduct(product);
        } catch (InvalidIdException e) {
            System.err.println("Product ID already exists, please enter a unique ID number.");
        }
    }

    // MODIFIES: this
    // EFFECTS: Processes the removing of a product from the inventory.
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

    // EFFECTS: Processes and prints the total price value of the inventory.
    private void processViewValue() {
        System.out.println("The total value of your inventory is " + inventory.getValue() + ".");
    }

    // MODIFIES: this
    // EFFECTS: Processes the increase of a product quantity in the inventory.
    private void processAddQuantity(Inventory inventory) {
        input = new Scanner(System.in);

        System.out.println("Product Unique ID Number: ");
        int id = input.nextInt();

        try {
            System.out.println("How much do you want to increase the quantity by? ");
            int num = input.nextInt();
            inventory.getProductGivenId(id).addQty(num);

        } catch (InvalidProductException e) {
            System.err.println("This product does not exist.");
        }
    }

    // MODIFIES: this
    // EFFECTS: Processes the reduction of a product quantity in the inventory.
    private void processReduceQuantity(Inventory inventory) {
        input = new Scanner(System.in);

        System.out.println("Product Unique ID Number: ");
        int id = input.nextInt();

        try {
            System.out.println("How much do you want to reduce the quantity by? ");
            int num = input.nextInt();
            inventory.getProductGivenId(id).reduceQty(num);

        } catch (InvalidProductException e) {
            System.err.println("This product does not exist.");
        }
    }

    // MODIFIES: this
    // EFFECTS: Processes the increase of a product price in the inventory.
    private void processAddPrice(Inventory inventory) {
        input = new Scanner(System.in);

        System.out.println("Product Unique ID Number: ");
        int id = input.nextInt();

        try {
            System.out.println("How much do you want to increase the price by? ");
            double num = input.nextDouble();
            inventory.getProductGivenId(id).increasePrice(num);

        } catch (InvalidProductException e) {
            System.err.println("This product does not exist.");
        }
    }

    // MODIFIES: this
    // EFFECTS: Processes the decrease of a product price in the inventory.
    private void processReducePrice(Inventory inventory) {
        input = new Scanner(System.in);

        System.out.println("Product Unique ID Number: ");
        int id = input.nextInt();

        try {
            System.out.println("How much do you want to reduce the price by? ");
            double num = input.nextDouble();
            inventory.getProductGivenId(id).reducePrice(num);

        } catch (InvalidProductException e) {
            System.err.println("This product does not exist.");
        }
    }

    // EFFECTS: saves the inventory to file
    private void saveInventory() {
        try {
            jsonWriter.open();
            jsonWriter.write(inventory);
            jsonWriter.close();
            System.out.println("Saved inventory to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads inventory from file
    private void loadInventory() throws InvalidPriceException, ZeroNameLengthException, InvalidQtyException,
                                        InvalidIdException {
        try {
            inventory = jsonReader.read();
            System.out.println("Loaded inventory from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
