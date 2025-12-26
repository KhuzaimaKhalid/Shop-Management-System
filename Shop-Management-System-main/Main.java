import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
       /* Scanner scanner = new Scanner(System.in);
        List<Product> products = new ArrayList<>();

        System.out.println("=== Shop Management System ===");
        System.out.println("Cashier: Enter product details for each item.");
        System.out.println("Type 'done' when finished.");

        while (true) {
            System.out.print("\nEnter product name (or type 'done' to finish): ");
            String name = scanner.nextLine();
            if (name.equalsIgnoreCase("done")) {
                break;
            }

            System.out.print("Enter product id: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter product price: ");
            double price = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter product quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            Product product = new Product(id, name, price, quantity);
            products.add(product);
            System.out.println("Product added: " + product);
        }

        if (products.isEmpty()) {
            System.out.println("No products entered. Exiting.");
            return;
        }

        // Generate bill (Transaction)
        Transaction transaction = new Transaction(1, products); // Transaction ID can be generated as needed

        System.out.println("\n=== Bill ===");
        for (Product p : products) {
            System.out.printf("%s (x%d): %.2f each, Subtotal: %.2f\n",
                    p.getName(), p.getQuantity(), p.getPrice(), p.getPrice() * p.getQuantity());
        }
        System.out.printf("Total (without tax): %.2f\n", transaction.calculateTotal());
        System.out.printf("Total (with tax): %.2f\n", transaction.applyTax());
        System.out.println("Thank you!");/*

        */
        SwingUtilities.invokeLater(() -> {
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
        });
    }
}