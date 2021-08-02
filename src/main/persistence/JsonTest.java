package persistence;

import model.Product;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class JsonTest {
    protected void checkProduct(String name, int id, String category, Double price, int quantity,
                                Product product) {

        assertEquals(name, product.getName());
        assertEquals(id, product.getId());
        assertEquals(category, product.getCtg());
        assertEquals(price, product.getPrice());
        assertEquals(quantity, product.getQty());

    }
}
