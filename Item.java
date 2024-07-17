import java.io.Serializable;

public class Item implements Serializable {
    private static final long serialVersionUID = 9055816455504099132L;

    private String itemId;
    private String name;
    private double price;
    private String vendorId;

    public Item(String itemId, String name, double price, String vendorId) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.vendorId = vendorId;
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
}
