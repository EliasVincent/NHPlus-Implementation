package de.hitec.nhplus.controller;

import de.hitec.nhplus.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * The MainWindowController class is a controller class for the main application window.
 * It handles navigation to the different views.
 */
public class MainWindowController {

    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private Button logoutButton;

    @FXML
    private VBox vBox;

    /**
     * The initialize method is called automatically when the associated FXML document is loaded.
     * It checks if a user is logged in and loads the appropriate content.
     */
    @FXML
    private void initialize() {
        if (!isUserLoggedIn()) {
            loadLoginPage();
            setLogoutButtonVisible(false);
            setVBoxVisible(false);
        } else {
            loadMainContent();
            setLogoutButtonVisible(true);
            setVBoxVisible(true);
        }
    }

    /**
     * Checks if a user is logged in.
     * @return true if a user is logged in, false otherwise
     */
    private boolean isUserLoggedIn() {
        return SessionManager.getInstance().isLoggedIn();
    }

    private void loadLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/Views/LoginView.fxml"));
            mainBorderPane.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the main content of the application, if a user is logged in.
     */
    private void loadMainContent() {
        if (isUserLoggedIn()) {
            handleShowAllPatient();
        }
    }

    /**
     * Handles the event when the "Show All Patient" button is clicked.
     */
    @FXML
    private void handleShowAllPatient() {
        if (!isUserLoggedIn()) return;
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/Views/AllPatientView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Handles the event when the "Show All Treatments" button is clicked.
     */
    @FXML
    private void handleShowAllTreatments() {
        if (!isUserLoggedIn()) return;
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/Views/AllTreatmentView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Handles the event when the "Show All Caregiver" button is clicked.
     */
    @FXML
    public void handleShowAllCaregiver(ActionEvent actionEvent) {
        if (!isUserLoggedIn()) return;
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/Views/AllCaregiverView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Handles the event when the "Show All Medication" button is clicked.
     */
    public void handleLogout(ActionEvent actionEvent) {
        SessionManager.getInstance().logout();
        loadLoginPage();
        setLogoutButtonVisible(false);
        setVBoxVisible(false);
    }

    /**
     * Sets the visibility of the logout button.
     * @param visible true to make the logout button visible, false to hide it.
     */
    public void setLogoutButtonVisible(boolean visible) {
        logoutButton.setVisible(visible);
    }

    /**
     * Sets the visibility of the VBox.
     * @param visible true to make the VBox visible, false to hide it.
     */
    public void setVBoxVisible(boolean visible) {
        vBox.setVisible(visible);
    }
}
