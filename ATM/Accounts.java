
package ATM;

import java.util.ArrayList;

public class Accounts {
    private final String userName;
    private String password;
    private final ArrayList<Transactions> transactions = new ArrayList<>();

    public Accounts(String userName,String password)
    {
        this.userName = userName;
        this.password = password;
    }
    public String getUserName()
    {
        return userName;
    }
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public ArrayList<Transactions> getAvailableTransactions() {
        return transactions;
    }
}
