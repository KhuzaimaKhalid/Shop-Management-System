public class Product implements Storable {
    private int id;
    private String name;
    private double price;
    private int quantity;

    public Product() {}

    public Product(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    // Storable interface methods
    @Override
    public void addItem() {
        // logic for adding product to inventory (e.g., add to a list or DB)
        System.out.println("Product added: " + this);
    }

    @Override
    public void updateItem() {
        // logic for updating product details in inventory
        System.out.println("Product updated: " + this);
    }

    @Override
    public void deleteItem() {
        // logic for removing product from inventory
        System.out.println("Product deleted: " + this);
    }

    @Override
    public String toString() {
        return String.format("Product{id=%d, name='%s', price=%.2f, quantity=%d}", id, name, price, quantity);
    }
}