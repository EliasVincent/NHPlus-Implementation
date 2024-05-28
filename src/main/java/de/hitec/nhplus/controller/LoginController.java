package de.hitec.nhplus.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.application.Platform;

import de.hitec.nhplus.model.User;
import de.hitec.nhplus.service.AuthenticationService;
import de.hitec.nhplus.datastorage.ConnectionBuilder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private ImageView userLogo;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private AuthenticationService authenticationService;

    public LoginController() {
        Connection connection = ConnectionBuilder.getConnection();
        this.authenticationService = new AuthenticationService(connection);
    }

    /**
     * The initialize method is called automatically when the associated FXML document is loaded.
     * This method initializes the user interface and sets placeholder texts, adds event handlers
     * and loads an image for the user logo.
     * @param location The URL used as the basis for resolving relative paths. Can be null,
     * if the FXML document was not loaded from a URL.
     * @param resources A ResourceBundle containing the resources for localization. Can be null,
     * if the FXML document was not loaded with a ResourceBundle.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set placeholder text
        emailField.setPromptText("Email");
        passwordField.setPromptText("Password");

        // Add key event handler to emailField
        emailField.setOnKeyPressed(this::handleKeyPressed);
        // Add key event handler to passwordField
        passwordField.setOnKeyPressed(this::handleKeyPressed);

        // Load user logo image
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/de/hitec/nhplus/Images/userLogo.png")));
        userLogo.setImage(image);
    }

    /**
     * Handles key pressed events, specifically targeting the Enter key.
     * This method is designed to be invoked when a key press event occurs,
     * checking if the pressed key is the Enter key. If the Enter key is pressed,
     * the method consumes the event, preventing further propagation, and triggers
     * the login process through the handleLogin method.
     * @param event The KeyEvent representing the key press event.
     */

    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            event.consume();
            handleLogin(new ActionEvent(event.getSource(), null));
        }
    }

    /**
     * Handles the login process when triggered by an ActionEvent.
     * This method retrieves the email and password from input fields,
     * validates them, and attempts to authenticate the user. If authentication
     * is successful, it displays a welcome message, sets the user session, closes
     * the login window, and opens the main application window. If authentication
     * fails, it displays an error message. Database connectivity errors are also handled.
     * @param event The ActionEvent triggering the login process.
     */
    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Please enter both email and password.");
            return;
        }

        try {
            User user = authenticationService.authenticate(email, password);
            if (user != null) {
                showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + user.getEmail() + "!");

                SessionManager.getInstance().setUserSession(user);

                Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                loginStage.close();

                openMainWindow();
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid email or password.");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while connecting to the database.");
        }
    }
    /**
     * Handles the cancellation action by exiting the application.
     * This method is invoked when the cancellation action is triggered,
     * typically in response to a user action such as clicking a cancel button.
     * It exits the application by calling Platform.exit().
     * @param event The ActionEvent triggering the cancellation action.
     */
    @FXML
    private void handleCancel(ActionEvent event) {
        Platform.exit();
    }

    private void openMainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/hitec/nhplus/Views/MainWindowView.fxml"));
            BorderPane root = loader.load();
            Scene scene = new Scene(root);
            Stage mainWindowStage = new Stage();
            mainWindowStage.setScene(scene);
            mainWindowStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while loading the main window.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
