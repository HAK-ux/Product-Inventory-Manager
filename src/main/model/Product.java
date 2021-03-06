package model;

import exceptions.InvalidPriceException;
import exceptions.InvalidQtyException;
import exceptions.ZeroNameLengthException;
import org.json.JSONObject;
import persistence.Writable;

// Represents a product which has a name, id, category, price, and quantity.
public class Product implements Writable {
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
        }

        if (price <= 0) {
            throw new InvalidPriceException();
        }

        if (qty < 0) {
            throw new InvalidQtyException();
        }

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
    public void setCtg(String ctg) {
        this.ctg = ctg;
    }

    // MODIFIES: this
    // EFFECTS: Changes the product name to the given name.
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("id", id);
        json.put("category", ctg);
        json.put("price", price);
        json.put("quantity", qty);

        return json;
    }

}
