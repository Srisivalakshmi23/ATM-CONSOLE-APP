package ATM;

public interface UserAction {
    // function to check user and return objects for verification
    Accounts login();

    // function to change the pin
    void changePin(User currentUser);

    void withdrawCash(User currentUser);

    void depositMoney(Accounts currentAccount);

    void viewTransactions(Accounts currentAccount);
}
