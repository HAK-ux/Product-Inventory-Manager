package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    Product product;

    @BeforeEach
    void setup() {
        product = new Product("Test", 1234, "Testing", 20, 2);
    }

    @Test
    void increasePriceTest() {
        assertEquals(40, product.increasePrice(20));
    }

    @Test
    void reducePriceTest() {
        assertEquals(0, product.reducePrice(20));
    }

    @Test
    void addQtyTest() {
        assertEquals(10, product.addQty(8));
    }

    @Test
    void reduceQtyTest() {
        assertEquals(1, product.reduceQty(1));
    }
}