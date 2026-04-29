package controllers;

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

    }

}

