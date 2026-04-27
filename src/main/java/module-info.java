module com.pluralsight.bnyfinanceledger {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.pluralsight.bnyfinanceledger to javafx.fxml;
    exports com.pluralsight.bnyfinanceledger;
}