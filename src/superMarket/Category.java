package superMarket;

import java.util.ArrayList;

public class Category {
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ArrayList<String> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(ArrayList<String> subCategory) {
        this.subCategory = subCategory;
    }

    private String categoryName;
    private ArrayList<String> subCategory = new ArrayList<String>();
    public Category(String categoryName){
        this.categoryName = categoryName;
    }
    public static boolean addCategory(Category category){
        return CategoryDatabase.categories.add(category);

    }
}
