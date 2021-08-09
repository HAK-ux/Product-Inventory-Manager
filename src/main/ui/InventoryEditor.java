package ui;

import exceptions.*;
import model.Inventory;
import model.Product;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class InventoryEditor extends JPanel {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 700;

    private Inventory inventory;
    private String name;
    private int id;
    private String ctg;
    private Double price;
    private int qty;
    private int removeId;


    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;
    private JButton addButton;
    private JButton removeButton;
    private JTextField inputField;
    private JPanel topTextPnl;
    private JButton inputButton;
    private JButton saveButton;
    private JButton loadButton;

    private static final String addString = "Add Product";
    private static final String removeString = "Remove Product";
    private static final String saveString = "Save Inventory";
    private static final String loadString = "Load Inventory";

    private static final String JSON_STORE = "./data/inventory.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: Constructs an Inventory Editor gui.
    public InventoryEditor() {
        inventory = new Inventory();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        initJTable();
        // Adding table to JScrollPane
        JScrollPane sp = new JScrollPane(table);
        initFrame(sp);
        initButtons();
        frame.pack();
    }

    // MODIFIES: this
    // EFFECTS: Initializes the JTable in the gui.
    public void initJTable() {
        model = new DefaultTableModel();
        table = new JTable(model);
        model.addColumn("Name");
        model.addColumn("ID");
        model.addColumn("Category");
        model.addColumn("Price");
        model.addColumn("Quantity");
        table.setBounds(30, 40, 200, 300);

    }

    // MODIFIES: this
    // EFFECTS: Initializes the frame of the gui.
    public void initFrame(JScrollPane sp) {
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setTitle("Inventory Editor");
        frame.add(sp);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // MODIFIES: this
    // EFFECTS: Initializes the buttons in the gui.
    public void initButtons() {
        JPanel buttonPnl = new JPanel(new BorderLayout());
        JPanel bottomButtonPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));

        addButton = new JButton(addString);
        addButton.addActionListener(addListener);
        removeButton = new JButton(removeString);
        removeButton.addActionListener(removeListener);
        saveButton = new JButton(saveString);
        saveButton.addActionListener(saveListener);
        loadButton = new JButton(loadString);
        loadButton.addActionListener(loadListener);

        bottomButtonPnl.add(addButton);
        bottomButtonPnl.add(removeButton);
        bottomButtonPnl.add(saveButton);
        bottomButtonPnl.add(loadButton);

        buttonPnl.add(bottomButtonPnl, BorderLayout.CENTER);
        table.getTableHeader().setReorderingAllowed(false);
        frame.add(buttonPnl, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: Initializes the text field to collect user information when adding product.
    public void initTextFieldAdd() {
        topTextPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputButton = new JButton("Submit Name");
        inputButton.addActionListener(addListener);

        inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(200, 20));

        topTextPnl.add(inputButton);
        topTextPnl.add(inputField);

        frame.add(topTextPnl, BorderLayout.NORTH);
        frame.pack();

    }

    // MODIFIES: this
    // EFFECTS: Initializes the text field to collect user information when removing product
    public void initTextFieldRemove() {
        topTextPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputButton = new JButton("Submit Unique ID");
        inputButton.addActionListener(removeListener);

        inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(200, 20));

        topTextPnl.add(inputButton);
        topTextPnl.add(inputField);

        frame.add(topTextPnl, BorderLayout.NORTH);
        frame.pack();

    }

    ActionListener addListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addButton) {
                disableButtons();
                initTextFieldAdd();
            }
            if (collectAddInputs(e)) {
                addProduct();
                enableButtons();
                frame.remove(topTextPnl);
                frame.pack();
            }
        }
    };

    ActionListener removeListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == removeButton) {
                disableButtons();
                initTextFieldRemove();
            }

            boolean collected = false;
            if (e.getSource() == inputButton) {
                removeId = Integer.parseInt(inputField.getText());
                collected = true;
            }

            if (collected) {
                removeProduct();
                enableButtons();
                frame.remove(topTextPnl);
                frame.pack();
            }
        }
    };

    ActionListener saveListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == saveButton) {
                try {
                    jsonWriter.open();
                    jsonWriter.write(inventory);
                    jsonWriter.close();
                    System.out.println("Saved inventory to " + JSON_STORE);

                } catch (FileNotFoundException ex) {
                    System.out.println("Unable to write to file: " + JSON_STORE);
                }
            }

        }
    };

    ActionListener loadListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loadButton) {
                try {
                    inventory.getInventory().clear();
                    model.getDataVector().removeAllElements();
                    inventory = jsonReader.read();
                    System.out.println("Loaded inventory from " + JSON_STORE);
                    for (Product product : inventory.getInventory()) {
                        name = product.getName();
                        id = product.getId();
                        ctg = product.getCtg();
                        price = product.getPrice();
                        qty = product.getQty();
                    }
                    addLoadedRows();
                    loadButton.setEnabled(false);
                } catch (IOException | InvalidPriceException | ZeroNameLengthException | InvalidQtyException
                         | InvalidIdException ex) {
                    System.out.println("Unable to read from file: " + JSON_STORE);
                }
            }
        }
    };


    // EFFECTS: Assigns user input to name. Resets text field.
    public void submitName(ActionEvent e) {
        name = inputField.getText();
        inputField.setText(null);
    }

    // EFFECTS: Assigns user input to id. Resets text field.
    public void submitID(ActionEvent e) {
        if (e.getSource() == inputButton) {
            id = Integer.parseInt(inputField.getText());
            inputField.setText(null);
        }
    }

    // EFFECTS: Assigns user input to ctg. Resets text field.
    public void submitCtg(ActionEvent e) {
        if (e.getSource() == inputButton) {
            ctg = inputField.getText();
            inputField.setText(null);
        }
    }

    // EFFECTS: Assigns user input to price. Resets text field.
    public void submitPrice(ActionEvent e) {
        if (e.getSource() == inputButton) {
            price = Double.parseDouble(inputField.getText());
            inputField.setText(null);
        }
    }

    // EFFECTS: Assigns user input to qty. Resets text field.
    public void submitQty(ActionEvent e) {
        if (e.getSource() == inputButton) {
            qty = Integer.parseInt(inputField.getText());
        }
    }

    // EFFECTS: Collects all the user inputs needed for a product to be added.
    public boolean collectAddInputs(ActionEvent e) {
        boolean collected = false;

        if (inputButton.getText().equals("Submit Name")) {
            submitName(e);
            if (e.getSource() == inputButton) {
                inputButton.setText("Submit ID");
            }

        } else if (inputButton.getText().equals("Submit ID")) {
            submitID(e);
            inputButton.setText("Submit Category");

        } else if (inputButton.getText().equals("Submit Category")) {
            submitCtg(e);
            inputButton.setText("Submit Price");

        } else if (inputButton.getText().equals("Submit Price")) {
            submitPrice(e);
            inputButton.setText("Submit Quantity");

        } else {
            submitQty(e);
            collected = true;
        }

        return collected;
    }

    // MODIFIES: inventory, table
    // EFFECTS: Adds product to inventory and adds a corresponding row in the TableModel.
    public void addProduct() {
        try {
            inventory.addProduct(new Product(name, id, ctg, price, qty));
            addRow();
        } catch (InvalidIdException ex) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(frame, "Please enter a valid ID.");
        } catch (ZeroNameLengthException ex) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(frame, "Please enter a valid name.");
        } catch (InvalidQtyException ex) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(frame, "Please enter a valid quantity.");
        } catch (InvalidPriceException ex) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(frame, "Please enter a valid price.");
        }
    }

    // MODIFIES: inventory, table
    // EFFECTS: Removes product from inventory and TableModel.
    public void removeProduct() {
        try {
            removeRow();
            inventory.removeProduct(inventory.getProductGivenId(removeId));
        } catch (InvalidProductException e) {
            JOptionPane.showMessageDialog(frame, "This product does not exist.");
        }
    }

    // MODIFIES: table
    // EFFECTS: Adds a product to the TableModel.
    public void addRow() {
        int index = inventory.numProducts() - 1;
        ArrayList<Product> products = inventory.getInventory();
        String name = (products.get(index)).getName();
        int id = (products.get(index)).getId();
        String ctg = (products.get(index)).getCtg();
        double price = (products.get(index)).getPrice();
        int qty = (products.get(index)).getQty();

        model.addRow(new Object[]{name, id, ctg, price, qty});
    }

    // MODIFIES: table
    // EFFECTS: Removes a product from the TableModel.
    public void removeRow() throws InvalidProductException {
        Product toBeRemoved = inventory.getProductGivenId(removeId);
        for (int i = 0; i < inventory.numProducts(); i++) {
            Product product = inventory.getInventory().get(i);
            if (toBeRemoved == product) {
                model.removeRow(i);
            }
        }
    }

    // MODIFIES: table
    // EFFECTS: Adds the rows in the inventory loaded from the Json file.
    public void addLoadedRows() {
        for (int i = 0; i < inventory.numProducts(); i++) {
            ArrayList<Product> products = inventory.getInventory();
            String name = (products.get(i)).getName();
            int id = (products.get(i)).getId();
            String ctg = (products.get(i)).getCtg();
            double price = (products.get(i)).getPrice();
            int qty = (products.get(i)).getQty();
            model.addRow(new Object[]{name, id, ctg, price, qty});
        }
    }

    // MODIFIES: this
    // EFFECTS: Disables all the buttons in the gui.
    public void disableButtons() {
        addButton.setEnabled(false);
        removeButton.setEnabled(false);
        saveButton.setEnabled(false);
        loadButton.setEnabled(false);
    }

    // MODIFIES: this
    // EFFECTS: Enables all the buttons in the gui.
    public void enableButtons() {
        addButton.setEnabled(true);
        removeButton.setEnabled(true);
        saveButton.setEnabled(true);
        loadButton.setEnabled(true);
    }
}