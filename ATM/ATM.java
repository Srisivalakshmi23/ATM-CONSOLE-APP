package ATM;

import ATM.Notes.Notes;
import ATM.ListofNotes.FiveHundred;
import ATM.ListofNotes.OneHundred;
import ATM.ListofNotes.TwoHundred;
import ATM.ListofNotes.TwoThousand;

import java.util.ArrayList;
import java.util.List;

public class ATM {

    // Stores both admin and user accounts
    private static final List<Accounts> accounts = new ArrayList<>();

    // Stores the notes available in the ATM, initialized with denominations and a count of 0
    private static List<Notes> notesInAtm = new ArrayList<>(
            List.of(new TwoThousand("2000", 0), new FiveHundred("500", 0),
                    new TwoHundred("200", 0), new OneHundred("100", 0)));

    // The total balance available in the ATM
    private static double balance;

    // Getter for the list of available accounts
    public static List<Accounts> getAvailableAccounts() {
        return accounts;
    }

    // Getter for the total balance in the ATM
    public static double getBalance() {
        return balance;
    }

    // Setter for updating the total balance in the ATM
    public static void setBalance(double newBalance) {
        balance = newBalance;
    }

    // Getter for the list of notes in the ATM
    public static List<Notes> getNotesInAtm() {
        return notesInAtm;
    }

    public static void setNotesInAtm(ArrayList<Notes> notes){
        ATM.notesInAtm = notes;
    }
}
