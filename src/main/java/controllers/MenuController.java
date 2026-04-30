package controllers;

import javafx.stage.Stage;
import launch.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private Button dashboardButton;
    @FXML
    private Button ledgerButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button transactionsButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListener();
    }

    //Action events that will swap between screens depending on what is pressed
    private void addListener() {
        dashboardButton.setOnAction(event -> onDashboard());
        transactionsButton.setOnAction(event -> onTransactions());
        ledgerButton.setOnAction(event -> onLedger());
        logoutButton.setOnAction(event -> onLogout());
    }

    private void onDashboard() {
        Model.getInstance().getViewSwap().getUserSelectedMenuItem().set("Dashboard");
    }
    private void onTransactions() {
        Model.getInstance().getViewSwap().getUserSelectedMenuItem().set("Transactions");
    }
    private void onLedger() {
        Model.getInstance().getViewSwap().getUserSelectedMenuItem().set("Ledger");
    }
    private void onLogout() {
        Stage currentStage = (Stage) logoutButton.getScene().getWindow();
        currentStage.close();
        Model.getInstance().getViewSwap().showLoginView();
    }
}

