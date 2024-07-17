import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerFrame extends JFrame {
    private final String customerId;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final DefaultTableModel menuTableModel;
    private final DefaultTableModel orderTableModel;
    private final List<VendorFrame.Item> menuItems;
    private final List<VendorFrame.Item> orderItems;
    private double totalOrderPrice = 0.0;
    private JLabel totalOrderLabel;

    public CustomerFrame(String customerId) {
        this.customerId = customerId;
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        menuItems = new ArrayList<>();
        orderItems = new ArrayList<>();
        menuTableModel = new DefaultTableModel(new Object[]{"Item ID", "Name", "Price (NPR)", "Image"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        orderTableModel = new DefaultTableModel(new Object[]{"Item ID", "Name", "Price (NPR)", "Image"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        initializeUI();
        loadMenuItemsFromFile(); // Load menu items from file on initialization
    }

    private void initializeUI() {
        setTitle("Customer Interface");
        setSize(1200, 800); // Increased the size of the dashboard
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Sidebar buttons with different colors
        JButton viewMenuButton = createButton("View Menu", new Color(70, 130, 180), new ViewMenuListener());
        JButton placeOrderButton = createButton("Place Order", new Color(100, 149, 237), new PlaceOrderListener());
        JButton checkOrderStatusButton = createButton("Check Order Status", new Color(255, 69, 0), new CheckOrderStatusListener());
        JButton checkOrderHistoryButton = createButton("Check Order History", new Color(60, 179, 113), new CheckOrderHistoryListener());
        JButton checkTransactionHistoryButton = createButton("Check Transaction History", new Color(32, 178, 170), new CheckTransactionHistoryListener());
        JButton provideReviewButton = createButton("Provide Review", new Color(138, 43, 226), new ProvideReviewListener());
        JButton reorderButton = createButton("Reorder", new Color(75, 0, 130), new ReorderListener());
        JButton logoutButton = createButton("Logout", new Color(220, 20, 60), new LogoutListener());

        // Sidebar panel with a gradient background
        JPanel sidebarPanel = new JPanel(new GridBagLayout()) {
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
        sidebarPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;

        gbc.gridy = 0;
        sidebarPanel.add(viewMenuButton, gbc);
        gbc.gridy = 1;
        sidebarPanel.add(placeOrderButton, gbc);
        gbc.gridy = 2;
        sidebarPanel.add(checkOrderStatusButton, gbc);
        gbc.gridy = 3;
        sidebarPanel.add(checkOrderHistoryButton, gbc);
        gbc.gridy = 4;
        sidebarPanel.add(checkTransactionHistoryButton, gbc);
        gbc.gridy = 5;
        sidebarPanel.add(provideReviewButton, gbc);
        gbc.gridy = 6;
        sidebarPanel.add(reorderButton, gbc);
        gbc.gridy = 7;
        sidebarPanel.add(logoutButton, gbc);

        // Main content panel with a white background
        mainPanel.setBackground(Color.WHITE);
        mainPanel.add(new JPanel(), "Default");
        mainPanel.add(createViewMenuPanel(), "View Menu");
        mainPanel.add(createPlaceOrderPanel(), "Place Order");
        mainPanel.add(createCheckOrderStatusPanel(), "Check Order Status");
        mainPanel.add(createCheckOrderHistoryPanel(), "Check Order History");
        mainPanel.add(createCheckTransactionHistoryPanel(), "Check Transaction History");
        mainPanel.add(createProvideReviewPanel(), "Provide Review");
        mainPanel.add(createReorderPanel(), "Reorder");

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

    private JPanel createViewMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JTable table = new JTable(menuTableModel) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 3) {
                    return ImageIcon.class;
                } else {
                    return Object.class;
                }
            }
        };
        table.setRowHeight(100); // Adjust row height for larger images
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createPlaceOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JTable table = new JTable(menuTableModel) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 3) {
                    return ImageIcon.class;
                } else {
                    return Object.class;
                }
            }
        };
        table.setRowHeight(100); // Adjust row height for larger images
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                VendorFrame.Item selectedItem = menuItems.get(selectedRow);
                int response = JOptionPane.showConfirmDialog(this, "Do you want to order this item?", "Confirm Order", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    orderItems.add(selectedItem);
                    totalOrderPrice += selectedItem.getPrice();
                    updateOrderTable();
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel totalPanel = new JPanel();
        totalOrderLabel = new JLabel("Total Order Price: NPR 0.0");
        totalOrderLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalPanel.add(totalOrderLabel);

        JButton placeOrderButton = new JButton("Place Order");
        placeOrderButton.addActionListener(e -> placeOrder());
        totalPanel.add(placeOrderButton);

        panel.add(totalPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void updateOrderTable() {
        orderTableModel.setRowCount(0);
        for (VendorFrame.Item item : orderItems) {
            ImageIcon itemIcon = new ImageIcon(new ImageIcon(item.getImagePath()).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            orderTableModel.addRow(new Object[]{item.getItemId(), item.getName(), "NPR " + item.getPrice(), itemIcon});
        }
        totalOrderLabel.setText("Total Order Price: NPR " + totalOrderPrice);
    }

    private void placeOrder() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("orders.txt", true))) {
            StringBuilder orderDetails = new StringBuilder();
            for (VendorFrame.Item item : orderItems) {
                orderDetails.append(item.getItemId()).append(",").append(item.getName()).append(",").append(item.getPrice()).append(",").append(item.getImagePath()).append(";");
            }
            writer.write(customerId + "," + orderDetails.toString() + totalOrderPrice);
            writer.newLine();
            JOptionPane.showMessageDialog(this, "Order placed successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JPanel createCheckOrderStatusPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Check Order Status Panel"));
        return panel;
    }

    private JPanel createCheckOrderHistoryPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Check Order History Panel"));
        return panel;
    }

    private JPanel createCheckTransactionHistoryPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Check Transaction History Panel"));
        return panel;
    }

    private JPanel createProvideReviewPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Provide Review Panel"));
        return panel;
    }

    private JPanel createReorderPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Reorder Panel"));
        return panel;
    }

    private void loadMenuItemsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("items.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                VendorFrame.Item item = VendorFrame.Item.fromString(line);
                if (item != null) {
                    menuItems.add(item);
                    ImageIcon itemIcon = new ImageIcon(new ImageIcon(item.getImagePath()).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                    menuTableModel.addRow(new Object[]{item.getItemId(), item.getName(), "NPR " + item.getPrice(), itemIcon});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ViewMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "View Menu");
        }
    }

    private class PlaceOrderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "Place Order");
        }
    }

    private class CheckOrderStatusListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "Check Order Status");
        }
    }

    private class CheckOrderHistoryListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "Check Order History");
        }
    }

    private class CheckTransactionHistoryListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "Check Transaction History");
        }
    }

    private class ProvideReviewListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "Provide Review");
        }
    }

    private class ReorderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "Reorder");
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
            CustomerFrame customerFrame = new CustomerFrame("customer1");
            customerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            customerFrame.setVisible(true);
        });
    }
}
