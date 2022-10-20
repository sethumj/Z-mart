package superMarket;

import java.util.ArrayList;

public class Category {
    private final String categoryName;
    private static final CategoryDatabase categoryDatabase = CategoryDatabase.getInstance();
    private final ArrayList<String> subCategory = new ArrayList<>();
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
