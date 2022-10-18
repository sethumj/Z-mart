package drivers;

import admin.Admin;
import superMarket.*;
import utilities.Help;
import utilities.Input;
import utilities.PrintStatements;

import java.util.ArrayList;


public class AdminDriver implements Driver{
    static AdminDriver adminObject = new AdminDriver();
    private AdminDriver(){
    }
   static AdminDriver getInstance(){
        return adminObject;
    }
    @Override
    public void startDriver() {
        while(true) {
            System.out.println(PrintStatements.adminSignMenu);
            switch(Input.getInteger()){
                case 1:
                    System.out.println(PrintStatements.enterEmail);
                    if(Admin.validateMail(Input.getString())){
                        System.out.println(PrintStatements.enterPassword);
                        if(Admin.validatePassword(Input.getString())){
                            System.out.println(PrintStatements.signInSuccessful);
                            AdminDriver.goToMenu();
                        }
                        else{
                            System.out.println(PrintStatements.signInError);
                            System.out.println(PrintStatements.tryAgain);
                            int option = Input.getInteger();
                            if(option==1);
                            else if(option ==2)return;
                            else{
                                System.out.println(PrintStatements.inputError);
                            }
                        }
                    }
                    else{
                        System.out.println(PrintStatements.signInError);
                        System.out.println(PrintStatements.tryAgain);
                        int option = Input.getInteger();
                        if(option==1);
                        else if(option ==2)return;
                        else{
                            System.out.println(PrintStatements.inputError);
                        }
                    }
                    break;
                case 2:
                    return;
                default:
                    System.out.println(PrintStatements.inputError);
                    break;
            }
        }
    }
    private static void goToMenu() {
        while (true) {
            System.out.println(PrintStatements.adminOptions);
            switch(Input.getInteger()){
                case 1:
                    if(AdminDriver.addProduct()) System.out.println(PrintStatements.addedSuccessfully);
                    else System.out.println(PrintStatements.addingFailed);
                    break;
                case 2:
                    if(AdminDriver.updateProduct()) System.out.println(PrintStatements.updateSuccessful);
                    else System.out.println(PrintStatements.updateFailed);
                    break;
                case 3:
                    if(AdminDriver.removeProduct()) System.out.println(PrintStatements.removeProduct);
                    else System.out.println(PrintStatements.unableToRemove);
                    break;
                case 4:
                    if(AdminDriver.addCategory())System.out.println(PrintStatements.categoryAddedSuccessfully);
                    else System.out.println(PrintStatements.failedToAddCategory);
                    break;
                case 5:
                    if(AdminDriver.removeCategory()) System.out.println(PrintStatements.updateSuccessful);
                    else System.out.println(PrintStatements.updateFailed);
                    break;
                case 6:
                    AdminDriver.checkStock();
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    AdminDriver.pincodes();
                    break;
                case 11:
                    AdminDriver.help();
                    break;
                case 12:
                    AdminDriver.announcement();
                    break;
                case 13:
                    System.out.println(PrintStatements.aboutUs);
                    break;
                case 14:
                    return;
                default:
                    System.out.println(PrintStatements.inputError);
                    break;
            }
        }
    }
    private static boolean addProduct(){
        System.out.println(PrintStatements.enterProductName);
        String productName = Input.getString();
        System.out.println(PrintStatements.enterQuantity);
        int quantity = Input.getInteger();
        System.out.println(PrintStatements.enterMrp);
        float mrp = Input.getFloat();
        System.out.println(PrintStatements.enterDiscount);
        int discount = Input.getInteger();
        float discountedPrice = mrp - mrp * ((float)discount/100);
        System.out.println(PrintStatements.selectCategory);
        AdminDriver.printCategory();
        int categoryIndex = AdminDriver.getCategory();
        if(categoryIndex==-1) return false;
        AdminDriver.printSubCategory(categoryIndex);
        int subCategoryIndex = AdminDriver.getSubCategory(categoryIndex);
        if(subCategoryIndex == -1) return false;
        String category = CategoryDatabase.categories.get(categoryIndex-1).subCategory.get(subCategoryIndex-1);
        ArrayList<String> tags = new ArrayList<String>();
        tags.add(CategoryDatabase.categories.get(categoryIndex-1).categoryName);
        tags.add(CategoryDatabase.categories.get(categoryIndex-1).subCategory.get(subCategoryIndex-1));
        System.out.println(PrintStatements.tag);
        System.out.println(PrintStatements.enterHowMany);
        int noOfTags = Input.getInteger();
        for(int i = 0;i<noOfTags;i++){
            tags.add(Input.getString());
        }
        System.out.println(PrintStatements.description);
        String description = Input.getString();
        while(true) {
            System.out.println(PrintStatements.confirmProduct);
            int confirm = Input.getInteger();
            if (confirm == 1)break;
            else if(confirm == 2) return false;
            else System.out.println(PrintStatements.inputError);
        }
        Product product = new Product(productName,quantity,mrp,discount,category,tags,description,ProductsDatabase.productId,discountedPrice);
        return ProductsDatabase.addToDb(product);
    }
    private static boolean updateProduct(){
        System.out.println(PrintStatements.enterProductId);
        int productId = Input.getInteger();
        for(int i=0;i<ProductsDatabase.products.size();i++){
            if(ProductsDatabase.products.get(i).productId == productId){
                System.out.println(PrintStatements.previousDetails);
                System.out.println(ProductsDatabase.products.get(i).productId+"       "+ProductsDatabase.products.get(i).productName+"       "+ProductsDatabase.products.get(i).quantity+"       "+ProductsDatabase.products.get(i).mrp+"       "+ProductsDatabase.products.get(i).discount+"       "+ProductsDatabase.products.get(i).discountedPrice+"       "+ProductsDatabase.products.get(i).category+"       "+ProductsDatabase.products.get(i).description);
                System.out.println(PrintStatements.tag);
                for(int j = 1;j<=ProductsDatabase.products.get(i).tags.size();j++){
                    System.out.println(j+"."+ProductsDatabase.products.get(i).tags.get(j-1));
                }
                int modificationCount = 0;
                while(true) {
                    System.out.println(PrintStatements.updateProductMenu);
                    switch(Input.getInteger()){
                        case 1:
                            System.out.println(PrintStatements.enterProductName);
                            ProductsDatabase.products.get(i).productName = Input.getString();
                            modificationCount++;
                            break;
                        case 2:
                            System.out.println(PrintStatements.enterQuantity);
                            int quantity = Input.getInteger();
                            ProductsDatabase.products.get(i).quantity= ProductsDatabase.products.get(i).quantity+quantity;
                            modificationCount++;
                            break;
                        case 3:
                            System.out.println(PrintStatements.enterMrp);
                            ProductsDatabase.products.get(i).mrp = Input.getFloat();
                            modificationCount++;
                            break;
                        case 4:
                            System.out.println(PrintStatements.enterDiscount);
                            ProductsDatabase.products.get(i).discount = Input.getInteger();
                            ProductsDatabase.products.get(i).discountedPrice =ProductsDatabase.products.get(i).mrp - ProductsDatabase.products.get(i).mrp*((float)ProductsDatabase.products.get(i).discount/100);
                            modificationCount++;
                            break;
                        case 5:
                            System.out.println(PrintStatements.enterCategory);
                            ProductsDatabase.products.get(i).category = Input.getString();
                            modificationCount++;
                            break;
                        case 6:
                            System.out.println(PrintStatements.description);
                            ProductsDatabase.products.get(i).description = Input.getString();
                            modificationCount++;
                            break;
                        case 7:
                            while(true) {
                                System.out.println(PrintStatements.tagMenu);
                                int option = Input.getInteger();
                                if(option == 1){
                                    System.out.println(PrintStatements.enterHowMany);
                                    int noOfTags = Input.getInteger();
                                    for(int l = 0;l<noOfTags;l++){
                                        ProductsDatabase.products.get(i).tags.add(Input.getString());
                                    }
                                    modificationCount++;
                                    break;
                                }
                                else if(option == 2) {
                                    for (int k = 0; k < ProductsDatabase.products.get(i).tags.size(); k++) {
                                        System.out.println(k + 1 + "." + ProductsDatabase.products.get(i).tags.get(k));
                                    }
                                    System.out.println(PrintStatements.selectFromOption);
                                    int index = Input.getInteger();
                                    if (index > ProductsDatabase.products.get(i).tags.size()) {
                                        System.out.println(PrintStatements.noSuchThing);
                                    } else {
                                        ProductsDatabase.products.get(i).tags.remove(index - 1);
                                        modificationCount++;
                                    }
                                    break;
                                }
                                else if(option == 3){
                                    break;
                                }
                            }
                            break;
                        case 8:
                            return modificationCount > 0;
                        default:
                            System.out.println(PrintStatements.inputError);
                            break;
                    }
                }
            }
        }
        return false;
    }
    private static boolean addCategory(){
        int modificationCount = 0;
        while(true) {
            System.out.println(PrintStatements.addCategoryMenu);
            switch (Input.getInteger()) {
                case 1:
                    System.out.println(PrintStatements.enterCategory);
                    Category category = new Category(Input.getString());
                    if(Category.addCategory(category)){
                        modificationCount++;
                        return true;
                    }
                    return false;
                case 2:
                    System.out.println(PrintStatements.selectFromOption);
                    for(int i=0;i< CategoryDatabase.categories.size();i++){
                        System.out.println(i+1+"."+CategoryDatabase.categories.get(i).categoryName);
                    }
                    int index;
                    while(true) {
                        index = Input.getInteger();
                        if(index<=CategoryDatabase.categories.size()) break;
                        else{
                            System.out.println(PrintStatements.noSuchThing);
                            System.out.println(PrintStatements.justTryAgain);
                        }
                    }
                    System.out.println(PrintStatements.enterCategory);
                    CategoryDatabase.categories.get(index-1).subCategory.add(Input.getString());
                    modificationCount++;
                    return true;
                case 3:
                    return modificationCount > 0;
                default :
                    System.out.println(PrintStatements.inputError);
                    break;
            }

        }

    }
    public static void checkStock(){
        if(ProductsDatabase.products.size() == 0) {
            System.out.println(PrintStatements.noProductsAvailable);
            return;
        }
        System.out.println(PrintStatements.byCategory);
        int option = Input.getInteger();
        if(option == 1){
            AdminDriver.printCategory();
            int categoryIndex = AdminDriver.getCategory();
            AdminDriver.printSubCategory(categoryIndex);
            int subCategoryIndex = AdminDriver.getSubCategory(categoryIndex);
            String subCategory = CategoryDatabase.categories.get(categoryIndex-1).subCategory.get(subCategoryIndex-1);
            AdminDriver.printStockByCategory(subCategory);
        }
        else if(option == 2){
            int productId = Input.getInteger();
            AdminDriver.printStockByProductId(productId);
        }
        else if(option == 3){
            return;
        }
    }
    private static boolean removeProduct(){
        System.out.println(PrintStatements.enterProductId);
        int productId = Input.getInteger();
        while(true) {
            System.out.println(PrintStatements.confirmProduct);
            int option = Input.getInteger();
            if(option == 1)break;
            else if(option == 2)return false;
            else System.out.println(PrintStatements.inputError);
        }
        for(int i=0;i<ProductsDatabase.products.size();i++){
            if(ProductsDatabase.products.get(i).productId == productId){
                ProductsDatabase.products.remove(i);
                return true;
            }
        }
        return false;
    }
    public static int getSubCategory(int categoryIndex) {
        int subCategoryIndex;
        while (true) {
            subCategoryIndex = Input.getInteger();
            if (subCategoryIndex > CategoryDatabase.categories.get(categoryIndex - 1).subCategory.size() && subCategoryIndex != 0) {
                System.out.println(PrintStatements.noSuchThing);
                System.out.println(PrintStatements.tryAgain);
                int option = Input.getInteger();
                if (option == 1) ;
                else if (option == 2) return -1;
                else {
                    System.out.println(PrintStatements.inputError);
                    System.out.println(PrintStatements.justTryAgain);
                }
            } else {
                if (subCategoryIndex != 0) break;
                System.out.println(PrintStatements.inputError);
                System.out.println(PrintStatements.justTryAgain);
            }
        }
        return subCategoryIndex;
    }
    public static int getCategory(){
        int categoryIndex;
        while(true){
            categoryIndex = Input.getInteger();
            if(categoryIndex>CategoryDatabase.categories.size() && categoryIndex!=0){
                System.out.println(PrintStatements.noSuchThing);
                System.out.println(PrintStatements.tryAgain);
                int option =Input.getInteger();
                if(option == 1);
                else if(option == 2) return -1;
                else {
                    System.out.println(PrintStatements.inputError);
                    System.out.println(PrintStatements.justTryAgain);
                }

            }
            else{
                if(categoryIndex!=0)break;
                System.out.println(PrintStatements.inputError);
                System.out.println(PrintStatements.justTryAgain);
            }
        }
        return categoryIndex;
    }
    public static void printCategory(){
        for(int c =0;c<CategoryDatabase.categories.size();c++){
            System.out.println(c+1+"."+CategoryDatabase.categories.get(c).categoryName);
        }
    }
    public static void printSubCategory(int categoryIndex){
        for(int sc = 0;sc<CategoryDatabase.categories.get(categoryIndex-1).subCategory.size();sc++){
            System.out.println(sc+1+"."+CategoryDatabase.categories.get(categoryIndex-1).subCategory.get(sc));
        }
    }
    public static void printStockByCategory(String categoryName){
        int count = 1;
        System.out.println(PrintStatements.checkStock);
        for(int i=0;i<ProductsDatabase.products.size();i++){
            if(ProductsDatabase.products.get(i).category.equals(categoryName))System.out.println(count+"."+ProductsDatabase.products.get(i).productId+"             "+ProductsDatabase.products.get(i).productName+"               "+ProductsDatabase.products.get(i).quantity);
        }
    }
    private static void printStockByProductId(int productId){
        for(int i=0;i<ProductsDatabase.products.size();i++){
            if(ProductsDatabase.products.get(i).productId == productId){
                System.out.println(ProductsDatabase.products.get(i).productId+"              "+ProductsDatabase.products.get(i).productName+"                "+ProductsDatabase.products.get(i).quantity);
                break;
            }
        }
    }
    private static boolean addPincodes(){
       if(PincodesDb.pincodes.size()>0) System.out.println(PrintStatements.previousPincodes);
        for(int i = 0;i<PincodesDb.pincodes.size();i++){
            System.out.println(i+1+"."+PincodesDb.pincodes.get(i));
        }
        System.out.println(PrintStatements.enterHowMany);
        int total = Input.getInteger();
        for(int i = 0;i<total;i++){
            PincodesDb.addPincode(Input.getInteger());
        }
        return total>0;
    }
    private static void pincodes(){
        System.out.println(PrintStatements.pincodeMenu);
        int option = Input.getInteger();
        if(option == 1){
            if(AdminDriver.addPincodes()) System.out.println(PrintStatements.updateSuccessful);
            else System.out.println(PrintStatements.updateFailed);
        }
        else if(option == 2 ){
            System.out.println(PrintStatements.enterPincode);
            int removePin = Input.getInteger();
            for(int i = 0;i<PincodesDb.pincodes.size();i++){
                if(removePin == PincodesDb.pincodes.get(i)){
                    PincodesDb.pincodes.remove(i);
                    System.out.println(PrintStatements.updateSuccessful);
                    return;
                }
            }
            System.out.println(PrintStatements.noSuchThing);
        }
        else if(option == 3) return;
        else{
            System.out.println(PrintStatements.inputError);
        }
    }
    private static void help(){
        while(true) {
            System.out.println(PrintStatements.helpMenu);
            switch(Input.getInteger()){
                case 1:
                    System.out.println(PrintStatements.enterText);
                    if(Help.addHelp(Input.getString())) System.out.println(PrintStatements.updateSuccessful);
                    else System.out.println(PrintStatements.updateFailed);
                    break;
                case 2:
                    System.out.println(PrintStatements.selectFromOption);
                    Help.viewHelps();
                    if(Help.removeHelp((Input.getInteger()))) System.out.println(PrintStatements.updateSuccessful);
                    else System.out.println(PrintStatements.updateFailed);
                    break;
                case 3:
                    Help.viewHelps();
                    break;
                case 4: return;
                default :
                    System.out.println(PrintStatements.inputError);
                    break;
            }
        }
    }
    private static boolean removeCategory(){
        int modificationCount = 0;
        while(true) {
            System.out.println(PrintStatements.categoryRemoveMenu);
            switch(Input.getInteger()){
                case 1:
                    AdminDriver.removeMainCategory();
                    modificationCount++;
                    break;
                case 2:
                    AdminDriver.removeSubCategory();
                    modificationCount++;
                    break;
                case 3:
                    return modificationCount>0;
                default:
                    System.out.println(PrintStatements.inputError);
                    break;
            }

        }
    }
    private static boolean removeSubCategory(){
        AdminDriver.printCategory();
        int categoryIndex = AdminDriver.getCategory();
        AdminDriver.printSubCategory(categoryIndex);
        int subCategoryIndex = AdminDriver.getSubCategory(categoryIndex);
        CategoryDatabase.categories.get(categoryIndex-1).subCategory.remove(subCategoryIndex-1);
        return true;
    }
    private static boolean removeMainCategory(){
        AdminDriver.printCategory();
        int categoryIndex = AdminDriver.getCategory();
        CategoryDatabase.categories.remove(categoryIndex-1);
        return true;
    }
    private static void announcement(){
        while(true) {
            System.out.println(PrintStatements.announcementMenu);
            switch(Input.getInteger()){
                case 1:
                    System.out.println(PrintStatements.enterText);
                    if(Announcement.addAnnouncement(Input.getString())) System.out.println(PrintStatements.updateSuccessful);
                    else System.out.println(PrintStatements.updateFailed);
                    break;
                case 2:
                    System.out.println(PrintStatements.selectFromOption);
                    Announcement.viewAnnouncements();
                    if(Announcement.removeAnnouncement((Input.getInteger()))) System.out.println(PrintStatements.updateSuccessful);
                    else System.out.println(PrintStatements.updateFailed);
                    break;
                case 3:
                    Announcement.viewAnnouncements();
                    break;
                case 4: return;
                default :
                    System.out.println(PrintStatements.inputError);
                    break;
            }
        }

    }
}
