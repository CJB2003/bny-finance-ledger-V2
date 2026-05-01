package controllers;

import launch.DatabaseConnection;
import launch.LaunchBnyApp;
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
import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private Button backButton;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private Button signUpButton;
    @FXML
    private Label signUpLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private ImageView horizontalLogoImageView;

    @Override
    //Loads the images up to their respective variables before everything else
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            Image horizontalBrand = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/HorizontalBranding2.png")));
            horizontalLogoImageView.setImage(horizontalBrand);

        } catch(Exception e) {
            System.out.println("Could not load image.");
        }
    }
    //Sign up button method
    public void signUpButtonOnAction(ActionEvent event) {

        //Initializing variables to store user input for all fields
        String firstName = firstNameTextField.getText().trim();
        String lastName = lastNameTextField.getText().trim();
        String username = usernameTextField.getText().trim();
        String password = setPasswordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();

        //If any of the credentials are blank, it will prompt the user to enter the fields
        if (firstName.isBlank() || lastName.isBlank() || username.isBlank() || password.isBlank() || confirmPassword.isBlank()) {

            signUpLabel.setTextFill(Color.RED);
            signUpLabel.setText("All fields required!");
            return;
        }

        //Checks if passwords match
        if (setPasswordField.getText().equals(confirmPasswordField.getText())) {
            userSignUp();
            //Clears the text inside the password message label
            confirmPasswordLabel.setText("");
        } else {
            //If passwords don't match each other, this prints out and sets the text color to red
            confirmPasswordLabel.setTextFill(Color.RED);
            confirmPasswordLabel.setText("Password does not match!");
        }
    }

    //Method for back button, reopens the login screen
    public void backButtonOnAction(ActionEvent event) {

        try {
            //loads the login screen when backButton is pressed, and closes the sign-up screen
            FXMLLoader fxmlLoader = new FXMLLoader(LaunchBnyApp.class.getResource("/fxml/bny-login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);

            Stage loginStage = new Stage();
            loginStage.initStyle(StageStyle.UNDECORATED);
            loginStage.setScene(scene);

            Stage currentStage = (Stage) backButton.getScene().getWindow();
            currentStage.close();

            loginStage.show();

        } catch(Exception e) {
            System.out.println("Could not go back to login screen.");
        }
    }

    public void userSignUp() {

        //Connecting to the SQL database
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();

        //assigning the user input into string variables
        String insertToSignUp = getString();

        try {
            //Writing the data to the database and executing the query
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToSignUp);
            signUpLabel.setText("User Sign Up Successful!");

        } catch (Exception e) {
            System.out.println("User Sign Up failed.");
        }
    }

    private String getString() {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String username = usernameTextField.getText();
        String password = setPasswordField.getText();

        //Query for SQL database, concatenating the 4 values together with the insertField string
        String insertFields = "INSERT INTO user_account(firstname, lastname, username, password) VALUES ('";
        String insertValues = firstName + "','" + lastName + "','" + username + "','" + password + "')";
        return insertFields + insertValues;
    }
}




