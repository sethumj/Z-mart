package admin;

import java.util.ArrayList;

 public class Product {
    private String productName;
    private int quantity;
    private float mrp;
    private int discount;
    private String subCategoryName;
    private String description;
    private ArrayList<String> tags;
    private final int productId;
    private float discountedPrice;

    public Product(String productName, int quantity, float mrp, int discount, String categoryName, String subCategoryName, ArrayList<String> tags, String description, int productId){
        this.productName = productName;
        this.quantity = quantity;
        this.mrp = mrp;
        this.discount = discount;
        this.subCategoryName = subCategoryName;
        this.description = description;
        this.tags = tags;
        this.productId = productId;
        this.discountedPrice =mrp - mrp*((float)discount/100);
        this.tags.add(productName);
        this.tags.add(subCategoryName);
        this.tags.add(categoryName);
        this.tags.add(categoryName.toLowerCase());
        this.tags.add(subCategoryName.toLowerCase());
        this.tags.add(productName.toLowerCase());
    }

     public String getProductName() {
         return productName;
     }
     void setProductName(String productName) {
         this.productName = productName;
     }
     public int getQuantity() {
         return quantity;
     }
     void setQuantity(int quantity) {
         this.quantity = quantity;
     }
     public float getMrp() {
         return mrp;
     }
     void setMrp(float mrp) {
         this.mrp = mrp;
     }
     public int getDiscount() {
         return discount;
     }
     void setDiscount(int discount) {
         this.discount = discount;
         this.discountedPrice = this.mrp-this.mrp*((float)this.discount/100);
     }
     public String getSubCategoryName() {
         return subCategoryName;
     }
     void setSubCategoryName(String subCategoryName) {
         this.subCategoryName = subCategoryName;
     }
     public String getDescription() {
         return description;
     }
     void setDescription(String description) {
         this.description = description;
     }
     public ArrayList<String> getTags() {
         return tags;
     }

     public int getProductId() {
         return productId;
     }
     public float getDiscountedPrice() {
         return discountedPrice;
     }
}
