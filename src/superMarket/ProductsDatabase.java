package superMarket;

import utilities.PrintStatements;

import java.util.ArrayList;
import java.util.Iterator;

public class ProductsDatabase {
    public static ArrayList<Product> products = new ArrayList<Product>();
    public static int productId = 1000;
    public static boolean addToDb(Product product){
        products.add(product);
        productId++;
        return true;
    }
    public static ArrayList<Integer> searchForProduct(String productName){
        int count = 1;
        ArrayList<Integer> templist = new ArrayList<Integer>();
        for(int i=0;i<products.size();i++){
            for(int j=0;j<products.get(i).tags.size();j++){
                if(products.get(i).tags.get(j).equals(productName)){
                    System.out.println(count+"."+products.get(i).productName);
                    templist.add(products.get(i).productId);
                    count++;
                    break;
                }
            }
        }
        return templist;
    }
    public static void displayProductDetails(int productId){
        System.out.println(PrintStatements.productDetails);
        for(int i=0;i<products.size();i++){
            if(productId == products.get(i).productId){
                System.out.println(products.get(i).productName+"  "+products.get(i).quantity+"     "+products.get(i).mrp+"     "+products.get(i).discount+"     "+products.get(i).discountedPrice+"     "+products.get(i).category+"     "+products.get(i).description);
                return;
            }
        }
        System.out.println(PrintStatements.productNotAvailable);
    }
    public static int checkQuantity(int productId){
        for(int i=0;i<products.size();i++){
            if(products.get(i).productId == productId){
                return products.get(i).quantity;
            }
        }
        return 0;
    }
    public static Product getProduct(int productId){
        for(int i=0;i<products.size();i++){
            if(products.get(i).productId == productId) return products.get(i);
        }
        return null;
    }
}
