package admin;

import utilities.Input;
import utilities.PrintStatements;
import utilities.Validation;

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
            System.out.println(PrintStatements.ADMIN_SIGNING_MENU);
            switch(Input.getInteger()){
                case 1:
                    System.out.println(PrintStatements.ENTER_EMAIL);
                    if(Admin.validateMail(Input.getString())){
                        System.out.println(PrintStatements.ENTER_PASSWORD);
                        if(Admin.validatePassword(Input.getString())){
                            System.out.println(PrintStatements.SIGN_IN_SUCCESSFUL);
                            AdminDriver.goToMenu();
                        }
                        else{
                            System.out.println(PrintStatements.SIGN_IN_ERROR);
                            System.out.println(PrintStatements.TRY_AGAIN);
                            int option = Input.getInteger();
                            if (option != 1) {
                                if(option ==2)return;
                                else{
                                    System.out.println(PrintStatements.INPUT_ERROR);
                                }
                            }
                        }
                    }
                    else{
                        System.out.println(PrintStatements.SIGN_IN_ERROR);
                        System.out.println(PrintStatements.TRY_AGAIN);
                        int option = Input.getInteger();
                        if (option != 1) {
                            if(option ==2)return;
                            else{
                                System.out.println(PrintStatements.INPUT_ERROR);
                            }
                        }
                    }
                    break;
                case 2:
                    return;
                default:
                    System.out.println(PrintStatements.INPUT_ERROR);
                    break;
            }
        }
    }
    private static void goToMenu() {
        while (true) {
            System.out.println(PrintStatements.ADMIN_OPTIONS);
            switch(Input.getInteger()){
                case 1:
                    if(AdminDriver.addProduct()) System.out.println(PrintStatements.ADDED_SUCCESSFULLY);
                    else System.out.println(PrintStatements.ADDING_FAILED);
                    break;
                case 2:
                    if(AdminDriver.updateProduct()) System.out.println(PrintStatements.UPDATE_SUCCESSFUL);
                    else System.out.println(PrintStatements.UPDATE_FAILED);
                    break;
                case 3:
                    if(AdminDriver.removeProduct()) System.out.println(PrintStatements.REMOVE_PRODUCT);
                    else System.out.println(PrintStatements.UNABLE_TO_REMOVE);
                    break;
                case 4:
                    if(AdminDriver.addCategory())System.out.println(PrintStatements.CATEGORY_ADDED_SUCCESSFULLY);
                    else System.out.println(PrintStatements.FAILED_TO_ADD_CATEGORY);
                    break;
                case 5:
                    if(AdminDriver.removeCategory()) System.out.println(PrintStatements.UPDATE_SUCCESSFUL);
                    else System.out.println(PrintStatements.UPDATE_FAILED);
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
                    System.out.println(PrintStatements.ABOUT_US);
                    break;
                case 14:
                    return;
                default:
                    System.out.println(PrintStatements.INPUT_ERROR);
                    break;
            }
        }
    }
    private static boolean addProduct(){
        System.out.println(PrintStatements.ENTER_PRODUCT_NAME);
        String productName = Input.getString();
        System.out.println(PrintStatements.ENTER_QUANTITY);
        int quantity = Validation.getPositiveInt();
        System.out.println(PrintStatements.ENTER_MRP);
        float mrp = Validation.getPositiveFloat();
        System.out.println(PrintStatements.ENTER_DISCOUNT);
        int discount = Validation.rangeOfHundred();
        System.out.println(PrintStatements.SELECT_CATEGORY);
        AdminDriver.printCategory();
        int categoryIndex = AdminDriver.getCategory();
        if(categoryIndex==-1) return false;
        AdminDriver.printSubCategory(categoryIndex);
        int subCategoryIndex = AdminDriver.getSubCategory(categoryIndex);
        if(subCategoryIndex == -1) return false;
        String subCategoryName = categoryDatabase.categories.get(categoryIndex-1).getSubCategory().get(subCategoryIndex-1);
        ArrayList<String> tags = new ArrayList<>();
        System.out.println(PrintStatements.TAG);
        System.out.println(PrintStatements.ENTER_HOW_MANY);
        int noOfTags = Validation.getPositiveIntOrZero();
        for(int i = 0;i<noOfTags;i++){
            tags.add(Input.getString());
        }
        System.out.println(PrintStatements.DESCRIPTION);
        String description = Input.getString();
        while(true) {
            System.out.println(PrintStatements.CONFIRM_PRODUCT);
            int confirm = Input.getInteger();
            if (confirm == 1)break;
            else if(confirm == 2) return false;
            else System.out.println(PrintStatements.INPUT_ERROR);
        }
        Product product = new Product(productName,quantity,mrp,discount,categoryDatabase.categories.get(categoryIndex-1).getCategoryName(),subCategoryName,tags,description,productsDatabase.productId);
        return productsDatabase.addToDb(product);
    }
    private static boolean updateProduct(){
        System.out.println(PrintStatements.ENTER_PRODUCT_ID);
        int productId = Input.getInteger();
        for(int i=0;i<productsDatabase.products.size();i++){
            if(productsDatabase.products.get(i).getProductId() == productId){
                System.out.println(PrintStatements.PREVIOUS_DETAILS);
                System.out.println(PrintStatements.PRODUCT_FULL_LIST);
                System.out.printf("%-13s %-40s %-7s %-10s %-15s %-15s %-30s %-15s",productsDatabase.products.get(i).getProductId(),productsDatabase.products.get(i).getProductName(),productsDatabase.products.get(i).getQuantity(),productsDatabase.products.get(i).getMrp(),productsDatabase.products.get(i).getDiscount(),productsDatabase.products.get(i).getDiscountedPrice(),productsDatabase.products.get(i).getSubCategoryName(),productsDatabase.products.get(i).getDescription());
                System.out.println();
                System.out.println(PrintStatements.TAG);
                for(int j = 1;j<=productsDatabase.products.get(i).getTags().size();j++){
                    System.out.println(j+"."+productsDatabase.products.get(i).getTags().get(j-1));
                }
                System.out.println();
                int modificationCount = 0;
                while(true) {
                    System.out.println(PrintStatements.UPDATE_PRODUCT_MENU);
                    switch(Input.getInteger()){
                        case 1:
                            System.out.println(PrintStatements.ENTER_PRODUCT_NAME);
                            String productName = Input.getString();
                            productsDatabase.products.get(i).setProductName(productName);
                            System.out.println(PrintStatements.UPDATE_SUCCESSFUL);
                            modificationCount++;
                            break;
                        case 2:
                            System.out.println(PrintStatements.QUANTITY_CHANGE);
                            int option = Validation.getPositiveInt();
                            int quantity;
                            switch(option){
                                case 1:
                                    System.out.println(PrintStatements.ENTER_QUANTITY);
                                    quantity = Validation.getPositiveInt();
                                    if(productsDatabase.products.get(i).getQuantity()+quantity>=0) {
                                        productsDatabase.products.get(i).setQuantity(productsDatabase.products.get(i).getQuantity() + quantity);
                                    }
                                    else{
                                        System.out.println(PrintStatements.INVALID_QUANTITY);
                                        break;
                                    }
                                    System.out.println(PrintStatements.UPDATE_SUCCESSFUL);
                                    modificationCount++;
                                    break;
                                case 2:
                                    System.out.println(PrintStatements.ENTER_QUANTITY);
                                    quantity = Validation.getPositiveInt();
                                    if(productsDatabase.products.get(i).getQuantity()-quantity>=0) {
                                        productsDatabase.products.get(i).setQuantity(productsDatabase.products.get(i).getQuantity() - quantity);
                                    }
                                    else{
                                        System.out.println(PrintStatements.INVALID_QUANTITY);
                                        break;
                                    }
                                    System.out.println(PrintStatements.UPDATE_SUCCESSFUL);
                                    modificationCount++;
                                    break;
                                case 3:
                                    break;
                                default:
                                    System.out.println(PrintStatements.INPUT_ERROR);
                                    break;
                            }
                            break;
                        case 3:
                            System.out.println(PrintStatements.ENTER_MRP);
                            productsDatabase.products.get(i).setMrp(Validation.getPositiveFloat());
                            modificationCount++;
                            break;
                        case 4:
                            System.out.println(PrintStatements.ENTER_DISCOUNT);
                            productsDatabase.products.get(i).setDiscount(Validation.rangeOfHundred());
                            System.out.println(PrintStatements.UPDATE_SUCCESSFUL);
                            modificationCount++;
                            break;
                        case 5:
                            System.out.println(PrintStatements.SELECT_CATEGORY);
                            AdminDriver.printCategory();
                            int categoryIndex = AdminDriver.getCategory();
                            if(categoryIndex==-1) return false;
                            AdminDriver.printSubCategory(categoryIndex);
                            int subCategoryIndex = AdminDriver.getSubCategory(categoryIndex);
                            if(subCategoryIndex == -1) return false;
                            String subCategoryName = categoryDatabase.categories.get(categoryIndex-1).getSubCategory().get(subCategoryIndex-1);
                            productsDatabase.products.get(i).setSubCategoryName(subCategoryName);
                            productsDatabase.changeCategory(productsDatabase.products.get(i).getProductName(),categoryDatabase.categories.get(categoryIndex-1).getCategoryName(),subCategoryName,i);
                            System.out.println(PrintStatements.UPDATE_SUCCESSFUL);
                            modificationCount++;
                            break;
                        case 6:
                            System.out.println(PrintStatements.DESCRIPTION);
                            productsDatabase.products.get(i).setDescription(Validation.getNonNullString());
                            System.out.println(PrintStatements.UPDATE_SUCCESSFUL);
                            modificationCount++;
                            break;
                        case 7:
                            while(true) {
                                System.out.println(PrintStatements.TAG_MENU);
                                option = Input.getInteger();
                                if(option == 1){
                                    System.out.println(PrintStatements.ENTER_HOW_MANY);
                                    int noOfTags = Input.getInteger();
                                    for(int l = 0;l<noOfTags;l++){
                                        System.out.println(PrintStatements.ENTER_TAG);
                                        productsDatabase.products.get(i).getTags().add(Validation.getNonNullString());
                                    }
                                    System.out.println(PrintStatements.UPDATE_SUCCESSFUL);
                                    modificationCount++;
                                    break;
                                }
                                else if(option == 2) {
                                    for (int k = 0; k < productsDatabase.products.get(i).getTags().size(); k++) {
                                        System.out.println(k + 1 + "." + productsDatabase.products.get(i).getTags().get(k));
                                    }
                                    System.out.println(PrintStatements.SELECT_FROM_OPTION);
                                    int index = Input.getInteger();
                                    if (index > productsDatabase.products.get(i).getTags().size()) {
                                        System.out.println(PrintStatements.NO_SUCH_THING);
                                    } else {
                                        productsDatabase.products.get(i).getTags().remove(index - 1);
                                        System.out.println(PrintStatements.UPDATE_SUCCESSFUL);
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
                            System.out.println(PrintStatements.INPUT_ERROR);
                            break;
                    }
                }
            }
        }
        return false;
    }
    private static boolean addCategory(){
        while(true) {
            System.out.println(PrintStatements.ADD_CATEGORY_MENU);
            switch (Input.getInteger()) {
                case 1:
                    String categoryName;
                    while(true) {
                        System.out.println(PrintStatements.ENTER_CATEGORY);
                        categoryName = Input.getString();
                        if(categoryDatabase.checkCategoryNameExist(categoryName)){
                            System.out.println(PrintStatements.CATEGORY_EXIST);
                            System.out.println(PrintStatements.TRY_AGAIN);
                            int option = Input.getInteger();
                            if (option != 1) {
                                if(option ==2)return false;
                                else{
                                    System.out.println(PrintStatements.INPUT_ERROR);
                                }
                            }
                        }
                        else break;
                    }
                    Category newCategory = new Category(categoryName);
                    return Category.addCategory(newCategory);
                case 2:
                    System.out.println(PrintStatements.SELECT_FROM_OPTION);
                    for(int i=0;i< categoryDatabase.categories.size();i++){
                        System.out.println(i+1+"."+categoryDatabase.categories.get(i).getCategoryName());
                    }
                    int index;
                    while(true) {
                        index = Input.getInteger();
                        if(index<=categoryDatabase.categories.size()) break;
                        else{
                            System.out.println(PrintStatements.NO_SUCH_THING);
                            System.out.println(PrintStatements.JUST_TRY_AGAIN);
                        }
                    }
                    String subCategoryName;
                    while(true) {
                        System.out.println(PrintStatements.ENTER_CATEGORY);
                        subCategoryName = Input.getString();
                        if(categoryDatabase.checkSubCategoryNameExist(index-1,subCategoryName)){
                            System.out.println(PrintStatements.CATEGORY_EXIST);
                            System.out.println(PrintStatements.TRY_AGAIN);
                            int option = Input.getInteger();
                            if (option != 1) {
                                if(option ==2)return false;
                                else{
                                    System.out.println(PrintStatements.INPUT_ERROR);
                                }
                            }
                        }
                        else break;
                    }
                    categoryDatabase.categories.get(index-1).getSubCategory().add(subCategoryName);
                    return true;
                case 3:
                    return false;
                default :
                    System.out.println(PrintStatements.INPUT_ERROR);
            }
        }
    }
    private static void checkStock(){
        if(productsDatabase.products.size() == 0) {
            System.out.println(PrintStatements.NO_PRODUCTS_AVAILABLE);
            return;
        }
        System.out.println(PrintStatements.CHECK_STOCK_MENU);
        int option = Input.getInteger();
        if(option == 1){
            AdminDriver.printCategory();
            int categoryIndex = AdminDriver.getCategory();
            if(categoryIndex == -1) return;
            AdminDriver.printSubCategory(categoryIndex);
            int subCategoryIndex = AdminDriver.getSubCategory(categoryIndex);
            if(subCategoryIndex == -1) return;
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
    }
    public static String printByCategory() {
        AdminDriver.printCategory();
        int categoryIndex = AdminDriver.getCategory();
        if(categoryIndex == -1) return "";
        AdminDriver.printSubCategory(categoryIndex);
        int subCategoryIndex = AdminDriver.getSubCategory(categoryIndex);
        if(subCategoryIndex == -1) return "";
        return categoryDatabase.categories.get(categoryIndex - 1).getSubCategory().get(subCategoryIndex - 1);
    }
    private static boolean removeProduct(){
        System.out.println(PrintStatements.ENTER_PRODUCT_ID);
        int productId = Input.getInteger();
        while(true) {
            System.out.println(PrintStatements.CONFIRM_PRODUCT);
            int option = Input.getInteger();
            if(option == 1)break;
            else if(option == 2)return false;
            else System.out.println(PrintStatements.INPUT_ERROR);
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
                System.out.println(PrintStatements.NO_SUCH_THING);
                System.out.println(PrintStatements.TRY_AGAIN);
                int option = Input.getInteger();
                if (option != 1) {
                    if (option == 2) return -1;
                    else {
                        System.out.println(PrintStatements.INPUT_ERROR);
                        System.out.println(PrintStatements.JUST_TRY_AGAIN);
                    }
                }
            } else {
                if (subCategoryIndex != 0) break;
                System.out.println(PrintStatements.INPUT_ERROR);
                System.out.println(PrintStatements.JUST_TRY_AGAIN);
            }
        }
        return subCategoryIndex;
    }
    public static int getCategory(){
        int categoryIndex;
        while(true){
            categoryIndex = Input.getInteger();
            if(categoryIndex>categoryDatabase.categories.size() && categoryIndex!=0){
                System.out.println(PrintStatements.NO_SUCH_THING);
                System.out.println(PrintStatements.TRY_AGAIN);
                int option =Input.getInteger();
                switch (option) {
                    case 1:
                        break;
                    case 2:
                        return -1;
                    default:
                        System.out.println(PrintStatements.INPUT_ERROR);
                        System.out.println(PrintStatements.JUST_TRY_AGAIN);
                        break;
                }
            }
            else{
                if(categoryIndex!=0)break;
                System.out.println(PrintStatements.INPUT_ERROR);
                System.out.println(PrintStatements.JUST_TRY_AGAIN);
            }
        }
        return categoryIndex;
    }
    private static void printCategory(){
        for(int c =0;c<categoryDatabase.categories.size();c++){
            System.out.println(c+1+"."+categoryDatabase.categories.get(c).getCategoryName());
        }
    }
    private static void printSubCategory(int categoryIndex){
        for(int i = 0;i<categoryDatabase.categories.get(categoryIndex-1).getSubCategory().size();i++){
            System.out.println(i+1+"."+categoryDatabase.categories.get(categoryIndex-1).getSubCategory().get(i));
        }
    }
    private static void printStockByCategory(String categoryName){
        int count = 1;
        System.out.println(PrintStatements.CHECK_STOCK_PRODUCT);
        for(int i=0;i<productsDatabase.products.size();i++){
            if(productsDatabase.products.get(i).getSubCategoryName().equals(categoryName)){
                System.out.print(count+".");
                System.out.printf("%-13s %-50s %-7s",productsDatabase.products.get(i).getProductId(),productsDatabase.products.get(i).getProductName(),productsDatabase.products.get(i).getQuantity());
                System.out.println();
            }
        }
    }
    private static void printStockByProductId(int productId){
        System.out.println(PrintStatements.CHECK_STOCK_PRODUCT);
        for(int i=0;i<productsDatabase.products.size();i++){
            if(productsDatabase.products.get(i).getProductId() == productId){
                System.out.printf("%-13s %-50s %-7s",productsDatabase.products.get(i).getProductId(),productsDatabase.products.get(i).getProductName(),productsDatabase.products.get(i).getQuantity());
                System.out.println();
                break;
            }
        }
    }
    private static boolean addPincodes(){
       if(pincodesDb.getPincodes().size()>0) System.out.println(PrintStatements.PREVIOUS_PINCODES);
       for(int i = 0; i< pincodesDb.getPincodes().size(); i++){
            System.out.println(i+1+"."+ pincodesDb.getPincodes().get(i));
        }
        System.out.println(PrintStatements.ENTER_HOW_MANY);
        int total = Input.getInteger();
        for(int i = 0;i<total;i++){
            pincodesDb.addPincode(Validation.getPincode());
        }
        return total>0;
    }
    private static void pincodes(){
        System.out.println(PrintStatements.PINCODE_MENU);
        int option = Input.getInteger();
        if(option == 1){
            if(AdminDriver.addPincodes()) System.out.println(PrintStatements.UPDATE_SUCCESSFUL);
            else System.out.println(PrintStatements.UPDATE_FAILED);
        }
        else if(option == 2 ){
            System.out.println(PrintStatements.ENTER_PINCODE);
            int removePin = Input.getInteger();
            for(int i = 0; i< pincodesDb.getPincodes().size(); i++){
                if(removePin == pincodesDb.getPincodes().get(i)){
                    pincodesDb.getPincodes().remove(i);
                    System.out.println(PrintStatements.UPDATE_SUCCESSFUL);
                    return;
                }
            }
            System.out.println(PrintStatements.NO_SUCH_THING);
        }
        else if(option == 3) return;
        else{
            System.out.println(PrintStatements.INPUT_ERROR);
        }
    }
    private static void help(){
        while(true) {
            System.out.println(PrintStatements.HELP_MENU);
            switch(Input.getInteger()){
                case 1:
                    System.out.println(PrintStatements.ENTER_TEXT);
                    if(help.addHelp(Input.getString())) System.out.println(PrintStatements.UPDATE_SUCCESSFUL);
                    else System.out.println(PrintStatements.UPDATE_FAILED);
                    break;
                case 2:
                    System.out.println(PrintStatements.SELECT_FROM_OPTION);
                    help.viewHelps();
                    if(help.removeHelp((Input.getInteger()))) System.out.println(PrintStatements.UPDATE_SUCCESSFUL);
                    else System.out.println(PrintStatements.UPDATE_FAILED);
                    break;
                case 3:
                    help.viewHelps();
                    break;
                case 4:
                    if(help.getUserQuestions().size() == 0){
                        System.out.println(PrintStatements.NOTHING_TO_SHOW);
                        break;
                    }
                    help.viewUserQuestions();
                    System.out.println(PrintStatements.SELECT_FROM_OPTION);
                    int index = Validation.getPositiveInt();
                    if(index-1>= help.getUserQuestions().size()){
                        System.out.println(PrintStatements.INPUT_ERROR);
                        return;
                    }
                    System.out.println(PrintStatements.HELP_RESPONSE);
                    switch(Input.getInteger()){
                        case 1:
                            System.out.println(PrintStatements.ENTER_TEXT);
                            String answer = Validation.getNonNullString();
                            help.addHelp(help.getUserQuestions().get(index-1)+"\n"+answer);
                            help.getUserQuestions().remove(index-1);
                            break;
                        case 2:
                            help.getUserQuestions().remove(index-1);
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println(PrintStatements.INPUT_ERROR);
                    }
                    break;
                case 5:
                    return;
                default :
                    System.out.println(PrintStatements.INPUT_ERROR);
                    break;
            }
        }
    }
    private static boolean removeCategory(){
        int modificationCount = 0;
        while(true) {
            System.out.println(PrintStatements.REMOVE_CATEGORY_MENU);
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
                    System.out.println(PrintStatements.INPUT_ERROR);
                    break;
            }
        }
    }
    private static void removeSubCategory(){
        AdminDriver.printCategory();
        int categoryIndex = AdminDriver.getCategory();
        AdminDriver.printSubCategory(categoryIndex);
        int subCategoryIndex = AdminDriver.getSubCategory(categoryIndex);
        AdminDriver.removeByProductName(categoryDatabase.categories.get(categoryIndex-1).getSubCategory().get(subCategoryIndex-1));
        categoryDatabase.categories.get(categoryIndex-1).getSubCategory().remove(subCategoryIndex-1);
    }
    private static void removeMainCategory(){
        AdminDriver.printCategory();
        int categoryIndex = AdminDriver.getCategory();
        AdminDriver.removeByProductName(categoryDatabase.categories.get(categoryIndex-1).getCategoryName());
        categoryDatabase.categories.remove(categoryIndex-1);
    }
    private static void removeByProductName(String productName){
        for(int i=productsDatabase.products.size()-1;i>=0;i--){
            for(int j=0;j<productsDatabase.products.get(i).getTags().size();j++){
                if(productsDatabase.products.get(i).getTags().get(j).equals(productName)){
                    productsDatabase.products.remove(i);
                    break;
                }
            }
        }
    }
    private static void announcement(){
        while(true) {
            System.out.println(PrintStatements.ANNOUNCEMENT_MENU);
            switch(Input.getInteger()){
                case 1:
                    System.out.println(PrintStatements.ENTER_TEXT);
                    if(announcement.addAnnouncement(Input.getString())) System.out.println(PrintStatements.UPDATE_SUCCESSFUL);
                    else System.out.println(PrintStatements.UPDATE_FAILED);
                    break;
                case 2:
                    System.out.println(PrintStatements.SELECT_FROM_OPTION);
                    announcement.viewAnnouncements();
                    if(announcement.removeAnnouncement((Input.getInteger()))) System.out.println(PrintStatements.UPDATE_SUCCESSFUL);
                    else System.out.println(PrintStatements.UPDATE_FAILED);
                    break;
                case 3:
                    announcement.viewAnnouncements();
                    break;
                case 4: return;
                default :
                    System.out.println(PrintStatements.INPUT_ERROR);
                    break;
            }
        }

    }
    private static void printAllProducts(){
        System.out.println(PrintStatements.CHECK_STOCK_PRODUCT);
        for(Product product : productsDatabase.products){
            System.out.printf( "%-13s %-50s %-7s",product.getProductId(),product.getProductName(),product.getQuantity());
            System.out.println();
        }
        System.out.println();
    }
    private static void checkOrders(){
        if(order.getOrders().size() == 0) {
            System.out.println(PrintStatements.NOTHING_TO_SHOW);
            return;
        }
        order.printOrders();
            System.out.println(PrintStatements.ENTER_ORDER_ID);
            int orderId = Input.getInteger();
            if (order.getOrders().containsKey(orderId)) {
                order.printSpecificUserOrder(orderId);
            } else {
                System.out.println(PrintStatements.ORDER_ID_NOT_PRESENT);
            }
    }
    private static void deliveryStatus() {
        if(order.getReadyToDeliver().size() == 0){
            System.out.println(PrintStatements.NOTHING_TO_SHOW);
            return;
        }
        order.readyOrders();
        System.out.println(PrintStatements.ENTER_ORDER_ID);
        int orderId = Input.getInteger();
        if (order.getReadyToDeliver().containsKey(orderId)) {
            order.setReadyToDeliver(orderId);
        } else {
            System.out.println(PrintStatements.ORDER_ID_NOT_PRESENT);
        }
    }
    private static void checkHistory(){
        if(order.getHistory().size() == 0){ System.out.println(PrintStatements.NOTHING_TO_SHOW);return;}
        order.history();
    }
}
