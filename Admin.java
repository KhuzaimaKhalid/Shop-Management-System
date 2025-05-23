public class Admin extends User {
    public Admin() {}

    public Admin(String username, String password) {
        super(username, password);
    }

    public void manageInventory() {
        // logic to manage inventory (add/update/delete products)
        System.out.println("Managing inventory...");
    }

    public void generateReports() {
        // logic to generate sales and inventory reports
        System.out.println("Generating reports...");
    }
}