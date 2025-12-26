import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginScreen() {
        initUI();
    }

    private void initUI() {
        setTitle("Shop Login");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this::authenticateUser);
        panel.add(loginButton);

        add(panel, BorderLayout.CENTER);
    }

    private void authenticateUser(ActionEvent e) {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();

        // Add your actual authentication logic here
        if (isValidCredentials(username, new String(password))) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            new Dashboard().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Credentials", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isValidCredentials(String username, String password) {
        // Connect to your User authentication system
        return !username.isEmpty() && !password.isEmpty();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginScreen().setVisible(true));
    }
}