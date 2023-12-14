package com.jsp.oibsip;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class User {
    private String userId;
    private String pin;
    private double balance;
    private Map<Integer, String> transactionHistory;

    public User(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
        this.balance = 0.0;
        this.transactionHistory = new HashMap<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public Map<Integer, String> getTransactionHistory() {
        return transactionHistory;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.put(transactionHistory.size() + 1, "Deposit: +" + amount);
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.put(transactionHistory.size() + 1, "Withdraw: -" + amount);
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void transfer(User recipient, double amount) {
        if (amount <= balance) {
            balance -= amount;
            recipient.deposit(amount);
            transactionHistory.put(transactionHistory.size() + 1, "Transfer to " + recipient.getUserId() + ": -" + amount);
        } else {
            System.out.println("Insufficient funds for transfer.");
        }
    }

    public User get(String recipientId, Map<String, User> users) {
        return users.get(recipientId);
    }
}

public class ATMInterface {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, User> users = new HashMap<>();

        // Creating some sample users
        users.put("user1", new User("user1", "1234"));
        users.put("user2", new User("user2", "5678"));

        System.out.println("Welcome to the ATM System!");

        // User authentication
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        User currentUser = users.get(userId);

        if (currentUser != null && currentUser.getPin().equals(pin)) {
            System.out.println("Authentication successful. Welcome, " + userId + "!");
            performATMOperations(currentUser, scanner, users);
        } else {
            System.out.println("Invalid credentials. Exiting.");
        }

        scanner.close();
    }

    private static void performATMOperations(User user, Scanner scanner, Map<String, User> users) {
        while (true) {
            System.out.println("\nATM Operations:");
            System.out.println("1. Transactions History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayTransactionHistory(user);
                    break;
                case 2:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    user.withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    user.deposit(depositAmount);
                    break;
                case 4:
                    System.out.print("Enter recipient's User ID: ");
                    String recipientId = scanner.next();
                    User recipient = user.get(recipientId, users);

                    if (recipient != null) {
                        System.out.print("Enter transfer amount: ");
                        double transferAmount = scanner.nextDouble();
                        user.transfer(recipient, transferAmount);
                    } else {
                        System.out.println("Recipient not found.");
                    }
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayTransactionHistory(User user) {
        System.out.println("\nTransaction History for " + user.getUserId() + ":");
        Map<Integer, String> transactionHistory = user.getTransactionHistory();

        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (Map.Entry<Integer, String> entry : transactionHistory.entrySet()) {
                System.out.println(entry.getKey() + ". " + entry.getValue());
            }
        }
    }
}

