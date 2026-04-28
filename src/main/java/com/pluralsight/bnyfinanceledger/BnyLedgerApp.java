package com.pluralsight.bnyfinanceledger;

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
            ====================================
            || Welcome to BNY Financial Corp. ||
            ====================================
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
            /*
            Storing user input in userChoice and keeps the input uppercase
            without using an ignore case
            */
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
    //Created method for array list loading and formatting for reusability
    public static ArrayList<Transactions> all() {
        //reads/loads the transactions from file manager class into the array list
        ArrayList<Transactions> all = FileManager.loadTransactions();
        /*
        In order to get newest to oldest, I need to reverse the order of transactions
        in the array
        */
        Collections.reverse(all);
        return all;
    }

    //method for the ledger opener
    public static void ledger() throws InterruptedException {
        //similar to the home menu, just with different options
        boolean ledgerOpen = true;

        while(ledgerOpen) {
            System.out.println(formatter.bold(
            """
            ================
            || Bny Ledger ||
            ================
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

            switch(command) {
                case "A":
                    displayAll(all());
                    break;
                case "D":
                    displayDeposits(all());
                    break;
                case "P":
                    displayPayments(all());
                    break;
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
    //Displays all transactions
    public static void displayAll(ArrayList<Transactions> list) {
        //adding formatted header
        System.out.printf("%s | %s | %s | %s | %s\n", "Date", "Time", "Description", "Vendor", "Amount");

        //For-each loop that prints out in a formatted way
        for (Transactions t : list) {
            System.out.printf("%s | %s | %s | %s | %.2f\n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
        }
    }
    //Displays all deposits
    public static void displayDeposits(ArrayList<Transactions> list) {
        System.out.printf("%s | %s | %s | %s | %s\n", "Date", "Time", "Description", "Vendor", "Amount");

        //For-each loop
        for (Transactions t : list) {
            /*
            Important for displaying deposits, ensures that amount is always greater than 0
            Greater than 0 = deposits
            */
            if (t.getAmount() > 0) {
                System.out.printf("%s | %s | %s | %s | %.2f\n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
            }
        }

    }
    //Displays all payments, opposite of deposits
    public static void displayPayments(ArrayList<Transactions> list) {
        System.out.printf("%s | %s | %s | %s | %s\n", "Date", "Time", "Description", "Vendor", "Amount");

        //For-each loop
        for (Transactions t : list) {
            /*
            Ensures that amount is always a negative number
            Less than 0 is a payment
            */
            if (t.getAmount() < 0) {
                System.out.printf("%s | %s | %s | %s | %.2f\n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
            }
        }

    }
    //Reports menu
    public static void reports() {


        boolean reportOpen = true;

        //Another while loop for a menu...
        while(reportOpen) {
            System.out.println(formatter.bold("""
                    ===========================
                    || BNY Financial Reports ||
                    ===========================
                    """));
            System.out.println("""
                    1) Month to Date
                    2) Previous Month
                    3) Year to Date
                    4) Previous Year
                    5) Search by Vendor
                    0) Back
                    """);

            System.out.println("Select a choice above.");
            System.out.print("Selection: ");
            int choice = myScanner.nextInt();

            //Switch statement for choice
            switch(choice) {
                case 1:
                    monthToDate(all());
                    break;
                case 2:
                    prevMonth();
                    break;
                case 3:
                    yearToDate();
                    break;
                case 4:
                    prevYear();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Try Again.\n");
            }

        }

    }
    //Getting the current month
    public static void monthToDate(ArrayList<Transactions> list) {
        /*
        Creating new array list that will have values added
        if condition is met
         */
        ArrayList<Transactions> result = new ArrayList<>();

        /*
        Variables storing current month and year
        I want both current month and year in case there is input from different years
        */
        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();

        /*
        for-each loop, gets the actual month and year values
        Checks whether they are the same as current month and current year
        if both are true, it will that information to the new array
        */
        for (Transactions t : list) {
            if (t.getDate().getMonthValue() == currentMonth && t.getDate().getYear() == currentYear) {

                result.add(t);
            }
        }
        //reusing display method to format array list result
        displayAll(result);
    }
    //Getting the previous month
    public static void prevMonth(ArrayList<Transactions> list) {
        //Creating new array list
        ArrayList<Transactions> result = new ArrayList<>();

        /*
        Local date variable storing last month value
        Instead of using getMonthValue() - 1, I used minusMonths methods instead
        Fewer bugs in the future
         */
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        int month = lastMonth.getMonthValue();
        int year = lastMonth.getYear();

        //Same for-each from monthToDate method
        for (Transactions t : list) {
            if (t.getDate().getMonthValue() == month &&
                    t.getDate().getYear() == year) {

                result.add(t);
            }
        }
        displayAll(result);
    }
    public static void yearToDate() {

    }
    public static void prevYear() {

    }
    public static void searchByVendor() {

    }
}


