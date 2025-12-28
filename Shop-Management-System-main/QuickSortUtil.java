import java.util.ArrayList;
import java.util.Comparator;

public class QuickSortUtil {

    // Main QuickSort method
    public static void quickSort(ArrayList<Product> items, int low, int high, String sortBy) {
        if (low < high) {
            int pivotIndex = partition(items, low, high, sortBy);
            quickSort(items, low, pivotIndex - 1, sortBy);
            quickSort(items, pivotIndex + 1, high, sortBy);
        }
    }

    // Partition method
    private static int partition(ArrayList<Product> items, int low, int high, String sortBy) {
        Product pivot = items.get(low + (high - low) / 2);

        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (compare(items.get(j), pivot, sortBy) <= 0) {
                i++;
                swap(items, i, j);
            }
        }

        swap(items, i + 1, high);
        return i + 1;
    }

    // Comparison logic
    private static int compare(Product p1, Product p2, String sortBy) {
        switch (sortBy.toLowerCase()) {
            case "price":
                return Double.compare(p1.getPrice(), p2.getPrice());
            case "quantity":
                return Integer.compare(p1.getQuantity(), p2.getQuantity());
            case "total":
                double total1 = p1.getPrice() * p1.getQuantity();
                double total2 = p2.getPrice() * p2.getQuantity();
                return Double.compare(total1, total2);
            case "name":
                return p1.getName().compareToIgnoreCase(p2.getName());
            default:
                return 0;
        }
    }

    // Swap helper method
    private static void swap(ArrayList<Product> items, int i, int j) {
        Product temp = items.get(i);
        items.set(i, items.get(j));
        items.set(j, temp);
    }

    // Overloaded method for descending order
    public static void quickSortDescending(ArrayList<Product> items, int low, int high, String sortBy) {
        if (low < high) {
            int pivotIndex = partitionDescending(items, low, high, sortBy);
            quickSortDescending(items, low, pivotIndex - 1, sortBy);
            quickSortDescending(items, pivotIndex + 1, high, sortBy);
        }
    }

    private static int partitionDescending(ArrayList<Product> items, int low, int high, String sortBy) {
        Product pivot = items.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (compare(items.get(j), pivot, sortBy) >= 0) { // Changed to >= for descending
                i++;
                swap(items, i, j);
            }
        }

        swap(items, i + 1, high);
        return i + 1;
    }
}