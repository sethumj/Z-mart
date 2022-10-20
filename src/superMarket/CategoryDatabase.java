package superMarket;

import java.util.ArrayList;

public class CategoryDatabase {
    private CategoryDatabase(){}
    static CategoryDatabase categoryDatabase = new CategoryDatabase();
    public static CategoryDatabase getInstance(){
        return categoryDatabase;
    }
    public ArrayList<Category> categories = new ArrayList<>();
    public boolean addToDb(Category category){
        categories.add(category);
        return true;
    }
}
