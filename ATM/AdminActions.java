package ATM;

import ATM.Notes.Notes;
import java.util.ArrayList;
import java.util.Scanner;

// AdminActions class implements the AdminAction interface
public class AdminActions implements AdminAction {

    // Admin login method to authenticate admin accounts
    @Override
    public Accounts login() {
        Scanner scanner = new Scanner(System.in); // Create a scanner object to read input

        System.out.print("Enter Admin Name: ");
        String name = scanner.nextLine(); // get admin name

        System.out.print("Enter Password: ");
        String password = scanner.nextLine(); // get admin password

        // Loop through all available accounts in the ATM
        for (Accounts account : ATM.getAvailableAccounts()) {
            // Check if the account is an admin account
            if (account instanceof Admin admin) { // Verify the username
                if (admin.getUserName().equals(name)) { // If the password matches, return the admin account
                    return admin.getPassword().equals(password) ? admin : new Accounts(null, null);
                }
            }
        }
        return null; // Return null if login credentials are invalid
    }

    // Add a new user to the ATM system
    @Override
    public void addUser() {
        Scanner scanner = new Scanner(System.in); // Create a scanner object for input

        System.out.print("Enter the Username: ");
        String userName = scanner.nextLine(); // get the username

        // Check if the user already exists in the accounts list
        for (Accounts account : ATM.getAvailableAccounts()) {
            if (account instanceof User user && user.getUserName().equals(userName)) {
                System.out.println("User already exists.");
                return;
            }
        }

        // Prompt to enter the password for the new user
        System.out.print("Enter the user Password: ");
        String password = scanner.nextLine(); // Read the password

        // Add the new user account to the ATM system
        ATM.getAvailableAccounts().add(new User(userName, password, 0));
        System.out.println("User added successfully!");
    }

    // Delete an existing user from the ATM system
    @Override
    public void deleteUser() {
        Scanner scanner = new Scanner(System.in); // Create a scanner object for input

        // Display all available users
        System.out.println("Available Users:");
        ArrayList<Accounts> accounts = (ArrayList<Accounts>) ATM.getAvailableAccounts(); // Get all accounts
        ArrayList<User> users = new ArrayList<>(); // Create a list to store users

        // Loop through accounts and list only user accounts
        for (Accounts account : accounts) {
            if (account instanceof User user) {
                users.add(user); // Add the user to the users list
                System.out.println("=> " + user.getUserName()); // Display the username
            }
        }

        // If no users are found, notify and return
        if (users.isEmpty()) {
            System.out.println("No users available.");
            return;
        }

        System.out.print("Enter the Username: ");
        String userName = scanner.nextLine(); // Read the username

        // Search for the user and delete them
        for (User user : users) {
            if (user.getUserName().equals(userName)) {
                accounts.remove(user); // Remove the user account
                System.out.println("User deleted successfully!");
                return;
            }
        }

        System.out.println("User not found.");
    }

    // Deposit money into the ATM
    @Override
    public void depositMoney(Accounts currentAccount) {
        Scanner scanner = new Scanner(System.in); // Create a scanner for input

        System.out.print("Enter Total Deposit Amount: ");
        long enteredAmount = Long.parseLong(scanner.nextLine()); // Read and parse the entered amount

        // Message to enter the count of notes for each denomination
        System.out.print("2000 Notes: ");
        int twoThousand = Integer.parseInt(scanner.nextLine());
        System.out.print("500 Notes: ");
        int fiveHundred = Integer.parseInt(scanner.nextLine());
        System.out.print("200 Notes: ");
        int twoHundred = Integer.parseInt(scanner.nextLine());
        System.out.print("100 Notes: ");
        int oneHundred = Integer.parseInt(scanner.nextLine());

        // Calculate the total deposited amount based on the entered counts
        long calculatedAmount = ATMActions.getDepositedBalance(twoThousand, fiveHundred, twoHundred, oneHundred);

        // Check if the calculated amount matches the entered amount
        if (enteredAmount == calculatedAmount) {
            // Update the counts of notes in the ATM
            for (Notes note : ATM.getNotesInAtm()) {
                switch (note.getDenomination()) { // Match the denomination
                    case "2000" -> note.setCount(note.getCount() + twoThousand);
                    case "500" -> note.setCount(note.getCount() + fiveHundred);
                    case "200" -> note.setCount(note.getCount() + twoHundred);
                    case "100" -> note.setCount(note.getCount() + oneHundred);
                }
            }

            // Add a transaction record for the deposit
            currentAccount.getAvailableTransactions().add(
                    new Transactions("Deposited", calculatedAmount, currentAccount.getUserName())
            );

            // Update the total ATM balance
            ATM.setBalance(ATM.getBalance() + calculatedAmount);
            System.out.println("Deposit Successful! Updated ATM Balance: " + ATM.getBalance());
        } else {
            System.out.println("Mismatch amount! Try again!");
        }
    }

    // View all users in the ATM system
    public void viewUsers() {
        System.out.println("Available Users:");
        // Loop through accounts and display users
        for (Accounts account : ATM.getAvailableAccounts()) {
            if (account instanceof User user) {
                System.out.println("=> " + user.getUserName()); // Display username
            }
        }
    }

    // View all transactions
    @Override
    public void viewTransactions(Accounts currentAccount) {
        Scanner scanner = new Scanner(System.in); // Create a scanner for input
        Admin admin = (Admin) currentAccount; // Cast the current account to Admin

        // Loop until the admin chooses to exit
        while (true) {
            System.out.println(" Enter your choice: \n1. View All Transactions \n2. View Admin Transactions \n3. View Specific User Transactions \n4. Exit");
            int choice = Integer.parseInt(scanner.nextLine()); // get admin choice

            // Handle the admin choice
            switch (choice) {
                case 1 -> { // View all transactions
                    boolean transactionsFound = false; // Track if any transactions are found

                    // Display admin transactions
                    for (Transactions transaction : admin.getAvailableTransactions()) {
                        System.out.println(transaction.getUser() + " " + transaction.getType() + " Rs." + transaction.getAmount());
                        transactionsFound = true;
                    }

                    // Display user transactions
                    for (Accounts account : ATM.getAvailableAccounts()) {
                        if (account instanceof User user) {
                            for (Transactions transaction : user.getAvailableTransactions()) {
                                System.out.println(transaction.getUser() + " " + transaction.getType() + " Rs." + transaction.getAmount());
                                transactionsFound = true;
                            }
                        }
                    }

                    if (!transactionsFound) System.out.println("No transactions found!");
                }
                case 2 -> { // View admin-specific transactions
                    if (admin.getAvailableTransactions().isEmpty()) {
                        System.out.println("No admin transactions found!");
                    } else {
                        for (Transactions transaction : admin.getAvailableTransactions()) {
                            System.out.println(transaction.getUser() + " " + transaction.getType() + " Rs." + transaction.getAmount());
                        }
                    }
                }
                case 3 -> { // View specific user transactions
                    System.out.println("Available Users:"); // Display all users
                    for (Accounts account : ATM.getAvailableAccounts()) {
                        if (account instanceof User user) System.out.println("=>" + user.getUserName());
                    }

                    System.out.print("Enter User Name: ");
                    String userName = scanner.nextLine(); // get username

                    // Search for the user and display their transactions
                    for (Accounts account : ATM.getAvailableAccounts()) {
                        if (account instanceof User user && user.getUserName().equals(userName)) {
                            if (user.getAvailableTransactions().isEmpty()) {
                                System.out.println("No transactions for this user!");
                            } else {
                                for (Transactions transaction : user.getAvailableTransactions()) {
                                    System.out.println(transaction.getUser() + " " + transaction.getType() + " Rs." + transaction.getAmount());
                                }
                            }
                            return;
                        }
                    }
                    System.out.println("User not found!");
                }
                case 4 -> { // Exit the loop
                    return;
                }
                default -> System.out.println("Invalid choice! Try again!");
            }
        }
    }

    // Add a new admin to the ATM system
    public void addAdmin() {
        Scanner scanner = new Scanner(System.in); // Create a scanner for input

        System.out.print("Enter Admin Name to Add: ");
        String adminName = scanner.nextLine(); // get admin name

        // Check if the admin already exists
        for (Accounts account : ATM.getAvailableAccounts()) {
            if (account instanceof Admin admin && admin.getUserName().equals(adminName)) {
                System.out.println("Admin already exists!"); // Notify if admin exists
                return;
            }
        }

        System.out.print("Enter Password: ");
        String password = scanner.nextLine(); // get password

        // Add the new admin account to the system
        ATM.getAvailableAccounts().add(new Admin(adminName, password));
        System.out.println("Admin added successfully!");
    }
}
