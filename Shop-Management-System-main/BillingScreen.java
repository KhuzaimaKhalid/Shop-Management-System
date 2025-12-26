import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BillingScreen extends JPanel {
    // Custom Dynamic Array for cart management
    private CustomArray<Product> cart = new CustomArray<>();

    private final List<Transaction> transactionHistory = new ArrayList<>();
    private final DefaultTableModel cartModel = new DefaultTableModel(
            new Object[]{"ID", "Name", "Price", "Qty", "Total"}, 0
    );
    private final JTable cartTable = new JTable(cartModel);

    private JTextField idField;
    private JTextField nameField;
    private JTextField priceField;
    private JTextField quantityField;
    private JComboBox<String> sortByComboBox;
    private JComboBox<String> sortOrderComboBox;

    public BillingScreen() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        // Product Entry Panel
        JPanel entryPanel = new JPanel(new GridLayout(2, 5, 5, 5));
        idField = new JTextField();
        nameField = new JTextField();
        priceField = new JTextField();
        quantityField = new JTextField();
        JButton addButton = new JButton("Add to Cart");
        addButton.addActionListener(this::addToCart);

        entryPanel.add(new JLabel("Product ID"));
        entryPanel.add(new JLabel("Name"));
        entryPanel.add(new JLabel("Price"));
        entryPanel.add(new JLabel("Quantity"));
        entryPanel.add(new JLabel("")); // Empty for layout

        entryPanel.add(idField);
        entryPanel.add(nameField);
        entryPanel.add(priceField);
        entryPanel.add(quantityField);
        entryPanel.add(addButton);

        // Cart Table
        JScrollPane scrollPane = new JScrollPane(cartTable);

        // Sorting Panel
        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sortPanel.setBorder(BorderFactory.createTitledBorder("QuickSort Options"));

        sortPanel.add(new JLabel("Sort By:"));
        sortByComboBox = new JComboBox<>(new String[]{"Price", "Quantity", "Total", "Name"});
        sortPanel.add(sortByComboBox);

        sortPanel.add(new JLabel("Order:"));
        sortOrderComboBox = new JComboBox<>(new String[]{"Ascending", "Descending"});
        sortPanel.add(sortOrderComboBox);

        JButton sortButton = new JButton("Apply QuickSort");
        sortButton.addActionListener(this::applySorting);
        sortPanel.add(sortButton);

        // Control Buttons
        JPanel controlPanel = new JPanel();
        JButton checkoutButton = new JButton("Process Payment");
        checkoutButton.addActionListener(this::processPayment);
        JButton clearButton = new JButton("Clear Cart");
        clearButton.addActionListener(this::clearCart);

        controlPanel.add(sortPanel);
        controlPanel.add(checkoutButton);
        controlPanel.add(clearButton);

        add(entryPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private void addToCart(ActionEvent e) {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            double total = price * quantity;

            if (name.isEmpty() || quantity <= 0 || price <= 0) {
                throw new IllegalArgumentException("Invalid input values.");
            }

            // Use custom array add method
            Product product = new Product(id, name, price, quantity);
            cart.add(product);

            cartModel.addRow(new Object[]{id, name, price, quantity, total});

            // Clear fields after adding
            idField.setText("");
            nameField.setText("");
            priceField.setText("");
            quantityField.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for ID, Price, and Quantity.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    // Custom clear cart method using CustomArray
    private void clearCart(ActionEvent e) {
        cart.clear(); // Using custom array clear method
        cartModel.setRowCount(0);
        JOptionPane.showMessageDialog(this, "Cart cleared using custom array logic!");
    }

    // Apply QuickSort to cart items
    private void applySorting(ActionEvent e) {
        if (cart.size() == 0) {
            JOptionPane.showMessageDialog(this, "Cart is empty. Add items first.");
            return;
        }

        // Convert custom array to ArrayList for QuickSort
        ArrayList<Product> cartItems = cart.toArrayList();

        // Get sort criteria
        String sortBy = (String) sortByComboBox.getSelectedItem();
        String sortOrder = (String) sortOrderComboBox.getSelectedItem();

        // Apply QuickSort
        long startTime = System.nanoTime();
        if (sortOrder.equals("Ascending")) {
            QuickSortUtil.quickSort(cartItems, 0, cartItems.size() - 1, sortBy);
        } else {
            QuickSortUtil.quickSortDescending(cartItems, 0, cartItems.size() - 1, sortBy);
        }
        long endTime = System.nanoTime();
        double timeTaken = (endTime - startTime) / 1_000_000.0;

        // Update custom array with sorted items
        cart.clear();
        for (Product p : cartItems) {
            cart.add(p);
        }

        // Update table with sorted items
        updateTableFromCart();

        JOptionPane.showMessageDialog(this,
                String.format("Cart sorted by %s (%s) using QuickSort\nTime taken: %.3f ms\nItems sorted: %d\nUsing Custom Array DS",
                        sortBy, sortOrder, timeTaken, cart.size()),
                "Sort Complete",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void processPayment(ActionEvent e) {
        if (cart.size() == 0) {
            JOptionPane.showMessageDialog(this, "Cart is empty.");
            return;
        }

        // Convert custom array to ArrayList for processing
        ArrayList<Product> cartItems = cart.toArrayList();

        // Sort items by total (highest first) for bill presentation
        QuickSortUtil.quickSortDescending(cartItems, 0, cartItems.size() - 1, "total");

        Transaction transaction = new Transaction(transactionHistory.size() + 1, cartItems);
        transactionHistory.add(transaction);

        // Build receipt
        StringBuilder bill = new StringBuilder();
        bill.append("=========== BILL ===========\n");
        bill.append("Transaction ID: ").append(transaction.getTransactionId()).append("\n");
        bill.append("(Items sorted by Total - Highest First)\n");
        bill.append("Using Custom Array & QuickSort\n\n");
        bill.append(String.format("%-5s %-15s %-7s %-5s %-8s\n", "ID", "Name", "Price", "Qty", "Total"));
        bill.append("------------------------------------------\n");

        for (Product p : cartItems) {
            double total = p.getPrice() * p.getQuantity();
            bill.append(String.format("%-5d %-15s %-7.2f %-5d %-8.2f\n",
                    p.getId(), p.getName(), p.getPrice(), p.getQuantity(), total));
        }

        bill.append("\n-----------------------------\n");
        bill.append(String.format("Subtotal: %.2f\n", transaction.getTotalAmount()));
        bill.append(String.format("Total (incl. 10%% tax): %.2f\n", transaction.applyTax()));
        bill.append("\n[Data Structure: Custom Dynamic Array]");
        bill.append("\n[Algorithm: QuickSort]");
        bill.append("\nThank you for your purchase!");

        // Show bill in dialog
        JTextArea billArea = new JTextArea(bill.toString());
        billArea.setEditable(false);
        billArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(billArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(this, scrollPane, "Receipt", JOptionPane.INFORMATION_MESSAGE);

        // Save receipt to file
        String fileName = "receipt_" + transaction.getTransactionId() + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(bill.toString());
            JOptionPane.showMessageDialog(this, "Receipt saved as " + fileName);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to save receipt: " + ex.getMessage());
        }

        // Clear cart using custom array clear
        cart.clear();
        cartModel.setRowCount(0);
    }

    private void updateTableFromCart() {
        cartModel.setRowCount(0);
        for (int i = 0; i < cart.size(); i++) {
            Product p = cart.get(i);
            double total = p.getPrice() * p.getQuantity();
            cartModel.addRow(new Object[]{p.getId(), p.getName(), p.getPrice(), p.getQuantity(), total});
        }
    }

    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }

    // Custom Dynamic Array Implementation
    private static class CustomArray<T> {
        private Object[] array;
        private int size;
        private int capacity;

        public CustomArray() {
            capacity = 10; // Initial capacity
            array = new Object[capacity];
            size = 0;
        }

        // Add element (custom implementation)
        public void add(T element) {
            if (size == capacity) {
                resize();
            }
            array[size] = element;
            size++;
        }

        // Get element at index
        @SuppressWarnings("unchecked")
        public T get(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }
            return (T) array[index];
        }

        // Remove element at index
        public void remove(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }

            // Shift elements to the left
            for (int i = index; i < size - 1; i++) {
                array[i] = array[i + 1];
            }
            array[size - 1] = null;
            size--;
        }

        // Clear all elements (custom implementation)
        public void clear() {
            // Reset size to 0, don't delete array
            for (int i = 0; i < size; i++) {
                array[i] = null; // Help garbage collection
            }
            size = 0;
        }

        // Get current size
        public int size() {
            return size;
        }

        // Resize array when capacity is reached
        private void resize() {
            capacity = capacity * 2;
            Object[] newArray = new Object[capacity];

            // Copy elements to new array
            for (int i = 0; i < size; i++) {
                newArray[i] = array[i];
            }

            array = newArray;
        }

        // Convert to ArrayList for QuickSort compatibility
        @SuppressWarnings("unchecked")
        public ArrayList<T> toArrayList() {
            ArrayList<T> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                list.add((T) array[i]);
            }
            return list;
        }
    }
}