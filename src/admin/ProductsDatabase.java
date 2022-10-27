package admin;

import utilities.PrintStatements;

import java.util.ArrayList;


public class ProductsDatabase {
    static ProductsDatabase productsDatabase = new ProductsDatabase();
    public static ProductsDatabase getInstance(){return productsDatabase;}
    private ProductsDatabase(){}
    ArrayList<Product> products = new ArrayList<>();
    int productId = 1000;
    boolean addToDb(Product newProduct){
        productsDatabase.products.add(newProduct);
        productsDatabase.productId++;
        return true;
    }
    public ArrayList<Integer> searchForProduct(String productName){
        int count = 1;
        ArrayList<Integer> tempList = new ArrayList<>();
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
    public ArrayList<Integer> searchForProduct(int discountFrom, int discountTo){
        int count = 1;
        ArrayList<Integer> tempList = new ArrayList<>();
        for(int i=0;i<productsDatabase.products.size();i++){
                if(productsDatabase.products.get(i).getDiscount() < discountTo && productsDatabase.products.get(i).getDiscount() > discountFrom){
                    System.out.println(count+"."+productsDatabase.products.get(i).getProductName());
                    tempList.add(productsDatabase.products.get(i).getProductId());
                    count++;
                }
        }
        return tempList;
    }
    public void displayProductDetails(int productId){
        System.out.println(PrintStatements.PRODUCT_DETAILS);
        for(int i=0;i<productsDatabase.products.size();i++){
            if(productId == productsDatabase.products.get(i).getProductId()){
                System.out.printf("%-40s %-7s %-10s %-15s %-15s %-30s %-15s",productsDatabase.products.get(i).getProductName(),productsDatabase.products.get(i).getQuantity(),productsDatabase.products.get(i).getMrp(),productsDatabase.products.get(i).getDiscount(),productsDatabase.products.get(i).getDiscountedPrice(),productsDatabase.products.get(i).getSubCategoryName(),productsDatabase.products.get(i).getDescription());
                System.out.println();
                return;
            }
        }
        System.out.println(PrintStatements.PRODUCT_NOT_AVAILABLE);
    }
    public int checkQuantity(int productId){
        for(int i=0;i<productsDatabase.products.size();i++){
            if(productsDatabase.products.get(i).getProductId() == productId){
                return productsDatabase.products.get(i).getQuantity();
            }
        }
        return 0;
    }
    public  Product getProduct(int productId){
        for(int i=0;i<productsDatabase.products.size();i++){
            if(productsDatabase.products.get(i).getProductId() == productId) return productsDatabase.products.get(i);
        }
        return null;
    }
    public String getProductName(int productId){
        for(int i=0;i<productsDatabase.products.size();i++){
            if(productsDatabase.products.get(i).getProductId() == productId) return productsDatabase.products.get(i).getProductName();
        }
        return null;
    }
    public float getDiscountedPrice(int productId){
        for(int i=0;i<productsDatabase.products.size();i++){
            if(productsDatabase.products.get(i).getProductId() == productId) return productsDatabase.products.get(i).getDiscountedPrice();
        }
        return -1;
    }
    void changeCategory(String productName ,String categoryName ,String subCategoryName,int index){
        productsDatabase.products.get(index).getTags().clear();
        productsDatabase.products.get(index).getTags().add(productName.toLowerCase());
        productsDatabase.products.get(index).getTags().add(categoryName.toLowerCase());
        productsDatabase.products.get(index).getTags().add(subCategoryName.toLowerCase());
    }
}
