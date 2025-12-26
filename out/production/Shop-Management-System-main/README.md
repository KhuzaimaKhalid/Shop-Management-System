# Shop Management System 🛒

A Java Swing-based desktop application designed for small businesses to streamline billing, inventory management, and transaction processing. Easily add products, generate receipts, and manage sales all within a user-friendly interface.

**Download the latest release:** [Shop Management System.zip](./Shop%20Management%20System.zip)

## 🚀 Brief Overview

* **Add Products to Cart**: Quickly enter product details (ID, Name, Price, Quantity) and see them appear in a dynamic cart table.
* **Process Payments**: Calculate subtotal, apply a 10% tax, and finalize transactions with a single click.
* **Generate & Print Receipts**: View a well-formatted receipt in-app, save it as a `.txt` file, and print directly from Notepad using **Ctrl+P** or the Print menu.
* **Transaction History**: Maintain an in-memory log of all transactions for review and reporting.

## ✨ Key Features

| Icon | Feature                     | Description                                                                 |
| ---- | --------------------------- | --------------------------------------------------------------------------- |
| 🛒   | Add to Cart                 | Enter product details and add multiple items to your shopping cart.         |
| 💰   | Process Invoice             | Automatically calculate totals, taxes, and finalize payments.               |
| 🧾   | Receipt Generation & Saving | Display receipt in a formatted dialog and save it as a `.txt` file.         |
| 🖨️  | Print Support               | Open saved receipt in Notepad and print via **Ctrl+P** or **File > Print**. |
| 📜   | Transaction Log             | Keep track of each sale with unique transaction IDs and details.            |

## 🛠 How to Use

1. **Launch** the application by running `BillingScreen` in your IDE or via the compiled `.jar`.
2. **Add Products**:

   * Fill in **Product ID**, **Name**, **Price**, and **Quantity** fields.
   * Click **Add to Cart**.
3. **Review Cart**: See all items listed in the table with individual totals.
4. **Process Payment**:

   * Click **Process Payment** to generate a formatted receipt.
   * A dialog will show the receipt details.
5. **Save Receipt**:

   * The receipt is automatically saved in the project directory as `receipt_<transactionId>.txt`.
6. **Print Receipt**:

   * Open the `.txt` in Notepad.
   * Press **Ctrl+P** or select **File > Print** to print.

## 📂 Project Structure

```
src/
├── model/
│   ├── Product.java
│   └── Transaction.java
├── ui/
│   └── BillingScreen.java
└── Main.java
```

* **model**: Data classes implementing `Storable` and `Calculable` interfaces.
* **ui**: Swing-based panels and screens for user interaction.
* **Main.java**: Entry point to launch the application.

## 📋 Requirements

* **Java SE 8** or higher
* No external libraries required (Swing is built into the JDK)

## 📄 License

This project is open source under the [MIT License](LICENSE).

---

✉️ **Feedback & Contributions**

Found a bug or have a feature request? Please open an issue or submit a pull request on GitHub.
