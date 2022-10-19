package init;

import superMarket.*;

import java.util.ArrayList;

public class Init {
    private static ProductsDatabase productsDatabase = ProductsDatabase.getInstance();
    public static void initiate() {
        Category fruitsVegetables = new Category("Fruits and Vegetables");
        Category  dairy = new Category("Dairy & Bakery");
        Category staples = new Category("Staples");
        Category beverages = new Category("Beverages");
        Category electronics = new Category("Electronics");

        CategoryDatabase.categories.add(fruitsVegetables);
        CategoryDatabase.categories.add(dairy);
        CategoryDatabase.categories.add(staples);
        CategoryDatabase.categories.add(beverages);
        CategoryDatabase.categories.add(electronics);


        fruitsVegetables.getSubCategory().add("Fresh Fruits");
        productsDatabase.addToDb(new Product("Apple Shimla 1Kg",20,139,10,"Fresh Fruits",new ArrayList<String>(),"From zoho farms", productsDatabase.productId,"Fruits and Vegetables"));
        productsDatabase.addToDb(new Product("Mosambi 1Kg",20,58,0,"Fresh Fruits",new ArrayList<String>(),"From zoho farms", productsDatabase.productId,"Fruits and Vegetables"));
        productsDatabase.addToDb(new Product("Orange Imported 1Kg",20,169,25,"Fresh Fruits",new ArrayList<String>(),"From zoho farms", productsDatabase.productId,"Fruits and Vegetables"));
        productsDatabase.addToDb(new Product("Papaya (Each)(Approx. 800g - 1600g)",20,45,10,"Fresh Fruits",new ArrayList<String>(),"From zoho farms", productsDatabase.productId,"Fruits and Vegetables"));
        fruitsVegetables.getSubCategory().add("Fresh Vegetables");
        productsDatabase.addToDb(new Product("Tomato Country 1Kg",20,45,10,"Fresh Vegetables",new ArrayList<String>(),"From zoho farms", productsDatabase.productId,"Fruits and Vegetables"));
        productsDatabase.addToDb(new Product("Potato 1Kg",20,44,10,"Fresh Vegetables",new ArrayList<String>(),"From zoho farms", productsDatabase.productId,"Fruits and Vegetables"));
        fruitsVegetables.getSubCategory().add("Herbs & Seasoning");

        dairy.getSubCategory().add("Dairy");
        dairy.getSubCategory().add("Baked Cookies");
        dairy.getSubCategory().add("Cheese");
        dairy.getSubCategory().add("Ghee");

        staples.getSubCategory().add("Atta, Flours & Sooji");
        staples.getSubCategory().add("Dals & Pulses");
        staples.getSubCategory().add("Rice & Rice Products");
        staples.getSubCategory().add("Edible Oils");
        staples.getSubCategory().add("Dry Fruits & Nuts");

        beverages.getSubCategory().add("Tea");
        beverages.getSubCategory().add("Coffee");
        beverages.getSubCategory().add("Fruit Juices");
        beverages.getSubCategory().add("Energy & Soft Drinks");

        electronics.getSubCategory().add("Mobiles and Tables");
        electronics.getSubCategory().add("TV & Speakers");

        PincodesDb.addPincode(123456);
        PincodesDb.addPincode(632059);

    }
}
