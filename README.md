# Shop Management System 🛒

A Java Swing-based desktop application for small businesses to streamline billing, inventory management, and transaction processing. Add products, sort and manage a dynamic cart, generate receipts, and review transaction history — all within a compact, user-friendly interface.

> **Download the latest release:** `Shop Management System.zip`

---

## 🚀 Brief Overview

* **Add Products to Cart:** Enter Product ID, Name, Price, and Quantity; items appear in a dynamic cart table.
* **Process Payments:** Calculate subtotal, apply a 10% tax, and finalize transactions with one click.
* **Generate & Save Receipts:** View a formatted receipt in-app and save it as a `.txt` file (saved as `receipt_<transactionId>.txt`).
* **Transaction History:** Maintain an in-memory log of transactions for review and reporting.
* **Sorting & DSA Demonstration:** Sort cart items using a QuickSort implementation and a custom dynamic array (demonstrates data structure and algorithm concepts).

---

## ✨ Key Features

| Icon | Feature                     | Description                                                           |
| ---- | --------------------------- | --------------------------------------------------------------------- |
| 🛒   | Add to Cart                 | Enter product details and add multiple items to the cart.             |
| 💰   | Process Invoice             | Automatically calculate totals, taxes, and finalize payments.         |
| 🧾   | Receipt Generation & Saving | Display the receipt in a dialog and save it to a `.txt` file.         |
| 🖨️  | Print Support               | Open saved receipt in Notepad and print via `Ctrl+P` or File > Print. |
| 📜   | Transaction Log             | Keep track of each sale with unique transaction IDs and details.      |
| ⚙️   | Sorting (QuickSort)         | Apply QuickSort to cart items by Price, Quantity, Total or Name.      |

---

## 🔎 Data Structures & Algorithms (DSA)

This project intentionally demonstrates practical DSA concepts in the billing module:

* **Custom Dynamic Array (`CustomArray<T>`)**

  * Custom implementation of a dynamic array (similar to `ArrayList`) with `add`, `get`, `remove`, `clear`, `size`, `resize` and `toArrayList()` methods.
  * Used for storing and managing cart items to illustrate how dynamic arrays resize and manage capacity.

* **QuickSort (`QuickSortUtil`)**

  * Generic QuickSort implementation that sorts `Product` objects by a chosen attribute (`price`, `quantity`, `total`, or `name`).
  * Both ascending and descending variants provided.
  * **Complexity:** Average `O(n log n)`, Worst `O(n^2)`, Space `O(log n)` (stack).
  * Execution time for each sort is measured (nanoseconds → milliseconds) and displayed to demonstrate real performance on sample inputs.

* **Transactions**

  * `Transaction` objects store the items sold in each sale along with total amounts and a transaction ID.

> These additions make the Billing screen the primary place where algorithmic logic is implemented and tested while keeping the Dashboard as a UI container.

---

## 🛠 How to Use

### Run in IDE

1. Open the project in your Java IDE (IntelliJ IDEA, Eclipse, NetBeans).
2. Run `Main.java` or right-click and run `BillingScreen.java` / `LoginScreen.java` for quick testing.

### Build and run JAR

```bash
# From project root (if you have a build tool configured or use javac/jar manually)
javac -d out src/**/*.java
jar cvfe ShopManagementSystem.jar Main -C out .
java -jar ShopManagementSystem.jar
```

### Typical workflow

1. Fill Product ID, Name, Price, Quantity and click **Add to Cart**.
2. Use **Apply QuickSort** to sort by Price/Quantity/Total/Name in Ascending/Descending order.
3. Click **Process Payment** to generate a receipt dialog, save a `.txt` file and store transaction in memory.
4. Generate reports from the **Dashboard** menu which saved transactions use.

---

## 📂 Project Structure

```
src/
├── model/
│   ├── Product.java
│   └── Transaction.java
├── ui/
│   ├── LoginScreen.java
│   ├── Dashboard.java
│   └── BillingScreen.java
├── util/
│   ├── QuickSortUtil.java
│   └── ReportFileHandler.java
└── Main.java
```

* `model/` — data classes and business objects
* `ui/` — Swing panels and screens
* `util/` — helper utilities and algorithm implementations
* `Main.java` — primary application entry point

---

## 📋 Requirements

* Java SE 8 or higher
* No external libraries required (uses built-in Swing and standard Java APIs)

---

## 🧪 Notes & Tips

* Passwords and user authentication in the sample code are placeholder examples. Replace with secure authentication (hashed passwords, DB-backed) for production.
* Receipts are saved in the working directory. To change location, update `handleReportGeneration` or `processPayment` logic.
* The QuickSort timing is for demonstration purposes only — actual times vary by machine and load.

---

## 📄 License

This project is open source under the MIT License. See `LICENSE` for detail
