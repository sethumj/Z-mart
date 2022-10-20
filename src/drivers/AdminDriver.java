package drivers;

import admin.Admin;
import superMarket.*;
import utilities.Help;
import utilities.Input;
import utilities.PrintStatements;

import java.util.ArrayList;


public class AdminDriver implements Driver{
    static AdminDriver adminObject = new AdminDriver();
    static Order order = Order.getInstance();
    private static final CategoryDatabase categoryDatabase = CategoryDatabase.getInstance();
    private static final ProductsDatabase productsDatabase = ProductsDatabase.getInstance();
    private static final Help help = Help.getInstance();
    private static final Announcement announcement = Announcement.getInstance();
    private static final PincodesDb pincodesDb = PincodesDb.getInstance();
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
                    AdminDriver.checkOrders();
                    break;
                case 8:
                    AdminDriver.deliveryStatus();
                    break;
                case 9:
                    AdminDriver.checkHistory();
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
        System.out.println(PrintStatements.selectCategory);
        AdminDriver.printCategory();
        int categoryIndex = AdminDriver.getCategory();
        if(categoryIndex==-1) return false;
        AdminDriver.printSubCategory(categoryIndex);
        int subCategoryIndex = AdminDriver.getSubCategory(categoryIndex);
        if(subCategoryIndex == -1) return false;
        String subCategoryName = categoryDatabase.categories.get(categoryIndex-1).getSubCategory().get(subCategoryIndex-1);
        ArrayList<String> tags = new ArrayList<String>();
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
        Product product = new Product(productName,quantity,mrp,discount,categoryDatabase.categories.get(categoryIndex-1).getCategoryName(),subCategoryName,tags,description,productsDatabase.productId);
        return productsDatabase.addToDb(product);
    }
    private static boolean updateProduct(){
        System.out.println(PrintStatements.enterProductId);

        int productId = Input.getInteger();
        for(int i=0;i<productsDatabase.products.size();i++){
            if(productsDatabase.products.get(i).getProductId() == productId){
                System.out.println(PrintStatements.previousDetails);
                System.out.println(productsDatabase.products.get(i).getProductId()+"       "+productsDatabase.products.get(i).getProductName()+"       "+productsDatabase.products.get(i).getQuantity()+"       "+productsDatabase.products.get(i).getMrp()+"       "+productsDatabase.products.get(i).getDiscount()+"       "+productsDatabase.products.get(i).getDiscountedPrice()+"       "+productsDatabase.products.get(i).getSubCategoryName()+"       "+productsDatabase.products.get(i).getDescription());
                System.out.println(PrintStatements.tag);
                for(int j = 1;j<=productsDatabase.products.get(i).getTags().size();j++){
                    System.out.println(j+"."+productsDatabase.products.get(i).getTags().get(j-1));
                }
                int modificationCount = 0;
                while(true) {
                    System.out.println(PrintStatements.updateProductMenu);
                    switch(Input.getInteger()){
                        case 1:
                            System.out.println(PrintStatements.enterProductName);
                            productsDatabase.products.get(i).setProductName(Input.getString());
                            modificationCount++;
                            break;
                        case 2:
                            System.out.println(PrintStatements.enterQuantity);
                            int quantity = Input.getInteger();
                            productsDatabase.products.get(i).setQuantity(productsDatabase.products.get(i).getQuantity()+quantity);
                            modificationCount++;
                            break;
                        case 3:
                            System.out.println(PrintStatements.enterMrp);
                            productsDatabase.products.get(i).setMrp(Input.getFloat());
                            modificationCount++;
                            break;
                        case 4:
                            System.out.println(PrintStatements.enterDiscount);
                            productsDatabase.products.get(i).setDiscount(Input.getInteger());
                            productsDatabase.products.get(i).setDiscountedPrice(productsDatabase.products.get(i).getMrp() - productsDatabase.products.get(i).getMrp()*((float)productsDatabase.products.get(i).getDiscount()/100));
                            modificationCount++;
                            break;
                        case 5:
                            System.out.println(PrintStatements.enterCategory);
                            productsDatabase.products.get(i).setSubCategoryName(Input.getString());
                            modificationCount++;
                            break;
                        case 6:
                            System.out.println(PrintStatements.description);
                            productsDatabase.products.get(i).setDescription(Input.getString());
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
                                        productsDatabase.products.get(i).getTags().add(Input.getString());
                                    }
                                    modificationCount++;
                                    break;
                                }
                                else if(option == 2) {
                                    for (int k = 0; k < productsDatabase.products.get(i).getTags().size(); k++) {
                                        System.out.println(k + 1 + "." + productsDatabase.products.get(i).getTags().get(k));
                                    }
                                    System.out.println(PrintStatements.selectFromOption);
                                    int index = Input.getInteger();
                                    if (index > productsDatabase.products.get(i).getTags().size()) {
                                        System.out.println(PrintStatements.noSuchThing);
                                    } else {
                                        productsDatabase.products.get(i).getTags().remove(index - 1);
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
        while(true) {
            System.out.println(PrintStatements.addCategoryMenu);
            switch (Input.getInteger()) {
                case 1:
                    System.out.println(PrintStatements.enterCategory);
                    Category category = new Category(Input.getString());
                    if(Category.addCategory(category)){
                        return true;
                    }
                    return false;
                case 2:
                    System.out.println(PrintStatements.selectFromOption);
                    for(int i=0;i< categoryDatabase.categories.size();i++){
                        System.out.println(i+1+"."+categoryDatabase.categories.get(i).getCategoryName());
                    }
                    int index;
                    while(true) {
                        index = Input.getInteger();
                        if(index<=categoryDatabase.categories.size()) break;
                        else{
                            System.out.println(PrintStatements.noSuchThing);
                            System.out.println(PrintStatements.justTryAgain);
                        }
                    }
                    System.out.println(PrintStatements.enterCategory);
                    categoryDatabase.categories.get(index-1).getSubCategory().add(Input.getString());
                    return true;
                case 3:
                    return false;
                default :
                    System.out.println(PrintStatements.inputError);
                    break;
            }

        }

    }
    private static void checkStock(){
        if(productsDatabase.products.size() == 0) {
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
            String subCategory = categoryDatabase.categories.get(categoryIndex-1).getSubCategory().get(subCategoryIndex-1);
            AdminDriver.printStockByCategory(subCategory);
        }
        else if(option == 2){
            int productId = Input.getInteger();
            AdminDriver.printStockByProductId(productId);
        }
        else if(option == 3){
            AdminDriver.printAllProducts();
        }
        else if(option == 4){
            return;
        }
    }
    public static String printByCategory() {
        AdminDriver.printCategory();
        int categoryIndex = AdminDriver.getCategory();
        AdminDriver.printSubCategory(categoryIndex);
        int subCategoryIndex = AdminDriver.getSubCategory(categoryIndex);
        return categoryDatabase.categories.get(categoryIndex - 1).getSubCategory().get(subCategoryIndex - 1);
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
        for(int i=0;i<productsDatabase.products.size();i++){
            if(productsDatabase.products.get(i).getProductId() == productId){
                productsDatabase.products.remove(i);
                return true;
            }
        }
        return false;
    }
    public static int getSubCategory(int categoryIndex) {
        int subCategoryIndex;
        while (true) {
            subCategoryIndex = Input.getInteger();
            if (subCategoryIndex > categoryDatabase.categories.get(categoryIndex - 1).getSubCategory().size() && subCategoryIndex != 0) {
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
            if(categoryIndex>categoryDatabase.categories.size() && categoryIndex!=0){
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
        for(int c =0;c<categoryDatabase.categories.size();c++){
            System.out.println(c+1+"."+categoryDatabase.categories.get(c).getCategoryName());
        }
    }
    public static void printSubCategory(int categoryIndex){
        for(int sc = 0;sc<categoryDatabase.categories.get(categoryIndex-1).getSubCategory().size();sc++){
            System.out.println(sc+1+"."+categoryDatabase.categories.get(categoryIndex-1).getSubCategory().get(sc));
        }
    }
    public static void printStockByCategory(String categoryName){
        int count = 1;
        System.out.println(PrintStatements.checkStock);
        for(int i=0;i<productsDatabase.products.size();i++){
            if(productsDatabase.products.get(i).getSubCategoryName().equals(categoryName))System.out.println(count+"."+productsDatabase.products.get(i).getProductId()+"             "+productsDatabase.products.get(i).getProductName()+"               "+productsDatabase.products.get(i).getQuantity());
        }
    }
    private static void printStockByProductId(int productId){
        for(int i=0;i<productsDatabase.products.size();i++){
            if(productsDatabase.products.get(i).getProductId() == productId){
                System.out.println(productsDatabase.products.get(i).getProductId()+"              "+productsDatabase.products.get(i).getProductName()+"                "+productsDatabase.products.get(i).getQuantity());
                break;
            }
        }
    }
    private static boolean addPincodes(){
       if(pincodesDb.getPincodes().size()>0) System.out.println(PrintStatements.previousPincodes);
       for(int i = 0; i< pincodesDb.getPincodes().size(); i++){
            System.out.println(i+1+"."+ pincodesDb.getPincodes().get(i));
        }
        System.out.println(PrintStatements.enterHowMany);
        int total = Input.getInteger();
        for(int i = 0;i<total;i++){
            pincodesDb.addPincode(Input.getInteger());
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
            for(int i = 0; i< pincodesDb.getPincodes().size(); i++){
                if(removePin == pincodesDb.getPincodes().get(i)){
                    pincodesDb.getPincodes().remove(i);
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
                    if(help.addHelp(Input.getString())) System.out.println(PrintStatements.updateSuccessful);
                    else System.out.println(PrintStatements.updateFailed);
                    break;
                case 2:
                    System.out.println(PrintStatements.selectFromOption);
                    help.viewHelps();
                    if(help.removeHelp((Input.getInteger()))) System.out.println(PrintStatements.updateSuccessful);
                    else System.out.println(PrintStatements.updateFailed);
                    break;
                case 3:
                    help.viewHelps();
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
    private static void removeSubCategory(){
        AdminDriver.printCategory();
        int categoryIndex = AdminDriver.getCategory();
        AdminDriver.printSubCategory(categoryIndex);
        int subCategoryIndex = AdminDriver.getSubCategory(categoryIndex);
        categoryDatabase.categories.get(categoryIndex-1).getSubCategory().remove(subCategoryIndex-1);
    }
    private static void removeMainCategory(){
        AdminDriver.printCategory();
        int categoryIndex = AdminDriver.getCategory();
        categoryDatabase.categories.remove(categoryIndex-1);
    }
    private static void announcement(){
        while(true) {
            System.out.println(PrintStatements.announcementMenu);
            switch(Input.getInteger()){
                case 1:
                    System.out.println(PrintStatements.enterText);
                    if(announcement.addAnnouncement(Input.getString())) System.out.println(PrintStatements.updateSuccessful);
                    else System.out.println(PrintStatements.updateFailed);
                    break;
                case 2:
                    System.out.println(PrintStatements.selectFromOption);
                    announcement.viewAnnouncements();
                    if(announcement.removeAnnouncement((Input.getInteger()))) System.out.println(PrintStatements.updateSuccessful);
                    else System.out.println(PrintStatements.updateFailed);
                    break;
                case 3:
                    announcement.viewAnnouncements();
                    break;
                case 4: return;
                default :
                    System.out.println(PrintStatements.inputError);
                    break;
            }
        }

    }
    private static void printAllProducts(){
        System.out.println(PrintStatements.checkStockProduct);
        for(int i=0;i<productsDatabase.products.size();i++){
            System.out.println(productsDatabase.products.get(i).getProductId() +"      "+productsDatabase.products.get(i).getProductName()+"         "+productsDatabase.products.get(i).getQuantity());
        }
        System.out.println();
    }
    private static void checkOrders(){
        order.printOrders();
        System.out.println(PrintStatements.selectFromOption);
        System.out.println(PrintStatements.productHeader);
        order.printSpecificUserOrder(Input.getInteger());
    }
    private static void deliveryStatus(){
        order.readyOrders();
        System.out.println(PrintStatements.selectFromOption);
        System.out.println(PrintStatements.productHeader);
        order.setReadyToDeliver(Input.getInteger());
    }
    private static void checkHistory(){
        order.history();
    }
}
