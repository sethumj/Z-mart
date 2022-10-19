package superMarket;

import java.util.ArrayList;

public class Product {
    private String productName;
    private int quantity;
    private float mrp;
    private int discount;
    private String category;
    private String description;
    private ArrayList<String> tags;
    private int productId;
    private float discountedPrice;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getMrp() {
        return mrp;
    }

    public void setMrp(float mrp) {
        this.mrp = mrp;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public int getProductId() {
        return productId;
    }
    public float getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(float discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public Product(String productName, int quantity, float mrp, int discount, String category, ArrayList<String> tags, String description, int productId, String mainCategory){
        this.productName = productName;
        this.quantity = quantity;
        this.mrp = mrp;
        this.discount = discount;
        this.category = category;
        this.description = description;
        this.tags = tags;
        this.productId = productId;
        this.discountedPrice =mrp - mrp*((float)discount/100);
        this.tags.add(productName);
        this.tags.add(category);
        this.tags.add(mainCategory);
    }

}
