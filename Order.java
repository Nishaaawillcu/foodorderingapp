import java.io.Serializable;

public class Order implements Serializable {
    private static final long serialVersionUID = 123456789L;

    private String orderId;
    private String customerId;
    private String vendorId;
    private String deliveryRunnerId;
    private String status;
    private String review;
    private double totalAmount;

    public Order(String orderId, String customerId, String vendorId, String deliveryRunnerId, String status, double totalAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.vendorId = vendorId;
        this.deliveryRunnerId = deliveryRunnerId;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    // Removed the unused constructor

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public String getDeliveryRunnerId() {
        return deliveryRunnerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setDeliveryRunnerId(String runnerId) {
        this.deliveryRunnerId = runnerId;
    }
}
