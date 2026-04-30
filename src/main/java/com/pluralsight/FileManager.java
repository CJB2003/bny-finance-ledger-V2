package com.pluralsight;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class FileManager {
    //creating a shortcut, so I don't have to type the pathname out everytime
    static File file = new File("src/main/resources/transactions.csv");

    //method for writing to file
    public static void writeToFile(Transactions transaction) {

        try {
            /*
            EmptyFile is true if transaction file is empty otherwise it will be false
            Using this to add header to file if file is empty
             */
            boolean emptyFile = file.length() == 0;

            //Buffered writer with file writer
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(file, true));

            //writes the header into the file if the file is empty
            if (emptyFile) {
                bWriter.write("Date|Time|Description|Vendor|Amount");
                bWriter.newLine();
            }

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

    //method for loading the transactions into an array list and formatting it
    public static ArrayList<Transactions> loadTransactions() {
        ArrayList<Transactions> list = new ArrayList<>();

        try {
            //buffered reader and file reader
            BufferedReader bReader = new BufferedReader(new FileReader(file));

            //variable that will later contain the read method
            String line;

            //skips the header I made
            bReader.readLine();

            //reads each line from the file while it's not empty and splits by pipe
            while ((line = bReader.readLine()) != null) {
                String[] parts = line.split("\\|");

                //parses the data by index
                LocalDate date = LocalDate.parse(parts[0]);
                LocalTime time = LocalTime.parse(parts[1]);
                String desc = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);

                //storing the values into object t
                Transactions t = new Transactions(date, time, desc, vendor, amount);

                //adding to array list
                list.add(t);
            }
            bReader.close();
        } catch (Exception e) {
            System.out.println("No file found yet.");
        }

        return list;
    }
}