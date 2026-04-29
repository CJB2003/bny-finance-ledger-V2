package com.pluralsight;

import javafx.application.Application;
import javafx.stage.Stage;


public class LaunchBnyApp extends Application {

    public void start(Stage stage) {
        Model.getInstance().getViewSwap().showLoginView();
    }
}
