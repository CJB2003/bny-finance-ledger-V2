package com.pluralsight;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private PasswordField enterPasswordField;

    @FXML
    private Button exitButton;

    @FXML
    private ImageView lockImageView;

    @FXML
    private ImageView loginBrandImageView;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private TextField userNameTextField;

    @FXML
    private Label loginMessageLabel;


    //initialize method to load images up, otherwise it will give me an error statement
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            Image brandImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/pluralsight/Branding_Image.png")));
            loginBrandImageView.setImage(brandImage);

            Image lockImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/pluralsight/lock_icon.png")));
            lockImageView.setImage(lockImage);
        } catch(Exception e) {
            System.out.println("Could not load images.");
            e.printStackTrace();
        }
    }
    public void loginButtonOnAction(ActionEvent event) {
        if (userNameTextField.getText().isBlank() == false && enterPasswordField.getText().isBlank() == false) {
            validateLogin();
        } else {
            loginMessageLabel.setTextFill(Color.RED);
            loginMessageLabel.setText("Please enter a username and password");
        }
    }
    public void validateLogin() {

    }
    //method for exiting the application
    public void onExitButtonClick(ActionEvent event) {

        //sets current stage (login screen) to exit button and closes it
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }
    //method for when user clicks on create account button
    public void onRegisterButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource())

        } catch(Exception e) {
            System.out.println("Could not load sign up screen.");
        }

    }

}


