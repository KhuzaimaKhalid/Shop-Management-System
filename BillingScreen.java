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
    private final List<Transaction> transactionHistory = new ArrayList<>();
    private final DefaultTableModel cartModel = new DefaultTableModel(
            new Object[]{"ID", "Name", "Price", "Qty", "Total"}, 0
    );
    private final JTable cartTable = new JTable(cartModel);

    private JTextField idField;
    private JTextField nameField;
    private JTextField priceField;
    private JTextField quantityField;

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

        // Control Buttons
        JPanel controlPanel = new JPanel();
        JButton checkoutButton = new JButton("Process Payment");
        checkoutButton.addActionListener(this::processPayment);
        controlPanel.add(checkoutButton);

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

    private void processPayment(ActionEvent e) {
        if (cartModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Cart is empty.");
            return;
        }

        List<Product> cartItems = getCartItems();
        Transaction transaction = new Transaction(transactionHistory.size() + 1, cartItems);
        transactionHistory.add(transaction);

        // Build receipt
        StringBuilder bill = new StringBuilder();
        bill.append("=========== BILL ===========\n");
        bill.append("Transaction ID: ").append(transaction.getTransactionId()).append("\n\n");
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

        // Clear cart
        cartModel.setRowCount(0);
    }

    private List<Product> getCartItems() {
        List<Product> items = new ArrayList<>();
        for (int i = 0; i < cartModel.getRowCount(); i++) {
            items.add(new Product(
                    (Integer) cartModel.getValueAt(i, 0),  // ID
                    (String) cartModel.getValueAt(i, 1),   // Name
                    (Double) cartModel.getValueAt(i, 2),   // Price
                    (Integer) cartModel.getValueAt(i, 3)   // Quantity
            ));
        }
        return items;
    }

    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }
}
