module com.pluralsight.bnyfinanceledger {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires mysql.connector.j;
    requires java.desktop;


    opens com.pluralsight to javafx.fxml;
    exports com.pluralsight;
}