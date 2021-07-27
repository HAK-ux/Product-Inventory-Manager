package model;

import exceptions.InvalidPriceException;
import exceptions.InvalidQtyException;
import exceptions.ZeroNameLengthException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    Product product;

    @BeforeEach
    void setup() throws InvalidPriceException, ZeroNameLengthException, InvalidQtyException {
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

    @Test
    void setCtgTest() {
        product.setCtg("Changed");
        assertEquals ("Changed", product.getCtg());
    }

    @Test
    void setNameTest() {
        product.setName("Changed");
        assertEquals ("Changed", product.getName());

    }

    @Test
    void validProductExceptionTest() {
        try {
            Product product = new Product("Valid", 1234, "Testing", 20, 2);
        } catch (ZeroNameLengthException | InvalidQtyException | InvalidPriceException e) {
            fail();
        }

    }
    @Test
    void zeroLengthNameExceptionTest() {
        try {
            Product product = new Product("", 1234, "Testing", 20, 2);
            fail();
        } catch (ZeroNameLengthException e) {
            // Good!
        } catch (InvalidQtyException | InvalidPriceException e) {
            fail();
        }
    }

    @Test
    void invalidQtyExceptionTest() {
        try {
            Product product = new Product("Test",1234, "Testing", 20, -2);
            fail();
        } catch (ZeroNameLengthException | InvalidPriceException e) {
            fail();
        } catch (InvalidQtyException e) {
            // Good!
        }
    }

    @Test
    void invalidPriceExceptionTest() {
        try {
            Product product = new Product("Test",1234, "Testing", -2, 2);
        } catch (ZeroNameLengthException | InvalidQtyException e) {
            fail();
        } catch (InvalidPriceException e) {
            // Good!
        }
    }
}