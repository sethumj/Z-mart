package init;

import superMarket.*;
import utilities.Help;

import java.util.ArrayList;

public class Init {
    private static final ProductsDatabase productsDatabase = ProductsDatabase.getInstance();
    private static final CategoryDatabase categoryDatabase = CategoryDatabase.getInstance();
    private static final Announcement announcement = Announcement.getInstance();
    private static final PincodesDb pincodesDb = PincodesDb.getInstance();
    private static final Help help = Help.getInstance();
    public static void initiate() {
        Category fruitsVegetables = new Category("Fruits and Vegetables");
        Category  dairy = new Category("Dairy & Bakery");
        Category staples = new Category("Staples");
        Category beverages = new Category("Beverages");
        Category electronics = new Category("Electronics");

        categoryDatabase.categories.add(fruitsVegetables);
        categoryDatabase.categories.add(dairy);
        categoryDatabase.categories.add(staples);
        categoryDatabase.categories.add(beverages);
        categoryDatabase.categories.add(electronics);


        fruitsVegetables.getSubCategory().add("Fresh Fruits");
        productsDatabase.addToDb(new Product("Apple Shimla 1Kg",20,139,10,"Fruits and Vegetables","Fresh Fruits",new ArrayList<>(),"From zoho farms", productsDatabase.productId));
        productsDatabase.addToDb(new Product("Mosambi 1Kg",20,58,0,"Fruits and Vegetables","Fresh Fruits",new ArrayList<>(),"From zoho farms", productsDatabase.productId));
        productsDatabase.addToDb(new Product("Orange Imported 1Kg",20,169,25,"Fruits and Vegetables","Fresh Fruits",new ArrayList<>(),"From zoho farms", productsDatabase.productId));
        productsDatabase.addToDb(new Product("Papaya (Each)(Approx. 800g - 1600g)",20,45,10,"Fruits and Vegetables","Fresh Fruits",new ArrayList<>(),"From zoho farms", productsDatabase.productId));
        fruitsVegetables.getSubCategory().add("Fresh Vegetables");
        productsDatabase.addToDb(new Product("Tomato Country 1Kg",20,45,10,"Fruits and Vegetables","Fresh Vegetables",new ArrayList<>(),"From zoho farms", productsDatabase.productId));
        productsDatabase.addToDb(new Product("Potato 1Kg",20,44,10,"Fruits and Vegetables","Fresh Vegetables",new ArrayList<>(),"From zoho farms", productsDatabase.productId));
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

        pincodesDb.addPincode(123456);
        pincodesDb.addPincode(632059);
        pincodesDb.addPincode(600056);

        announcement.addAnnouncement("Diwali sale is LIVE !!");
        announcement.addAnnouncement("Next Big sale will be near to Christmas");
        announcement.addAnnouncement("new Branch near zoho corp will be opened soon !!");

        help.addHelp("""
                How do I look for a particular Product?
                You can search for a product by navigating through the category section or by using search option.
                """);
        help.addHelp("""
                How will you ensure the freshness of products?
                We ensure that all our products are hygienically and carefully handled and maintain them in the correct temperature & packaging.
                """);
        help.addHelp("""
                Can I call and place an order?
                No, currently this service is not available.
                """);
        help.addHelp("""
                How are the fruits and vegetables weighed?
                All fruits and vegetables vary in size and weight. You can choose any size \s
                 weight available on the website. While you shop, we will show an estimated weight and price. At the time of processing, we pack the closest size\s
                 weight and charge you for the actual weight of each item. E.g. If you order 1 kg of apples, we will try to pack exactly 1 kg or the weight closest to 1 kg.\s
                If the actual weight is 987 gm, we will bill you for 987 gm and not 1 kg.
                """);
    }
}
