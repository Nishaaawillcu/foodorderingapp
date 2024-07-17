import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoginFrame extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JComboBox<String> roleComboBox;
    private JButton registerButton;

    public LoginFrame() {
        setTitle("Food Ordering System Login");
        setSize(800, 600); // Adjusted the size of the frame
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Logo panel
        JPanel logoPanel = new JPanel(new BorderLayout());
        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("images/logo.png"); // Ensure you have a logo at this path
        logoLabel.setIcon(resizeIcon(logoIcon, 200, 200)); // Resize the logo
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoPanel.add(logoLabel, BorderLayout.CENTER);
        logoPanel.setBackground(new Color(0x2E3B4E));

        // Create panel for inputs
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        inputPanel.setBackground(new Color(0xF5F5F5));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding between elements
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username field
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        inputPanel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        userField = new JTextField(20);
        userField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(userField, gbc);

        // Password field
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        inputPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        passField = new JPasswordField(20);
        passField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(passField, gbc);

        // Role selection
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        inputPanel.add(roleLabel, gbc);
        gbc.gridx = 1;
        String[] roles = {"Vendor", "Customer", "Delivery Runner", "Administrator"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(roleComboBox, gbc);

        add(logoPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);

        // Create panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0x2E3B4E));
        JButton loginButton = createButton("Login", "images/login.png"); // Ensure you have a login icon at this path
        registerButton = createButton("Register", "images/register.png"); // Ensure you have a register icon at this path
        registerButton.setVisible(false); // Initially hidden

        // Add listeners
        roleComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("Administrator".equals(roleComboBox.getSelectedItem().toString())) {
                    registerButton.setVisible(true);
                } else {
                    registerButton.setVisible(false);
                }
            }
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        loginButton.addActionListener(new LoginButtonListener());
        registerButton.addActionListener(new RegisterButtonListener());
    }

    private JButton createButton(String text, String imagePath) {
        ImageIcon icon = resizeIcon(new ImageIcon(imagePath), 20, 20); // Resize icon to fit the button
        JButton button = new JButton(text, icon);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(0x5BC0EB));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT); // Align text and icon
        return button;
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            String role = (String) roleComboBox.getSelectedItem();

            List<User> users = FileHandler.readUsers();
            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password) && user.getRole().equals(role)) {
                    switch (role) {
                        case "Vendor":
                            new VendorFrame(username).setVisible(true);
                            break;
                        case "Customer":
                            new CustomerFrame(username).setVisible(true);
                            break;
                        case "Delivery Runner":
                            new DeliveryRunnerFrame(username).setVisible(true);
                            break;
                        case "Administrator":
                            new AdminFrame(username).setVisible(true);
                            break;
                    }
                    dispose();
                    return;
                }
            }
            JOptionPane.showMessageDialog(LoginFrame.this, "Invalid credentials!");
        }
    }

    private class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new RegisterFrame().setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginFrame.setVisible(true);
        });
    }
}
