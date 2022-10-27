package admin;

import user.CartProduct;
import user.User;
import user.UserDb;
import utilities.Input;
import utilities.PrintStatements;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

public class Order {
    private Order(){}
    static Order order = new Order();
    public static Order getInstance(){
        return order;
    }
    private static final ProductsDatabase productsDatabase = ProductsDatabase.getInstance();
    private static final UserDb userDb = UserDb.getInstance();
    private final LinkedHashMap<Integer,ArrayList<CartProduct>> orders = new LinkedHashMap<>();
    private final LinkedHashMap<Integer,ArrayList<CartProduct>> readyToDeliver = new LinkedHashMap<>();
    private final LinkedHashMap<Integer,ArrayList<CartProduct>> history = new LinkedHashMap<>();
    private static int orderId = 40000;

    LinkedHashMap<Integer,ArrayList<CartProduct> >getOrders() {
        return order.orders;
    }
    LinkedHashMap<Integer,ArrayList<CartProduct>>getReadyToDeliver(){
        return order.readyToDeliver;
    }
    LinkedHashMap<Integer,ArrayList<CartProduct>>getHistory(){
        return order.history;
    }
    public int getOrderId(){
        return orderId;
    }
    public static void generateOrderId(){
        orderId++;
    }
    void printOrders(){
        System.out.println(PrintStatements.ORDER_HEADER);
       Set<Integer> entrySet = orders.keySet();
       for(Integer i : entrySet){
           System.out.println(i);
       }
    }
    void decreaseProductDatabase(int orderId){
        for(int i = 0;i<order.orders.get(orderId).size();i++){
            order.decreaseProductQuantity(order.orders.get(orderId).get(i).getProductId(),order.orders.get(orderId).get(i).getQuantity());
        }
    }
    void decreaseProductQuantity(int productId,int quantity){
        for(int i = 0;i<productsDatabase.products.size();i++){
            if(productsDatabase.products.get(i).getProductId() == productId){
                productsDatabase.products.get(i).setQuantity(productsDatabase.products.get(i).getQuantity()-quantity);
            }
        }
    }
     void increaseProductDatabase(ArrayList<CartProduct> orders){
        for (CartProduct userProduct : orders) {
            order.increaseProductQuantity(userProduct.getProductId(), userProduct.getQuantity());
        }
    }
    void increaseProductQuantity(int productId,int quantity){
        for(int i = 0;i<productsDatabase.products.size();i++){
            if(productsDatabase.products.get(i).getProductId() == productId){
                productsDatabase.products.get(i).setQuantity(productsDatabase.products.get(i).getQuantity()+quantity);
            }
        }
    }

    void printSpecificUserOrder(int orderId){
        System.out.println(PrintStatements.CHECK_STOCK_PRODUCT);
            for(int i=0;i<order.orders.get(orderId).size();i++){
                System.out.printf("%-13s %-50s %-7s",order.orders.get(orderId).get(i).getProductId(),productsDatabase.getProductName(order.orders.get(orderId).get(i).getProductId()),order.orders.get(orderId).get(i).getQuantity());
                System.out.println();
            }
            while(true) {
                System.out.println(PrintStatements.ORDER_READY);
                int option = Input.getInteger();
                if(option == 1){
                    order.readyToDeliver.put(orderId,order.orders.remove(orderId));
                    break;
                }
                else if(option == 2){
                    return;
                }
                else{
                    System.out.println(PrintStatements.INPUT_ERROR);
                }
            }
    }
    void readyOrders(){
        System.out.println(PrintStatements.ORDER_HEADER);
        Set<Integer> entrySet = readyToDeliver.keySet();
        for(Integer i : entrySet){
            System.out.println(i);
        }
    }
    void setReadyToDeliver(int orderId){
        System.out.println(PrintStatements.CHECK_STOCK_PRODUCT);
        for(int i=0;i<order.readyToDeliver.get(orderId).size();i++){
            System.out.printf("%-13s %-50s %-7s",order.readyToDeliver.get(orderId).get(i).getProductId(),productsDatabase.getProductName(order.readyToDeliver.get(orderId).get(i).getProductId()),order.readyToDeliver.get(orderId).get(i).getQuantity());
            System.out.println();
        }
        while(true) {
            System.out.println(PrintStatements.ORDER_READY);
            int option = Input.getInteger();
            if(option == 1){
                order.history.put(orderId,order.readyToDeliver.remove(orderId));
                removeFromUserOrder(orderId);
                break;
            }
            else if(option == 2){
                return;
            }
            else{
                System.out.println(PrintStatements.INPUT_ERROR);
            }
        }
    }
    void history(){
        System.out.println(PrintStatements.ORDER_HEADER);
        Set<Integer> entrySet = history.keySet();
        for(Integer i : entrySet){
            float total = 0;
            System.out.println(i);
            for(int j=0;j<order.history.get(i).size();j++){
                total += order.history.get(i).get(j).getQuantity()*productsDatabase.getDiscountedPrice(order.history.get(i).get(j).getProductId());
            }
            System.out.println(PrintStatements.REVENUE_GENERATED +total);
        }
    }

    void removeFromUserOrder(int key){
        userDb.orderReady(key);
    }
    public boolean cancelFromDb(int orderId){
        if(order.getOrders().containsKey(orderId)){
            order.increaseProductDatabase(order.getOrders().remove(orderId));
            return true;
        }
        else {
            return false;
        }
    }
    public void addOrders(int orderId,ArrayList<CartProduct> orders){
        order.getOrders().put(orderId,orders);
        order.decreaseProductDatabase(orderId);
    }
}
