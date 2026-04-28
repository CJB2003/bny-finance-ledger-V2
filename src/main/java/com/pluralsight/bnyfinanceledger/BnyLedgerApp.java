package com.pluralsight.bnyfinanceledger;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class BnyLedgerApp {
    static Scanner myScanner = new Scanner(System.in);
    static TextFormatter formatter = new TextFormatter();

    public static void main(String[] args) throws InterruptedException {

        //boolean variable set to true
        boolean appRunning = true;

        //while loop for the menu with multiple options
        while (appRunning) {
            //Welcomes the user to the account ledger experience at BNY Financial Corp.
            System.out.println(
            formatter.bold("""
            ==============================
            Welcome to BNY Financial Corp.
            ==============================
            """));
            Thread.sleep(1000);
            System.out.println(
                            """
                            D) Make Deposit
                            P) Make Payment (Debit)
                            L) Go to ledger
                            X) Exit
                            """
            );
            Thread.sleep(1000);
            System.out.println("Select one of the options above.\n");
            System.out.print("Selection: ");
            //Storing user input in userChoice and keeps the input uppercase
            //without using an ignore case
            String userChoice = myScanner.nextLine().toUpperCase();
            System.out.println();

            switch (userChoice) {
                case "D":
                    makeDeposit();
                    break;
                case "P":
                    makePayment();
                    break;
                case "L":
                    ledger();
                    break;
                case "X":
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.\n");

            }
        }
    }

    //method for making a deposit
    public static void makeDeposit() {

        //prompting user to enter values
        System.out.println("\nYou have selected to deposit.");
        System.out.print("Please enter a description: ");
        String userDesc = myScanner.nextLine();
        System.out.print("Please enter the vendor name: ");
        String userVendor = myScanner.nextLine();
        System.out.print("Please enter the amount: ");
        double userAmount = myScanner.nextDouble();
        myScanner.nextLine();

        //this ensures all deposits are greater than zero
        if (userAmount <= 0) {
            System.out.println("Error: The deposit amount is less than zero!");
        } else {
            //create new object for deposits and saving to
            Transactions deposit = new Transactions(LocalDate.now(), LocalTime.now(), userDesc, userVendor, userAmount);

            //deposit information gets written to transaction.csv
            FileManager.writeToFile(deposit);
            System.out.println("Your deposit was successful.");
        }
    }

    //method for making a payment
    public static void makePayment() {
        //similar to the makeDeposit method, prompts user
        System.out.println("\nYou have selected to make a payment.");
        System.out.print("Please enter a description: ");
        String userDesc = myScanner.nextLine();
        System.out.print("Please enter the vendor name: ");
        String userVendor = myScanner.nextLine();
        System.out.print("Please enter the amount: ");
        double userAmount = myScanner.nextDouble();
        myScanner.nextLine();

        //if the amount the user enters is positive, it forces the input to be negative
        if (userAmount > 0) {
            userAmount *= -1;
        }
        //writes values of object payment to the transactions.csv file
        Transactions payment = new Transactions(LocalDate.now(), LocalTime.now(), userDesc, userVendor, userAmount);
        FileManager.writeToFile(payment);
        System.out.println("Your payment was successful!");
    }

    //method for the ledger opener
    public static void ledger() throws InterruptedException {
        //similar to the home menu, just with different options
        boolean ledgerOpen = true;

        while(ledgerOpen) {
            System.out.println(formatter.bold(
            """
             ==============
            || Bny Ledger ||
             ==============
            """));
            Thread.sleep(1000);
            System.out.println(
                    """
                    A) All
                    D) Deposits
                    P) Payments
                    R) Reports
                    H) Home
                    """
            );
            Thread.sleep(1000);
            System.out.println("Select one of the options above.\n");
            System.out.print("Selection: ");
            String command = myScanner.nextLine().toUpperCase();
            System.out.println();

            //reads/loads the transactions from file manager class into the array list
            ArrayList<Transactions> all = FileManager.loadTransactions();
            //In order to get newest to oldest, I need to reverse the order of transactions
            //in the array
            Collections.reverse(all);

            switch(command) {
                case "A":
                    displayAll(all);
                    break;
                case "D":
                    displayDeposits();
                    break;
                case "P":
                    displayPayments();
                case "R":
                    reports();
                    break;
                case "H":
                    return;
                default:
                    System.out.println("Invalid Choice. Try again.\n");
            }
        }
    }
    public static void displayAll(ArrayList<Transactions> list) {
        System.out.printf("%s | %s | %s | %s | %s\n", "Date", "Time", "Description", "Vendor", "Amount");

        for (Transactions t : list) {
            System.out.printf("%s | %s | %s | %s | %.2f\n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
        }
    }
    public static void displayDeposits() {

    }
    public static void displayPayments() {

    }
    public static void reports() {

    }
}


