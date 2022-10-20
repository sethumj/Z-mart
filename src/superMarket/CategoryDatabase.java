package superMarket;

import drivers.AdminDriver;

import java.util.ArrayList;

public class CategoryDatabase {
    private CategoryDatabase(){}
    static CategoryDatabase categoryDatabase = new CategoryDatabase();
    public static CategoryDatabase getInstance(){
        return categoryDatabase;
    }
    public ArrayList<Category> categories = new ArrayList<Category>();
    public boolean addToDb(Category category){
        categories.add(category);
        return true;
    }
}
