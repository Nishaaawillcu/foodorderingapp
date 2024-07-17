import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RegisterFrame extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JComboBox<String> roleComboBox;

    public RegisterFrame() {
        setTitle("Register New User");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create panel for inputs
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));

        // Username field
        inputPanel.add(new JLabel("Username:"));
        userField = new JTextField();
        inputPanel.add(userField);

        // Password field
        inputPanel.add(new JLabel("Password:"));
        passField = new JPasswordField();
        inputPanel.add(passField);

        // Role selection
        inputPanel.add(new JLabel("Role:"));
        String[] roles = {"Vendor", "Customer", "Delivery Runner"};
        roleComboBox = new JComboBox<>(roles);
        inputPanel.add(roleComboBox);

        add(inputPanel, BorderLayout.CENTER);

        // Create register button
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterButtonListener());
        add(registerButton, BorderLayout.SOUTH);
    }

    private class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            String role = (String) roleComboBox.getSelectedItem();

            List<User> users = FileHandler.readUsers();
            users.add(new User(username, password, role));
            FileHandler.writeUsers(users);

            JOptionPane.showMessageDialog(RegisterFrame.this, "User registered successfully!");
            dispose();
        }
    }
}
