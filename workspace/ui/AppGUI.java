import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * The main GUI class for the application.
 * Extends the JFrame class to create a windowed application.
 * Provides functionality for user registration, login, browsing items, adding items to cart,
 * viewing cart, placing orders, and more.
 */

public class AppGUI extends JFrame {
    private JButton businessButton; // Button to register as business
    private JButton customerButton; // Button to register as customer
    private JButton loginButton; // Button to allow Users to login
    private JButton browseButton; // New button for browsing items
    private JButton addToCartButton; // New button for adding items to cart
    private JButton viewCartButton; // New button for viewing cart
    private JButton placeOrderButton; // New button for placing orders
    private JCheckBox[] itemCheckBoxes; // Array of checkboxes for items
    private JPanel listingsPanel;
    private JPanel viewContainer;
    private JButton logoutButton;
    App app;

    /**
     * Constructs an instance of the AppGUI class.
     * Initializes the GUI components, sets the title and layout of the frame.
     * Also sets the default close operation, packs the frame, and centers it on the
     * screen.
     * 
     * @author Mustafa Mian
     */
    public AppGUI() {
        setTitle("FeedForward");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Initializes the GUI components.
     * Creates the buttons and sets their labels.
     * Creates an instance of the App class.
     * Retrieves the users ArrayList stored in the savedUsers.dat file.
     * Sets the layout manager for the main content pane and shows the main menu.
     * 
     * @author Mustafa Mian
     */
    private void initComponents() {
        businessButton = new JButton("Register as a Business");
        customerButton = new JButton("Register as a Customer");
        loginButton = new JButton("Login");
        browseButton = new JButton("Browse Items"); // Initialize browse button
        addToCartButton = new JButton("Add to Cart"); // Initialize add to cart button
        viewCartButton = new JButton("View Cart"); // Initialize browse button
        placeOrderButton = new JButton("Place Order"); // Initialize place order button

        app = new App(); // Create instance off App Class
        app.manageData(true); // Load all data

        // Set layout manager for the main content pane
        getContentPane().setLayout(new FlowLayout());

        showMainMenu();
    }

    /**
     * Displays the main menu.
     * Adds buttons to the main content pane.
     * Registers action listeners for the buttons.
     * Handles button actions, such as business or customer registration, and login.
     * 
     * @author Mustafa Mian
     */
    void showMainMenu() {
        // Add buttons to the main content pane
        getContentPane().add(businessButton);
        getContentPane().add(customerButton);
        getContentPane().add(loginButton);

        businessButton.addActionListener(new ActionListener() {
            /**
             * Action performed when the business button is clicked.
             * Opens a dialog to get the business registration details.
             * Registers the business user by calling the registerUser method of the app
             * instance.
             * 
             * @author Mustafa Mian
             * @param e The action event.
             */
            public void actionPerformed(ActionEvent e) {
                // Open a dialog to get the business registration details
                JTextField usernameField = new JTextField();
                JPasswordField passwordField = new JPasswordField();

                JPanel panel = new JPanel(new GridLayout(2, 2));
                panel.add(new JLabel("Username:"));
                panel.add(usernameField);
                panel.add(new JLabel("Password:"));
                panel.add(passwordField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Business Registration",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());

                    // Perform business registration logic here
                    app.registerUser(true, username, password);
                }
            }
        });

        customerButton.addActionListener(new ActionListener() {
            /**
             * Action performed when the customer button is clicked.
             * Opens a dialog to get the customer registration details.
             * Registers the customer user by calling the registerUser method of the app
             * instance.
             * 
             * @author Mustafa Mian
             * @param e The action event.
             */
            public void actionPerformed(ActionEvent e) {
                // Open a dialog to get the business registration details
                JTextField usernameField = new JTextField();
                JPasswordField passwordField = new JPasswordField();

                JPanel panel = new JPanel(new GridLayout(2, 2));
                panel.add(new JLabel("Username:"));
                panel.add(usernameField);
                panel.add(new JLabel("Password:"));
                panel.add(passwordField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Customer Registration",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());

                    // Performs customer registration logic here by calling method in App class and
                    // passing user input as arguements
                    app.registerUser(false, username, password);
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            /**
             * Action performed when the login button is clicked.
             * Opens a dialog to get the login details.
             * Calls the login method on the app instance.
             * Shows the user menu if login is successful, else displays an error message.
             * 
             * @author Mustafa Mian
             * @param e The action event.
             */
            public void actionPerformed(ActionEvent e) {
                // Open a dialog to get the login details
                JTextField usernameField = new JTextField();
                JPasswordField passwordField = new JPasswordField();

                JPanel panel = new JPanel(new GridLayout(2, 2));
                panel.add(new JLabel("Username:"));
                panel.add(usernameField);
                panel.add(new JLabel("Password:"));
                panel.add(passwordField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Login",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());

                    // Call the login method on the app instance
                    if (app.loginUser(username, password) == true) {
                        showUserMenu();
                    } else if (app.loginUser(username, password) == false) {
                        JOptionPane.showMessageDialog(null, "Invalid username or password!", "Login Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    /**
     * Shows the user menu based on the user type.
     * Determines whether to display the business or customer menu.
     * 
     * @author Mustafa Mian
     */
    void showUserMenu() {
        if (app.determineMenu() == true) {
            showBusinessMenu();
        } else {
            showCustomerMenu();
        }
    }

    /**
     * Shows the business menu.
     * Removes existing components from the content pane.
     * Adds components specific to the business menu, such as text fields and
     * buttons.
     * Handles button actions, such as adding a listing or logging out.
     * 
     * @author Mustafa Mian
     */
    void showBusinessMenu() {
        getContentPane().removeAll();
        getContentPane().repaint();
        getContentPane().revalidate();

        JTextField titleField = new JTextField(20);
        JTextField priceField = new JTextField(20);
        JTextField dateField = new JTextField(20);
        JTextField ticketIdField = new JTextField(20);
        String[] categories = { "Bakery", "Canned Food", "Dairy", "Meat", "Produce", "Other" };
        JComboBox typeField = new JComboBox(categories);

        JButton addButton = new JButton("Add Listing");
        JButton readTicketButton = new JButton("Read Ticket");
        JButton backButton = new JButton("Logout");

        addButton.addActionListener(new ActionListener() {
            /**
             * Action performed when the add listing button is clicked.
             * Opens a dialog to get the listing details.
             * Calls the addListing method on the app instance.
             * Displays a success message if the listing is added successfully.
             * 
             * @author Mustafa Mian
             * @param e The action event.
             */
            public void actionPerformed(ActionEvent e) {
                JPanel addPanel = new JPanel();
                addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
                addPanel.add(new JLabel("Name:"));
                addPanel.add(titleField);
                addPanel.add(new JLabel("Price (Per Unit):"));
                addPanel.add(priceField);
                addPanel.add(new JLabel("Days Until Expiration:"));
                addPanel.add(dateField);
                addPanel.add(new JLabel("Item's Category"));
                addPanel.add(typeField);

                int result = JOptionPane.showConfirmDialog(null, addPanel, "Add Listing",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String title = titleField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    int days = Integer.parseInt(dateField.getText());
                    String type = typeField.getSelectedItem().toString();

                    app.addListing(title, price, days, type);
                    JOptionPane.showMessageDialog(null, "Listing added successfully!");
                }
            }
        });

        readTicketButton.addActionListener(new ActionListener() {
            /**
             * Action performed when the read ticket button is clicked.
             * Opens a dialog to get the input of ticket id.
             * Calls the getCartById function from the App object.
             * Displays food items if the cart matching the ticket is found (WIP).
             * 
             * @author Muhammad Daud
             * @param e The action event.
             */
            public void actionPerformed(ActionEvent e) {
                JPanel addPanel = new JPanel();
                addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
                addPanel.add(new JLabel("Ticket ID:"));
                addPanel.add(ticketIdField);

                int result = JOptionPane.showConfirmDialog(null, addPanel, "Read Ticket",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String ticketId = ticketIdField.getText();

                    Cart cart = app.getCartById(ticketId);

                    if (cart != null) {
                        List<FoodItem> items = cart.getItems();
                        StringBuilder sb = new StringBuilder();
                        sb.append("Cart found for ticket ID: " + ticketId + "\n");
                        sb.append("Food items in the cart:\n");

                        for (FoodItem item : items) {
                            sb.append(item.getName()).append("\n");
                        }

                        JOptionPane.showMessageDialog(null, sb.toString(), "Cart Items",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "No cart found for ticket ID: " + ticketId);
                    }
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            /**
             * Action performed when the logout button is clicked.
             * Removes all components from the content pane.
             * Calls the logoutUser method on the app instance.
             * Shows the main menu.
             * 
             * @author Mustafa Mian
             * @param e The action event.
             */
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                getContentPane().repaint();
                getContentPane().revalidate();
                app.logoutUser();
                showMainMenu();
            }
        });

        getContentPane().add(addButton);
        getContentPane().add(readTicketButton);
        getContentPane().add(backButton);

        getContentPane().revalidate();
    }

    /**
     * Shows the customer menu.
     * Removes existing components from the content pane.
     * Adds components specific to the customer menu, such as checkboxes and
     * buttons.
     * Handles button actions, such as browsing items, adding to cart, and logging
     * out.
     * 
     * @author Mustafa Mian
     */
    void showCustomerMenu() {
        getContentPane().removeAll();
        getContentPane().repaint();
        getContentPane().revalidate();

        browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showItemListings(); // Show the available items to browse
            }
        });

        addToCartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addSelectedItemsToCart(); // Add selected items to the cart
            }
        });

        viewCartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewCart();
            }
        });

        // places the user's order - Muhammad Daud
        placeOrderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                placeOrder();
            }
        });

        //logs out customer - Hassan Omran
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                getContentPane().repaint();
                getContentPane().revalidate();
                app.logoutUser();
                showMainMenu();
            }
        });

        getContentPane().add(browseButton);
        getContentPane().add(viewCartButton);
        getContentPane().add(placeOrderButton);
        getContentPane().add(logoutButton);
        getContentPane().revalidate();

    }

    /**
     * Displays the items in the user's cart.
     * Retrieves the cart items from the App instance.
     * If the cart is empty, displays a message indicating that the cart is empty.
     * Otherwise, builds a message with the names of the cart items and displays it
     * in a dialog.
     * 
     * @author Mustafa Mian
     */
    void viewCart() {
        List<FoodItem> cartItems = app.getCartItems();
        if (cartItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your cart is empty.", "Cart", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder cartMessage = new StringBuilder();
        cartMessage.append("Cart Items:\n");
        for (FoodItem item : cartItems) {
            cartMessage.append(item.getName()).append("\n");
        }

        JOptionPane.showMessageDialog(this, cartMessage.toString(), "Cart", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Places an order given the user's cart.
     * Creates a unique ticket ID associated with the cart and saves it.
     * If the cart is empty, displays a message indicating that the cart is empty.
     * 
     * @author Muhammad Daud
     */
    void placeOrder() {
        List<FoodItem> cartItems = app.getCartItems();
        if (cartItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty!\nPlease add items to the cart before placing an order.",
                    "Cart", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.getRootFrame().dispose();
            return;
        }

        String ticketId = app.placeOrder(); // place the order and get ticket id

        // copy ticket id to clipboard
        StringSelection selection = new StringSelection(ticketId);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);

        String message = "Order placed successfully!\nYour ticket ID: " + ticketId + "\n\n(copied to your clipboard)";
        JOptionPane.showMessageDialog(this, message, "Cart", JOptionPane.INFORMATION_MESSAGE);
        JOptionPane.getRootFrame().dispose();
    }

    /**
     * Displays a list of item listings in the application's graphical user interface.
     * Shows a dialog box instructing the user to resize the window to view all listings and buttons.
     * Disposes of the root frame of the JOptionPane.
     * Retrieves the item listings from the App instance.
     * Creates user interface components such as checkboxes, buttons, and a combo box.
     * Adds action listeners to the back button and filter button and constructs a layout and containers for displaying the listings.
     * Iterates over the item listings and creates a checkbox for each listing with relevant information.
     * Adds the "Add to Cart" button to the content pane.
     * @author Mustafa Mian
     */
    void showItemListings() {
        getContentPane().removeAll();
        getContentPane().setSize(400, 500);
        getContentPane().repaint();
        getContentPane().revalidate();

        JOptionPane.showMessageDialog(null, "Please resize the window to view all listings and buttons!");
        JOptionPane.getRootFrame().dispose();

        CardLayout cardLayout;
        List<Listing> listings = app.getListings();
        int itemCount = listings.size();
        itemCheckBoxes = new JCheckBox[itemCount];
        JButton backButton = new JButton("Back");
        JButton filterButton = new JButton("Filter");
        String[] categories = { "All", "Bakery", "Canned Food", "Dairy", "Meat", "Produce", "Other" };
        JComboBox<String> categoryComboBox = new JComboBox<>(categories);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                getContentPane().repaint();
                getContentPane().revalidate();
                showCustomerMenu();
            }
        });

        getContentPane().add(backButton);

        listingsPanel = new JPanel();
        listingsPanel.setLayout(new BoxLayout(listingsPanel, BoxLayout.Y_AXIS));
        JPanel filterPanel = new JPanel();
        filterPanel.add(new JLabel("Category:"));
        filterPanel.add(categoryComboBox);
        getContentPane().add(filterPanel);
        getContentPane().add(filterButton);

        cardLayout = new CardLayout();
        viewContainer = new JPanel();
        viewContainer.setLayout(cardLayout);

        // Loops through listings List and creates checkbox for each listing
        // Checkboxes display name, price, seller, category, and expiry date
        for (int i = 0; i < itemCount; i++) {
            Listing listing = listings.get(i);
            JCheckBox checkBox = new JCheckBox(
                    "Item: " + listing.getItem().getName() +
                            ", Price: $" + listing.getItem().getPrice() +
                            ", Seller: " + listing.getSeller() +
                            ", Category: " + listing.getType() +
                            ", Expiry Date: " + listing.getExpirationDate());
            itemCheckBoxes[i] = checkBox;
            listingsPanel.add(checkBox);
        }

        // Calls filterListings method - Hassan Omran
        filterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = categoryComboBox.getSelectedItem().toString();
                filterListings(selectedCategory);
            }
        });

        viewContainer.add(listingsPanel, "listingsPanel");
        listingsPanel.add(Box.createVerticalStrut(10)); // Add vertical spacing between checkboxes and button

        JScrollPane scrollPane = new JScrollPane(listingsPanel);
        getContentPane().add(scrollPane);
        getContentPane().revalidate();
        pack();

        getContentPane().add(addToCartButton);
        getContentPane().revalidate();
    }

    /**
     * Filters and displays the listings based on the specified category.
     * This method clears the listingsPanel and dynamically populates it with
     * checkboxes representing the filtered listings.
     * Each checkbox displays information about a listing, including its associated
     * item's name, price, seller, category, and expiry date.
     * The filtered listings are retrieved from the app object, which is an instance
     * of the application containing the listings data.
     * The checkboxes are added to the listingsPanel and the "Add to Cart" button is
     * displayed.
     * Finally, the viewContainer is updated to show the listingsPanel.
     * 
     * @param category The category to filter the listings by. Use "All" to show all
     *                 listings.
     * @author Hassan Omran
     */
    void filterListings(String category) {
        CardLayout cardLayout = (CardLayout) viewContainer.getLayout();
        listingsPanel.removeAll(); // Clear the listingsPanel

        if (category.equals("All")) {
            // Show all listings
            for (int i = 0; i < itemCheckBoxes.length; i++) {
                Listing listing = app.getListings().get(i);
                JCheckBox checkBox = new JCheckBox(
                        "Item: " + listing.getItem().getName() +
                                ", Price: $" + listing.getItem().getPrice() +
                                ", Seller: " + listing.getSeller() +
                                ", Category: " + listing.getType() +
                                ", Expiry Date: " + listing.getExpirationDate());
                itemCheckBoxes[i] = checkBox;
                listingsPanel.add(checkBox);
                getContentPane().add(addToCartButton);
            }
        } else {
            // Show only listings of the selected category
            for (int i = 0; i < itemCheckBoxes.length; i++) {
                Listing listing = app.getListings().get(i);
                if (listing.getType().equals(category)) {
                    JCheckBox checkBox = new JCheckBox(
                            "Item: " + listing.getItem().getName() +
                                    ", Price: $" + listing.getItem().getPrice() +
                                    ", Seller: " + listing.getSeller() +
                                    ", Category: " + listing.getType() +
                                    ", Expiry Date: " + listing.getExpirationDate());
                    itemCheckBoxes[i] = checkBox;
                    listingsPanel.add(checkBox);
                    getContentPane().add(addToCartButton);
                }
            }
        }

        cardLayout.show(viewContainer, "listingsPanel"); // Show the listingsPanel in the viewContainer
        getContentPane().revalidate();
    }

    /**
     * Retrieves the category from the text of the provided checkbox.
     * This method parses the text of the checkbox to extract the category
     * information.
     * It assumes that the checkbox text follows a specific format, where the
     * category is enclosed between "Category: " and ", Expiry Date:".
     * The category text is then extracted and returned.
     * This method relies on the specific format of the checkbox text and may
     * produce unexpected results if the format is not adhered to.
     * It's important to ensure that the checkbox text is generated consistently
     * with the expected format to obtain accurate category extraction.
     * 
     * @param checkBox The checkbox from which to extract the category.
     * @return The category extracted from the checkbox text.
     * @author Hassan Omran
     */

    String getCategoryFromCheckBox(JCheckBox checkBox) {
        String text = checkBox.getText();
        int categoryStartIndex = text.indexOf("Category: ") + 10;
        int categoryEndIndex = text.indexOf(", Expiry Date:");
        String category = text.substring(categoryStartIndex, categoryEndIndex);
        return category.trim();
    }

    /**
     * This method adds the selected items to the shopping cart in the application's
     * user interface.
     * Retrieves the item listings from the List in App instance.
     * Creates a new list to store the selected listings.
     * Iterates over the itemCheckBoxes array and checks if a checkbox is selected.
     * If a checkbox is selected, the corresponding item is added to the shopping
     * cart, and the checkbox is disabled, unchecked, hidden, and set to zero size
     * to remove empty space.
     * Removes the selected listings from the main listings list.
     * Clears the content pane.
     * Calls the showItemListings() method to display the updated list of item
     * listings.
     * Shows a dialog box confirming that the selected items have been added to the
     * cart.
     * 
     * @author Mustafa Mian
     */
    void addSelectedItemsToCart() {
        if (itemCheckBoxes != null) {
            List<Listing> listings = app.getListings();
            List<Listing> selectedListings = new ArrayList<>();
            for (int i = 0; i < itemCheckBoxes.length; i++) {
                JCheckBox checkBox = itemCheckBoxes[i];
                if (checkBox.isSelected()) {
                    // Finds all text between colon and comma and stores it as a String.
                    String tempName = checkBox.getText();
                    int startIndex = tempName.indexOf(": ") + 2;
                    int endIndex = tempName.indexOf(",");
                    String itemName = tempName.substring(startIndex, endIndex);
                    app.addItemToCart(itemName);
                    selectedListings.add(listings.get(i));
                    itemCheckBoxes[i].setEnabled(false); // Disable the checkbox
                    itemCheckBoxes[i].setSelected(false); // Uncheck the checkbox
                    itemCheckBoxes[i].setVisible(false); // Hide the checkbox
                    itemCheckBoxes[i].setPreferredSize(new Dimension(0, 0)); // Set zero size to remove the empty space
                }
            }

            for (Listing listing : selectedListings) {
                listings.remove(listing);
            }

            getContentPane().removeAll();
            showItemListings();
            JOptionPane.showMessageDialog(null, "Selected items added to cart!");
        }
    }

    public void start() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AppGUI().setVisible(true);
            }
        });
    }
}
