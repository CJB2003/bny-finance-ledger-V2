package com.pluralsight.bnyfinanceledger;

import com.pluralsight.bnyfinanceledger.Transactions;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class FileManager {

    //method for writing to file
    public static void writeToFile(Transactions transaction) {

        try {
            //Buffered writer with file writer
            BufferedWriter bWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true));

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

    public static List<Transactions> loadTransactions() {
        List<Transactions> list = new ArrayList<>();

        try (BufferedReader bReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"))) {

            String line;

            while ((line = bReader.readLine()) != null) {
                String[] parts = line.split("\\|");

                LocalDate date = LocalDate.parse(parts[0]);
                LocalTime time = LocalTime.parse(parts[1]);
                String desc = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);

                Transactions t = new Transactions(date, time, desc, vendor, amount);

                list.add(t);
            }

        } catch (IOException e) {
            System.out.println("No file found yet.");
        }

        return list;
    }
}