import java.util.Date;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * The Listing class stores a FoodItem object and adds additional fields based on the seller.
 * 
 * @author Hassan Omran
 */

public class Listing implements Serializable {
    private static int nextId = 1; //The next identification number to be used.
    private int id; //The identification number of the Listing.
    private FoodItem item; //A FoodItem object.
    private String seller; //The name of the business selling the product.
    private Date dateAdded; // The date and time when the listing was added.
    private int expiryDays; // The number of days until the item expires.
    private String type; // The type of the item in this listing

    /**
     * Constructs a Listing object which has a FoodItem associated with it.
     * Gives the Listing a unique ID number which is incremented by 1 for each new listing.
     *
     * @param item       The FoodItem associated with the listing.
     * @param seller     The name of the business selling the product.
     * @param expiryDays The number of days until the item expires.
     * @param type       The type of the item.
     */

    public Listing(FoodItem item, String seller, int expiryDays, String type) {
        this.id = nextId++;
        this.item = item;
        this.seller = seller;
        this.dateAdded = new Date();
        this.expiryDays = expiryDays;
        this.type = type;
    }

    /**
     * Returns the ID number of the Listing.
     *
     * @return The ID number.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the associated FoodItem of the Listing.
     *
     * @return The associated FoodItem.
     */
    public FoodItem getItem() {
        return item;
    }

    /**
     * Returns the name of the associated FoodItem.
     *
     * @return The name of the FoodItem.
     */
    public String getName(){
        return item.getName();
    }

    /**
     * Returns the name of the seller.
     *
     * @return The name of the seller.
     */
    public String getSeller() {
        return seller;
    }

    /**
     * Returns the date and time when the listing was added.
     *
     * @return The date and time of the listing.
     */
    public String getDateAdded(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(dateAdded);
    }

    /**
     * Returns the expiration date of the item.
     *
     * @return The expiration date.
     */
    public String getExpirationDate() {
        Date expirationDate = calculateExpirationDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(expirationDate);
    }

    /**
     * Calculates and returns the expiration date based on the date added and the expiry days.
     *
     * @return The expiration date.
     */
    private Date calculateExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateAdded);
        calendar.add(Calendar.DAY_OF_YEAR, expiryDays);
        return calendar.getTime();
    }

    /**
     * Returns the type of the item.
     *
     * @return The type of the item.
     */
    public String getType(){
        return type;
    }
}
