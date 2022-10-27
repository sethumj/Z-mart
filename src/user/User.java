package user;

import admin.Order;
import admin.ProductsDatabase;
import utilities.Input;
import utilities.PrintStatements;
import utilities.Validation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Set;

public class User {
    private static final ProductsDatabase productsDatabase = ProductsDatabase.getInstance();
    private static final UserDb userDb = UserDb.getInstance();
    private static final Order order = Order.getInstance();
    private final String name;
    private final long mobileNo;
    private final Address address;
    private final int userId;
    private boolean redeemed;
    private float total;
    private static final UserAuthentication userAuthentication = UserAuthentication.getInstance();
    private final ArrayList<CartProduct> cart = new ArrayList<CartProduct>();
    final LinkedHashMap<Integer,ArrayList<CartProduct>> userOrders = new LinkedHashMap<>();
    final LinkedHashMap<Integer,ArrayList<CartProduct>> history = new LinkedHashMap<>();
    User(String name,long mobileNo,Address address){
        this.name = name;
        this.mobileNo = mobileNo;
        this.address = address;
        this.redeemed = false;
        this.total = 0;
        this.userId = userDb.getUserId();
    }
    public int getUserId(){return userId;};
    public static User signUp(){
        System.out.println(PrintStatements.GET_NAME);
        String name = Input.getString();
        long mobileNo;
        while(true){
            System.out.println(PrintStatements.GET_MOBILE_NUMBER);
            mobileNo = Validation.getMobileNo();
            if(Validation.checkMobileNo(mobileNo)){
                if(userAuthentication.checkAlreadyExist(mobileNo)){
                    System.out.println(PrintStatements.ACCOUNT_ALREADY_EXISTS);
                    switch(Input.getInteger()){
                        case 1:
                              return null;
                        case 2:
                            continue;
                        default:
                            System.out.println(PrintStatements.INPUT_ERROR);
                    }
                }
                else{
                    break;
                }
            }
            else{
                System.out.println(PrintStatements.INVALID_MOBILE_NO);
            }
        }
        Address address = Address.getAddress();
        System.out.println(PrintStatements.SET_PASSWORD);
        String password = Input.getString();
        System.out.println(PrintStatements.CONFIRM_PASSWORD);
        String confirmPassword;
        while(true) {
            confirmPassword = Input.getString();
            if(confirmPassword.equals(password))break;
            else{
                System.out.println(PrintStatements.PASSWORD_MISMATCH);
            }
        }
        userAuthentication.addUser(mobileNo,password);
        return userDb.addUser(new User(name,mobileNo,address));

    }
    static User signIn(){
        while(true){
            System.out.println(PrintStatements.GET_MOBILE_NUMBER);
            long mobileNo = Validation.getMobileNo();
            System.out.println(PrintStatements.ENTER_PASSWORD);
            String password = Input.getString();
            if(!userAuthentication.userCheck(mobileNo,password)) return null;
            for(int i =0;i<userDb.users.size();i++){
                if(userDb.users.get(i).mobileNo == mobileNo){
                    return userDb.users.get(i);
                }
            }
            System.out.println(PrintStatements.SIGN_IN_ERROR);
            System.out.println(PrintStatements.TRY_AGAIN);
            switch (Input.getInteger()) {
                case 1:
                    break;
                case 2:
                    return null;
                default:
                    System.out.println(PrintStatements.INPUT_ERROR);
                    return null;
            }
        }
    }
    boolean addToCart(CartProduct product, User user) {
            user.cart.add(product);
            return true;
    }
    void viewCart(User user){
        float total = 0;
        int i=1;
        System.out.println(PrintStatements.CART_DETAILS);
        for (CartProduct cartProduct : cart) {
            System.out.print(i+".");
            System.out.printf("%-50s %-8s %-20s",productsDatabase.getProductName(cartProduct.getProductId()),cartProduct.getQuantity(),productsDatabase.getDiscountedPrice(cartProduct.getProductId()) * cartProduct.getQuantity());
            System.out.println();
            total+= productsDatabase.getDiscountedPrice(cartProduct.getProductId()) * cartProduct.getQuantity();
            i++;
        }
        System.out.println();
        System.out.println(PrintStatements.TOTAL_AMOUNT +total);
        System.out.println();
        user.total = total;
    }
    public String getName(User user){
        return user.name;
    }
    boolean checkPresentInCart(int productId,User user){
        for(int i=0;i<user.cart.size();i++){
            if(user.cart.get(i).getProductId() == productId){
                return true;
            }
        }
        return false;
    }
    boolean updateCart(int productId,User user,int quantity){
        for(int i=0;i<user.cart.size();i++){
            if(user.cart.get(i).getProductId() == productId){
                if(user.cart.get(i).getQuantity() +quantity> Objects.requireNonNull(productsDatabase.getProduct(productId)).getQuantity()){
                    System.out.println(PrintStatements.QUANTITY_NOT_AVAILABLE);
                    return false;
                }
                user.cart.get(i).setQuantity(user.cart.get(i).getQuantity()+quantity);
                return true;
            }
        }
        return false;
    }
    boolean changeQuantity(int index,User user,int quantity){
        user.cart.get(index).setQuantity(user.cart.get(index).getQuantity()+quantity);
        if(user.cart.get(index).getQuantity() == 0) user.cart.remove(index);
        return true;
    }
    boolean checkQuantity(int index,User user,int quantity){
        return (user.cart.get(index).getQuantity() + quantity) >= 0 && user.cart.get(index).getQuantity() + quantity <= Objects.requireNonNull(productsDatabase.getProduct(user.cart.get(index).getProductId())).getQuantity();
    }
    boolean checkNegativeQuantity(int index,User user,int quantity){
        return (user.cart.get(index).getQuantity() - quantity) >= 0 && user.cart.get(index).getQuantity() - quantity <= Objects.requireNonNull(productsDatabase.getProduct(user.cart.get(index).getProductId())).getQuantity();

    }
    boolean removeProduct(int index,User user){
        user.cart.remove(index);
        return true;
    }
    float getOrderTotal(User user){
        return user.total;
    }
    ArrayList<CartProduct> placeOrder(User user){
        history(new ArrayList<>(user.cart),user,order.getOrderId());
        order.generateOrderId();
        return new ArrayList<>(user.cart);
    }
    void history(ArrayList<CartProduct> cart , User user, int orderId){
        user.userOrders.put(orderId,cart);
    }
    void clearCart(User user){
        user.cart.clear();
    }
    void viewHistory(User user){
        Set<Integer> entrySet = user.userOrders.keySet();
        for(Integer i : entrySet){
            System.out.print(i);
            float total  = 0;
            for(int j = 0; j<user.userOrders.get(i).size(); j++){
                total+=user.userOrders.get(i).get(j).getQuantity()*productsDatabase.getDiscountedPrice(user.userOrders.get(i).get(j).getProductId());
            }
            System.out.println("         "+total);
        }
    }
    int getCartSize(User user){
        return user.cart.size();
    }
    boolean cancelOrder(User user,int orderId) {
        if (user.userOrders.containsKey(orderId)) {
            if (order.cancelFromDb(orderId)) {
                user.userOrders.remove(orderId);
                return true;
            } else {
                System.out.println(PrintStatements.ORDER_IS_PACKED);
                return false;
            }
        } else {
            System.out.println(PrintStatements.NO_SUCH_THING);
            return false;
        }
    }
}
