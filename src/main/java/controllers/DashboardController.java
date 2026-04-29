package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Label accountBalance;
    @FXML
    private Label accountNum;
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
    private ListView<?> transactionList;
    @FXML
    private TextField vendorTextField;
    @FXML
    private Text welcomeUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
