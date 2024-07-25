import java.util.ArrayList;
import java.util.List;

/**
* This class handles and updates the user interface.
* It processes input recieved by class AppGUI.
* It allows the user to interact with the program to browse items, list items, and place orders.
*/

public class App {
    private ArrayList<User> users; //An ArrayList of Users who can be logged in.
    protected User loggedInUser; //The current User who is logged into the program.
    private ArrayList<Listing> listings; //An ArrayList of Listings which have been created by businesses.
    private ArrayList<Ticket> tickets; //An ArrayList of Users who can be logged in.
    private Cart cart; //A cart object which will have FoodItems added to it.

    /**
     * Constructs an App object which creates a new scanner, Users ArrayList, listings ArrayList, orders ArrayList, and Cart object.
     */
    public App() {
        users = new ArrayList<>();
        listings = new ArrayList<>();
        tickets = new ArrayList<>();
        cart = new Cart();
    }

    /**
     * Manages data by either loading or saving it.
     *
     * @param load If true, the data will be loaded; otherwise, it will be saved.
     * @author Muhammad Daud
     */
    public void manageData(boolean load) {
        ArrayList<?>[] dataArrays = new ArrayList<?>[] {users, listings, tickets};
        String[] fileNames = new String[] {"savedUsers.dat", "savedListings.dat", "savedTickets.dat"};

        FileManagement getFiles = new FileManagement(dataArrays, fileNames);

        if (load) { // Load data
            ArrayList<?>[] loadedData = getFiles.loadData();
            users = (ArrayList<User>) loadedData[0];
            listings = (ArrayList<Listing>) loadedData[1];
            tickets = (ArrayList<Ticket>) loadedData[2];
        }
        else { // Save data
            getFiles.saveData();
        }
    }

    /**
    * Is called from AppGui and registers a new user in the application.
    * Creates a new User object with the provided username, password, and business status.
    * Adds the user to the list of users in the application.
    * Calls the manageData() method to update the data storage.
    * @param isBusiness a boolean indicating whether the user is a business user or not
    * @param username the username of the user
    * @param password the password of the user
    * @author Mustafa Mian
    */
    public void registerUser(boolean isBusiness, String username, String password) {
        User user = new User(username, password, isBusiness);
        users.add(user);
        manageData(false);
        System.out.println("User registered successfully!");
    }

   /**
    * Takes input username and password and checks if they match with usermane and password of User in users ArrayList.
    * Loops through the users ArrayList and sets loggedInUser to matching User.
    * @author Mustafa Mian
    * @return true if login is succesfully completed, false if no User matches login details
    */
    public boolean loginUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedInUser = user;
                System.out.println("User logged in successfully!");
                return true;
            }
        }
        System.out.println("Invalid username or password!");
        return false;
    }

    /**
    * Determines the menu type based on the logged-in user.
    * This method performs the following tasks:
    * 1. Prints a welcome message with the username of the logged-in user.
    * Checks if the logged-in user is a business user.
    * @return true if the logged-in user is a business user, false otherwise
    */
    boolean determineMenu() {
        System.out.println("Welcome, " + loggedInUser.getUsername() + "!");
        if (loggedInUser.isBusiness() == true) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Called from AppGUI class
     * A method only accessable by Business Users. Allows businesses to create a new FoodItem/Listing.
     * @param itemName the name of the item
     * @param itemPrice the price of the item
     * @param expirationDate the number of days until the item expires
     * @param type the category of food of the item
     * @author Mustafa Mian
     */
    protected void addListing(String itemName, double itemPrice, int expirationDate, String type) {
        FoodItem item = new FoodItem(itemName, itemPrice, type);
        Listing listing = new Listing(item, loggedInUser.getUsername(), expirationDate, type);
        listings.add(listing);
        manageData(false);
        System.out.println("Listing added successfully!");
    }

    /**
     * Takes itemName String and calls getListingByName() method to determine if there is a listing with same name
     * Adds selected listing to cart items ArrayList
     * @author Mustafa Mian
     */
    protected void addItemToCart(String itemName) {
        Listing selectedListing = getListingByName(itemName);
        if (selectedListing != null) {
            cart.addItem(selectedListing.getItem());
        }
    }

    /**
     * Takes name of food item and loops through listings List to see if the String matches a Listing name
     * @author Mustafa Mian
     * @return listing based on name String.
     */
    private Listing getListingByName(String foodItemName) {
        for (Listing listing : listings) {
            if (listing.getName().equals(foodItemName)) {
                return listing;
            }
        }
        return null;
    }

    /**
     * Creates a new Ticket object using the user's cart.
     * Adds it to the tickets array.
     * The array is then saved using the manageData method.
     *
     * @return The ticket ID for the newly created ticket.
     * @author Muhammad Daud
     */
    protected String placeOrder() {
        Ticket ticket = new Ticket(cart);
        String ticketId = ticket.getTicketId();
        tickets.add(ticket);
        cart.clearCart();
        System.out.println("Order placed successfully!");

        manageData(false);
        return ticketId;
    }

    /**
     * Retrieves the Cart object associated with the given ticket ID.
     *
     * @param ticketId The ID of the ticket to retrieve the Cart for.
     * @return The Cart object associated with the ticket ID, or null if not found.
     * @author Muhammad Daud
     */
    protected Cart getCartById(String ticketId) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId().equals(ticketId)) {
                return ticket.getCart();
            }
        }
        return null;
    }

    /**
     * Sets loggedInUser to null, which retuns User to main menu.
     * @author Mustafa Mian
     */
    protected void logoutUser() {
        loggedInUser = null;
        System.out.println("User logged out successfully!");
    }

    /**
    * Retrieves the list of listings.
    * @return the list of listings
    */
    public List<Listing> getListings() {
        return listings;
    }

    /**
     * Retrieves the logged-in user.
     * @return the logged-in user
     */
    public User getLoggedInUser(){
        return loggedInUser;
    }

    /**
     * Retrieves the list of items in the cart.
     * @return the list of items in the cart
     */
    public List<FoodItem> getCartItems() {
        return cart.getItems();
    }
}
