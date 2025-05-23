import java.util.ArrayList;
import java.util.List;

public class Transaction implements Calculable {
    private int transactionId;
    private List<Product> productList;
    private double totalAmount;

    public Transaction() {
        this.productList = new ArrayList<>();
        this.totalAmount = 0.0;
    }

    public Transaction(int transactionId, List<Product> productList) {
        this.transactionId = transactionId;
        this.productList = productList;
        this.totalAmount = calculateTotal();
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        this.totalAmount = calculateTotal();
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public double calculateTotal() {
        double sum = 0.0;
        for (Product p : productList) {
            sum += p.getPrice() * p.getQuantity();
        }
        this.totalAmount = sum;
        return totalAmount;
    }

    @Override
    public double applyTax() {
        double taxRate = 0.10; // 10%
        return calculateTotal() * (1 + taxRate);
    }

    @Override
    public String toString() {
        return String.format("Transaction{id=%d, totalAmount=%.2f, products=%s}", transactionId, totalAmount, productList);
    }
}