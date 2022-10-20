package superMarket;

import java.util.ArrayList;

public class Category {
    private final String categoryName;
    private static CategoryDatabase categoryDatabase = CategoryDatabase.getInstance();
    private ArrayList<String> subCategory = new ArrayList<String>();
    public Category(String categoryName){
        this.categoryName = categoryName;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public ArrayList<String> getSubCategory() {
        return subCategory;
    }
    public static boolean addCategory(Category category){
        return categoryDatabase.categories.add(category);
    }
}
