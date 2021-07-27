package model;

import exceptions.InvalidPriceException;
import exceptions.InvalidQtyException;
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
            InvalidQtyException, InvalidPriceException {

        if (name.equals("")) {
            throw new ZeroNameLengthException();
        } else {
            this.name = name;
        }

        if (price <= 0) {
            throw new InvalidPriceException();
        } else {
            this.price = price;
        }
        if (qty < 0) {
            throw new InvalidQtyException();
        } else {
            this.qty = qty;
        }

        this.name = name;

        this.id = id;
        this.ctg = ctg;


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

    // REQUIRES: amount < price
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

    // REQUIRES: num <= qty
    // MODIFIES: this
    // EFFECTS: Decreases the product quantity by the number given.
    public int reduceQty(int num) {
        return qty -= num;
    }

    // MODIFIES: this
    // EFFECTS: Changes the category name to the given name.
    public String setCtg(String ctg) {
        return this.ctg = ctg;
    }

    // MODIFIES: this
    // EFFECTS: Changes the product name to the given name.
    public String setName(String name) {
        return this.name = name;
    }


}
