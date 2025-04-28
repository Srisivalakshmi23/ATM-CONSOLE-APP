package ATM;

import java.util.Scanner;

public class ATMActions {

    public static void start() {
        Scanner scanner = new Scanner(System.in);
        AdminActions adminActions = new AdminActions();
        UserActions userActions = new UserActions();

        // Creating an admin account for login
        ATM.getAvailableAccounts().add(new Admin("admin", "admin"));

        while (true) {
            System.out.println("Enter your choice: \n 1. Admin \n 2. User \n 3. Exit");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    handleAdminLogin(adminActions);
                    break;
                case 2:
                    handleUserLogin(userActions);
                    break;
                case 3:
                    System.out.println("Exiting");
                    return;
                default:
                    System.out.println("Enter a valid option.");
                    break;
            }
        }
    }

    private static void handleAdminLogin(AdminActions adminActions) {
        Accounts currentAdmin = adminActions.login();

        if (currentAdmin == null) {
            System.out.println("No admins found.");
        } else if (currentAdmin.getUserName() == null) {
            System.out.println("Invalid credentials...");
        } else {
            adminEntry(currentAdmin);
        }
    }

    private static void handleUserLogin(UserActions userActions) {
        Accounts currentUser = userActions.login();

        if (currentUser == null) {
            System.out.println("No users found.");
        } else if (currentUser.getUserName() == null) {
            System.out.println("Invalid credentials...");
        } else {
            userEntry(currentUser);
        }
    }

    public static void userEntry(Accounts currentAccount) {
        Scanner scanner = new Scanner(System.in);
        UserActions userActions = new UserActions();
        User currentUser = (User) currentAccount;

        System.out.println("User Login Successful.");

        while (true) {
            System.out.println("Enter your choice:");
            System.out.println(" 1. Change PIN \n 2. Check Balance \n 3. Withdraw Cash \n 4. Deposit Cash \n 5. Show History \n 6. Logout");
            int operationChoice = Integer.parseInt(scanner.nextLine());

            switch (operationChoice) {
                case 1:
                    userActions.changePin(currentUser);
                    break;
                case 2:
                    System.out.println("Your current balance is " + currentUser.getBalance());
                    break;
                case 3:
                    userActions.withdrawCash(currentUser);
                    break;
                case 4:
                    userActions.depositMoney(currentUser);
                    break;
                case 5:
                    userActions.viewTransactions(currentUser);
                    break;
                case 6:
                    System.out.println("Logged out");
                    return;
                default:
                    System.out.println("Enter a valid option.");
                    break;
            }
        }
    }

    public static void adminEntry(Accounts currentAccount) {
        Scanner scanner = new Scanner(System.in);
        AdminActions adminActions = new AdminActions();
        Admin currentAdmin = (Admin) currentAccount;

        System.out.println("Access Granted.");

        while (true) {
            System.out.println("Enter your choice:");
            System.out.println(" 1. Add User \n 2. Delete User \n 3. Deposit Cash in ATM \n 4. Show All Transaction History");
            System.out.println(" 5. View All Users \n 6. Add Admin \n 7. Logout");
            int operationChoice = Integer.parseInt(scanner.nextLine());

            switch (operationChoice) {
                case 1:
                    adminActions.addUser();
                    break;
                case 2:
                    adminActions.deleteUser();
                    break;
                case 3:
                    adminActions.depositMoney(currentAdmin);
                    break;
                case 4:
                    adminActions.viewTransactions(currentAdmin);
                    break;
                case 5:
                    adminActions.viewUsers();
                    break;
                case 6:
                    adminActions.addAdmin();
                    break;
                case 7:
                    System.out.println("Logged out...");
                    return;
                default:
                    System.out.println("Enter a valid option.");
                    break;
            }
        }
    }

    public static long getDepositedBalance(int twoThousand, int fiveHundred, int twoHundred, int oneHundred) {
        return 2000L * twoThousand + 500L * fiveHundred + 200L * twoHundred + 100L * oneHundred;
    }
}
