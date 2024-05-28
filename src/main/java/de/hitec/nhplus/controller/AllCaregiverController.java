package de.hitec.nhplus.controller;

import de.hitec.nhplus.datastorage.CaregiverDAO;
import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.model.Caregiver;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

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

    @FXML
    public void handleOnEditFirstName(TableColumn.CellEditEvent<Caregiver, String> event) {
        event.getRowValue().setFirstName(event.getNewValue());
        this.doUpdate(event);
    }

    @FXML
    public void handleOnEditSurname(TableColumn.CellEditEvent<Caregiver, String> event) {
        event.getRowValue().setSurname(event.getNewValue());
        this.doUpdate(event);
    }

    @FXML
    public void handleOnEditPhoneNumber(TableColumn.CellEditEvent<Caregiver, String> event) {
        event.getRowValue().setPhoneNumber(event.getNewValue());
        this.doUpdate(event);
    }

    /**
     * This method is called when the delete button is clicked. It deletes the selected caregiver from the database.
     */
    public void handleDelete() {
        Caregiver selectedItem = this.tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                DaoFactory.getDaoFactory().createCaregiverDAO().deleteById(selectedItem.getCid());
                this.tableView.getItems().remove(selectedItem);
            } catch (SQLException exception) {
                exception.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Eintrag kann nicht gelöscht werden!");
                alert.setContentText("Bitte löschen sie vorher alle Behandlungen, die mit diesem Eintrag verknüpft sind!");
                alert.showAndWait();
            }
        }

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
