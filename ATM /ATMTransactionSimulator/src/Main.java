import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ATM atm = new ATM();

        // Sample customers

        Customer c1 =
                new Customer(
                    1,
                    "Shivam Yadav",
                    "9876543210"
                );

        Customer c2 =
                new Customer(
                    2,
                    "Rahul Sharma",
                    "9876501234"
                );

        // Sample accounts

        Account a1 =
                new Account(
                    "1001",
                    c1,
                    10000,
                    1234
                );

        Account a2 =
                new Account(
                    "1002",
                    c2,
                    15000,
                    5678
                );

        atm.addAccount(a1);
        atm.addAccount(a2);

        System.out.println("================================");
        System.out.println("      ATM TRANSACTION SYSTEM");
        System.out.println("================================");

        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Enter PIN: ");
        int pin = scanner.nextInt();

        try {

            atm.login(accountNumber, pin);

            System.out.println("\nLogin Successful!");

            boolean running = true;

            while (running) {

                System.out.println("\n========== ATM MENU ==========");
                System.out.println("1. Balance Enquiry");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Transaction History");
                System.out.println("5. Exit");

                System.out.print("Enter choice: ");

                int choice = scanner.nextInt();

                try {

                    switch (choice) {

                        case 1:

                            double balance =
                                    atm.checkBalance(
                                        accountNumber
                                    );

                            System.out.println(
                                "Current Balance: ₹"
                                + balance
                            );

                            break;

                        case 2:

                            System.out.print(
                                "Enter deposit amount: "
                            );

                            double deposit =
                                    scanner.nextDouble();

                            atm.deposit(
                                accountNumber,
                                deposit
                            );

                            System.out.println(
                                "Amount deposited successfully!"
                            );

                            break;

                        case 3:

                            System.out.print(
                                "Enter withdrawal amount: "
                            );

                            double withdraw =
                                    scanner.nextDouble();

                            atm.withdraw(
                                accountNumber,
                                withdraw
                            );

                            System.out.println(
                                "Please collect your cash."
                            );

                            break;

                        case 4:

                            atm.showTransactions(
                                accountNumber
                            );

                            break;

                        case 5:

                            running = false;

                            System.out.println(
                                "Thank you for using ATM."
                            );

                            break;

                        default:

                            System.out.println(
                                "Invalid choice!"
                            );
                    }

                } catch (ATMException e) {

                    System.out.println(
                        "Error: " + e.getMessage()
                    );
                }
            }

        } catch (ATMException e) {

            System.out.println(
                "Login Failed: " + e.getMessage()
            );
        }

        scanner.close();
    }
}