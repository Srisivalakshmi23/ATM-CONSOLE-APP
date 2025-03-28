package ATM;

public class User extends Accounts {
    private double accBalance;

    public User(String userName, String password, double accBalance) {
        super(userName, password);
        this.accBalance = accBalance;
    }

    public double getBalance() {
        return accBalance;
    }

    public void setBalance(double balance) {
        this.accBalance = balance;
    }
}