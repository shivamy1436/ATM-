import javax.swing.*;
import java.awt.*;

public class ATMGUI extends JFrame {

    private ATM atm;
    private String accountNumber;

    private JLabel balanceLabel;

    public ATMGUI() {

        // Create ATM
        atm = new ATM();

        // Sample customer
        Customer customer = new Customer(
                1,
                "Shivam Yadav",
                "9876543210"
        );

        // Sample account
        Account account = new Account(
                "1001",
                customer,
                10000,
                1234
        );

        atm.addAccount(account);

        // Login screen
        showLoginScreen();
    }

    // ================= LOGIN SCREEN =================

    private void showLoginScreen() {

        setTitle("ATM Transaction Simulator");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        JLabel title = new JLabel(
                "ATM TRANSACTION SYSTEM",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 22));

        JLabel accountLabel = new JLabel(
                "Account Number:",
                SwingConstants.CENTER
        );

        JTextField accountField = new JTextField();

        JLabel pinLabel = new JLabel(
                "PIN:",
                SwingConstants.CENTER
        );

        JPasswordField pinField = new JPasswordField();

        JButton loginButton = new JButton("LOGIN");

        panel.add(title);
        panel.add(accountLabel);
        panel.add(accountField);
        panel.add(pinLabel);
        panel.add(pinField);

        add(panel, BorderLayout.CENTER);
        add(loginButton, BorderLayout.SOUTH);

        loginButton.addActionListener(e -> {

            String enteredAccount =
                    accountField.getText();

            String pinText =
                    new String(pinField.getPassword());

            try {

                int pin =
                        Integer.parseInt(pinText);

                atm.login(
                        enteredAccount,
                        pin
                );

                accountNumber = enteredAccount;

                JOptionPane.showMessageDialog(
                        this,
                        "Login Successful!"
                );

                showDashboard();

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "PIN must contain numbers only!"
                );

            } catch (ATMException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        setVisible(true);
    }

    // ================= DASHBOARD =================

    private void showDashboard() {

        getContentPane().removeAll();

        setTitle("ATM Dashboard");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(
                new GridLayout(6, 1, 10, 10)
        );

        JLabel title = new JLabel(
                "ATM DASHBOARD",
                SwingConstants.CENTER
        );

        title.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        balanceLabel = new JLabel(
                "Balance: ₹" +
                atm.searchAccount(accountNumber)
                   .getBalance(),
                SwingConstants.CENTER
        );

        balanceLabel.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        JButton balanceButton =
                new JButton("Balance Enquiry");

        JButton depositButton =
                new JButton("Deposit");

        JButton withdrawButton =
                new JButton("Withdraw");

        JButton historyButton =
                new JButton("Transaction History");

        JButton exitButton =
                new JButton("Exit");

        mainPanel.add(title);
        mainPanel.add(balanceLabel);
        mainPanel.add(balanceButton);
        mainPanel.add(depositButton);
        mainPanel.add(withdrawButton);
        mainPanel.add(historyButton);

        add(mainPanel, BorderLayout.CENTER);
        add(exitButton, BorderLayout.SOUTH);

        // Balance
        balanceButton.addActionListener(e -> {

            try {

                double balance =
                        atm.checkBalance(accountNumber);

                JOptionPane.showMessageDialog(
                        this,
                        "Current Balance: ₹" + balance
                );

            } catch (ATMException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage()
                );
            }
        });

        // Deposit
        depositButton.addActionListener(e -> {

            String input =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter deposit amount:"
                    );

            if (input == null) {
                return;
            }

            try {

                double amount =
                        Double.parseDouble(input);

                atm.deposit(
                        accountNumber,
                        amount
                );

                updateBalance();

                JOptionPane.showMessageDialog(
                        this,
                        "Amount deposited successfully!"
                );

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Enter a valid amount!"
                );

            } catch (ATMException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage()
                );
            }
        });

        // Withdraw
        withdrawButton.addActionListener(e -> {

            String input =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter withdrawal amount:"
                    );

            if (input == null) {
                return;
            }

            try {

                double amount =
                        Double.parseDouble(input);

                atm.withdraw(
                        accountNumber,
                        amount
                );

                updateBalance();

                JOptionPane.showMessageDialog(
                        this,
                        "Please collect your cash."
                );

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Enter a valid amount!"
                );

            } catch (ATMException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage()
                );
            }
        });

        // Transaction History
        historyButton.addActionListener(e -> {

            Account account =
                    atm.searchAccount(accountNumber);

            StringBuilder history =
                    new StringBuilder();

            if (account.getTransactions().isEmpty()) {

                history.append(
                        "No transactions found."
                );

            } else {

                for (Transaction transaction :
                        account.getTransactions()) {

                    history.append(transaction);
                    history.append("\n\n");
                }
            }

            JTextArea textArea =
                    new JTextArea(
                            history.toString()
                    );

            textArea.setEditable(false);

            JScrollPane scrollPane =
                    new JScrollPane(textArea);

            scrollPane.setPreferredSize(
                    new Dimension(500, 300)
            );

            JOptionPane.showMessageDialog(
                    this,
                    scrollPane,
                    "Transaction History",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        // Exit
        exitButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "Thank you for using ATM!"
            );

            System.exit(0);
        });

        revalidate();
        repaint();
    }

    // ================= UPDATE BALANCE =================

    private void updateBalance() {

        double balance =
                atm.searchAccount(accountNumber)
                   .getBalance();

        balanceLabel.setText(
                "Balance: ₹" + balance
        );
    }

    // ================= MAIN =================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> new ATMGUI()
        );
    }
}