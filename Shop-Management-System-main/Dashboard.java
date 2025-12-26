import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class Dashboard extends JFrame {
    private BillingScreen billingScreen;

    public Dashboard() {
        billingScreen = new BillingScreen();
        initUI();
    }

    private void initUI() {
        setTitle("Shop Dashboard");
        setSize(1024, 768);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create tabbed interface
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Billing", billingScreen);
        tabbedPane.addTab("Reports", createReportsPanel());

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem reportItem = new JMenuItem("Generate Report");
        reportItem.addActionListener(this::handleReportGeneration);
        fileMenu.add(reportItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        add(tabbedPane);
    }

    private JPanel createReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton generateBtn = new JButton("Generate Custom Report");
        generateBtn.addActionListener(this::handleReportGeneration);
        panel.add(generateBtn, BorderLayout.NORTH);
        return panel;
    }

    private void handleReportGeneration(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Report");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Files", "txt"));

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filename.toLowerCase().endsWith(".txt")) {
                filename += ".txt";
            }

            List<Transaction> transactions = billingScreen.getTransactionHistory();
            new ReportFileHandler().generateReport(transactions, filename);

            JOptionPane.showMessageDialog(this,
                    "Report generated successfully!\nLocation: " + filename,
                    "Report Status",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Dashboard().setVisible(true));
    }
}