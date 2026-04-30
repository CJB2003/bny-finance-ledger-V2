package controllers;

import com.pluralsight.Transactions;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import launch.DatabaseConnection;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LedgerController implements Initializable {

    //Transaction objects for the table
    @FXML
    private TableView<Transactions> transactionTable;
    @FXML
    private TableColumn<Transactions, String> dateColumn;
    @FXML
    private TableColumn<Transactions, String> timeColumn;
    @FXML
    private TableColumn<Transactions, String> descColumn;
    @FXML
    private TableColumn<Transactions, String> vendorColumn;
    @FXML
    private TableColumn<Transactions, Double> amountColumn;
    @FXML
    private Button allButton;
    @FXML
    private Button depositsButton;
    @FXML
    private Button paymentsButton;
    @FXML
    private Button reportsButton;
    @FXML
    private Label accountLabel;

    private ArrayList<Transactions> allT;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setColumns();
        /*
        Assigning amountColumn to use the method colorAmountCell so the colors change
        Renders for each row repeatedly
         */
        amountColumn.setCellFactory(col -> colorAmountCell());

        loadTable();
    }

    //Setting up columns for the table
    private void setColumns() {

        /*
        Tells what values are being displayed in each column, converts time and date to strings.
        The SimpleStringProperty basically allows javafx read and render the data onto the columns
         */
        dateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDate().toString()));
        timeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTime().toString()));
        descColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription()));
        vendorColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getVendor()));
        amountColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getAmount()));
    }

    //method
    private TableCell<Transactions, Double> colorAmountCell() {
        //creating new table cell class instead of separate class
        return new TableCell<>() {
            @Override
            protected void updateItem(Double amount, boolean empty) {
                //Ensures no bugs, calls parent class of updateItem, a built-in method of javafx
                super.updateItem(amount, empty);

                //checks if cell is empty and will set text to null displaying nothing
                if (empty || amount == null) {
                    setText(null);
                } else {
                    //Forgot to format the text properly for amount
                    setText(String.format("%.2f", amount));
                    //Sets text to green or red depending on whether the amount if positive or negative
                    if (amount > 0) {
                        setStyle("-fx-text-fill: #0F6E56; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: #993C1D; -fx-font-weight: bold;");
                    }
                }
            }
        };
    }

    //Connects to the SQL database and gets data from transaction table
    public void loadTable() {
        DatabaseConnection db = new DatabaseConnection();
        allT = db.getTransactionsFromDB();

        transactionTable.getItems().setAll(allT);

        //waits until scene is fully rendered, just wanting to check if my transaction table is actually loading, was getting bugs
        Platform.runLater(() -> {
            System.out.println("Table width: " + transactionTable.getWidth());
            System.out.println("Table height: " + transactionTable.getHeight());
        });
    }

    //When all button is pressed, it shows all the data in table
    public void onAll(ActionEvent event) {
        setActiveButton(allButton);
        transactionTable.getItems().setAll(allT);
    }

    /*
    Will only add transactions with a positive amount to table for deposits
    When deposit button is pressed
     */
    public void onDeposits(ActionEvent event) {
        setActiveButton(depositsButton);
        ArrayList<Transactions> deposits = new ArrayList<>();
        for (Transactions t : allT) {
            if (t.getAmount() > 0) {
                deposits.add(t);
            }
        }
        //sets the table to deposits
        transactionTable.getItems().setAll(deposits);
    }

    //Opposite of deposits, adds transactions that are negative
    public void onPayments(ActionEvent event) {
        setActiveButton(paymentsButton);
        ArrayList<Transactions> payment = new ArrayList<>();
        for (Transactions p : allT) {
            if (p.getAmount() < 0) {
                payment.add(p);
            }
        }
        transactionTable.getItems().setAll(payment);
    }

    public void onReports(ActionEvent event) {
        setActiveButton(reportsButton);
    }

    //This will highlight the button when the button is actively being used
    private void setActiveButton(Button active) {
        String inactive = "-fx-background-color: transparent; -fx-text-fill: rgba(255,255,255,0.6); -fx-font-size: 13px; -fx-padding: 15 35 15 35; -fx-cursor: hand; -fx-border-color: transparent; -fx-border-width: 0 0 3 0;";
        String activeStyle = "-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 15 35 15 35; -fx-cursor: hand; -fx-border-color: transparent transparent white transparent; -fx-border-width: 0 0 3 0;";

        allButton.setStyle(inactive);
        depositsButton.setStyle(inactive);
        paymentsButton.setStyle(inactive);
        reportsButton.setStyle(inactive);

        active.setStyle(activeStyle);
    }
}
