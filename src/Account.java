import java.util.ArrayList;

public class Account {

    //Name of the account
    private String name;

    //Account ID number
    private String uuid;

    //User object that owns this account
    private User holder;

    //List of transactions from account
    private ArrayList<Transaction> transactions;

    //Create new account
    // Parameter name - name of the account
    // Parameter holder - User Object that holds this account
    // Parameter theBank - bank that issues the account

    public Account(String name, User holder, Bank theBank) {

        //Account name and holder
        this.name = name;
        this.holder = holder;

        //Get new account UUID
        this.uuid = theBank.getNewAccountUUID();

        //initialize transactions
        this.transactions = new ArrayList<Transaction>();
    }

    //Get account ID - return uuid
    public String getUUID() {
        return this.uuid;
    }

    //Get summary line for account
    //Return - string summary
    public String getSummaryLine () {

        //Get account's balance
        double balance = this.getBalance();

        //Format summary line depending on whether the balance is negative
        if (balance >= 0) {
            return String.format("%s : $%.02f : %s\n", this.uuid, balance, this.name);
        } else {
            return String.format("%s : $(%.02f) : %s\n", this.uuid, balance, this.name);
        }
    }

    //Get balance of account by adding the amounts of the transactions
    //Return - balance value
    public double getBalance() {

        double balance = 0;
        for (Transaction t : this.transactions) {
            balance += t.getAmount();
        }
        return balance;
    }

    //Print the transaction history of the account
    public void printTransHistory() {

        System.out.printf("\nTransaction history for account %s\n", this.uuid);
        for (int t = this.transactions.size()-1; t >= 0; t--) {
            System.out.println(this.transactions.get(t).getSummaryLine());
        }
        System.out.println();
    }

    //Add a new transaction in this account
    //Parameter amount - amount transacted
    //Parameter memo - transaction memo
    public void addTransaction(double amount, String memo) {

        //create new transaction and add it to our list
        Transaction newTrans = new Transaction(amount, memo, this);
        this.transactions.add(newTrans);
    }
}
