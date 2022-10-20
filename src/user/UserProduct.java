package user;

public class UserProduct {
    private final String productName;
    private final int productId;
    private int quantity;
    private float mrp;
    private final float discountedPrice;
    private int discount;
    private String name;
    private long mobileNo;
    private final int orderId;
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

    public String getProductName() {
        return productName;
    }
    public int getProductId() {
        return productId;
    }
    public int getQuantity() {
        return quantity;
    }
    public String getName(){
        return name;
    }
    public Long getMobileNo(){
        return mobileNo;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public float getDiscountedPrice() {
        return discountedPrice;
    }
    public int getOrderId() {
        return orderId;
    }
}
