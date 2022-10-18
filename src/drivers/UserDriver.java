package drivers;

import superMarket.*;
import user.User;
import user.UserDb;
import user.UserProduct;
import utilities.Help;
import utilities.Input;
import utilities.PrintStatements;

import java.io.PipedReader;
import java.util.ArrayList;

public class UserDriver implements Driver {
    static private UserDriver userObject = new UserDriver();
    private UserDriver(){
    }
    static UserDriver getInstance(){
        return userObject;
    }

    @Override
    public void startDriver() {
        System.out.println(PrintStatements.enterPincode);
        if(PincodesDb.checkPincode(Input.getInteger())){
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
                    System.out.println(PrintStatements.userSignInSuccessful + user.getName());
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
        while(true){
            System.out.println(PrintStatements.userOptions);
            switch(Input.getInteger()){
                case 1:
                    UserDriver.searchForProduct(user);
                    break;
                case 2:
                    UserDriver.searchByCategory(user);
                    break;
                case 3:
                    break;
                case 4:
                    UserDriver.goToCart(user);
                    break;
                case 5:
                    break;
                case 6:
                    Announcement.viewAnnouncements();
                    break;
                case 7:
                    Help.viewHelps();
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
            if(quantity<=ProductsDatabase.checkQuantity(productId)){
                Product product = ProductsDatabase.getProduct(productId);
                if(user.checkPresentInCart(productId,user)){
                    return user.updateCart(productId, user, quantity);

                }
                else if(user.addToCart(new UserProduct(product.productId,product.productName,quantity,product.mrp,product.discountedPrice,product.discount),user)){
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
            user.viewCart();
            System.out.println(PrintStatements.cartOptions);
            switch(Input.getInteger()){
                case 1:
                    user.viewCart();
                    System.out.println(PrintStatements.selectFromOption);
                    UserDriver.modifyCart(Input.getInteger(),user);
                    break;
                case 2:
                    here:while(true) {
                        System.out.println(PrintStatements.enterCoupon);
                        if (user.redeem(Input.getString(), user)) {
                            System.out.println(PrintStatements.afterCoupon+user.getTotal(user));
                            System.out.println(PrintStatements.pressAny);
                            Input.getString();
                            Order.orders.add(user.placeOrder(user));
                        }
                        else{
                            while(true) {
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
                    break;
                case 3:
                    return;
                default:
                    System.out.println(PrintStatements.inputError);
                    break;
            }

        }

    }
    private static void searchByCategory(User user){
        String categoryName = CategoryDatabase.printByCategory();
        search(categoryName,user);
    }
    private static void search(String product,User user){
        String productName = product;
        ArrayList<Integer> tempList = ProductsDatabase.searchForProduct(productName);
        if(tempList.size() == 0){
            System.out.println(PrintStatements.noSuchThing);
            return;
        }
        System.out.println(PrintStatements.selectFromOption);
        int option = Input.getInteger();
        ProductsDatabase.displayProductDetails(tempList.get(option-1));
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
                if(user.checkQuantity(index-1,user,(quantity*2)-quantity)){
                    if(user.changeQuantity(index-1,user,(quantity*2)-quantity)){
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
}
