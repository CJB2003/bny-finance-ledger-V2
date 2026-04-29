package com.pluralsight;

import controllers.ClientSideController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ViewSwap {

    @FXML
    private AnchorPane dashboardView;

    public ViewSwap() {

    }
    //Loads up the dashboard for users to see
    public AnchorPane getDashboardView() {
        //Checks if dashboard is empty
        if (dashboardView == null) {
            //Gets the dashboard view from its respective fxml file
            try {
                dashboardView = new FXMLLoader(getClass().getResource("/fxml/ledger-dashboard.fxml")).load();

            } catch(Exception e) {
                System.out.println("Could not load dashboard.");
                e.printStackTrace();
            }
        }
        return dashboardView;
    }
    public void showLoginView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/bny-login.fxml"));
        createStage(loader);
    }

    //loading up the ledger menu
    public void showLedgerMenu() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client-side.fxml"));
        ClientSideController clientSideController = new ClientSideController();
        loader.setController(clientSideController);
        createStage(loader);
    }

    //Got tired of writing this repetitively, so made method for loading a new stage
    public void createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch(Exception e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
}
