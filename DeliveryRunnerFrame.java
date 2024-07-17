import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeliveryRunnerFrame extends JFrame {
    private String runnerId;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    public DeliveryRunnerFrame(String runnerId) {
        this.runnerId = runnerId;
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Delivery Runner Interface");
        setSize(1200, 800); // Increased the size of the dashboard
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Sidebar buttons with different colors
        JButton viewTasksButton = createButton("View Tasks", new Color(70, 130, 180), new ViewTasksListener(), "image/view_tasks.png");
        JButton acceptDeclineTaskButton = createButton("Accept/Decline Task", new Color(100, 149, 237), new AcceptDeclineTaskListener(), "image/accept_decline_task.png");
        JButton updateTaskStatusButton = createButton("Update Task Status", new Color(255, 69, 0), new UpdateTaskStatusListener(), "image/update_task_status.png");
        JButton checkTaskHistoryButton = createButton("Check Task History", new Color(60, 179, 113), new CheckTaskHistoryListener(), "image/check_task_history.png");
        JButton readCustomerReviewButton = createButton("Read Customer Review", new Color(32, 178, 170), new ReadCustomerReviewListener(), "image/read_customer_review.png");
        JButton revenueDashboardButton = createButton("Revenue Dashboard", new Color(138, 43, 226), new RevenueDashboardListener(), "image/revenue_dashboard.png");
        JButton logoutButton = createButton("Logout", new Color(220, 20, 60), new LogoutListener(), "image/logout.png");

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
        sidebarPanel.add(viewTasksButton);
        sidebarPanel.add(acceptDeclineTaskButton);
        sidebarPanel.add(updateTaskStatusButton);
        sidebarPanel.add(checkTaskHistoryButton);
        sidebarPanel.add(readCustomerReviewButton);
        sidebarPanel.add(revenueDashboardButton);
        sidebarPanel.add(logoutButton);

        // Main content panel with a white background
        mainPanel.setBackground(Color.WHITE);
        mainPanel.add(new JPanel(), "Default");
        mainPanel.add(createViewTasksPanel(), "View Tasks");
        mainPanel.add(createAcceptDeclineTaskPanel(), "Accept/Decline Task");
        mainPanel.add(createUpdateTaskStatusPanel(), "Update Task Status");
        mainPanel.add(createCheckTaskHistoryPanel(), "Check Task History");
        mainPanel.add(createReadCustomerReviewPanel(), "Read Customer Review");
        mainPanel.add(createRevenueDashboardPanel(), "Revenue Dashboard");

        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebarPanel, mainPanel);
        splitPane.setDividerLocation(250); // Adjusted divider location for a bigger sidebar

        // Add split pane to the frame
        getContentPane().add(splitPane);
    }

    private JButton createButton(String text, Color backgroundColor, ActionListener listener, String iconPath) {
        ImageIcon icon = loadIcon(iconPath);
        JButton button = new JButton(text, icon);
        button.addActionListener(listener);
        button.setFocusPainted(false);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setHorizontalAlignment(SwingConstants.LEFT); // Align text
        return button;
    }

    private ImageIcon loadIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private JPanel createViewTasksPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("View Tasks Panel"));
        return panel;
    }

    private JPanel createAcceptDeclineTaskPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Accept/Decline Task Panel"));
        return panel;
    }

    private JPanel createUpdateTaskStatusPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Update Task Status Panel"));
        return panel;
    }

    private JPanel createCheckTaskHistoryPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Check Task History Panel"));
        return panel;
    }

    private JPanel createReadCustomerReviewPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Read Customer Review Panel"));
        return panel;
    }

    private JPanel createRevenueDashboardPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Revenue Dashboard Panel"));
        return panel;
    }

    private class ViewTasksListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "View Tasks");
        }
    }

    private class AcceptDeclineTaskListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "Accept/Decline Task");
        }
    }

    private class UpdateTaskStatusListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "Update Task Status");
        }
    }

    private class CheckTaskHistoryListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "Check Task History");
        }
    }

    private class ReadCustomerReviewListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "Read Customer Review");
        }
    }

    private class RevenueDashboardListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "Revenue Dashboard");
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
            DeliveryRunnerFrame deliveryRunnerFrame = new DeliveryRunnerFrame("runner1");
            deliveryRunnerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            deliveryRunnerFrame.setVisible(true);
        });
    }
}
