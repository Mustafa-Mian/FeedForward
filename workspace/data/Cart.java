import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Cart class represents a "shopping cart" that holds an ArrayList of FoodItem objects.
 * It allows adding, removing, retrieving items, and clearing the cart.
 *
 * @author Muhammad Daud
 */
class Cart implements Serializable {
    private ArrayList<FoodItem> items;

    /**
     * Constructs an empty Cart object.
     */
    public Cart() {
        items = new ArrayList<>(); // initialize the ArrayList
    }

    /**
     * Adds a FoodItem to the cart.
     *
     * @param item The FoodItem to be added.
     */
    public void addItem(FoodItem item) {
        items.add(item);
    }

    /**
     * Removes a FoodItem from the cart.
     *
     * @param item The FoodItem to be removed.
     */
    public void removeItem(FoodItem item) {
        items.remove(item);
    }

    /**
     * Retrieves the ArrayList of FoodItems in the cart.
     *
     * @return The ArrayList of FoodItem objects in the cart.
     */
    public ArrayList<FoodItem> getItems() {
        return items;
    }

    /**
     * Clears the cart by removing all items.
     */
    public void clearCart() {
        items.clear(); // remove from FoodItems array
    }
}
