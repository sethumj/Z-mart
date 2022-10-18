package superMarket;

import java.util.ArrayList;

public class Product {
    public String productName;
    public int quantity;
    public float mrp;
    public int discount;
    public String category;
    public String description;
    public ArrayList<String> tags;
    public int productId;
    public float discountedPrice;
    public Product(String productName, int quantity, float mrp, int discount, String category, ArrayList<String> tags,String description,int productId,float discountedPrice){
        this.productName = productName;
        this.quantity = quantity;
        this.mrp = mrp;
        this.discount = discount;
        this.category = category;
        this.description = description;
        this.tags = tags;
        this.productId = productId;
        this.discountedPrice =discountedPrice;
    }
}
