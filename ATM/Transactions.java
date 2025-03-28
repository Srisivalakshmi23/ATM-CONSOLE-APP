package ATM;

//Transaction POJO class to store all transactions as objects
public class Transactions {
    private final String user;
    private final String type;
    private final double amount;

    public Transactions(String type , double amount, String user)
    {
        this.type = type;
        this.amount = amount;
        this.user = user;
    }

    public String getType()
    {
        return type;
    }

    public String getUser()
    {
        return user;
    }

    public double getAmount()
    {
        return amount;
    }
}