package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import exceptions.InvalidIdException;
import exceptions.InvalidPriceException;
import exceptions.InvalidQtyException;
import exceptions.ZeroNameLengthException;
import model.Inventory;
import model.Product;
import org.json.*;

// UI Functionality and methods are implemented from JsonSerializationDemo. Link below:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Represents a reader that reads the product inventory from JSON data stored in file.

public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads inventory from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Inventory read() throws IOException, InvalidPriceException, ZeroNameLengthException, InvalidQtyException,
                                   InvalidIdException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseInventory(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses inventory from JSON object and returns it
    private Inventory parseInventory(JSONObject jsonObject) throws InvalidPriceException, ZeroNameLengthException,
                                                                   InvalidQtyException, InvalidIdException {
        Inventory inv = new Inventory();
        addInventory(inv, jsonObject);
        return inv;
    }

    // MODIFIES: inv
    // EFFECTS: parses products from JSON object and adds them to inventory
    private void addInventory(Inventory inv, JSONObject jsonObject) throws InvalidPriceException,
                                            ZeroNameLengthException, InvalidQtyException, InvalidIdException {

        JSONArray jsonArray = jsonObject.getJSONArray("products");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addThingy(inv, nextThingy);
        }
    }

    // MODIFIES: inv
    // EFFECTS: parses product from JSON object and adds it to inventory
    private void addThingy(Inventory inv, JSONObject jsonObject) throws InvalidPriceException, ZeroNameLengthException,
                                                                        InvalidQtyException, InvalidIdException {
        String name = jsonObject.getString("name");
        int id = jsonObject.getInt("id");
        String category = jsonObject.getString("category");
        double price = jsonObject.getDouble("price");
        int qty = jsonObject.getInt("quantity");

        Product product = new Product(name, id, category, price, qty);

        inv.addProduct(product);
    }
}