package com.pluralsight.bnyfinanceledger;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class BnyLedgerApp {
    static Scanner myScanner = new Scanner(System.in);


    public static void main(String[] args) throws InterruptedException {

        //boolean variable set to true
        boolean appRunning = true;

        //while loop for the menu with multiple options
        while (appRunning) {
            //Welcomes the user to the account ledger experience at BNY Financial Corp.
            System.out.println(("Welcome to BNY Financial Corp."));
            Thread.sleep(1000);
            System.out.println(
                    """
                            D) Make Deposit
                            P) Make Payment (Debit)
                            L) Go to Ledger
                            X) Exit
                            """
            );
            Thread.sleep(1000);
            System.out.println("Select one of the options above.\n");
            System.out.print("Selection: ");
            //Storing user input in userChoice and keeps the input uppercase
            //without using an ignore case
            String userChoice = myScanner.nextLine().toUpperCase();

            switch (userChoice) {
                case "D":
                    makeDeposit();
                    break;
                case "P":
                    makePayment();
                    break;
                case "L":
                    Ledger();
                    break;
                case "X":
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");

            }


        }
    }

    //method for making a deposit
    public static void makeDeposit() {

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
        //similar
        System.out.println("\nYou have selected to make a payment.");
        System.out.print("Please enter a description: ");
        String userDesc = myScanner.nextLine();
        System.out.print("Please enter the vendor name: ");
        String userVendor = myScanner.nextLine();
        System.out.print("Please enter the amount: ");
        double userAmount = myScanner.nextDouble();
        myScanner.nextLine();

        if (userAmount > 0) {
            System.out.println("Error: The amount you entered is greater than zero!");
        }
        else {
            Transactions payment = new Transactions(LocalDate.now(), LocalTime.now(), userDesc, userVendor, userAmount);

        }

    }
    public static void Ledger() {

    }
}
