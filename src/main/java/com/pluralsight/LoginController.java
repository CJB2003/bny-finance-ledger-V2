package com.pluralsight;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
    /*
    Method that runs when login button is click
    Makes sure that username and password aren't empty
     */
    public void loginButtonOnAction(ActionEvent event) {
        if (userNameTextField.getText().isBlank() == false && enterPasswordField.getText().isBlank() == false) {
            validateLogin();
        } else {
            loginMessageLabel.setTextFill(Color.RED);
            loginMessageLabel.setText("Please enter a username and password");
        }
    }
    public void validateLogin() {

        //Create object connection
        DatabaseConnection connection = new DatabaseConnection();
        //Connecting to SQL
        Connection connectDB = connection.getConnection();

        //Checks if the connection actually worked
        if (connectDB == null) {
            loginMessageLabel.setTextFill(Color.RED);
            loginMessageLabel.setText("Database connection failed.");
            return;
        }

        //This is the SQL query string that grabs an ID number
        String loginVerification = "SELECT count(1) FROM user_account WHERE username = '" + userNameTextField.getText().trim() + "' AND password = '" + enterPasswordField.getText().trim() + "'";

        System.out.println("Executing Query: " + loginVerification);

        try {

            //Searches for info and storing answer
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(loginVerification);

            //If queryResult count is greater than 1 than that user is in the database
            while(queryResult.next()) {
                if (queryResult.getInt(1) > 0) {

                } else {
                    loginMessageLabel.setTextFill(Color.RED);
                    loginMessageLabel.setText("Invalid login. Please try again.");
                }
            }

        } catch(Exception e) {
            System.out.println("Could not validate login.");
            e.printStackTrace();
        }
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
        accountCreationStage();
    }

    public void accountCreationStage() {

        try {

            //loads bny-signup fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("bny-signup.fxml"));
            Scene scene = new Scene(loader.load(), 600, 400);

            Stage signUpStage = new Stage();
            signUpStage.initStyle(StageStyle.UNDECORATED);
            signUpStage.setScene(scene);

            //closes the login page
            Stage currentStage = (Stage) registerButton.getScene().getWindow();
            currentStage.close();

            //shows the sign-up screen
            signUpStage.show();

        } catch(Exception e) {
            System.out.println("Could not load sign up screen.");
            e.printStackTrace();
        }
    }

}


