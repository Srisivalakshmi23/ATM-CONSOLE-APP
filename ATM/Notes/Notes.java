package ATM.Notes;

public class Notes {
    private static String note; // Denomination value of the note
    private int count; // Number of notes available

    // Constructor to initialize a Notes object with denomination and count
    public Notes(String note, int count) {
        Notes.note = note; // Initialize the denomination value
        this.count = count; // Initialize the count of notes
    }


    // Getter for count
    public int getCount() {
        return count;
    }

    // Setter to update the count of notes
    public void setCount(int count) {
        this.count = count; // Update the count of notes
    }

    public static String getNote(){
        return note;
    }

    public String getDenomination() {
        return note;
    }
}
