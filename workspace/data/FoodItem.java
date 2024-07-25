import java.io.Serializable;

/**
 * This class represents a food item which is used by businesses to create Listings.
 * 
 * @author Hassan Omran
 */
class FoodItem implements Serializable {
    private String name; //The name of the item.
    private double price; //The price of the item
    private String type; //The type of the item (e.g., Bakery, Canned food, etc.)

    /**
     * Constructs a FoodItem object with the specified name, price, and type.
     *
     * @param name  The name of the FoodItem.
     * @param price The price of the FoodItem.
     * @param type  The type of the FoodItem.
     */
    public FoodItem(String name, double price, String type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    /**
     * Returns the name of the FoodItem.
     *
     * @return The name of the FoodItem.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the price of the FoodItem.
     *
     * @return The price of the FoodItem.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the type of the FoodItem.
     *
     * @return The type of the FoodItem.
     */
    public String getType(){
        return type;
    }
}