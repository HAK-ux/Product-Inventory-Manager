package model;

import exceptions.NegativeNumberException;
import exceptions.ZeroNameLengthException;

// Represents a product which has a name, id, category, price, and quantity.
public class Product {
    private String name;  // product name
    private int id;       // unique product ID number
    private String ctg;   // product category
    private double price; // product price
    private int qty;      // product quantity

    // EFFECTS: Constructs a product with a name, ID number, category, price, and initial quantity.
    public Product(String name, int id, String ctg, double price, int qty) throws ZeroNameLengthException,
            NegativeNumberException {

        // Exceptions
        if (name.equals("")) {
            throw new ZeroNameLengthException();
        }

        if (price < 0) {
            throw new NegativeNumberException();
        }

        if (qty < 0) {
            throw new NegativeNumberException();
        }

        // Attributes
        this.name = name;
        this.id = id;
        this.ctg = ctg;
        this.price = price;
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getCtg() {
        return ctg;
    }

    public double getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }

    // MODIFIES: this
    // EFFECTS: Increases the price of the product by the amount given.
    public double increasePrice(double amount) {
        return price += amount;
    }

    // MODIFIES: this
    // EFFECTS: Reduces the price of the product by the amount given.
    public double reducePrice(double amount) {
        return price -= amount;
    }

    // MODIFIES: this
    // EFFECTS: Increases the product quantity by the number given.
    public int addQty(int num) {
        return qty += num;
    }

    // MODIFIES: this
    // EFFECTS: Decreases the product quantity by the number given.
    public int reduceQty(int num) {
        return qty -= num;
    }


}
