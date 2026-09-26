import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class ATM {

    private ArrayList<Account> accounts;

    private HashMap<String, Account> accountMap;

    private TreeMap<String, Account> sortedAccounts;

    public ATM() {

        accounts = new ArrayList<>();
        accountMap = new HashMap<>();
        sortedAccounts = new TreeMap<>();
    }

    // CREATE ACCOUNT

    public void addAccount(Account account) {

        accounts.add(account);

        accountMap.put(
            account.getAccountNumber(),
            account
        );

        sortedAccounts.put(
            account.getAccountNumber(),
            account
        );
    }

    // SEARCH ACCOUNT

    public Account searchAccount(String accountNumber) {

        return accountMap.get(accountNumber);
    }

    // LOGIN

    public boolean login(String accountNumber, int pin)
            throws ATMException {

        Account account = searchAccount(accountNumber);

        if (account == null) {
            throw new ATMException("Account not found");
        }

        if (account.getPin() != pin) {
            throw new ATMException("Invalid PIN");
        }

        return true;
    }

    // BALANCE

    public double checkBalance(String accountNumber)
            throws ATMException {

        Account account = searchAccount(accountNumber);

        if (account == null) {
            throw new ATMException("Account not found");
        }

        return account.getBalance();
    }

    // DEPOSIT

    public void deposit(String accountNumber, double amount)
            throws ATMException {

        Account account = searchAccount(accountNumber);

        if (account == null) {
            throw new ATMException("Account not found");
        }

        try {
            account.deposit(amount);
        } catch (IllegalArgumentException e) {
            throw new ATMException(e.getMessage());
        }
    }

    // WITHDRAW

    public void withdraw(String accountNumber, double amount)
            throws ATMException {

        Account account = searchAccount(accountNumber);

        if (account == null) {
            throw new ATMException("Account not found");
        }

        try {
            account.withdraw(amount);
        } catch (IllegalArgumentException e) {
            throw new ATMException(e.getMessage());
        }
    }

    // TRANSACTION HISTORY

    public void showTransactions(String accountNumber)
            throws ATMException {

        Account account = searchAccount(accountNumber);

        if (account == null) {
            throw new ATMException("Account not found");
        }

        System.out.println("\n===== TRANSACTION HISTORY =====");

        if (account.getTransactions().isEmpty()) {

            System.out.println("No transactions found.");

        } else {

            for (Transaction transaction :
                    account.getTransactions()) {

                System.out.println(transaction);
            }
        }
    }

    // SHOW ALL ACCOUNTS

    public void showAllAccounts() {

        System.out.println("\n===== ALL ACCOUNTS =====");

        for (Account account : accounts) {

            System.out.println(
                account.getAccountNumber()
                + " | "
                + account.getCustomer().getName()
                + " | ₹"
                + account.getBalance()
            );
        }
    }

    // SORTED ACCOUNTS

    public void showSortedAccounts() {

        System.out.println("\n===== SORTED ACCOUNTS =====");

        for (Account account :
                sortedAccounts.values()) {

            System.out.println(
                account.getAccountNumber()
                + " | "
                + account.getCustomer().getName()
                + " | ₹"
                + account.getBalance()
            );
        }
    }
}