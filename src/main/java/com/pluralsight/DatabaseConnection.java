package com.pluralsight;

import java.sql.Connection;
import java.sql.DriverManager;

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
}
