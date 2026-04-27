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
            System.out.println(((32)));
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
    //method for writing to file
    private static void writeToFile(Transactions transaction) {

        try {
            //Buffered writer with file writer
            BufferedWriter bWriter = new BufferedWriter(new FileWriter("transactions.csv", true));

            //writes the information in format from the method toFile() in transaction class
            bWriter.write(transaction.toFile());

            //goes to the next row for next transaction if needed
            bWriter.newLine();

            //closes the writer
            bWriter.close();

        } catch (Exception e) {
            System.out.println("Error: Could not write to file.");
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
            System.out.println("Error: deposit amount is less than zero!");
        } else {
            //create new object for deposits and saving to
            Transactions deposit = new Transactions(LocalDate.now(), LocalTime.now(), userDesc, userVendor, userAmount);

            //deposit information gets written to transaction.csv
            writeToFile(deposit);
            System.out.println("Your deposit was successful.");
        }
    }

    public static void makePayment() {


    }
    public static void Ledger() {

    }
}
