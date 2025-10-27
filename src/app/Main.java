package app;

import service.BankService;
import service.impl.BankServiceImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Console based Banking Application
        System.out.println("\nWelcome to our Console based Banking Application\n");
        Scanner sc = new Scanner(System.in);
        BankService bankService = new BankServiceImpl();
        boolean running = true;
        while(running){
            System.out.println("""
                1) Open Account
                2) Deposit
                3) Withdraw
                4) Transfer
                5) Account Statement
                6) List Accounts
                7) Search Accounts by Customer Name
                8) Exit
                """);
            System.out.println("CHOOSE: ");
            String choice = sc.nextLine().trim();
            System.out.println("CHOICE: " + choice);

            switch (choice){
                case "1" -> openAccount(sc, bankService);
                case "2" -> deposit(sc,  bankService);
                case "3" -> withdraw(sc,  bankService);
                case "4" -> transfer(sc,  bankService);
                case "5" -> statement(sc,  bankService);
                case "6" -> listAccounts(sc, bankService);
                case "7" -> searchAccounts(sc,  bankService);
                default -> running = false;
            }
        }

    }

    private static void openAccount(Scanner sc, BankService bankService) {
        System.out.println("Customer name: ");
        String name = sc.nextLine().trim();
        System.out.println("Customer email: ");
        String email = sc.nextLine().trim();
        System.out.println("Account Type (SAVINGS/CURRENT): ");
        String type = sc.nextLine().trim();
        System.out.println("Initial Deposit (optional, blank for 0): ");
        String amountStr = sc.nextLine().trim();
        Double initial = Double.valueOf(amountStr);
        String accountNumber = bankService.openAccount(name, email, type);
        if(initial > 0){
            bankService.deposit(accountNumber, initial, "Initial Deposit");
        }
        System.out.println("Account created successfully: " + accountNumber);
    }

    private static void deposit(Scanner sc, BankService bankService) {
        System.out.println("Account Number: ");
        String accountNumber = sc.nextLine().trim();
        System.out.println("Amount: ");
        Double amount = Double.valueOf(sc.nextLine().trim());
        bankService.deposit(accountNumber, amount, "Deposit");
        System.out.println("Deposited");
    }

    private static void withdraw(Scanner sc, BankService bankService) {
        System.out.println("Account Number: ");
        String accountNumber = sc.nextLine().trim();
        System.out.println("Amount: ");
        Double amount = Double.valueOf(sc.nextLine().trim());
        bankService.withdraw(accountNumber, amount, "Withdraw");
        System.out.println("Amount Withdrawn");
    }

    private static void transfer(Scanner sc, BankService bankService) {
    }

    private static void statement(Scanner sc, BankService bankService) {
    }

    private static void listAccounts(Scanner sc, BankService bankService) {
        bankService.listAccounts().forEach(a -> {
            System.out.println(a.getAccountNumber() + " | " + a.getAccountType() + " | " + a.getBalance());
        });
    }

    private static void searchAccounts(Scanner sc, BankService bankService) {
    }


}
