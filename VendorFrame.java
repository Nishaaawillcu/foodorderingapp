import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VendorFrame extends JFrame {
    private final String vendorId;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final DefaultTableModel tableModel;
    private final DefaultTableModel orderTableModel;
    private final List<Item> itemList;
    private static final String ITEM_FILE = "items.txt"; // File to store items
    private static final String ORDER_FILE = "orders.txt"; // File to store orders

    public VendorFrame(String vendorId) {
        this.vendorId = vendorId;
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        itemList = new ArrayList<>();
        tableModel = new DefaultTableModel(new Object[]{"Item ID", "Name", "Price (NPR)", "Image"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        orderTableModel = new DefaultTableModel(new Object[]{"Customer ID", "Items", "Total Price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        initializeUI();
        loadItemsFromFile(); // Load items from file on initialization
        loadOrdersFromFile(); // Load orders from file on initialization
    }

    private void initializeUI() {
        setTitle("Vendor Interface");
        setSize(800, 1000); // Increased the size of the dashboard
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Sidebar buttons with different colors
        JButton createItemButton = createButton("Create Item", new Color(70, 130, 180), new CreateItemListener()); // Light Steel Blue
        JButton updateItemButton = createButton("Update Item", new Color(100, 149, 237), new UpdateItemListener()); // Cornflower Blue
        JButton deleteItemButton = createButton("Delete Item", new Color(255, 69, 0), new DeleteItemListener()); // Red Orange
        JButton viewOrdersButton = createButton("View Orders", new Color(60, 179, 113), new ViewOrdersListener()); // Medium Sea Green
        JButton acceptOrderButton = createButton("Accept Order", new Color(32, 178, 170), new AcceptOrderListener()); // Light Sea Green
        JButton cancelOrderButton = createButton("Cancel Order", new Color(255, 140, 0), new CancelOrderListener()); // Dark Orange
        JButton updateOrderStatusButton = createButton("Update Order Status", new Color(138, 43, 226), new UpdateOrderStatusListener()); // Blue Violet
        JButton checkOrderHistoryButton = createButton("Check Order History", new Color(75, 0, 130), new CheckOrderHistoryListener()); // Indigo
        JButton readCustomerReviewsButton = createButton("Read Customer Reviews", new Color(199, 21, 133), new ReadCustomerReviewsListener()); // Medium Violet Red
        JButton revenueDashboardButton = createButton("Revenue Dashboard", new Color(255, 215, 0), new RevenueDashboardListener()); // Gold
        JButton logoutButton = createButton("Logout", new Color(220, 20, 60), new LogoutListener()); // Crimson

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
        sidebarPanel.add(createItemButton, gbc);
        gbc.gridy = 1;
        sidebarPanel.add(updateItemButton, gbc);
        gbc.gridy = 2;
        sidebarPanel.add(deleteItemButton, gbc);
        gbc.gridy = 3;
        sidebarPanel.add(viewOrdersButton, gbc);
        gbc.gridy = 4;
        sidebarPanel.add(acceptOrderButton, gbc);
        gbc.gridy = 5;
        sidebarPanel.add(cancelOrderButton, gbc);
        gbc.gridy = 6;
        sidebarPanel.add(updateOrderStatusButton, gbc);
        gbc.gridy = 7;
        sidebarPanel.add(checkOrderHistoryButton, gbc);
        gbc.gridy = 8;
        sidebarPanel.add(readCustomerReviewsButton, gbc);
        gbc.gridy = 9;
        sidebarPanel.add(revenueDashboardButton, gbc);
        gbc.gridy = 10;
        sidebarPanel.add(logoutButton, gbc);

        // Main content panel with a white background
        mainPanel.setBackground(Color.WHITE);
        mainPanel.add(new JPanel(), "Default");
        mainPanel.add(createItemPanel(), "Create Item");
        mainPanel.add(updateItemPanel(), "Update Item");
        mainPanel.add(deleteItemPanel(), "Delete Item");
        mainPanel.add(viewOrdersPanel(), "View Orders");
        mainPanel.add(acceptOrderPanel(), "Accept Order");
        mainPanel.add(cancelOrderPanel(), "Cancel Order");
        mainPanel.add(updateOrderStatusPanel(), "Update Order Status");
        mainPanel.add(checkOrderHistoryPanel(), "Check Order History");
        mainPanel.add(readCustomerReviewsPanel(), "Read Customer Reviews");
        mainPanel.add(revenueDashboardPanel(), "Revenue Dashboard");

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

    private JPanel createItemPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Item ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Item ID:"), gbc);
        JTextField itemIdField = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(itemIdField, gbc);

        // Item Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Item Name:"), gbc);
        JTextField itemNameField = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(itemNameField, gbc);

        // Item Price
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Item Price (NPR):"), gbc);
        JTextField itemPriceField = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(itemPriceField, gbc);

        // Item Image
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Item Image:"), gbc);
        JButton imageButton = new JButton("Select Image");
        JLabel imageLabel = new JLabel();
        gbc.gridx = 1;
        inputPanel.add(imageButton, gbc);
        gbc.gridx = 2;
        inputPanel.add(imageLabel, gbc);

        final String[] imagePath = new String[1];

        imageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                imagePath[0] = selectedFile.getAbsolutePath();
                ImageIcon itemIcon = new ImageIcon(new ImageIcon(imagePath[0]).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
                imageLabel.setIcon(itemIcon);
            }
        });

        // Add Item Button
        JButton addItemButton = new JButton("Add Item");
        addItemButton.addActionListener(e -> {
            String itemId = itemIdField.getText();
            String itemName = itemNameField.getText();
            double itemPrice;
            try {
                itemPrice = Double.parseDouble(itemPriceField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid price format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Item newItem = new Item(itemId, itemName, itemPrice, vendorId, imagePath[0]);
            itemList.add(newItem);
            saveItemsToFile(); // Save the new item to file
            ImageIcon itemIcon = new ImageIcon(new ImageIcon(imagePath[0]).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            tableModel.addRow(new Object[]{itemId, itemName, "NPR " + itemPrice, itemIcon});
            itemIdField.setText("");
            itemNameField.setText("");
            itemPriceField.setText("");
            imageLabel.setIcon(null);
        });

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        inputPanel.add(addItemButton, gbc);

        panel.add(inputPanel, BorderLayout.NORTH);

        JTable table = new JTable(tableModel) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 3) {
                    return ImageIcon.class;
                } else {
                    return Object.class;
                }
            }
        };
        table.setRowHeight(50);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void saveItemsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ITEM_FILE))) {
            for (Item item : itemList) {
                writer.write(item.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadItemsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ITEM_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Item item = Item.fromString(line);
                if (item != null) {
                    itemList.add(item);
                    ImageIcon itemIcon = new ImageIcon(new ImageIcon(item.getImagePath()).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
                    tableModel.addRow(new Object[]{item.getItemId(), item.getName(), "NPR " + item.getPrice(), itemIcon});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadOrdersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ORDER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String customerId = parts[0];
                    String items = parts[1];
                    String totalPrice = parts[2];
                    orderTableModel.addRow(new Object[]{customerId, items, "NPR " + totalPrice});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JPanel updateItemPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Item ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Item ID to Update:"), gbc);
        JTextField itemIdField = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(itemIdField, gbc);

        // Item Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("New Item Name:"), gbc);
        JTextField itemNameField = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(itemNameField, gbc);

        // Item Price
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("New Item Price (NPR):"), gbc);
        JTextField itemPriceField = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(itemPriceField, gbc);

        // Item Image
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("New Item Image:"), gbc);
        JButton imageButton = new JButton("Select Image");
        JLabel imageLabel = new JLabel();
        gbc.gridx = 1;
        inputPanel.add(imageButton, gbc);
        gbc.gridx = 2;
        inputPanel.add(imageLabel, gbc);

        final String[] imagePath = new String[1];

        imageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                imagePath[0] = selectedFile.getAbsolutePath();
                ImageIcon itemIcon = new ImageIcon(new ImageIcon(imagePath[0]).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
                imageLabel.setIcon(itemIcon);
            }
        });

        // Update Item Button
        JButton updateItemButton = new JButton("Update Item");
        updateItemButton.addActionListener(e -> {
            String itemId = itemIdField.getText();
            String itemName = itemNameField.getText();
            double itemPrice;
            try {
                itemPrice = Double.parseDouble(itemPriceField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid price format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            for (Item item : itemList) {
                if (item.getItemId().equals(itemId)) {
                    item.setName(itemName);
                    item.setPrice(itemPrice);
                    item.setImagePath(imagePath[0]);
                    saveItemsToFile(); // Save updated items to file
                    refreshTable();
                    break;
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        inputPanel.add(updateItemButton, gbc);

        panel.add(inputPanel, BorderLayout.NORTH);

        JTable table = new JTable(tableModel) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 3) {
                    return ImageIcon.class;
                } else {
                    return Object.class;
                }
            }
        };
        table.setRowHeight(50);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Item item : itemList) {
            ImageIcon itemIcon = new ImageIcon(new ImageIcon(item.getImagePath()).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            tableModel.addRow(new Object[]{item.getItemId(), item.getName(), "NPR " + item.getPrice(), itemIcon});
        }
    }

    private JPanel deleteItemPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Item ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Item ID to Delete:"), gbc);
        JTextField itemIdField = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(itemIdField, gbc);

        // Delete Item Button
        JButton deleteItemButton = new JButton("Delete Item");
        deleteItemButton.addActionListener(e -> {
            String itemId = itemIdField.getText();
            itemList.removeIf(item -> item.getItemId().equals(itemId));
            saveItemsToFile(); // Save updated items to file
            refreshTable();
        });

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        inputPanel.add(deleteItemButton, gbc);

        panel.add(inputPanel, BorderLayout.NORTH);

        JTable table = new JTable(tableModel) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 3) {
                    return ImageIcon.class;
                } else {
                    return Object.class;
                }
            }
        };
        table.setRowHeight(50);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel viewOrdersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTable orderTable = new JTable(orderTableModel) {
            @Override
            public Class<?> getColumnClass(int column) {
                return Object.class;
            }
        };
        orderTable.setRowHeight(50);
        JScrollPane scrollPane = new JScrollPane(orderTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel acceptOrderPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Accept Order Panel"));
        return panel;
    }

    private JPanel cancelOrderPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Cancel Order Panel"));
        return panel;
    }

    private JPanel updateOrderStatusPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Update Order Status Panel"));
        return panel;
    }

    private JPanel checkOrderHistoryPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Check Order History Panel"));
        return panel;
    }

    private JPanel readCustomerReviewsPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Read Customer Reviews Panel"));
        return panel;
    }

    private JPanel revenueDashboardPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Revenue Dashboard Panel"));
        return panel;
    }

    private class CreateItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "Create Item");
        }
    }

    private class UpdateItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "Update Item");
        }
    }

    private class DeleteItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "Delete Item");
        }
    }

    private class ViewOrdersListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "View Orders");
        }
    }

    private class AcceptOrderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "Accept Order");
        }
    }

    private class CancelOrderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "Cancel Order");
        }
    }

    private class UpdateOrderStatusListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "Update Order Status");
        }
    }

    private class CheckOrderHistoryListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "Check Order History");
        }
    }

    private class ReadCustomerReviewsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "Read Customer Reviews");
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
            VendorFrame vendorFrame = new VendorFrame("vendor1");
            vendorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            vendorFrame.setVisible(true);
        });
    }

    static class Item {
        private final String itemId;
        private String name;
        private double price;
        private final String vendorId;
        private String imagePath;

        public Item(String itemId, String name, double price, String vendorId, String imagePath) {
            this.itemId = itemId;
            this.name = name;
            this.price = price;
            this.vendorId = vendorId;
            this.imagePath = imagePath;
        }

        public String getItemId() {
            return itemId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getVendorId() {
            return vendorId;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        @Override
        public String toString() {
            return itemId + "," + name + "," + price + "," + vendorId + "," + imagePath;
        }

        public static Item fromString(String str) {
            String[] parts = str.split(",");
            if (parts.length != 5) {
                System.err.println("Invalid item format: " + str);
                return null;
            }
            return new Item(parts[0], parts[1], Double.parseDouble(parts[2]), parts[3], parts[4]);
        }
    }
}
