package admin;

import java.util.ArrayList;

public class Category {
    private final String categoryName;
    private static final CategoryDatabase categoryDatabase = CategoryDatabase.getInstance();
    private final ArrayList<String> subCategory = new ArrayList<>();
    Category(String categoryName){
        this.categoryName = categoryName;
    }
    String getCategoryName() {
        return categoryName;
    }
    ArrayList<String> getSubCategory() {
        return subCategory;
    }
    static boolean addCategory(Category newCategory){
        return categoryDatabase.categories.add(newCategory);
    }
}
