import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String USER_FILE = "users.txt";
    private static final String ORDER_FILE = "orders.txt";
    private static final String ITEM_FILE = "items.txt";

    // Read users from file
    public static List<User> readUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    users.add(new User(parts[0], parts[1], parts[2]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("User file not found, creating a new one.");
            writeUsers(users);  // Create an empty file if not found 777777
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Write users to file
    public static void writeUsers(List<User> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USER_FILE))) {
            for (User user : users) {
                bw.write(user.getUsername() + "," + user.getPassword() + "," + user.getRole());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read orders from file
    public static List<Order> readOrders() {
        List<Order> orders = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ORDER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    orders.add(new Order(parts[0], parts[1], parts[2], parts[3], parts[4], Double.parseDouble(parts[5])));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Order file not found, creating a new one.");
            writeOrders(orders);  // Create an empty file if not found
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orders;
    }

    // Write orders to file
    public static void writeOrders(List<Order> orders) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ORDER_FILE))) {
            for (Order order : orders) {
                bw.write(order.getOrderId() + "," + order.getCustomerId() + "," + order.getVendorId() + "," + order.getDeliveryRunnerId() + "," + order.getStatus() + "," + order.getTotalAmount());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read items from file
    public static List<Item> readItems() {
        List<Item> items = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ITEM_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    items.add(new Item(parts[0], parts[1], Double.parseDouble(parts[2]), parts[3]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Item file not found, creating a new one.");
            writeItems(items);  // Create an empty file if not found
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    // Write items to file
    public static void writeItems(List<Item> items) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ITEM_FILE))) {
            for (Item item : items) {
                bw.write(item.getItemId() + "," + item.getName() + "," + item.getPrice() + "," + item.getVendorId());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
