package controllers;

import com.pluralsight.Transactions;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import launch.DatabaseConnection;
import launch.Model;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Label accountBalance;
    @FXML
    private TextArea amountTextArea;
    @FXML
    private Button confirmTransButton;
    @FXML
    private TextField descTextField;
    @FXML
    private Label expensesBalance;
    @FXML
    private Label incomeBalance;
    @FXML
    private Label loginDate;
    @FXML
    private TextField vendorTextField;
    @FXML
    private Label welcomeUser;
    @FXML
    private Label confirmTextLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Sets the welcome user text at the top left
        if (welcomeUser != null) {
            welcomeUser.setText("Welcome, " + Model.getInstance().getLoggedInFirstName());
        }
        //Setting login date to the local date
        if (loginDate != null) {
            loginDate.setText("Today, " + LocalDate.now());
        }
        //Loads the balance labels
        refreshBalanceLabels();
        //
        confirmTransButton.setOnAction(event -> onConfirmTransaction());

    }

    private void refreshBalanceLabels() {
        //Connects to database and gets transactions from there
        DatabaseConnection db =  new DatabaseConnection();
        ArrayList<Transactions> allT = db.getTransactionsFromDB();

        //Setting default values here, I want to update the income and expenses you see on the dashboard
        double totalIncome = 0.0;
        double totalExpense = 0.0;

        for (Transactions t : allT) {
            //Adds amount to income if amount is greater than 0, otherwise it will get added to total expense
            if (t.getAmount() > 0) {
                totalIncome += t.getAmount();
            } else {
                totalExpense += t.getAmount();
            }
        }

        //Balance is the sum of income and expense, expenses are negative so will subtract from the income
        double balance = totalIncome + totalExpense;

        //This sets the text and updates the labels
        accountBalance.setText(String.format("$%,.2f", balance));
        incomeBalance.setText(String.format("+ $%,.2f", totalIncome));
        //Used absolute value here so it displays normally
        expensesBalance.setText(String.format("- $%,.2f", Math.abs(totalExpense)));
    }

    private void onConfirmTransaction() {
        //Getting info from the text fields, storing into variables
        String vendor = vendorTextField.getText().trim();
        String desc = descTextField.getText().trim();
        String amountText = amountTextArea.getText().trim();

        //Input validation here, makes sure none of the fields are empty
        if (vendor.isEmpty() || desc.isEmpty() || amountText.isEmpty()) {
            confirmTextLabel.setText("Please Fill all fields!");
            confirmTextLabel.setStyle("-fx-text-fill: #993C1D;");


            //Resetting the text to empty, after a short pause
            Platform.runLater(() -> {
                try {
                    Thread.sleep(2000);
                    confirmTextLabel.setText("");
                } catch (InterruptedException ignored) {
                    System.out.println("Text could not be set empty.");
                }
            });
            return;
        }
        double amount;
        try {
            //Converts text that user typed into a double
            amount = Double.parseDouble(amountText);
            //In case user types something that can't be converted to an integer
        } catch(NumberFormatException e) {
            confirmTextLabel.setText("Invalid amount! Please Try again");
            confirmTextLabel.setStyle("-fx-text-fill: #993C1D; -fx-font-weight: bold;");

            Platform.runLater(() -> {
                try {
                    Thread.sleep(2000);
                    confirmTextLabel.setText("");
                } catch(InterruptedException ignored) {
                    System.out.println("Text could not be set empty");
                }
            });
            return;
        }
        //Creating transactions object that stores the new data typed in the text fields with current time and date
        LocalDate now = LocalDate.now();
        LocalTime timeNow = LocalTime.now();
        Transactions newTransaction = new Transactions(now, timeNow, desc, vendor, amount);

        //Saving the data to my database, creating new array list
        DatabaseConnection db = new DatabaseConnection();
        ArrayList<Transactions> newList = new ArrayList<>();
        newList.add(newTransaction);
        db.saveToDataBase(newList);
        //Resets the ledger so the table gets updated after user saves info to the database
        Model.getInstance().getViewSwap().resetLedgerView();
        refreshBalanceLabels();

        //After saving, clearing the text fields...
        vendorTextField.clear();
        descTextField.clear();
        amountTextArea.clear();

        //Refreshing the total balance to update it
        refreshBalanceLabels();

        //Sets the text label depending on amount
        if (amount > 0) {
            confirmTextLabel.setText("Deposit saved!");
        } else {
            confirmTextLabel.setText("Payment saved!");
        }
        //Sets text style to green and bold
        confirmTextLabel.setStyle("-fx-text-fill: #0F6E56; -fx-font-weight: bold;");

        //Waits a bit before setting the text label back to empty
        PauseTransition pause = new PauseTransition(Duration.millis(1800));
        pause.setOnFinished(e -> {
            confirmTextLabel.setText("");
        });
        pause.play();
    }

}
