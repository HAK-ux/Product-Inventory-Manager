package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;


public class InventoryTest {
    Inventory emptyInventory;
    Product product1;
    Product product2;
    Product product3;
    Product product4;


    @BeforeEach
    void setup() {
        emptyInventory = new Inventory();
        product1 = new Product("Test", 1, "Testing", 20, 2);
        product2 = new Product("Test", 2, "Testing", 30, 3);
        product3 = new Product("Test", 3, "Testing", 40, 5);
        product4 = new Product("Test", 4, "Testing", 60, 1);
    }

    @Test
    void addProductTest() {
        emptyInventory.addProduct(product1);
        emptyInventory.addProduct(product2);
        emptyInventory.addProduct(product3);
        emptyInventory.addProduct(product4);

        assertTrue(emptyInventory.inventory.contains(product1));
        assertTrue(emptyInventory.inventory.contains(product2));
        assertTrue(emptyInventory.inventory.contains(product3));
        assertTrue(emptyInventory.inventory.contains(product4));

    }


    @Test
    void removeProductTest() {
        emptyInventory.addProduct(product3);
        emptyInventory.addProduct(product1);
        emptyInventory.addProduct(product4);

        emptyInventory.removeProduct(product1);
        emptyInventory.removeProduct(product4);

        assertFalse(emptyInventory.inventory.contains(product1));
        assertFalse(emptyInventory.inventory.contains(product4));

    }

    @Test
    void getValueTest() {
        assertEquals(0, emptyInventory.getValue());

        emptyInventory.addProduct(product1);
        emptyInventory.addProduct(product2);
        emptyInventory.addProduct(product3);
        emptyInventory.addProduct(product4);

        assertEquals(390, emptyInventory.getValue());
    }
}
