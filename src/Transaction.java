import java.util.Date;

public class Transaction {

    //Amount of transaction
    private double amount;

    //Time and Date of transaction
    private Date timestamp;

    //Memo for transaction
    private String memo;

    //Account in which transaction was performed
    private Account inAccount;

    //Create new transaction
    //Parameter amount - amount in transaction
    //Parameter inAccount - account transaction belongs to
    public Transaction(double amount, Account inAccount) {

        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";
    }

    //Create new transaction
    //Parameter amount - amount in transaction
    //Parameter memo - memo for transaction
    //Parameter inAccount - account transaction belongs to
    public Transaction(double amount, String memo, Account inAccount) {

        //Call the two-arg constructor first
        this(amount, inAccount);

        //Set memo
        this.memo = memo;
    }

    //Get amount of transaction
    //Return - amount
    public double getAmount() {
        return this.amount;
    }

    //Get a string summarizing the transaction
    //Return - Summary string
    public String getSummaryLine() {

        if(this.amount >= 0) {
            return  String.format("%s : $%.02f : %s\n", this.timestamp.toString(),
                    this.amount, this.memo);
        } else {
            return String.format("%s : $(%.02f) : %s\n", this.timestamp.toString(),
                    -this.amount, this.memo);
        }
    }

}
