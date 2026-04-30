package launch;

import com.pluralsight.Transactions;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;

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

    //array list method that gets transactions from SQL
    public ArrayList<Transactions> getTransactionsFromDB() {

        ArrayList<Transactions> getT = new ArrayList<>();
        String query = "SELECT date, time, description, vendor, amount FROM transactions ORDER BY date DESC, time DESC";

        try {
            Connection connect = getConnection();
            Statement statement = connect.createStatement();
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                LocalDate date = result.getDate("Date").toLocalDate();
                LocalTime time = result.getTime("Time").toLocalTime();
                String desc = result.getString("Description");
                String vendor = result.getString("Vendor");
                double amount = result.getDouble("Amount");

                getT.add(new Transactions(date, time, desc, vendor, amount));
            }

        } catch(Exception e) {
            System.out.println("Could not load transactions database.");
            e.printStackTrace();
        }
        return getT;
    }
}
