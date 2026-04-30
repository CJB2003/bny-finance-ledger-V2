package controllers;

import launch.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class UserSideController implements Initializable {

    public BorderPane userParent;

    /*
    Everytime the screen changes, listener gets a new value allowing the app to set the center to some other view
    Changes through the MenuController class (the buttons)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewSwap().getUserSelectedMenuItem().addListener((observableValue, oldValue, newValue) -> {

            /*
            Switch statement, if transactions is selected, it will swap the view to transactions
            Otherwise, it will set the dashboard as the view by default
             */
            switch(newValue) {
                case "Transactions":
                    userParent.setCenter(Model.getInstance().getViewSwap().getTransactionsView());
                    break;
                default:
                    userParent.setCenter(Model.getInstance().getViewSwap().getDashboardView());
            }

        });

    }

}
