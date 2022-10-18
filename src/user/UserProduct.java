package user;

public class UserProduct {
    public String productName;
    public int productId;
    public int quantity;
    public float mrp;
    public float discountedPrice;
    public int discount;
    public UserProduct(int productId, String productName, int quantity, float mrp, float discountedPrice, int discount){
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.mrp = mrp;
        this.discountedPrice = discountedPrice;
        this.discount =discount;
    }
}
