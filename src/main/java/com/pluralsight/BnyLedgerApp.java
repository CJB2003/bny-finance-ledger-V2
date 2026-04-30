package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class BnyLedgerApp {
    static final Scanner myScanner = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {

        //while loop for the menu with multiple options
        while (true) {
            //Welcomes the user to the account ledger experience at BNY Financial Corp.
            System.out.println(
                    TextFormatter.bold("""
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
        try {
            //prompting user to enter values
            System.out.println("\nYou have selected to deposit.");
            System.out.print("Please enter a description: ");
            String userDesc = myScanner.nextLine();
            System.out.print("Please enter the vendor name: ");
            String userVendor = myScanner.nextLine();
            System.out.print("Please enter the amount: ");
            double userAmount = myScanner.nextDouble();

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
        } catch(Exception e) {
            myScanner.nextLine();
        }
    }
    //method for making a payment
    public static void makePayment() {

        try {
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
        } catch(Exception e) {
            myScanner.nextLine();
        }
    }

    //Created method for array list loading and formatting for reusability
    public static ArrayList<Transactions> all() {
        //reads/loads the transactions from file manager class into the array list
        ArrayList<Transactions> all = FileManager.loadTransactions();
        /*
        In order to get newest to oldest, I need to reverse the order of transactions
        in the array. Changed to where it's based on the date of the transaction
        */
        all.sort(Comparator.comparing(Transactions::getDate).thenComparing(Transactions::getTime).reversed());

        return all;
    }

    //method for the ledger opener
    public static void ledger() throws InterruptedException {
        //similar to the home menu, just with different options
        boolean ledgerOpen = true;

        while (ledgerOpen) {
            System.out.println(TextFormatter.bold(
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

            switch (command) {
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
                    ledgerOpen = false;
                    break;
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
            System.out.printf("%s | %s | %s | %s | %s\n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), TextFormatter.colorForAmount(t.getAmount()));
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
                System.out.printf("%s | %s | %s | %s | %s\n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), TextFormatter.colorForAmount(t.getAmount()));
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
                System.out.printf("%s | %s | %s | %s | %s\n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), TextFormatter.colorForAmount(t.getAmount()));
            }
        }

    }

    //Reports menu
    public static void reports() {


        boolean reportOpen = true;

        //Another while loop for a menu...
        while (reportOpen) {
            System.out.println(TextFormatter.bold("""
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
            myScanner.nextLine();

            //Switch statement for choice
            switch (choice) {
                case 0:
                    reportOpen = false;
                    break;
                case 1:
                    monthToDate(all());
                    break;
                case 2:
                    prevMonth(all());
                    break;
                case 3:
                    yearToDate(all());
                    break;
                case 4:
                    prevYear(all());
                    break;
                case 5:
                    searchByVendor(all());
                    break;
                default:
                    System.out.println("Invalid choice. Try Again.\n");
            }

        }

    }

    //Getting data from the current month
    public static void monthToDate(ArrayList<Transactions> list) {
        /*
        Creating new array list that will have values added
        if condition is met
         */
        ArrayList<Transactions> resultMonth = new ArrayList<>();

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
        for (Transactions monthDate : list) {
            if (monthDate.getDate().getMonthValue() == currentMonth && monthDate.getDate().getYear() == currentYear) {

                resultMonth.add(monthDate);
            }
        }
        //reusing display method to format array list result
        displayAll(resultMonth);
    }

    //Getting data from the previous month
    public static void prevMonth(ArrayList<Transactions> list) {
        //Creating new array list
        ArrayList<Transactions> resultPrevMonth = new ArrayList<>();

        /*
        Local date variable storing last month value
        Instead of using getMonthValue() - 1, I used minusMonths methods instead
        Fewer bugs in the future
         */
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        int month = lastMonth.getMonthValue();
        int year = lastMonth.getYear();

        //Same for-each from monthToDate method
        for (Transactions prevMonth : list) {
            if (prevMonth.getDate().getMonthValue() == month &&
                    prevMonth.getDate().getYear() == year) {

                resultPrevMonth.add(prevMonth);
            }
        }
        displayAll(resultPrevMonth);
    }

    //Gets data from current year, same concept as monthToDate
    public static void yearToDate(ArrayList<Transactions> list) {
        ArrayList<Transactions> resultYear = new ArrayList<>();

        //Only difference for year is that you don't need the month
        int year = LocalDate.now().getYear();

        for (Transactions yearDate : list) {
            if (yearDate.getDate().getYear() == year) {

                resultYear.add(yearDate);
            }
        }
        displayAll(resultYear);
    }

    //Gets the data from previous year
    public static void prevYear(ArrayList<Transactions> list) {
        ArrayList<Transactions> resultPrevYear = new ArrayList<>();

        //Subtracts 1 from the current year to get previous year
        int year = LocalDate.now().getYear() - 1;

        //Same for-each loop
        for (Transactions prevYear : list) {
            if (prevYear.getDate().getYear() == year) {

                resultPrevYear.add(prevYear);
            }
        }
        displayAll(resultPrevYear);
    }

    public static void searchByVendor(ArrayList<Transactions> list) {

        ArrayList<Transactions> vendorResult = new ArrayList<>();

        //Prompting user to enter a vendor name
        System.out.println("Enter a vendor: ");
        String userVendor = myScanner.nextLine();

        /*
        Ignoring case sensitivity. If the vendor value is the same as user vendor
        add it to the array list
        */
        for (Transactions vendor : list) {
            //Made it to where the user can search for a part of the vendor name without having to fully type it out
            if (vendor.getVendor().toLowerCase().contains(userVendor.toLowerCase())) {
                vendorResult.add(vendor);
            }
        }
        displayAll(vendorResult);
    }
}


