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

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() throws InvalidPriceException, ZeroNameLengthException, InvalidQtyException,
                                            InvalidIdException {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Inventory inv = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyInventory() throws InvalidPriceException, ZeroNameLengthException, InvalidQtyException,
                                           InvalidIdException {
        JsonReader reader = new JsonReader("./data/testReaderEmptyInventory.json");
        try {
            Inventory inv = reader.read();
            assertEquals(0, inv.numProducts());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() throws InvalidPriceException, ZeroNameLengthException, InvalidQtyException,
                                            InvalidIdException {
        JsonReader reader = new JsonReader("./data/testReaderGeneralInventory.json");
        try {
            Inventory inv = reader.read();
            List<Product> products = inv.getInventory();
            assertEquals(2, products.size());
            checkProduct("Test", 1, "Category", 2.0, 1, products.get(0));
            checkProduct("Test2", 2, "Category2", 5.0, 3, products.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}