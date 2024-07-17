import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminFrame extends JFrame {
    private final String adminId;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    public AdminFrame(String adminId) {
        this.adminId = adminId;
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Administrator Interface");
        setSize(800, 1000); // Adjusted size of the frame
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Sidebar buttons with different colors
        JButton registerUserButton = createButton("Register User", new Color(70, 130, 180), new RegisterUserListener()); // Light Steel Blue
        JButton manageVendorsButton = createButton("Manage Vendors", new Color(100, 149, 237), new ManageVendorsListener()); // Cornflower Blue
        JButton manageCustomersButton = createButton("Manage Customers", new Color(60, 179, 113), new ManageCustomersListener()); // Medium Sea Green
        JButton manageRunnersButton = createButton("Manage Runners", new Color(32, 178, 170), new ManageRunnersListener()); // Light Sea Green
        JButton topupCreditButton = createButton("Top-up Credit", new Color(255, 140, 0), new TopUpCreditListener()); // Dark Orange
        JButton generateReceiptButton = createButton("Generate Receipt", new Color(138, 43, 226), new GenerateReceiptListener()); // Blue Violet
        JButton logoutButton = createButton("Logout", new Color(220, 20, 60), new LogoutListener()); // Crimson

        // Sidebar panel with a gradient background
        JPanel sidebarPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(45, 45, 45);
                Color color2 = new Color(85, 85, 85);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        sidebarPanel.add(registerUserButton);
        sidebarPanel.add(manageVendorsButton);
        sidebarPanel.add(manageCustomersButton);
        sidebarPanel.add(manageRunnersButton);
        sidebarPanel.add(topupCreditButton);
        sidebarPanel.add(generateReceiptButton);
        sidebarPanel.add(logoutButton);

        // Main content panel with a white background
        mainPanel.setBackground(Color.WHITE);
        mainPanel.add(new JPanel(), "Default");
        mainPanel.add(registerUserPanel(), "Register User");
        mainPanel.add(manageVendorsPanel(), "Manage Vendors");
        mainPanel.add(manageCustomersPanel(), "Manage Customers");
        mainPanel.add(manageRunnersPanel(), "Manage Runners");
        mainPanel.add(topupCreditPanel(), "Top-up Credit");
        mainPanel.add(generateReceiptPanel(), "Generate Receipt");

        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebarPanel, mainPanel);
        splitPane.setDividerLocation(250); // Adjusted divider location for a bigger sidebar

        // Add split pane to the frame
        getContentPane().add(splitPane);
    }

    private JButton createButton(String text, Color backgroundColor, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        button.setFocusPainted(false);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setHorizontalAlignment(SwingConstants.LEFT); // Align text
        return button;
    }

    private JPanel registerUserPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Register User Panel"));
        return panel;
    }

    private JPanel manageVendorsPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Manage Vendors Panel"));
        return panel;
    }

    private JPanel manageCustomersPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Manage Customers Panel"));
        return panel;
    }

    private JPanel manageRunnersPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Manage Runners Panel"));
        return panel;
    }

    private JPanel topupCreditPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Top-up Credit Panel"));
        return panel;
    }

    private JPanel generateReceiptPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Generate Receipt Panel"));
        return panel;
    }

    private class RegisterUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "Register User");
        }
    }

    private class ManageVendorsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "Manage Vendors");
        }
    }

    private class ManageCustomersListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "Manage Customers");
        }
    }

    private class ManageRunnersListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "Manage Runners");
        }
    }

    private class TopUpCreditListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "Top-up Credit");
        }
    }

    private class GenerateReceiptListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "Generate Receipt");
        }
    }

    private class LogoutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                dispose();
                SwingUtilities.invokeLater(() -> {
                    LoginFrame loginFrame = new LoginFrame();
                    loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    loginFrame.setVisible(true);
                });
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminFrame adminFrame = new AdminFrame("admin1");
            adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            adminFrame.setVisible(true);
        });
    }
}
