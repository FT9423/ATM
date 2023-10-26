import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.security.MessageDigest;

public class User {

    //First name of user
    private String firstName;

    //Last name of user
    private String lastName;

    //Unique User ID of user
    private String uuid;

    //MD5 hash of user
    private byte[] pinHash;

    //List of accounts of user
    private ArrayList<Account> accounts;

    //Create a new user
    // Parameter firstname - user's first name
    // Parameter lastName - user's last name
    // Parameter pin - user's account PIN
    // Parameter theBank - the Bank object that the user is a customer of
    public User(String firstName, String lastName, String pin, Bank theBank) {

        //Set user's name
        this.firstName = firstName;
        this.lastName = lastName;

        //Store the pin's MD5 hash, rather than the original value,
        // for security reasons
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Stores different byte value using the md digest
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        //Get a new Unique Universal ID for user
        this.uuid = theBank.getNewUserUUID();

        //Empty list of accounts
        this.accounts = new ArrayList<Account>();

        //Print log message
        System.out.printf("New user %s, %s with ID %s created. \n", lastName, firstName, this.uuid);
    }

    //Adds new account to User
    // Parameter anAcct - account being added
    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }

    //Returns the user's UUID
    public String getUUID() {
        return this.uuid;
    }

    //Check whether a given pin matches the true User pin
    // Parameter aPin - pin that's being checked
    // Return - whether the pin is valid or not
    public boolean validatePin(String aPin) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    //Return user's first name
    //Return - first name
    public String getFirstName() {
        return this.firstName;
    }

    //Print summaries for the accounts of this user
    public void printAccountsSummary(){

        System.out.printf("\n\n%s's accounts summary\n", this.firstName);
        for (int a = 0; a < this.accounts.size(); a++) {
            System.out.printf("  %d) %s", a+1, this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }

    //Get number of accounts of the user
    public int numAccounts() {
        return this.accounts.size();
    }

    //Print transaction history for particular account
    //Parameter accIdx - index of account to use
    public void printAcctTransHistory(int acctIdx) {
        this.accounts.get(acctIdx).printTransHistory();
    }

    //Get balance of particular account
    //Parameter acctIdx - index of the account being referenced
    //Return - balance of the account
    public double getAcctBalance(int acctIdx) {
        return this.accounts.get(acctIdx).getBalance();
    }

    //Get the UUID of a particular account
    //Parameter acctIdx - index of the account
    //Return - UUID of account
    public String getAcctUUID(int acctIdx) {
        return this.accounts.get(acctIdx).getUUID();
    }

    //Add a transaction to particular account
    public void addAcctTransaction(int acctIdx, double amount, String memo) {
        this.accounts.get(acctIdx).addTransaction(amount, memo);
    }


}
