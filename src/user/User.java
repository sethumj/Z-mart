package user;

import superMarket.Order;
import superMarket.ProductsDatabase;
import utilities.Input;
import utilities.PrintStatements;
import utilities.Validation;

import java.util.ArrayList;
import java.util.Objects;

public class User {
    private static ProductsDatabase productsDatabase = ProductsDatabase.getInstance();
    private static UserDb userDb = UserDb.getInstance();
    private static Order order = Order.getInstance();
    private String name;
    private long mobileNo;
    private Address address;
    private boolean redeemed;
    private String coupon = "WELCOME100";
    private float total;
    public static int orderId = 40000;
    private static UserAuthentication userAuthentication = UserAuthentication.getInstance();
    private  ArrayList<UserProduct> cart = new ArrayList<UserProduct>();
    private ArrayList<ArrayList<UserProduct>> history = new ArrayList<>();
    User(String name,long mobileNo,Address address){
        this.name = name;
        this.mobileNo = mobileNo;
        this.address = address;
        this.redeemed = false;
        this.total = 0;
    }
    public String getName(){
        return name;
    }
    public static User signUp(){
        System.out.println(PrintStatements.getName);
        String name = Input.getString();
        long mobileNo;
        while(true){
            System.out.println(PrintStatements.getMobileNumber);
            mobileNo = Input.getLong();
            if(Validation.checkMobileNo(mobileNo)){
                if(userAuthentication.checkAlreadyExist(mobileNo)){
                    System.out.println(PrintStatements.accountAlreadyExist);
                    switch(Input.getInteger()){
                        case 1:
                              return null;
                        case 2:
                            continue;
                        default:
                            System.out.println(PrintStatements.inputError);
                            break;
                    }
                }
                else{
                    break;
                }
            }
            else{
                System.out.println(PrintStatements.invalidMobileNo);
            }
        }
        Address address = Address.getAddress();
        System.out.println(PrintStatements.setPassword);
        String password = Input.getString();
        System.out.println(PrintStatements.confirmPassword);
        String confirmPassword;
        while(true) {
            confirmPassword = Input.getString();
            if(confirmPassword.equals(password))break;
            else{
                System.out.println(PrintStatements.passwordMismatch);
            }
        }
        userAuthentication.addUser(mobileNo,password);
        return UserDb.addUser(new User(name,mobileNo,address));

    }
    public static User signIn(){
        while(true){
            System.out.println(PrintStatements.getMobileNumber);
            long mobileNo = Input.getLong();
            System.out.println(PrintStatements.enterPassword);
            String password = Input.getString();
            if(!userAuthentication.userCheck(mobileNo,password)) return null;
            for(int i =0;i<userDb.users.size();i++){
                if(userDb.users.get(i).mobileNo == mobileNo){
                    return userDb.users.get(i);
                }
            }
            System.out.println(PrintStatements.signInError);
            System.out.println(PrintStatements.tryAgain);
            switch (Input.getInteger()) {
                case 1:
                    break;
                case 2:
                    return null;
                default:
                    System.out.println(PrintStatements.inputError);
                    return null;
            }
        }
    }
    private static boolean checkDb(long mobileNo){
        for(int i =0;i<userDb.users.size();i++){
            if(userDb.users.get(i).mobileNo == mobileNo) return true;
        }
        return false;
    }
    public boolean addToCart(UserProduct product,User user) {
            user.cart.add(product);
            return true;
    }
    public void viewCart(User user){
        float total = 0;
        int i=1;
        for (UserProduct userProduct : cart) {
            System.out.print(i+"."+ userProduct.getProductName() + "         ");
            System.out.print(userProduct.getQuantity() + "         " );
            System.out.print(userProduct.getDiscountedPrice() * userProduct.getQuantity());
            System.out.println();
            total+= userProduct.getDiscountedPrice() * userProduct.getQuantity();
            i++;
        }
        System.out.println(PrintStatements.totalAmount+total);
        user.total = total;
    }
    public String getName(User user){
        return user.name;
    }
    public boolean checkPresentInCart(int productId,User user){
        for(int i=0;i<user.cart.size();i++){
            if(user.cart.get(i).getProductId() == productId){
                return true;
            }
        }
        return false;
    }
    public boolean updateCart(int productId,User user,int quantity){
        for(int i=0;i<user.cart.size();i++){
            if(user.cart.get(i).getProductId() == productId){
                if(user.cart.get(i).getQuantity() +quantity> Objects.requireNonNull(productsDatabase.getProduct(productId)).getQuantity()){
                    return false;
                }
                user.cart.get(i).setQuantity(user.cart.get(i).getQuantity()+quantity);
                return true;
            }
        }
        return false;
    }
    public boolean changeQuantity(int index,User user,int quantity){
        user.cart.get(index).setQuantity(user.cart.get(index).getQuantity()+quantity);

        return true;
    }
    public boolean checkQuantity(int index,User user,int quantity){
        if((user.cart.get(index).getQuantity() +quantity)>=0 && user.cart.get(index).getQuantity() +quantity<=Objects.requireNonNull(productsDatabase.getProduct(user.cart.get(index).getProductId())).getQuantity()){
            return true;
        }
        return false;
    }
    public boolean checkNegativeQuantity(int index,User user,int quantity){
        if((user.cart.get(index).getQuantity() -quantity)>=0 && user.cart.get(index).getQuantity() -quantity<=Objects.requireNonNull(productsDatabase.getProduct(user.cart.get(index).getProductId())).getQuantity()){
            return true;
        }
        return false;
    }
    public boolean removeProduct(int index,User user){
        user.cart.remove(index);

        return true;
    }
    public boolean isRedeemed(User user){
        if(redeemed) return false;
        else{
            redeemed = true;
            return true;
        }
    }
    public boolean redeem(String coupon,User user){
        if(coupon.equals(this.coupon)){
            return isRedeemed(user);
        }
        else{
            return false;
        }
    }
    public long getMobileNo(User user){
        return user.mobileNo;
    }
    public float getOrderTotal(User user){

        return user.total;
    }
    public float getCouponTotal(User user){

        if(user.total/2 < 100) return user.total-(user.total/2);
        else return user.total - 100;
    }
    public ArrayList<UserProduct> placeOrder(User user){
        history(new ArrayList<>(user.cart),user);
        User.orderId++;
        return new ArrayList<>(user.cart);
    }
    public void history(ArrayList<UserProduct> cart ,User user){
        user.history.add(cart);
    }
    public void clearCart(User user){
        history(user.cart,user);
        user.cart.clear();
    }
    public void viewHistory(User user){
        int count=1;
        for(int i=0;i<user.history.size();i++){
            for(int j=0;j<user.history.get(i).size();) {
                System.out.println(count+ "." + user.history.get(i).get(j).getOrderId() + "     " + getTotal(i,user));
                count++;
                break;
            }
        }
    }
    public float getTotal(int index,User user){
        float total=0;
        for(int i=0;i<user.history.get(index).size();i++){
            total+= user.history.get(index).get(i).getDiscountedPrice() * user.history.get(index).get(i).getQuantity();
        }
        return total;
    }
    public int getCartSize(User user){
        return user.cart.size();
    }
    public boolean cancelOrder(User user,int orderId){
        for(int i=0;i<user.history.size();i++) {
            for (int j = 0; j < user.history.get(i).size(); ) {
                if(user.history.get(i).get(j).getOrderId() == orderId){
                    if(user.cancelFromDb(user,orderId)) {
                        order.increaseProductDatabase(user.history.get(i));
                        user.history.remove(i);
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                break;
            }
        }
        return false;
    }
    public boolean cancelFromDb(User user,int orderId){
        for(int i=0;i<order.getOrders().size();i++){
            for(int j=0;j<order.getOrders().get(i).size();j++) {
                if(order.getOrders().get(i).get(j).getOrderId() == orderId){
                    order.getOrders().remove(i);
                    return true;
                }
            }
        }
        return false;
    }
}
