package ATM;

import ATM.Notes.Notes;
import java.util.ArrayList;
import java.util.Scanner;

// The UserActions class implements the UserAction interface, which provides a template of methods for user-related operations.
public class UserActions implements UserAction {

    // Method to verify user credentials and return the corresponding account object.
    @Override
    public Accounts login() {
        Scanner s = new Scanner(System.in); // Create a Scanner object to read input.
        System.out.print("Enter the Username: ");
        String uname = s.nextLine(); // get the username from the user.
        System.out.print("Enter the Password: ");
        String password = s.nextLine(); // get the password from the user.

        // Retrieve the list of all accounts in the ATM system.
        ArrayList<Accounts> usersAvailable = (ArrayList<Accounts>) ATM.getAvailableAccounts();

        // Loop through the list of accounts to find a matching user account.
        for (Accounts individualUser : usersAvailable) {
            if (individualUser instanceof User) { // Check if the account is a user account.
                if (individualUser.getUserName().equals(uname) && individualUser.getPassword().equals(password)) {
                    // If both username and password match, return the user account.
                    return individualUser;
                } else if (individualUser.getUserName().equals(uname) && !individualUser.getPassword().equals(password)) {
                    // If username matches but password doesn't, return a placeholder account.
                    return new User(null, null, 0);
                }
            }
        }
        return null; // Return null if no matching account is found.
    }

    // Method to allow the user to change their PIN (password).
    @Override
    public void changePin(User currentUser) {
        Scanner s = new Scanner(System.in); // Create a Scanner object for input.
        System.out.print("Enter the current pin: ");
        String currentPassword = s.nextLine(); // get the current PIN from the user.

        // Check if the entered PIN matches the current user's PIN.
        if (!currentUser.getPassword().equals(currentPassword)) {
            System.out.println("This pin is wrong! Try again!");
            return;
        }

        System.out.print("Enter the new Pin: ");
        String newPassword = s.nextLine(); // get the new PIN from the user.
        currentUser.setPassword(newPassword); // Update the user's PIN.
        System.out.println("Password changed successfully!");
    }

    // Private helper method to perform the withdrawal logic for a specific denomination of notes.
    private static double performWithdraw(double userAmount, Notes note, ArrayList<String> denominationsList) {
        // Calculate the number of notes required for the current denomination.
        long count = (long) (userAmount / Integer.parseInt(Notes.getNote()));

        // Check if the denomination can be used for withdrawal.
        if (Long.parseLong(Notes.getNote()) <= userAmount && note.getCount() > 0) {
            if (count <= note.getCount()) {
                // Deduct the calculated amount and update the note count.
                userAmount -= count * Integer.parseInt(Notes.getNote());
                note.setCount((int) (note.getCount() - count));
                denominationsList.add("You withdrawn " + Notes.getNote() + " count " + count);
            } else {
                // Deduct the maximum possible amount based on available notes.
                userAmount -= note.getCount() * Integer.parseInt(Notes.getNote());
                denominationsList.add("You withdrawn " + Notes.getNote() + " count " + note.getCount());
                note.setCount(0); // Set the count to zero as all notes are used.
            }
        }
        return userAmount; // Return the remaining amount to be withdrawn.
    }

    // Method to allow the user to withdraw cash from their account.
    @Override
    public void withdrawCash(User currentUser) {
        Scanner s = new Scanner(System.in); // Create a Scanner object for input.
        ArrayList<String> notesTransaction = new ArrayList<>(); // Store details of the withdrawal for each denomination.
        ArrayList<Notes> originalNotes = (ArrayList<Notes>) ATM.getNotesInAtm(); // Get the original notes in the ATM.
        ArrayList<Notes> tempNotes = new ArrayList<>(); // Create a temporary copy of the notes.

        // Create a temporary copy of the notes to avoid modifying the original notes immediately.
        for (Notes note : originalNotes) {
            tempNotes.add(new Notes(Notes.getNote(), note.getCount()));
        }

        System.out.print("Enter the withdraw amount: ");
        long amountToWithdraw = Long.parseLong(s.nextLine()); // get the withdrawal amount from the user.

        // Check if the user has enough balance to withdraw the requested amount.
        if (amountToWithdraw <= currentUser.getBalance()) {
            // Check if the ATM has enough balance to dispense the requested amount.
            if (amountToWithdraw <= ATM.getBalance()) {
                long remainingAmount = amountToWithdraw; // Initialize the remaining amount to withdraw.

                // Trying to withdraw the amount using available denominations.
                for (Notes note : tempNotes) {
                    remainingAmount = (long) performWithdraw(remainingAmount, note, notesTransaction);
                }

                // Check if the withdrawal was successful.
                if (remainingAmount == 0) {
                    ATM.setNotesInAtm(tempNotes); // Update the ATM notes with the temporary copy.
                    currentUser.setBalance(currentUser.getBalance() - amountToWithdraw); // Deduct the amount from the user's balance.

                    // Print the withdrawal details for each denomination.
                    for (String transaction : notesTransaction) {
                        System.out.println("*" + transaction);
                    }

                    // Add a transaction record for the withdrawal.
                    currentUser.getAvailableTransactions().add(new Transactions("Withdrawn", amountToWithdraw, currentUser.getUserName()));
                } else {
                    System.out.println("Enter the amount in the multiples of 100!");
                }
            } else {
                System.out.println("Insufficient amount in ATM!");
            }
        } else {
            System.out.println("Insufficient amount in your account!");
        }
    }

    // Method to allow the user to deposit money into their account.
    @Override
    public void depositMoney(Accounts currentAccount) {
        User currentUser = (User) currentAccount; // Cast the current account to a user account.
        Scanner s = new Scanner(System.in); // Create a Scanner object for input.

        System.out.print("Enter the amount to deposit: ");
        long enteredAmount = Long.parseLong(s.nextLine()); // get the deposit amount from the user.

        // Message to enter the count of notes for each denomination.
        System.out.println("Enter the number of notes to deposit:");
        System.out.print("2000 notes: ");
        int twoThousandNotes = Integer.parseInt(s.nextLine());
        System.out.print("500 notes: ");
        int fiveHundredNotes = Integer.parseInt(s.nextLine());
        System.out.print("200 notes: ");
        int twoHundredNotes = Integer.parseInt(s.nextLine());
        System.out.print("100 notes: ");
        int oneHundredNotes = Integer.parseInt(s.nextLine());

        // Calculate the total amount based on the entered notes.
        long calculatedAmount = ATMActions.getDepositedBalance(twoThousandNotes, fiveHundredNotes, twoHundredNotes, oneHundredNotes);

        // Check if the entered amount matches the calculated amount.
        if (enteredAmount == calculatedAmount) {
            // Update the note counts in the ATM for each denomination.
            for (Notes note : ATM.getNotesInAtm()) {
                switch (Notes.getNote()) {
                    case "2000":
                        note.setCount(note.getCount() + twoThousandNotes);
                        break;
                    case "500":
                        note.setCount(note.getCount() + fiveHundredNotes);
                        break;
                    case "200":
                        note.setCount(note.getCount() + twoHundredNotes);
                        break;
                    case "100":
                        note.setCount(note.getCount() + oneHundredNotes);
                        break;
                }
            }

            currentUser.setBalance(currentUser.getBalance() + calculatedAmount); // Update the user's balance.
            ATM.setBalance(ATM.getBalance() + calculatedAmount); // Update the ATM's total balance.
            currentUser.getAvailableTransactions().add(new Transactions("Deposited", calculatedAmount, currentUser.getUserName())); // Add a transaction record.

            System.out.println("Deposit successful. Your new balance is: " + currentUser.getBalance());
        } else {
            System.out.println("Entered amount and calculated amount do not match. Please try again.");
        }
    }

    // Method to view the transaction history of the current user.
    @Override
    public void viewTransactions(Accounts currentAccount) {
        User currentUser = (User) currentAccount; // Cast the current account to a User account.
        ArrayList<Transactions> userHistory = currentUser.getAvailableTransactions(); // Get the user's transaction history.

        // Check if the user has any transactions.
        if (!userHistory.isEmpty()) {
            System.out.println("Your transaction history:");
            // Loop through the transactions and display them.
            for (Transactions transactionHistory : userHistory) {
                System.out.println("You " + transactionHistory.getType() + " Rs " + transactionHistory.getAmount());
            }
        } else {
            System.out.println("No transactions found!");
        }
    }
}
