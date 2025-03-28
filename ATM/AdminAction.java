package ATM;

public interface AdminAction {
    // Admin login method
    Accounts login();

    // Add a new user
    void addUser();

    // Delete an existing user
    void deleteUser();

    // Deposit money into ATM
    void depositMoney(Accounts currentAccount);

    // View transactions
    void viewTransactions(Accounts currentAccount);
}
