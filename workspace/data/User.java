import java.io.Serializable;

/**
* This class reprensents a user. Users have their own username and password to login and can self-identify as a business or not.
* @author Mustafa Mian
*/

public class User implements Serializable {
    private String username; //The username of the User.
    private String password; //The password of the User.
    private boolean isBusiness; //A boolean which is true is the User is the business.

    /**
     * Constructs a User object which has a username, password, and a boolean identifying if it is a business.
     */
    public User(String username, String password, boolean isBusiness) {
        this.username = username;
        this.password = password;
        this.isBusiness = isBusiness;
    }

    /**
     * @return the username of the User
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the password of the User
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return if the User is a business
     */
    public boolean isBusiness() {
        return isBusiness;
    }
}