import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {

    private JTextField userField;
    private JPasswordField passField;

    public LoginFrame() {
        setTitle("Parking System - Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");

        userField = new JTextField(15);
        passField = new JPasswordField(15);

        JButton loginBtn = new JButton("Login");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5,5,5,5);
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(userLabel, gbc);
        gbc.gridx = 1;
        panel.add(userField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(passLabel, gbc);
        gbc.gridx = 1;
        panel.add(passField, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(loginBtn, gbc);

        add(panel);

        // Hardcoded credentials: admin / admin123
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String u = userField.getText();
                String p = new String(passField.getPassword());

                if ("admin".equals(u) && "admin123".equals(p)) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Login successful!");
                    new MainFrame().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Invalid username or password.");
                }
            }
        });
    }

    public static void main(String[] args) {
        // Start app
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }
}
