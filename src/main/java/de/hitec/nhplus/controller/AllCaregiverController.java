package de.hitec.nhplus.controller;

import de.hitec.nhplus.datastorage.CaregiverDAO;
import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.model.Caregiver;
import de.hitec.nhplus.utils.DateConverter;
import de.hitec.nhplus.utils.DateUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalDate;

/**
 * This class is the controller for the view AllCaregiver.fxml. It provides the functionality to show all caregivers in
 * a table view, to add a new caregiver, to delete a caregiver and to edit the first name, surname and phone number of
 * a caregiver.
 */
public class AllCaregiverController {

    @FXML
    private TableView<Caregiver> tableView;

    @FXML
    private TableColumn<Caregiver, Integer> colID;

    @FXML
    private TableColumn<Caregiver, String> colFirstName;

    @FXML
    private TableColumn<Caregiver, String> colSurname;

    @FXML
    private TableColumn<Caregiver, String> colTelephone;

   // @FXML
   // private TableColumn<Caregiver, Boolean> columnLocked;

   // @FXML
    //  private TableColumn<Caregiver, String> columnDateCreated;

    @FXML
    private TextField txfFirstname, txfSurname, txfTelephone;

    @FXML
    private Button btnAdd, btnDelete, btnLock;

    private final ObservableList<Caregiver> caregivers = FXCollections.observableArrayList();
    private CaregiverDAO dao;

    /**
     * This method is called when the view is loaded. It initializes the table view, the columns and the data. It also
     * sets up listeners for the selection of a row in the table view and the input fields for the new caregiver.
     * The method is called by the FXMLLoader.
     */
    public void initialize() {
        this.readAllAndShowInTableView();

        this.colID.setCellValueFactory(new PropertyValueFactory<>("cid"));
        this.colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.colSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        this.colTelephone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        // this.columnLocked.setCellValueFactory(new PropertyValueFactory<>("locked"));
        // this.columnDateCreated.setCellValueFactory(new
        // PropertyValueFactory<>("dateCreated"));

        // Anzeigen der Daten
        this.tableView.setItems(this.caregivers);

        this.btnDelete.setDisable(true);
        this.tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Caregiver>() {
            @Override
            public void changed(ObservableValue<? extends Caregiver> observableValue, Caregiver oldCaregiver,
                                Caregiver newCaregiver) {
                if (newCaregiver != null && newCaregiver.isLocked()) {
                    AllCaregiverController.this.btnDelete.setDisable(true);
                } else if (newCaregiver == null) {
                    AllCaregiverController.this.btnDelete.setDisable(true);
                }
                else {
                    AllCaregiverController.this.btnDelete.setDisable(false);
                }
            }
        });

        this.btnAdd.setDisable(true);
        ChangeListener<String> inputNewCaregiverListener = (observableValue, oldText,
                newText) -> AllCaregiverController.this.btnAdd
                // This right here validator for adding and Deleting and the Lock Button TODO:
                        .setDisable(!AllCaregiverController.this.areInputDataValid());
        this.txfFirstname.textProperty().addListener(inputNewCaregiverListener);
        this.txfSurname.textProperty().addListener(inputNewCaregiverListener);
        this.txfTelephone.textProperty().addListener(inputNewCaregiverListener);

        this.btnLock.setDisable(true);
        this.tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Caregiver>() {
            @Override
            public void changed(ObservableValue<? extends Caregiver> observableValue, Caregiver oldCaregiver,
                    Caregiver newCaregiver) {
                AllCaregiverController.this.btnLock.setDisable(newCaregiver == null);
            }
        });

        /*
          Grey out the background of locked caregivers in the table view.
         */
        this.tableView.setRowFactory(tv -> new TableRow<Caregiver>() {
            @Override
            protected void updateItem(Caregiver caregiver, boolean empty) {
                super.updateItem(caregiver, empty);

                if (caregiver == null || empty) {
                    setStyle("");
                } else if (caregiver.isLocked()) {
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
        this.caregivers.clear();
        try {
            this.caregivers.addAll(this.dao.readAll());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Only when the input data passes all checks, the button to add a new caregiver is enabled.
     * @return True if all input data is valid, false otherwise.
     */
    private boolean areInputDataValid() {
        return !this.txfFirstname.getText().isBlank() && !this.txfSurname.getText().isBlank()
                && !this.txfTelephone.getText().isBlank() && this.validatePhoneNumber(this.txfTelephone.getText());
    }

    /**
     * This method is called when the user edits the first name of a caregiver.
     * @param event The event that triggered the method call.
     */
    @FXML
    public void handleOnEditFirstName(TableColumn.CellEditEvent<Caregiver, String> event) {
        event.getRowValue().setFirstName(event.getNewValue());
        this.doUpdate(event);
    }

    /**
     * This method is called when the user edits the surname of a caregiver.
     * @param event The event that triggered the method call.
     */
    @FXML
    public void handleOnEditSurname(TableColumn.CellEditEvent<Caregiver, String> event) {
        event.getRowValue().setSurname(event.getNewValue());
        this.doUpdate(event);
    }

    /**
     * This method is called when the user edits the phone number of a caregiver.
     * @param event The event that triggered the method call.
     */
    @FXML
    public void handleOnEditPhoneNumber(TableColumn.CellEditEvent<Caregiver, String> event) {
        event.getRowValue().setPhoneNumber(event.getNewValue());
        this.doUpdate(event);
    }

    /**
     * This method handles events fired by the button to delete caregivers. It calls {@link CaregiverDAO} to delete the
     * Caregiver from the database and removes the object from the list, which is the data source of the
     * <code>TableView</code>.
     */
    @FXML
    public void handleDelete() {
        Caregiver selectedItem = tableView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert(Alert.AlertType.ERROR, "No Selection", "No Caregiver selected", "Please select a Caregiver to delete.");
            return;
        }

        LocalDate creationDate = DateConverter.convertStringToLocalDate(selectedItem.getDateCreated());
        boolean confirmDelete = showConfirmationAlert("Delete Caregiver", "This entry has a retention period of 10 years. Do you really want to delete this entry?");

        if (!confirmDelete) {
            return;
        }

        if (!DateUtils.istMindestensZehnJahre(creationDate)) {
            showAlert(Alert.AlertType.ERROR, "Cannot Delete", "Deletion Error", "You cannot delete this Caregiver as it has not yet reached its 10 years retention period.");
            return;
        }

        try {
            deleteCaregiver(selectedItem);
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
     * @param caregiver The Caregiver to be deleted.
     * @throws SQLException If an SQL error occurs during the deletion.
     */
    private void deleteCaregiver(Caregiver caregiver) throws SQLException {
        DaoFactory.getDaoFactory().createCaregiverDAO().deleteById(caregiver.getCid());
    }

    /**
     * Handles an SQL exception by printing the stack trace and displaying an information alert.
     *
     * @param exception The SQLException that occurred.
     */
    private void handleSQLException(SQLException exception) {
        exception.printStackTrace();
        showAlert(Alert.AlertType.INFORMATION, "Information", "Deletion Failed", "Please delete all related treatments before deleting this entry.");
    }

    /**
     * This method is called when the add button is clicked. It creates a new caregiver with the data from the text fields
     * and adds it to the database.
     * Then, the input fields are cleared.
     */
    public void handleAdd() {
        String surname = this.txfSurname.getText();
        String firstName = this.txfFirstname.getText();
        String phoneNumber = this.txfTelephone.getText();
        try {
            this.dao.create(new Caregiver(firstName, surname, phoneNumber, false));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        this.readAllAndShowInTableView();
        clearTextfields();
    }

    /**
     * This method is called when the lock button is clicked. It toggles the lock status of the selected caregiver.
     */
    public void handleLock() {
        Caregiver selectedItem = this.tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                selectedItem.setLocked(!selectedItem.isLocked());
                DaoFactory.getDaoFactory().createCaregiverDAO().update(selectedItem);
                this.refreshTable();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Validates the phone number. It must have at least 5 characters.
     * @param phoneNumber Phone number to validate as text from the text field.
     * @return True if the phone number is valid, false otherwise.
     */
    private boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.length() < 5) {
            return false;
        }
        return true;
    }

    /**
     * Clears the text fields for the first name, surname and phone number.
     */
    private void clearTextfields() {
        this.txfFirstname.clear();
        this.txfSurname.clear();
        this.txfTelephone.clear();
    }

    /**
     * Reads all caregivers from the database and shows them in the table view.
     */
    private void readAllAndShowInTableView() {
        this.caregivers.clear();
        this.dao = DaoFactory.getDaoFactory().createCaregiverDAO();
        try {
            this.caregivers.addAll(this.dao.readAll());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * This method is called when the user edits a cell in the table view. It updates the caregiver in the database.
     * @param event The event (button click) that triggered the method call.
     */
    private void doUpdate(TableColumn.CellEditEvent<Caregiver, String> event) {
        try {
            this.dao.update(event.getRowValue());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
