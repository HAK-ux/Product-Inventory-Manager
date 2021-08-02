package ui;

import exceptions.InvalidIdException;
import exceptions.InvalidPriceException;
import exceptions.InvalidQtyException;
import exceptions.ZeroNameLengthException;

public class Main {
    public static void main(String[] args) throws InvalidPriceException, ZeroNameLengthException, InvalidQtyException,
                                                  InvalidIdException {
        new InventoryManager();
    }
}
