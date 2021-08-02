package persistence;

import exceptions.InvalidIdException;
import exceptions.InvalidPriceException;
import exceptions.InvalidQtyException;
import exceptions.ZeroNameLengthException;
import model.Inventory;
import model.Product;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Inventory inv = new Inventory();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyInventory() throws InvalidPriceException, ZeroNameLengthException, InvalidQtyException,
                                           InvalidIdException {
        try {
            Inventory inv = new Inventory();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyInventory.json");
            writer.open();
            writer.write(inv);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyInventory.json");
            inv = reader.read();
            assertEquals(0, inv.numProducts());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralInventory() throws InvalidPriceException, ZeroNameLengthException, InvalidQtyException,
                                             InvalidIdException {
        try {
            Inventory inv = new Inventory();
            inv.addProduct(new Product("TestWrite", 1, "TestCtg", 2, 1));
            inv.addProduct(new Product("TestWrite2", 2, "TestCtg2", 3, 2));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralInventory.json");
            writer.open();
            writer.write(inv);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralInventory.json");
            inv = reader.read();
            List<Product> products = inv.getInventory();
            assertEquals(2, products.size());
            checkProduct("TestWrite", 1, "TestCtg", 2.0, 1, products.get(0));
            checkProduct("TestWrite2", 2, "TestCtg2", 3.0, 2, products.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
    
}
