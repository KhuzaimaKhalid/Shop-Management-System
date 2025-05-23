import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ReportFileHandler extends FileHandler {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void generateReport(List<Transaction> transactions, String filename) {
        List<String> reportContent = formatReport(transactions);
        writeFile(filename, reportContent);
    }

    private List<String> formatReport(List<Transaction> transactions) {
        List<String> lines = new ArrayList<>();
        lines.add("SHOP MANAGEMENT SYSTEM REPORT");
        lines.add("Generated at: " + DATE_FORMAT.format(new Date()));
        lines.add("==============================================");

        double grandTotal = 0;
        for (Transaction t : transactions) {
            lines.add(String.format("Transaction #%d | Date: %s",
                    t.getTransactionId(),
                    DATE_FORMAT.format(new Date())));

            for (Product p : t.getProductList()) {
                lines.add(String.format("  - %-20s (Qty: %2d) $%-8.2f",
                        p.getName(),
                        p.getQuantity(),
                        p.getPrice()));
            }

            lines.add(String.format("  Transaction Total: $%.2f", t.getTotalAmount()));
            lines.add("----------------------------------------------");
            grandTotal += t.getTotalAmount();
        }

        lines.add("==============================================");
        lines.add(String.format("GRAND TOTAL: $%.2f", grandTotal));
        return lines;
    }

    @Override
    public List<String> readFile(String filename) {
        try {
            return Files.readAllLines(Paths.get(filename));
        } catch (IOException e) {
            System.err.println("Error reading report: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public void writeFile(String filename, List<?> data) {
        if (data.isEmpty()) return;

        try {
            if (data.get(0) instanceof String) {
                Files.write(Paths.get(filename), (List<String>) data);
                System.out.println("Report generated successfully: " + filename);
            } else {
                System.err.println("Unsupported data format for reports");
            }
        } catch (IOException e) {
            System.err.println("Error writing report: " + e.getMessage());
        }
    }
}