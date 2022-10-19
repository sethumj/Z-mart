package superMarket;

import utilities.PrintStatements;

import java.util.ArrayList;


public class ProductsDatabase {
    static ProductsDatabase productsDatabase = new ProductsDatabase();
    public static ProductsDatabase getInstance(){return productsDatabase;}
    private ProductsDatabase(){}
    public ArrayList<Product> products = new ArrayList<Product>();
    public  int productId = 1000;
    public static boolean addToDb(Product product){
        productsDatabase.products.add(product);
        productsDatabase.productId++;
        return true;
    }
    public ArrayList<Integer> searchForProduct(String productName){
        int count = 1;
        ArrayList<Integer> tempList = new ArrayList<Integer>();
        for(int i=0;i<productsDatabase.products.size();i++){
            for(int j=0;j<productsDatabase.products.get(i).getTags().size();j++){
                if(productsDatabase.products.get(i).getTags().get(j).equals(productName)){
                    System.out.println(count+"."+productsDatabase.products.get(i).getProductName());
                    tempList.add(productsDatabase.products.get(i).getProductId());
                    count++;
                    break;
                }
            }
        }
        return tempList;
    }
    public void displayProductDetails(int productId){
        System.out.println(PrintStatements.productDetails);
        for(int i=0;i<productsDatabase.products.size();i++){
            if(productId == productsDatabase.products.get(i).getProductId()){
                System.out.println(productsDatabase.products.get(i).getProductName()+"  "+productsDatabase.products.get(i).getQuantity()+"      "+productsDatabase.products.get(i).getMrp()+"       "+productsDatabase.products.get(i).getDiscount()+"              "+productsDatabase.products.get(i).getDiscountedPrice()+"           "+productsDatabase.products.get(i).getCategory()+"          "+productsDatabase.products.get(i).getDescription());
                return;
            }
        }
        System.out.println(PrintStatements.productNotAvailable);
    }
    public  int checkQuantity(int productId){
        for(int i=0;i<productsDatabase.products.size();i++){
            if(productsDatabase.products.get(i).getProductId() == productId){
                return productsDatabase.products.get(i).getQuantity();
            }
        }
        return 0;
    }
    public Product getProduct(int productId){
        for(int i=0;i<productsDatabase.products.size();i++){
            if(productsDatabase.products.get(i).getProductId() == productId) return productsDatabase.products.get(i);
        }
        return null;
    }
}
