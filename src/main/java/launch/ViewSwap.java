package launch;

import controllers.UserSideController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ViewSwap {

    @FXML
    private final StringProperty userSelectedMenuItem;
    @FXML
    private AnchorPane dashboardView;
    @FXML
    private AnchorPane transactionsView;
    @FXML
    private AnchorPane ledgerView;


    public ViewSwap() {

        this.userSelectedMenuItem = new SimpleStringProperty("");

    }

    //Return the property itself, not just the string
    public StringProperty getUserSelectedMenuItem() {
        return userSelectedMenuItem;
    }

    //Loads up the dashboard for users to see
    public AnchorPane getDashboardView() {
        //Checks if dashboard is empty
        if (dashboardView == null) {
            //Gets the dashboard view from its respective fxml file
            try {
                dashboardView = new FXMLLoader(getClass().getResource("/fxml/bny-app-dashboard.fxml")).load();

            } catch(Exception e) {
                System.out.println("Could not load dashboard.");
            }
        }
        return dashboardView;
    }

    //Same as the dashboardView method, loads up transaction view
    public AnchorPane getTransactionsView() {
        if (transactionsView == null) {
            try {
                transactionsView = new FXMLLoader(getClass().getResource("/fxml/Transactions.fxml")).load();

            } catch(Exception e) {
                System.out.println("Could not load transactions screen.");
            }
        }
        return transactionsView;
    }

    //Loads up the ledger view
    public AnchorPane getLedgerView() {
        if (ledgerView == null) {
            try {
                ledgerView = new FXMLLoader(getClass().getResource("/fxml/bny-ledger.fxml")).load();

            } catch(Exception e) {
                System.out.println("Could not load ledger screen");
            }
        }
        return ledgerView;
    }

    //shows the login view
    public void showLoginView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/bny-login.fxml"));
        createStage(loader);
    }

    //loading up the ledger menu
    public void showLedgerMenu() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client-side.fxml"));
        UserSideController userSideController = new UserSideController();
        loader.setController(userSideController);
        createStage(loader);
    }

    //Got tired of writing this repetitively, so made method for loading a new stage
    public void createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch(Exception e) {
            System.out.println("Could not create new scene.");
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
    //Trying to reset my ledger view
    public void resetLedgerView() {
        ledgerView = null;
    }
}
