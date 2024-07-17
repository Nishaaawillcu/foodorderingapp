import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class FoodOrderingSystem {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Initialize with dummy accounts
            AdminFrame adminFrame = new AdminFrame("admin");
            adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            adminFrame.setVisible(false); // Temporarily set invisible to initialize accounts okk

            // Open login frame
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginFrame.setVisible(true);
        });
    }
}
