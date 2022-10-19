package superMarket;

import drivers.AdminDriver;

import java.util.ArrayList;

public class CategoryDatabase {
    public static ArrayList<Category> categories = new ArrayList<Category>();
    public static int categoryId = 100;
    public boolean addToDb(Category category){
        categories.add(category);
        return true;
    }
    public static String printByCategory() {
        AdminDriver.printCategory();
        int categoryIndex = AdminDriver.getCategory();
        AdminDriver.printSubCategory(categoryIndex);
        int subCategoryIndex = AdminDriver.getSubCategory(categoryIndex);
        return CategoryDatabase.categories.get(categoryIndex - 1).getSubCategory().get(subCategoryIndex - 1);
    }

}
