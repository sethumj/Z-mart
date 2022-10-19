package user;

public class UserProduct {
    public String productName;
    public int productId;
    public int quantity;
    public float mrp;
    public float discountedPrice;
    public int discount;
    public String name;
    public long mobileNo;
    public int orderId;
    public UserProduct(int orderId ,int productId, String productName, int quantity, float mrp, float discountedPrice, int discount,String name,long mobileNo){
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.mrp = mrp;
        this.discountedPrice = discountedPrice;
        this.discount =discount;
        this.name = name;
        this.mobileNo= mobileNo;

    }
}
