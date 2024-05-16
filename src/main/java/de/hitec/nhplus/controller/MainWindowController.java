package de.hitec.nhplus.controller;

import de.hitec.nhplus.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import de.hitec.nhplus.controller.SessionManager;
import java.io.IOException;

public class MainWindowController {

    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private Button logoutButton;

    @FXML
    private void initialize() {
        if (!SessionManager.getInstance().isLoggedIn()) {
            loadLoginPage();
            setLogoutButtonVisible(false);
        } else {
            loadMainContent();
            setLogoutButtonVisible(true);
        }
    }
    private boolean isUserLoggedIn() {
        return false;
    }

    private void loadLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/LoginView.fxml"));
            mainBorderPane.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadMainContent() {
        if (isUserLoggedIn()) {
            handleShowAllPatient();
        }
    }

    @FXML
    private void handleShowAllPatient() {
        if (!isUserLoggedIn()) return;
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/AllPatientView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    @FXML
    private void handleShowAllTreatments() {
        if (!isUserLoggedIn()) return;
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/AllTreatmentView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void handleShowAllCaregiver(ActionEvent actionEvent) {
        if (!isUserLoggedIn()) return;
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/AllCaregiverView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void handleLogout(ActionEvent actionEvent) {
        SessionManager.getInstance().logout();
        loadLoginPage();
        setLogoutButtonVisible(false);
    }

    public void setLogoutButtonVisible(boolean visible) {
        logoutButton.setVisible(visible);
    }
}


