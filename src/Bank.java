import java.util.ArrayList;
import java.util.Random;

public class Bank {

    //Name of bank
    private String name;

    //List of all users
    private ArrayList<User> users;

    //List of all accounts
    private ArrayList<Account> accounts;

    //Create new Bank Object with empty lists of users and accounts
    //Parameter name - name of bank
    public Bank (String name) {

        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    //Generate new Universal Unique ID for user - return the uuid
    public String getNewUserUUID(){

        //initializes
        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;

        //Continue looping until unique ID is given
        do {

            //Generate new number
            uuid = "";
            for (int x = 0; x < len; x++) {
                uuid += ((Integer)rng.nextInt(10)).toString();
            }

            //Check to make sure number is unique
            nonUnique = false;
            for (User u : this.users){
                if (uuid.compareTo(u.getUUID())  == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);

        return uuid;
    }

    //Generate new Universally Unique ID for account
    public String getNewAccountUUID(){

        //initializes
        String uuid;
        Random rng = new Random();
        int len = 10;
        boolean nonUnique;

        //Continue looping until unique ID is given
        do {

            //Generate new number
            uuid = "";
            for (int x = 0; x < len; x++) {
                uuid += ((Integer)rng.nextInt(10)).toString();
            }

            //Check to make sure number is unique
            nonUnique = false;
            for (Account a : this.accounts){
                if (uuid.compareTo(a.getUUID())  == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);

        return uuid;
        }

    //Add an account
    //Parameter anAcct -  account being added
    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }

    //Create a new user of the bank
    // Parameter firstName - user's first name
    // Parameter lastName - user's last name
    // Parameter pin - user's pin
    //Return - new User Object
    public User addUser(String firstName, String lastName,String pin) {

        //Creates new User Object and adds it to list
        User newUser = new User(firstName,lastName, pin, this);
        this.users.add(newUser);

        //Create savings account for user and add to User and Bank accounts list
        Account newAccount = new Account("Savings", newUser, this);
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }

    //Get the User Object associated with a particular userID and pin, if they are valid
    // Parameter userID - UUID of user currently logged in
    // Parameter pin - pin of user
    // Return - User Object , if the login is successful, or null, if it is not
    public User userLogin(String userID, String pin) {

        //Search through list of users
        for (User u : this.users) {

            //Check user ID is correct
            if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
                return u;
            }
        }

        //if we haven't found user or have an incorrect pin
        return null;
    }

    //Get name of bank
    //Return - name of bank
    public String getName() {
        return this.name;
    }
}
