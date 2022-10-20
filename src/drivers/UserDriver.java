package drivers;

import superMarket.*;
import user.User;
import user.UserProduct;
import utilities.Help;
import utilities.Input;
import utilities.PrintStatements;

import java.util.ArrayList;

public class UserDriver implements Driver {
    static private UserDriver userObject = new UserDriver();
    static private ProductsDatabase productsDatabase = ProductsDatabase.getInstance();
    static private Order order = Order.getInstance();
    static private Help help = Help.getInstance();
    static private Announcement announcement = Announcement.getInstance();
    static private PincodesDb pincodesDb = PincodesDb.getInstance();
    private UserDriver(){
    }
    static UserDriver getInstance(){
        return userObject;
    }

    @Override
    public void startDriver() {
        System.out.println(PrintStatements.enterPincode);
        if(pincodesDb.checkPincode(Input.getInteger())){
            System.out.println(PrintStatements.signMenu);
            switch(Input.getInteger()){
                case 1:
                    User user = User.signUp();
                    if(user == null) return;
                    System.out.println(PrintStatements.userSignUpSuccessful);
                    UserDriver.goToMenu(user);
                    break;
                case 2:
                    user = User.signIn();
                    if(user == null) return;
                    System.out.println(PrintStatements.userSignInSuccessful + user.getName(user));
                    UserDriver.goToMenu(user);
                    break;
                case 3:
                    System.out.println(PrintStatements.thankYou);
                    return;
                default:
                    System.out.println(PrintStatements.inputError);
                    break;
            }
        }
        else{
            System.out.println(PrintStatements.notServiceable);
        }
    }
    private static void goToMenu(User user){
        System.out.println();
        System.out.println(PrintStatements.greeting);
        System.out.println();
        while(true){
            System.out.println(PrintStatements.userOptions);
            switch(Input.getInteger()){
                case 1:
                    UserDriver.searchForProduct(user);
                    System.out.println();
                    break;
                case 2:
                    UserDriver.searchByCategory(user);
                    System.out.println();
                    break;
                case 3:
                    System.out.println(PrintStatements.underDevelopment);
                    break;
                case 4:
                    UserDriver.goToCart(user);
                    System.out.println();
                    break;
                case 5:
                    UserDriver.viewOrders(user);
                    System.out.println();
                    break;
                case 6:
                    announcement.viewAnnouncements();
                    System.out.println();
                    break;
                case 7:
                    help.viewHelps();
                    break;
                case 8:
                    System.out.println(PrintStatements.aboutUs);
                    break;
                case 9:
                    return;
                default :
                    System.out.println(PrintStatements.inputError);
                    break;
            }
        }
    }
    private static void searchForProduct(User user){
        System.out.println(PrintStatements.searchProducts);
        String productName = Input.getString();
        UserDriver.search(productName,user);
    }
    private static boolean addToCart(int productId,User user){
        int quantity;
        while(true){
            System.out.println(PrintStatements.enterQuantity);
            quantity = Input.getInteger();
            if(quantity<=productsDatabase.checkQuantity(productId)){
                Product product = productsDatabase.getProduct(productId);
                if(user.checkPresentInCart(productId,user)){
                    return user.updateCart(productId, user, quantity);

                }
                else if(user.addToCart(new UserProduct(User.orderId,product.getProductId(),product.getProductName(),quantity,product.getMrp(),product.getDiscountedPrice(),product.getDiscount(),user.getName(user),user.getMobileNo(user)),user)){
                    return true;
                }
            }
            else{
                System.out.println(PrintStatements.quantityNotAvailable);
                return false;
            }
        }
    }
    private static void goToCart(User user){
        System.out.println(PrintStatements.viewCart);
        while(true) {
            user.viewCart(user);
            if(user.getCartSize(user)==0)return;
            System.out.println(PrintStatements.cartOptions);
            switch(Input.getInteger()){
                case 1:
                    user.viewCart(user);
                    while(true) {
                        System.out.println(PrintStatements.selectFromOption);
                        int option = Input.getInteger();
                        if(option-1>user.getCartSize(user)){
                            System.out.println(PrintStatements.inputError);
                            System.out.println(PrintStatements.justTryAgain);
                        }
                        else {
                            UserDriver.modifyCart(option, user);
                            break;
                        }
                    }
                    break;
                case 2:
                    if(user.getCartSize(user)<=0){
                        System.out.println(PrintStatements.noItemInCart);
                        return;
                    }
                    /*System.out.println(PrintStatements.doYouHaveACoupon);
                    int choice =  Input.getInteger();
                    if(choice == 1) {
                        here:
                        while (true) {
                            System.out.println(PrintStatements.enterCoupon);
                            if (user.redeem(Input.getString(), user)) {
                                System.out.println(PrintStatements.afterCoupon + user.getCouponTotal(user));
                                System.out.println(PrintStatements.pressAny);
                                Input.getString();
                                order.getOrders().add(user.placeOrder(user));
                                user.clearCart(user);
                                System.out.println(PrintStatements.orderPlaced);
                                order.decreaseProductDatabase();
                                return;
                            } else {
                                while (true) {
                                    System.out.println(PrintStatements.tryAgain);
                                    int option = Input.getInteger();
                                    if (option == 1) continue here;
                                    else if (option == 2) break here;
                                    else {
                                        System.out.println(PrintStatements.inputError);
                                    }
                                }
                            }
                        }
                    }
                    else if(choice == 2) {*/
                        System.out.println(PrintStatements.afterCoupon + user.getOrderTotal(user));
                        System.out.println(PrintStatements.pressAny);
                        Input.getString();
                        order.getOrders().add(user.placeOrder(user));
                        System.out.println(PrintStatements.orderPlaced);
                        order.decreaseProductDatabase();
                        user.clearCart(user);
                        return;
                  /*  }
                    else{
                        System.out.println(PrintStatements.inputError);
                    }
                    break;*/
                case 3:
                    return;
                default:
                    System.out.println(PrintStatements.inputError);
                    break;
            }

        }

    }
    private static void searchByCategory(User user){
        String categoryName = AdminDriver.printByCategory();
        search(categoryName,user);
    }
    private static void search(String productName,User user){
        ArrayList<Integer> tempList = productsDatabase.searchForProduct(productName);
        if(tempList.size() == 0){
            System.out.println(PrintStatements.noSuchThing);
            return;
        }
        int option;
        here:while(true) {
            System.out.println(PrintStatements.selectFromOption);
            option = Input.getInteger();
            if (option-1 >tempList.size() && option-1 <0) continue here;
            else{break;}
        }
        productsDatabase.displayProductDetails(tempList.get(option-1));
        here:while(true) {
            System.out.println(PrintStatements.addToCartMenu);
            switch(Input.getInteger()){
                case 1:
                    if(UserDriver.addToCart(tempList.get(option-1),user)) System.out.println(PrintStatements.addedSuccessfully);
                    else System.out.println(PrintStatements.addingFailed);
                    break here;
                case 2:
                    break here;
                default:
                    System.out.println(PrintStatements.inputError);
                    break;
            }
        }
    }
    private static void modifyCart(int index,User user){
        System.out.println(PrintStatements.editProducts);
        switch(Input.getInteger()){
            case 1:
                    System.out.println(PrintStatements.enterQuantity);
                    int quantity = Input.getInteger();
                    if(user.checkQuantity(index-1,user,quantity)){
                        if(user.changeQuantity(index-1,user,quantity)){
                            System.out.println(PrintStatements.updateSuccessful);
                        }
                    }
                    else{
                        System.out.println(PrintStatements.quantityNotAvailable);
                    }
                break;
            case 2:
                System.out.println(PrintStatements.enterQuantity);
                quantity = Input.getInteger();
                if(quantity<0) quantity*=-1;
                if(user.checkNegativeQuantity(index-1,user,quantity)){
                    if(user.changeQuantity(index-1,user,quantity-(quantity*2))){
                        System.out.println(PrintStatements.updateSuccessful);
                    }
                }
                else{
                    System.out.println(PrintStatements.quantityNotAvailable);
                }
                break;
            case 3:
                if(user.removeProduct(index-1,user)){
                    System.out.println(PrintStatements.updateSuccessful);
                }
                else{
                    System.out.println(PrintStatements.updateFailed);
                }
                break;
            case 4:
                return;
            default:
                System.out.println(PrintStatements.inputError);
                break;
        }
    }
    private static void viewOrders(User user){
        System.out.println(PrintStatements.placedOrderHeader);
        user.viewHistory(user);
        System.out.println(PrintStatements.cancelOrderMenu);
        switch(Input.getInteger()){
            case 1:
                System.out.println(PrintStatements.enterOrderId);
                if(user.cancelOrder(user,Input.getInteger())){
                    System.out.println(PrintStatements.cancelled);
                    return;
                }
                else{
                    System.out.println(PrintStatements.noSuchThing);
                    break;
                }
            case 2:
                return;
            default:
                System.out.println(PrintStatements.inputError);
                break;
        }
    }
}
