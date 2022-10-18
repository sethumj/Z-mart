package superMarket;

import java.util.ArrayList;

public class Category {
    public String categoryName;
    public ArrayList<String> subCategory = new ArrayList<String>();
    public Category(String categoryName){
        this.categoryName = categoryName;
    }
    public static boolean addCategory(Category category){
        return CategoryDatabase.categories.add(category);

    }
}
