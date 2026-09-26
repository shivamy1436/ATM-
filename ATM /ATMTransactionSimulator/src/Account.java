import java.util.LinkedList;

public class Account {

    private String accountNumber;
    private Customer customer;
    private double balance;
    private int pin;

    private LinkedList<Transaction> transactions;

    // Constructor
    public Account(String accountNumber, Customer customer,
                   double balance, int pin) {

        this.accountNumber = accountNumber;
        this.customer = customer;
        this.balance = balance;
        this.pin = pin;

        transactions = new LinkedList<>();
    }

    // Get Account Number
    public String getAccountNumber() {
        return accountNumber;
    }

    // Get Customer
    public Customer getCustomer() {
        return customer;
    }

    // Get Balance
    public double getBalance() {
        return balance;
    }

    // Get PIN
    public int getPin() {
        return pin;
    }

    // Get Transaction History
    public LinkedList<Transaction> getTransactions() {
        return transactions;
    }

    // Deposit Money
    public void deposit(double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                "Deposit amount must be greater than 0"
            );
        }

        balance = balance + amount;

        transactions.add(
            new Transaction(
                "DEPOSIT",
                amount,
                balance
            )
        );
    }

    // Withdraw Money
    public void withdraw(double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                "Withdrawal amount must be greater than 0"
            );
        }

        if (amount > balance) {
            throw new IllegalArgumentException(
                "Insufficient balance"
            );
        }

        balance = balance - amount;

        transactions.add(
            new Transaction(
                "WITHDRAW",
                amount,
                balance
            )
        );
    }
}