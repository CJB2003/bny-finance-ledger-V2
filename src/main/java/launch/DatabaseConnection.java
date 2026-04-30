package launch;

import com.pluralsight.Transactions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class DatabaseConnection {

    public Connection databaselink;

    //Connection method that serves as a database connection for Java and MySQL
    public Connection getConnection() {

        //Credentials needed to log into MySQL server
        String databaseName = "bnyfinancialcorp";
        String databaseUser = "root";
        String databasePassword = "Chrisjb@2003";
        String url = "jdbc:mysql://localhost:3306/" + databaseName;

        try {

            //this is what actually translates Java for SQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaselink = DriverManager.getConnection(url, databaseUser, databasePassword);
            System.out.println("Database connection successful!");
        } catch(Exception e) {
            e.printStackTrace();
        }
        return databaselink;
    }
    //Saves information from transactions.csv file to MySQL database
    public void saveToDataBase(ArrayList<Transactions> list) {
        String insert = "INSERT INTO transactions (date, time, description, vendor, amount) VALUES (?, ?, ?, ?, ?)";

        try {
            //calling getConnection method
            Connection connect = getConnection();
            //instructions allowing SQL to fill in the blanks for the ?
            PreparedStatement prepStatement = connect.prepareStatement(insert);

            /*
            The java.sql lines are for conversion purposes for SQL
            YYYY-MM-DD for date and HH:MM:SS for time
            */
            for (Transactions t : list) {
                    prepStatement.setDate(1, java.sql.Date.valueOf(t.getDate()));
                    prepStatement.setTime(2, java.sql.Time.valueOf(t.getTime()));
                    prepStatement.setString(3, t.getDescription());
                    prepStatement.setString(4, t.getVendor());
                    prepStatement.setDouble(5, t.getAmount());

                    prepStatement.execute();
            }
            System.out.println("Transactions have been successfully sent over to database!");

            } catch(Exception e) {
                e.printStackTrace();
                System.out.println("Could not save to database.");
        }
    }
}
