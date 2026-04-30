package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class LedgerController implements Initializable {

    @FXML
    private TableView transactionTable;
    @FXML
    private TableColumn dateColumn;
    @FXML
    private TableColumn timeColumn;
    @FXML
    private TableColumn descColumn;
    @FXML
    private TableColumn vendorColumn;
    @FXML
    private TableColumn amountColumn;
    @FXML
    private Button allButton;
    @FXML
    private Button depositsButton;
    @FXML
    private Button paymentsButton;
    @FXML
    private Button reportsButton;
    @FXML
    private Button homeButton;
    @FXML
    private Label accountLabel;

    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    public void onAll(ActionEvent event) {
    }

    public void onDeposits(ActionEvent event) {
    }

    public void onPayments(ActionEvent event) {
    }

    public void onReports(ActionEvent event) {
    }

    public void onHome(ActionEvent event) {
    }
}
