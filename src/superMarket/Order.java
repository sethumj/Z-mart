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
    private static ProductsDatabase productsDatabase = ProductsDatabase.getInstance();
private ArrayList<ArrayList<UserProduct>> orders = new ArrayList<ArrayList<UserProduct>>();
private  ArrayList<ArrayList<UserProduct>> readyToDeliver = new ArrayList<ArrayList<UserProduct>>();
private  ArrayList<ArrayList<UserProduct>> history = new ArrayList<ArrayList<UserProduct>>();

    public ArrayList<ArrayList<UserProduct>> getOrders() {
        return order.orders;
    }
    public void printOrders(){
        for(int i=0;i<order.orders.size();i++){
            for(int j=0;j<order.orders.get(i).size();j++){
                System.out.println(i+1+"."+order.orders.get(i).get(j).name+"          "+order.orders.get(i).get(j).mobileNo);
                break;
            }
        }
    }
    public void decreaseProductDatabase(){
        for(int i = 0;i<order.orders.size();i++){
            for(int j=0;j<order.orders.get(i).size();j++){
                order.decreaseProductQuantity(order.orders.get(i).get(j).productId,order.orders.get(i).get(j).quantity);
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
        for(int i = 0;i<orders.size();i++){
                order.increaseProductQuantity(orders.get(i).productId,orders.get(i).quantity);
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
                System.out.println(i+1+"."+order.orders.get(index-1).get(i).productId+"       "+order.orders.get(index-1).get(i).productName+"     "+order.orders.get(index-1).get(i).quantity+"     "+order.orders.get(index-1).get(i).discountedPrice*order.orders.get(index-1).get(i).quantity);
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
        for(int i=0;i<order.readyToDeliver.size();i++){
            for(int j=0;j<order.readyToDeliver.get(i).size();j++){
                System.out.println(i+1+"."+order.readyToDeliver.get(i).get(j).name+"          "+order.readyToDeliver.get(i).get(j).mobileNo);
                break;
            }
        }
    }
    public void setReadyToDeliver(int index){
        if(index-1<order.readyToDeliver.size()) {
            for (int i = 0; i < order.readyToDeliver.get(index - 1).size();i++){
                System.out.println(i+1+"."+order.readyToDeliver.get(index-1).get(i).productId+"       "+order.readyToDeliver.get(index-1).get(i).productName+"     "+order.readyToDeliver.get(index-1).get(i).quantity+"     "+order.readyToDeliver.get(index-1).get(i).discountedPrice*order.readyToDeliver.get(index-1).get(i).quantity);
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
        for(int i = 0 ;i<order.history.size();i++){
            int total = 0;
            System.out.print(order.history.get(i).get(0).name+"          "+order.history.get(i).get(0).mobileNo+"          ");
            for(int j = 0;j<order.history.get(i).size();j++){
                total+=order.history.get(i).get(j).discountedPrice*order.history.get(i).get(j).quantity;
            }
            System.out.println(PrintStatements.revenueGenerated+total);
        }
    }
}
