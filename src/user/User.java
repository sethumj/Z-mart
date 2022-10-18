package user;

import superMarket.ProductsDatabase;
import utilities.Input;
import utilities.PrintStatements;
import utilities.Validation;

import java.util.ArrayList;
import java.util.Objects;

public class User {
    private String name;
    private long mobileNo;
    private Address address;
    private String password;
    private boolean redeemed;
    private String coupon = "WELCOME100";
    private float total;
    private  ArrayList<UserProduct> cart = new ArrayList<UserProduct>();
    User(String name,long mobileNo,Address address,String password){
        this.name = name;
        this.mobileNo = mobileNo;
        this.address = address;
        this.password = password;
        this.redeemed = false;
        this.total = 0;
    }
    public static User signUp(){
        System.out.println(PrintStatements.getName);
        String name = Input.getString();
        long mobileNo;
        while(true){
            System.out.println(PrintStatements.getMobileNumber);
            mobileNo = Input.getLong();
            if(Validation.checkMobileNo(mobileNo)){
                if(User.checkDb(mobileNo)){
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
        return UserDb.addUser(new User(name,mobileNo,address,password));

    }
    public static User signIn(){
        while(true){
            System.out.println(PrintStatements.getMobileNumber);
            long mobileNo = Input.getLong();
            System.out.println(PrintStatements.enterPassword);
            String password = Input.getString();
            for(int i =0;i<UserDb.users.size();i++){
                if(UserDb.users.get(i).mobileNo == mobileNo && UserDb.users.get(i).password.equals(password)){
                    return UserDb.users.get(i);
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
        for(int i =0;i<UserDb.users.size();i++){
            if(UserDb.users.get(i).mobileNo == mobileNo) return true;
        }
        return false;
    }
    public boolean addToCart(UserProduct product,User user) {
        user.cart.add(product);
        return true;
    }
    public void viewCart(){
        for (UserProduct userProduct : cart) {
            System.out.print(userProduct.productName + "         ");
            System.out.print(userProduct.quantity + "         " );
            System.out.print(userProduct.discountedPrice*userProduct.quantity);
            System.out.println();
        }
        System.out.println(PrintStatements.totalAmount+total);
    }
    public String getName(){
        return this.name;
    }
    public boolean checkPresentInCart(int productId,User user){
        for(int i=0;i<user.cart.size();i++){
            if(user.cart.get(i).productId == productId){
                return true;
            }
        }
        return false;
    }
    public boolean updateCart(int productId,User user,int quantity){
        for(int i=0;i<user.cart.size();i++){
            if(user.cart.get(i).productId == productId){
                if(user.cart.get(i).quantity+quantity> Objects.requireNonNull(ProductsDatabase.getProduct(productId)).quantity){
                    return false;
                }
                user.cart.get(i).quantity+=quantity;
                user.updateTotal(user);
                return true;
            }
        }
        return false;
    }
    public boolean changeQuantity(int index,User user,int quantity){
        user.cart.get(index).quantity+=quantity;
        user.updateTotal();
        return true;
    }
    public boolean checkQuantity(int index,User user,int quantity){
        if((user.cart.get(index).quantity+=quantity)>=0 && user.cart.get(index).quantity+quantity<=Objects.requireNonNull(ProductsDatabase.getProduct(user.cart.get(index).productId)).quantity){
            return true;
        }
        return false;
    }
    public boolean removeProduct(int index,User user){
        user.cart.remove(index);
        user.updateTotal(user);
        return true;
    }
    public boolean isRedeemed(User user){
        if(redeemed) return false;
        else{
            redeemed = true;
            if(user.total/2 <= 100) user.total-=total/2;
            else user.total -= 100;
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
    public void updateTotal(User user){
        for(int i=0;i<user.cart.size();i++){
            total+=user.cart.get(i).discountedPrice;
        }
    }
    public float getTotal(User user){
        return user.total;
    }
    public ArrayList<UserProduct> placeOrder(User user){
        return user.cart;
    }


}
