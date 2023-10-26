import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {

        //initialize Scanner
        Scanner sc = new Scanner(System.in);

        //Initialize Bank
        Bank theBank = new Bank("Bank of SE");

        //Add user, which also creates savings account
        User aUser = theBank.addUser("John", "Doe", "1234");

        //Add checking account for user
        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true) {

            //Stay in the login prompt until successful login
            curUser = ATM.mainMenuPrompt(theBank, sc);

            //Stay in main menu until user quits
            ATM.printUserMenu(curUser, sc);
        }
    }

    //Print ATM's login menu
    //Parameter theBank - Bank Object whose accounts to use
    // Parameter sc - Scanner Object for user input
    //Return - authenticated User Object
    public static User mainMenuPrompt(Bank theBank, Scanner sc) {

        //Initialized
        String userID;
        String pin;
        User authUser;

        //Prompt user for user ID / Pin combo until a correct one is reached
        do {

            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter user ID: ");
            userID = sc.nextLine();
            System.out.print("Enter pin: ");
            pin = sc.nextLine();

            //Try to get User Object corresponding to the ID and pin combo
            authUser = theBank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("Incorrect user ID / pin combination. " + "Please try again");
            }

            //Continue looping until successful login
        } while (authUser == null);

        return authUser;
    }

    //
    public static void printUserMenu(User theUser, Scanner sc) {

        //Print summary of user's account(s)
        theUser.printAccountsSummary();

        //Initialize
        int choice;

        //User menu
        do {
            System.out.printf("Welcome %s, what would you like to do?\n", theUser.getFirstName());
            System.out.println("  1) Show account transaction history");
            System.out.println("  2) Withdrawal");
            System.out.println("  3) Deposit");
            System.out.println("  4) Transfer");
            System.out.println("  5) Quit");
            System.out.println();
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice. Please choose 1-5");
            }
        } while (choice < 1 || choice > 5);

        //Process choice
        switch (choice) {

            case 1:
                ATM.showTransHistory(theUser, sc);
                break;
            case 2:
                ATM.withdrawalFunds(theUser, sc);
                break;
            case 3:
                ATM.depositFunds(theUser, sc);
                break;
            case 4:
                ATM.transferFunds(theUser, sc);
                break;
            case 5:
                //Gobble up rest of previous input line
                sc.nextLine();
                break;
        }

        //Redisplay this menu unless user wants to quit
        if (choice != 5) {
            ATM.printUserMenu(theUser, sc);
        }
    }

    //Show transaction history for an account
    //Parameter theUser - logged-in User Object
    //Parameter sc - Scanner Object for user input
    public static void showTransHistory(User theUser, Scanner sc) {

        int theAcct;

        //Get account whose transaction history to look at
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "whose transactions you want to see: ", theUser.numAccounts());
            theAcct = sc.nextInt() - 1;
            if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (theAcct < 0 || theAcct >= theUser.numAccounts());

        //Print transaction history
        theUser.printAcctTransHistory(theAcct);
    }

    //Process transferring funds from one account to another
    //Parameter theUser - logged-in User Object
    //Parameter sc - Scanner Object for user input
    public static void transferFunds(User theUser, Scanner sc) {

        //inits
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        //Get account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to transfer from: ",theUser.numAccounts());
            fromAcct = sc.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
            ;
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);

        //Get account being transferred to
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to transfer to: ",theUser.numAccounts());
            toAcct = sc.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
            ;
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());

        //Get amount being transferred
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $",
                    acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero.");
            } else if (amount > acctBal) {
                System.out.printf("Amount must not be greater than\n" +
                        "balance of $%.02f.\n", acctBal);
            }
        } while (amount < 0 || amount > acctBal);

        //Execute transfer
        theUser.addAcctTransaction(fromAcct, -1 * amount, String.format(
                "Transfer to account %s", theUser.getAcctUUID(toAcct)));
        theUser.addAcctTransaction(toAcct, amount, String.format(
                "Transfer to account %s", theUser.getAcctUUID(fromAcct)));
    }

    //Process a fund withdrawal from an account
    //Parameter theUser - logged-in UerObject
    //Parameter sc - Scanner Object for user input
    public static void withdrawalFunds(User theUser, Scanner sc) {

        //inits
        int fromAcct;
        double amount;
        double acctBal;
        String memo;

        //Get account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to withdraw from: ",theUser.numAccounts());
            fromAcct = sc.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
            ;
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);

        //Get amount being transferred
        do {
            System.out.printf("Enter the amount to withdraw (max $%.02f): $",
                    acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero.");
            } else if (amount > acctBal) {
                System.out.printf("Amount must not be greater than\n" +
                        "balance of $%.02f.\n", acctBal);
            }
        } while (amount < 0 || amount > acctBal);

        //Gobble up rest of previous input line
        sc.nextLine();

        //Get memo
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();

        //Do the withdrawal
        theUser.addAcctTransaction(fromAcct, -1 * amount, memo);
    }

    //Process a fund deposit to an account
    //Parameter theUser - logged-in UerObject
    //Parameter sc - Scanner Object for user input
    public static void depositFunds(User theUser, Scanner sc) {

        //inits
        int toAcct;
        double amount;
        double acctBal;
        String memo;

        //Get account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to deposit in: ", theUser.numAccounts());
            toAcct = sc.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
            ;
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(toAcct);

        //Get amount being transferred
        do {
            System.out.printf("Enter the amount to deposit (max $%.02f): $",
                    acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero.");
            }
        } while (amount < 0);

        //Gobble up rest of previous input line
        sc.nextLine();

        //Get memo
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();

        //Do the withdrawal
        theUser.addAcctTransaction(toAcct, amount, memo);
    }
}
