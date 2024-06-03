package de.hitec.nhplus.controller;

import de.hitec.nhplus.Main;
import de.hitec.nhplus.datastorage.CaregiverDAO;
import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.datastorage.PatientDao;
import de.hitec.nhplus.datastorage.TreatmentDao;
import de.hitec.nhplus.model.Caregiver;
import de.hitec.nhplus.utils.DateConverter;
import de.hitec.nhplus.utils.DateUtils;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import de.hitec.nhplus.model.Patient;
import de.hitec.nhplus.model.Treatment;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.beans.value.ObservableValue;

public class AllTreatmentController {

    @FXML
    private TableView<Treatment> tableView;

    @FXML
    private TableColumn<Treatment, Integer> columnId;

    @FXML
    private TableColumn<Treatment, Integer> columnPid;

    @FXML
    private TableColumn<Treatment, Integer> columnCid;

    @FXML
    private TableColumn<Treatment, String> columnDate;

    @FXML
    private TableColumn<Treatment, String> columnBegin;

    @FXML
    private TableColumn<Treatment, String> columnEnd;

    @FXML
    private TableColumn<Treatment, String> columnDescription;

    @FXML
    private ComboBox<String> comboBoxPatientSelection, comboBoxCaregiverSelection;

    @FXML
    private Button buttonDelete, buttonLock;

    private final ObservableList<Treatment> treatments = FXCollections.observableArrayList();
    private TreatmentDao dao;
    private final ObservableList<String> patientSelection = FXCollections.observableArrayList();
    private final ObservableList<String> caregiverSelection = FXCollections.observableArrayList();
    private ArrayList<Patient> patientList;
    private ArrayList<Caregiver> caregiverList;

    public void initialize() {
        readAllAndShowInTableView();
        comboBoxPatientSelection.setItems(patientSelection);
        comboBoxPatientSelection.getSelectionModel().select(0);
        comboBoxCaregiverSelection.setItems(caregiverSelection);
        comboBoxCaregiverSelection.getSelectionModel().select(0);

        this.columnId.setCellValueFactory(new PropertyValueFactory<>("tid"));
        this.columnPid.setCellValueFactory(new PropertyValueFactory<>("pid"));
        this.columnCid.setCellValueFactory(new PropertyValueFactory<>("cid"));
        this.columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.columnBegin.setCellValueFactory(new PropertyValueFactory<>("begin"));
        this.columnEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        this.columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        this.tableView.setItems(this.treatments);

        // Disabling the button to delete treatments as long, as no treatment was selected.
        this.buttonDelete.setDisable(true);
        this.tableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Treatment> observableValue, Treatment oldTreatment, Treatment newTreatment) -> {
            if (newTreatment != null && newTreatment.isLocked()) {
                AllTreatmentController.this.buttonDelete.setDisable(true);
            } else if (newTreatment == null) {
                AllTreatmentController.this.buttonDelete.setDisable(true);
            }
            else {
                AllTreatmentController.this.buttonDelete.setDisable(false);
            }
        });
        this.createComboBoxData();


        this.buttonLock.setDisable(true);
        this.tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Treatment>() {
            @Override
            public void changed(ObservableValue<? extends Treatment> observableValue, Treatment oldTreatment,
                                Treatment newTreatment) {
                AllTreatmentController.this.buttonLock.setDisable(newTreatment == null);
            }
        });

        /*
          Grey out the background of locked treatments in the table view.
         */
        this.tableView.setRowFactory(tv -> new TableRow<Treatment>() {
            @Override
            protected void updateItem(Treatment treatment, boolean empty) {
                super.updateItem(treatment, empty);

                if (treatment == null || empty) {
                    setStyle("");
                } else if (treatment.isLocked()) {
                    // Grey background for locked caregivers
                    setStyle("-fx-background-color: lightgray;");
                } else {
                    // Normal background for other caregivers
                    setStyle("");
                }
            }
        });
    }

    /**
     * Manually refreshes the table view.
     */
    private void refreshTable() {
        this.treatments.clear();
        try {
            this.treatments.addAll(this.dao.readAll());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void readAllAndShowInTableView() {
        this.treatments.clear();
        comboBoxPatientSelection.getSelectionModel().select(0);
        this.dao = DaoFactory.getDaoFactory().createTreatmentDao();
        try {
            this.treatments.addAll(dao.readAll());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void createComboBoxData() {
        PatientDao dao = DaoFactory.getDaoFactory().createPatientDAO();
        try {
            patientList = (ArrayList<Patient>) dao.readAll();
            this.patientSelection.add("alle");
            for (Patient patient: patientList) {
                this.patientSelection.add(patient.getSurname());
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        CaregiverDAO daoCaregiver = DaoFactory.getDaoFactory().createCaregiverDAO();
        try {
            caregiverList = (ArrayList<Caregiver>) daoCaregiver.readAll();
            this.caregiverSelection.add("alle");
            for (Caregiver caregiver: caregiverList) {
                this.caregiverSelection.add(caregiver.getSurname());
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }


    @FXML
    public void handleComboBox() {
        String selectedPatient = this.comboBoxPatientSelection.getSelectionModel().getSelectedItem();
        this.treatments.clear();
        this.dao = DaoFactory.getDaoFactory().createTreatmentDao();

        if (selectedPatient.equals("alle")) {
            try {
                this.treatments.addAll(this.dao.readAll());
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        Patient patient = searchInList(selectedPatient);
        if (patient !=null) {
            try {
                this.treatments.addAll(this.dao.readTreatmentsByPid(patient.getPid()));
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    @FXML
    public void handleCaregiverComboBox(ActionEvent actionEvent) {
        String selectedCaregiver = this.comboBoxPatientSelection.getSelectionModel().getSelectedItem();
        this.treatments.clear();
        this.dao = DaoFactory.getDaoFactory().createTreatmentDao();

        if (selectedCaregiver.equals("alle")) {
            try {
                this.treatments.addAll(this.dao.readAll());
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        Caregiver caregiver = searchCaregiverInList(selectedCaregiver);
        if (caregiver !=null) {
            try {
                this.treatments.addAll(this.dao.readTreatmentsByCid(caregiver.getCid()));
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    private Caregiver searchCaregiverInList(String surname) {
        for (Caregiver caregiver : this.caregiverList) {
            if (caregiver.getSurname().equals(surname)) {
                return caregiver;
            }
        }
        return null;
    }

    private Patient searchInList(String surname) {
        for (Patient patient : this.patientList) {
            if (patient.getSurname().equals(surname)) {
                return patient;
            }
        }
        return null;
    }

    @FXML
    public void handleDelete() {
        // Get the selected selectedItem from the table view
        Treatment selectedItem = tableView.getSelectionModel().getSelectedItem();

        // If no item is selected, show an alert and return
        if (selectedItem == null) {
            showAlert(Alert.AlertType.ERROR, "No Selection", "No Treatment selected", "Please select a Treatment to delete.");
            return;
        }
        LocalDate creationDate = DateConverter.convertStringToLocalDate(selectedItem.getDateCreated());
        boolean confirmDelete = showConfirmationAlert("Delete Treatment", "This entry has a retention period of 10 years. Do you really want to delete this entry?");

        if (!confirmDelete) {
            return;
        }
        if (!DateUtils.isAtLeastTenYears(creationDate)) {
            showAlert(Alert.AlertType.ERROR, "Cannot Delete", "Deletion Error", "You cannot delete this Treatment as it has not yet reached its 10 years retention period.");
            return;
        }
        try {
            deleteTreatment(selectedItem);
            tableView.getItems().remove(selectedItem);
        } catch (SQLException exception) {
            handleSQLException(exception);
        }

    }

    /**
     * Displays a confirmation alert with the given title and message.
     *
     * @param title   The title of the confirmation alert.
     * @param message The message content of the confirmation alert.
     * @return true if the user confirms the action, false otherwise.
     */
    private boolean showConfirmationAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
        alert.setTitle(title);
        alert.showAndWait();
        return alert.getResult() == ButtonType.YES;
    }

    /**
     * Displays an alert with the specified type, title, header, and content.
     *
     * @param alertType The type of the alert (e.g., ERROR, INFORMATION).
     * @param title     The title of the alert.
     * @param header    The header text of the alert.
     * @param content   The content text of the alert.
     */
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    /**
     * Deletes the specified Caregiver from the database.
     *
     * @param treatment The Caregiver to be deleted.
     * @throws SQLException If an SQL error occurs during the deletion.
     */
    private void deleteTreatment(Treatment treatment) throws SQLException {
        DaoFactory.getDaoFactory().createCaregiverDAO().deleteById(treatment.getCid());
    }

    /**
     * Handles an SQL exception by printing the stack trace and displaying an information alert.
     *
     * @param exception The SQLException that occurred.
     */
    private void handleSQLException(SQLException exception) {
        exception.printStackTrace();
        showAlert(Alert.AlertType.INFORMATION, "Information", "Deletion Failed", "Deletion Failed");
    }

    /**
     * This method is called when the lock button is clicked. It toggles the lock status of the selected treatment.
     */
    public void handleLock() {
        Treatment selectedItem = this.tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                selectedItem.setLocked(!selectedItem.isLocked());
                DaoFactory.getDaoFactory().createTreatmentDao().update(selectedItem);
                this.refreshTable();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    @FXML
    public void handleNewTreatment() {
        try{
            String selectedPatient = this.comboBoxPatientSelection.getSelectionModel().getSelectedItem();
            String selectedCaregiver = this.comboBoxCaregiverSelection.getSelectionModel().getSelectedItem();
            Patient patient = searchInList(selectedPatient);
            Caregiver caregiver = searchCaregiverInList(selectedCaregiver);
            newTreatmentWindow(patient, caregiver);
        } catch (NullPointerException exception){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Patient und/oder Pfleger für die Behandlung fehlt!");
            alert.setContentText("Wählen Sie über die Comboboxen einen Patienten und einen Pfleger aus!");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleMouseClick() {
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (tableView.getSelectionModel().getSelectedItem() != null)) {
                int index = this.tableView.getSelectionModel().getSelectedIndex();
                Treatment treatment = this.treatments.get(index);
                treatmentWindow(treatment);
            }
        });
    }

    public void newTreatmentWindow(Patient patient, Caregiver caregiver) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/Views/NewTreatmentView.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);

            // the primary stage should stay in the background
            Stage stage = new Stage();

            NewTreatmentController controller = loader.getController();
            controller.initialize(this, stage, patient, caregiver);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void treatmentWindow(Treatment treatment){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/Views/TreatmentView.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);

            // the primary stage should stay in the background
            Stage stage = new Stage();
            TreatmentController controller = loader.getController();
            controller.initializeController(this, stage, treatment);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


}
