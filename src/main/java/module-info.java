module com.pluralsight {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires mysql.connector.j;
    requires java.desktop;

    opens com.pluralsight to javafx.fxml;
    exports com.pluralsight;
    exports controllers;
    opens controllers to javafx.fxml;
    exports launch;
    opens launch to javafx.fxml;
}