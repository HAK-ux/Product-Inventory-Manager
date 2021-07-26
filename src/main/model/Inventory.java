package model;

import exceptions.InvalidIdException;
import exceptions.InvalidProductException;

import java.util.ArrayList;

// Represents a list of products
public class Inventory {
    ArrayList<Product> inventory;


    // EFFECTS: Constructs an empty inventory of products
    public Inventory() {
        inventory = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: If the given product has the same ID as a product already in the inventory, throws invalid id exception
    //          and does not add product to the inventory. Otherwise, adds the given product to the inventory list.
    public void addProduct(Product product) throws InvalidIdException {
        for (Product item : inventory) {
            if (item.getId() == product.getId()) {
                throw new InvalidIdException();
            }
        }
        inventory.add(product);
    }

    // MODIFIES: this
    // EFFECTS: Removes the given product from the list.
    public void removeProduct(Product product) {
        inventory.remove(product);
    }

    // EFFECTS: Gets the value of all products in the inventory.
    public int getValue() {
        int totVal = 0;
        for (Product product : inventory) {
            totVal += product.getPrice() * product.getQty();
        }
        return totVal;
    }

    // EFFECTS: Returns the product given its unique ID number.
    public Product getProductGivenId(int id) throws InvalidProductException {
        for (Product product : inventory) {
            if (id == product.getId()) {
                return product;
            }
        }
        throw new InvalidProductException();
    }

    // EFFECTS: Returns the inventory as an array list of Products.
    public ArrayList<Product> getInventory() {
        return inventory;
    }

}
