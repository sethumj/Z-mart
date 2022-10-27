package user;

import admin.*;
import admin.Help;
import utilities.Input;
import utilities.PrintStatements;
import utilities.Validation;

import java.util.ArrayList;
import java.util.Set;

public class UserDriver implements Driver {
    static private final UserDriver userObject = new UserDriver();
    static private final ProductsDatabase productsDatabase = ProductsDatabase.getInstance();
    static private final Order order = Order.getInstance();
    static private final Help help = Help.getInstance();
    static private final UserDb userDb = UserDb.getInstance();
    static private final Announcement announcement = Announcement.getInstance();
    static private final PincodesDb pincodesDb = PincodesDb.getInstance();
    private UserDriver(){
    }
    public static UserDriver getInstance(){
        return userObject;
    }
    @Override
    public void startDriver() {
        System.out.println(PrintStatements.SIGN_MENU);
        System.out.println(PrintStatements.SELECT_FROM_OPTION);
            switch(Input.getInteger()){
                case 1:
                    System.out.println(PrintStatements.ENTER_PINCODE);
                    int pincode = Input.getInteger();
                    if(Validation.checkPincode(pincode)) {
                        if (pincodesDb.checkPincode(pincode)) {
                            User user = User.signUp();
                            if (user == null) return;
                            System.out.println(PrintStatements.USER_SIGN_UP_SUCCESSFUL);
                            UserDriver.goToMenu(user);
                        } else {
                            System.out.println(PrintStatements.NOT_SERVICEABLE);
                        }
                    }
                    else{
                        System.out.println(PrintStatements.INVALID_PINCODE);
                    }
                    break;
                case 2:
                    User user = User.signIn();
                    if(user == null){
                        System.out.println(PrintStatements.NO_USER_FOUND);
                        return;
                    }
                    System.out.println(PrintStatements.USER_SIGN_IN_SUCCESSFUL + user.getName(user));
                    UserDriver.goToMenu(user);
                    break;
                case 3:
                    System.out.println(PrintStatements.THANK_YOU);
                    return;
                default:
                    System.out.println(PrintStatements.INPUT_ERROR);
            }
    }
    private static void goToMenu(User user){
        System.out.println();
        System.out.println(PrintStatements.GREETING);
        System.out.println();
        while(true){
            System.out.println(PrintStatements.USER_OPTIONS);
            System.out.println(PrintStatements.SELECT_FROM_OPTION);
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
                    UserDriver.selectDiscount(user);
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
                    UserDriver.viewHistory(user);
                    break;
                case 7:
                    announcement.viewAnnouncements();
                    System.out.println();
                    break;
                case 8:
                    UserDriver.helpSection();
                    break;
                case 9:
                    System.out.println(PrintStatements.ABOUT_US);
                    break;
                case 10:
                    return;
                default :
                    System.out.println(PrintStatements.INPUT_ERROR);
                    break;
            }
        }
    }
    private static void searchForProduct(User user){
        System.out.println(PrintStatements.SEARCH_PRODUCTS);
        String productName = Input.getString().toLowerCase();
        UserDriver.search(productName,user);
    }
    private static boolean addToCart(int productId,User user){
        int quantity;
        while(true){
            System.out.println(PrintStatements.ENTER_QUANTITY);
            quantity = Validation.getPositiveInt();
            if(quantity<=productsDatabase.checkQuantity(productId)){
                Product product = productsDatabase.getProduct(productId);
                if(user.checkPresentInCart(productId,user)){
                    return user.updateCart(productId, user, quantity);
                }
                else if(user.addToCart(new CartProduct(product.getProductId(),quantity),user)){
                    return true;
                }
            }
            else{
                System.out.println(PrintStatements.QUANTITY_NOT_AVAILABLE);
                return false;
            }
        }
    }
    private static void searchByCategory(User user){
        String categoryName = AdminDriver.printByCategory();
        if(categoryName.equals("")) return;
        search(categoryName.toLowerCase(),user);
    }
    private static void search(String productName,User user){
        ArrayList<Integer> tempList = productsDatabase.searchForProduct(productName);
        if(tempList.size() == 0){
            System.out.println(PrintStatements.NO_SUCH_THING);
            return;
        }
        int option;
        while(true) {
            System.out.println(PrintStatements.SELECT_FROM_OPTION);
            option = Input.getInteger();
            if (option-1 >=tempList.size() || option-1 <0);
            else{break;}
        }
        productsDatabase.displayProductDetails(tempList.get(option-1));
        here:while(true) {
            System.out.println(PrintStatements.ADD_TO_CART);
            switch(Input.getInteger()){
                case 1:
                    if(UserDriver.addToCart(tempList.get(option-1),user)) System.out.println(PrintStatements.ADDED_SUCCESSFULLY);
                    else System.out.println(PrintStatements.ADDING_FAILED);
                    break here;
                case 2:
                    break here;
                default:
                    System.out.println(PrintStatements.INPUT_ERROR);
                    break;
            }
        }
    }
    private static void goToCart(User user){
        System.out.println(PrintStatements.VIEW_CART);
        while(true) {
            user.viewCart(user);
            if(user.getCartSize(user)==0)return;
            System.out.println(PrintStatements.CART_OPTIONS);
            switch(Input.getInteger()){
                case 1:
                    user.viewCart(user);
                    while(true) {
                        System.out.println(PrintStatements.SELECT_FROM_OPTION);
                        int option = Input.getInteger();
                        if(option-1>=user.getCartSize(user)){
                            System.out.println(PrintStatements.INPUT_ERROR);
                            System.out.println(PrintStatements.JUST_TRY_AGAIN);
                        }
                        else {
                            UserDriver.modifyCart(option, user);
                            break;
                        }
                    }
                    break;
                case 2:
                    if(user.getCartSize(user)<=0){
                        System.out.println(PrintStatements.NO_ITEM_IN_CART);
                        return;
                    }
                    System.out.println(PrintStatements.JUST_PAY + user.getOrderTotal(user));
                    System.out.println(PrintStatements.PRESS_ANY);
                    Input.getString();
                    int orderId = order.getOrderId();
                    userDb.addOrder(orderId,user.getUserId());
                    order.addOrders(orderId,user.placeOrder(user));
                    System.out.println(PrintStatements.ORDER_PLACED);
                    user.clearCart(user);
                    return;
                case 3:
                    return;
                default:
                    System.out.println(PrintStatements.INPUT_ERROR);
            }
        }
    }
    private static void modifyCart(int index,User user){
        System.out.println(PrintStatements.EDIT_PRODUCTS);
        switch(Input.getInteger()){
            case 1:
                    System.out.println(PrintStatements.ENTER_QUANTITY);
                    int quantity = Validation.getPositiveInt();
                    if(quantity == 0) return;
                    if(user.checkQuantity(index-1,user,quantity)){
                        if(user.changeQuantity(index-1,user,quantity)){
                            System.out.println(PrintStatements.UPDATE_SUCCESSFUL);
                        }
                    }
                    else{
                        System.out.println(PrintStatements.QUANTITY_NOT_AVAILABLE);
                    }
                break;
            case 2:
                System.out.println(PrintStatements.ENTER_QUANTITY);
                quantity = Validation.getPositiveInt();
                if(user.checkNegativeQuantity(index-1,user,quantity)){
                    if(user.changeQuantity(index-1,user,quantity-(quantity*2))){
                        System.out.println(PrintStatements.UPDATE_SUCCESSFUL);
                    }
                }
                else{
                    System.out.println(PrintStatements.QUANTITY_NOT_AVAILABLE);
                }
                break;
            case 3:
                if(user.removeProduct(index-1,user)){
                    System.out.println(PrintStatements.UPDATE_SUCCESSFUL);
                }
                else{
                    System.out.println(PrintStatements.UPDATE_FAILED);
                }
                break;
            case 4:
                return;
            default:
                System.out.println(PrintStatements.INPUT_ERROR);
                break;
        }
    }
   private static void viewOrders(User user){
        if(user.userOrders.size() == 0){
            System.out.println(PrintStatements.NOTHING_TO_SHOW);
            return;
        }
        System.out.println(PrintStatements.PLACED_ORDER_MENU);
        user.viewHistory(user);
        System.out.println(PrintStatements.CANCEL_ORDER_MENU);
        switch(Input.getInteger()){
            case 1:
                System.out.println(PrintStatements.ENTER_ORDER_ID);
               if(user.cancelOrder(user,Input.getInteger())){
                    System.out.println(PrintStatements.CANCELLED);
                    return;
                }
                else{
                    break;
                }
            case 2:
                return;
            default:
                System.out.println(PrintStatements.INPUT_ERROR);
        }
    }
    private static void selectDiscount(User user){
        System.out.println(PrintStatements.OFFER_MENU);
        int option = Input.getInteger();
        switch(option){
            case 1:
                UserDriver.searchByDiscount(0,25,user);
                break;
            case 2:
                UserDriver.searchByDiscount(25,50,user);
                break;
            case 3:
                UserDriver.searchByDiscount(50,100,user);
                break;
            case 4:
                return;
            default :
                System.out.println(PrintStatements.INPUT_ERROR);
                break;
        }
    }
    private static void searchByDiscount(int discountFrom,int discountTo,User user){
        ArrayList<Integer> tempList = productsDatabase.searchForProduct(discountFrom,discountTo);
        if(tempList.size() == 0){
            System.out.println(PrintStatements.NO_SUCH_THING);
            return;
        }
        int option;
        while(true) {
            System.out.println(PrintStatements.SELECT_FROM_OPTION);
            option = Input.getInteger();
            if (option-1 >=tempList.size() || option-1 <0);
            else{break;}
        }
        productsDatabase.displayProductDetails(tempList.get(option-1));
        here:while(true) {
            System.out.println(PrintStatements.ADD_TO_CART);
            switch(Input.getInteger()){
                case 1:
                    if(UserDriver.addToCart(tempList.get(option-1),user)) System.out.println(PrintStatements.ADDED_SUCCESSFULLY);
                    else System.out.println(PrintStatements.ADDING_FAILED);
                    break here;
                case 2:
                    break here;
                default:
                    System.out.println(PrintStatements.INPUT_ERROR);
            }
        }
    }
    private static void viewHistory(User user){
        if(user.history.size() == 0) {
            System.out.println(PrintStatements.NOTHING_TO_SHOW);
            return;
        }
        printHistory(user);
        System.out.println(PrintStatements.ENTER_ORDER_ID);
        int orderId = Input.getInteger();
        if (user.history.containsKey(orderId)) {
            printSpecificUserOrder(orderId,user);
        } else {
            System.out.println(PrintStatements.ORDER_ID_NOT_PRESENT);
        }
    }
    private static void printHistory(User user){
        System.out.println(PrintStatements.ORDER_HEADER);
        int count =1;
        Set<Integer> entrySet = user.history.keySet();
        for(Integer i : entrySet){
            System.out.println(count++ +" "+i);
        }
    }
    private static void printSpecificUserOrder(int orderId,User user){
        System.out.println(PrintStatements.CHECK_STOCK_PRODUCT);
        for(int i=0;i<user.history.get(orderId).size();i++){
            System.out.println(user.history.get(orderId).get(i).getProductId()+"      "+productsDatabase.getProductName(user.history.get(orderId).get(i).getProductId())+"      "+user.history.get(orderId).get(i).getQuantity());
        }
    }
    private static void helpSection(){
        System.out.println(PrintStatements.USER_HELP_MENU);
        int option = Validation.getPositiveInt();
        switch(option){
            case 1:
                System.out.println(PrintStatements.ENTER_TEXT);
                help.addUserQuestions(Validation.getNonNullString());
                System.out.println(PrintStatements.UPDATE_SUCCESSFUL);
                break;
            case 2:
                help.viewHelps();
                break;
            case 3:
                return;
            default:
                System.out.println(PrintStatements.INPUT_ERROR);
        }
    }
}
