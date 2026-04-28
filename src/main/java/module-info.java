module com.pluralsight.bnyfinanceledger {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.pluralsight.bnyfinanceledger to javafx.fxml;
    exports com.pluralsight.bnyfinanceledger;
}