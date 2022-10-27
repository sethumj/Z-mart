package user;


public class CartProduct {
    private final int productId;
    private int quantity;
    public CartProduct(int productId, int quantity){
        this.productId = productId;
        this.quantity = quantity;
    }
    public int getProductId() {
        return productId;
    }
    public int getQuantity() {
        return quantity;
    }
    void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
