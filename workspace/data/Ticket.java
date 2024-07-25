import java.io.Serializable;
import java.util.Random;

/**
 * The Ticket class represents a ticket associated with a Cart object.
 * It generates a unique ticket ID and holds a reference to the Cart.
 *
 * @author Muhammad Daud
 */
public class Ticket implements Serializable {
    private String ticketId; // Unique ID for the ticket
    private Cart ticketCart; // The associated Cart object

    /**
     * Constructs a Ticket object using the provided Cart.
     *
     * @param cart The Cart object associated with the ticket.
     */
    public Ticket(Cart cart) {
        this.ticketId = generateTicketId(); // generate a unique ticket ID
        this.ticketCart = cart;
    }

    /**
     * Generates a unique ticket ID of 6 characters.
     * These can be numbers or alphabets.
     *
     * @return The generated ticket ID.
     */
    private String generateTicketId() {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }

    /**
     * Retrieves the ticket ID.
     *
     * @return The ticket ID.
     */
    public String getTicketId() {
        return ticketId;
    }

    /**
     * Retrieves the associated Cart object.
     *
     * @return The associated Cart object.
     */
    public Cart getCart() {
        return ticketCart;
    }
}
