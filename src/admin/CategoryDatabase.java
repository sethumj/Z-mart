package admin;

import java.util.ArrayList;

public class CategoryDatabase {
    private CategoryDatabase(){}
    static CategoryDatabase categoryDatabase = new CategoryDatabase();
    static CategoryDatabase getInstance(){
        return categoryDatabase;
    }
    ArrayList<Category> categories = new ArrayList<>();
    boolean addToDb(Category newCategory){
        categories.add(newCategory);
        return true;
    }
    boolean checkCategoryNameExist(String newCategoryName){
        for(Category previousCategory : categories){
            if(previousCategory.getCategoryName().equals(newCategoryName)) return true;
        }
        return false;
    }
    boolean checkSubCategoryNameExist(int index,String newSubCategoryName){
        for(String previousSubCategory : categories.get(index).getSubCategory()){
            if(previousSubCategory.equals(newSubCategoryName))return true;
        }
        return false;
    }
}
