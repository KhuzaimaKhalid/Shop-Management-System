public class Cashier extends User {
    public Cashier() {}

    public Cashier(String username, String password) {
        super(username, password);
    }

    public void processTransaction(Transaction transaction) {
        // logic for processing a transaction (billing, updating inventory)
        System.out.println("Processing transaction: " + transaction);
    }
}