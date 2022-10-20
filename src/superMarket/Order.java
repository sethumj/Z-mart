package superMarket;

import user.UserProduct;
import utilities.Input;
import utilities.PrintStatements;

import java.util.ArrayList;

public class Order {
    private Order(){}
    static Order order = new Order();
    public static Order getInstance(){
        return order;
    }
    private static final ProductsDatabase productsDatabase = ProductsDatabase.getInstance();
    private final ArrayList<ArrayList<UserProduct>> orders = new ArrayList<>();
    private final ArrayList<ArrayList<UserProduct>> readyToDeliver = new ArrayList<>();
    private final ArrayList<ArrayList<UserProduct>> history = new ArrayList<>();

    public ArrayList<ArrayList<UserProduct>> getOrders() {
        return order.orders;
    }
    public void printOrders(){
        System.out.println(PrintStatements.orderHeader);
        for(int i=0;i<order.orders.size();i++){
            for(int j=0;j<order.orders.get(i).size();){
                System.out.println(i+1+"."+order.orders.get(i).get(j).getOrderId()+"          "+ order.orders.get(i).get(j).getName() +"          "+ order.orders.get(i).get(j).getMobileNo());
                break;
            }
        }
    }
    public void decreaseProductDatabase(){
        for(int i = 0;i<order.orders.size();i++){
            for(int j=0;j<order.orders.get(i).size();j++){
                order.decreaseProductQuantity(order.orders.get(i).get(j).getProductId(), order.orders.get(i).get(j).getQuantity());
            }
        }
    }
    public  void decreaseProductQuantity(int productId,int quantity){
        for(int i = 0;i<productsDatabase.products.size();i++){
            if(productsDatabase.products.get(i).getProductId() == productId){
                productsDatabase.products.get(i).setQuantity(productsDatabase.products.get(i).getQuantity()-quantity);
            }
        }
    }
    public void increaseProductDatabase(ArrayList<UserProduct> orders){
        for (UserProduct userProduct : orders) {
            order.increaseProductQuantity(userProduct.getProductId(), userProduct.getQuantity());
        }
    }
    public void increaseProductQuantity(int productId,int quantity){
        for(int i = 0;i<productsDatabase.products.size();i++){
            if(productsDatabase.products.get(i).getProductId() == productId){
                productsDatabase.products.get(i).setQuantity(productsDatabase.products.get(i).getQuantity()+quantity);
            }
        }
    }

    public void printSpecificUserOrder(int index){
        if(index-1<order.orders.size()) {
            for (int i = 0; i < order.orders.get(index - 1).size();i++){
                System.out.println(i+1+"."+ order.orders.get(index - 1).get(i).getProductId() +"       "+ order.orders.get(index - 1).get(i).getProductName() +"     "+ order.orders.get(index - 1).get(i).getQuantity() +"     "+ order.orders.get(index - 1).get(i).getDiscountedPrice() * order.orders.get(index - 1).get(i).getQuantity());
            }
            while(true) {
                System.out.println(PrintStatements.orderReady);
                int option = Input.getInteger();
                if(option == 1){
                    order.readyToDeliver.add(order.orders.remove(index-1));
                    break;
                }
                else if(option == 2){
                    return;
                }
                else{
                    System.out.println(PrintStatements.inputError);
                }
            }

        }
    }
    public void readyOrders(){
        System.out.println(PrintStatements.orderHeader);
        for(int i=0;i<order.readyToDeliver.size();i++){
            for(int j=0;j<order.readyToDeliver.get(i).size();){
                System.out.println(i+1+"."+order.readyToDeliver.get(i).get(j).getOrderId()+"         " +order.readyToDeliver.get(i).get(j).getName() +"          "+ order.readyToDeliver.get(i).get(j).getMobileNo());
                break;
            }
        }
    }
    public void setReadyToDeliver(int index){
        if(index-1<order.readyToDeliver.size()) {
            for (int i = 0; i < order.readyToDeliver.get(index - 1).size();i++){
                System.out.println(i+1+"."+ order.readyToDeliver.get(index - 1).get(i).getProductId() +"       "+ order.readyToDeliver.get(index - 1).get(i).getProductName() +"     "+ order.readyToDeliver.get(index - 1).get(i).getQuantity() +"     "+ order.readyToDeliver.get(index - 1).get(i).getDiscountedPrice() * order.readyToDeliver.get(index - 1).get(i).getQuantity());
            }
            while(true) {
                System.out.println(PrintStatements.readyToDeliver);
                int option = Input.getInteger();
                if(option == 1){
                    order.history.add(order.readyToDeliver.remove(index-1));
                    break;
                }
                else if(option == 2){
                    return;
                }
                else{
                    System.out.println(PrintStatements.inputError);
                }
            }

        }
    }
    public void history(){
        System.out.println(PrintStatements.orderHeader);
        for(int i = 0 ;i<order.history.size();i++){
            int total = 0;
            System.out.print(order.history.get(i).get(0).getOrderId()+"        "+order.history.get(i).get(0).getName() +"          "+ order.history.get(i).get(0).getMobileNo() +"          ");
            for(int j = 0;j<order.history.get(i).size();j++){
                total+= order.history.get(i).get(j).getDiscountedPrice() * order.history.get(i).get(j).getQuantity();
            }
            System.out.println(PrintStatements.revenueGenerated+total);
        }
    }
}
