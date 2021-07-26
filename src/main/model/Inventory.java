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
            } else {
                inventory.add(product);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: If the given product is in the inventory list, removes it. Otherwise throws an InvalidProductException
    // and does not do anything to the inventory.
    public void removeProduct(Product product) throws InvalidProductException {
        for (Product item : inventory) {
            if (item.getId() == product.getId()) {
                inventory.remove(product);

            } else {
                throw new InvalidProductException();
            }
        }
    }

    // EFFECTS: Gets the value of all products in the inventory.
    public int getValue() {
        int totVal = 0;
        for (Product product : inventory) {
            totVal += product.getPrice() * product.getQty();
        }

        return totVal;
    }

}
