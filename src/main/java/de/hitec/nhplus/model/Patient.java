package de.hitec.nhplus.model;

import de.hitec.nhplus.utils.DateConverter;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Patients live in a NURSING home and are treated by nurses.
 */
public class Patient extends Person {
    private SimpleLongProperty pid;
    private final SimpleStringProperty dateOfBirth;
    private final SimpleStringProperty careLevel;
    private final SimpleStringProperty roomNumber;
    private final List<Treatment> allTreatments = new ArrayList<>();
    private final SimpleBooleanProperty locked = new SimpleBooleanProperty(false);
    private SimpleStringProperty dateCreated = new SimpleStringProperty(
            DateConverter.convertLocalDateToString(LocalDate.now()));

    /**
     * Constructor to initiate an object of class <code>Patient</code> with the
     * given parameter. Use this constructor
     * to initiate objects, which are not persisted yet, because it will not have a
     * patient id (pid).
     *
     * @param firstName   First name of the patient.
     * @param surname     Last name of the patient.
     * @param dateOfBirth Date of birth of the patient.
     * @param careLevel   Care level of the patient.
     * @param roomNumber  Room number of the patient.
     */
    public Patient(String firstName, String surname, LocalDate dateOfBirth, String careLevel, String roomNumber,
            boolean locked, String dateCreated) {
        super(firstName, surname);
        this.dateOfBirth = new SimpleStringProperty(DateConverter.convertLocalDateToString(dateOfBirth));
        this.careLevel = new SimpleStringProperty(careLevel);
        this.roomNumber = new SimpleStringProperty(roomNumber);
        this.locked.set(locked);
        this.dateCreated.set(dateCreated);
    }

    /**
     * Constructor to initiate an object of class <code>Patient</code> with the
     * given parameter. Use this constructor
     * to initiate objects, which are already persisted and have a patient id (pid).
     *
     * @param pid         Patient id.
     * @param firstName   First name of the patient.
     * @param surname     Last name of the patient.
     * @param dateOfBirth Date of birth of the patient.
     * @param careLevel   Care level of the patient.
     * @param roomNumber  Room number of the patient.
     */
    public Patient(long pid, String firstName, String surname, LocalDate dateOfBirth, String careLevel,
            String roomNumber, boolean locked, String dateCreated) {
        super(firstName, surname);
        this.pid = new SimpleLongProperty(pid);
        this.dateOfBirth = new SimpleStringProperty(DateConverter.convertLocalDateToString(dateOfBirth));
        this.careLevel = new SimpleStringProperty(careLevel);
        this.roomNumber = new SimpleStringProperty(roomNumber);
        this.locked.set(locked);
        this.dateCreated.set(dateCreated);
    }

    public long getPid() {
        return pid.get();
    }

    public SimpleLongProperty pidProperty() {
        return pid;
    }

    public String getDateOfBirth() {
        return dateOfBirth.get();
    }

    public SimpleStringProperty dateOfBirthProperty() {
        return dateOfBirth;
    }

    /**
     * Stores the given string as new <code>birthOfDate</code>.
     *
     * @param dateOfBirth as string in the following format: YYYY-MM-DD.
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
    }

    public String getCareLevel() {
        return careLevel.get();
    }

    public SimpleStringProperty careLevelProperty() {
        return careLevel;
    }

    public void setCareLevel(String careLevel) {
        this.careLevel.set(careLevel);
    }

    public String getRoomNumber() {
        return roomNumber.get();
    }

    public SimpleStringProperty roomNumberProperty() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber.set(roomNumber);
    }

    public void setPid(long pid) {
        this.pid.set(pid);
    }

    public List<Treatment> getAllTreatments() {
        return allTreatments;
    }

    public boolean isLocked() {
        return locked.get();
    }

    public SimpleBooleanProperty lockedProperty() {
        return locked;
    }

    public String getDateCreated() {
        return dateCreated.get();
    }

    public SimpleStringProperty dateCreatedProperty() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated.set(dateCreated);
    }

    /**
     * Adds a treatment to the list of treatments, if the list does not already
     * contain the treatment.
     *
     * @param treatment Treatment to add.
     * @return False, if the treatment was already part of the list, else true.
     */
    public boolean add(Treatment treatment) {
        if (this.allTreatments.contains(treatment)) {
            return false;
        }
        this.allTreatments.add(treatment);
        return true;
    }

    /**
     * Constructor to initiate an object of class <code>Patient</code> with the given parameter.
     * @param firstName First name of the patient.
     * @param surname Last name of the patient.
     * @param dateOfBirth Date of birth of the patient.
     * @param careLevel Care level of the patient.
     * @param roomNumber Room number of the patient.
     * @param locked Boolean to indicate if the patient is locked. (data privacy)
     * @param pid Id of the patient.
     */
    public Patient(String firstName, String surname, SimpleStringProperty dateOfBirth, SimpleStringProperty careLevel,
            SimpleStringProperty roomNumber, boolean locked, SimpleLongProperty pid) {
        super(firstName, surname);
        this.dateOfBirth = dateOfBirth;
        this.careLevel = careLevel;
        this.roomNumber = roomNumber;
        this.locked.set(locked);
        this.pid = pid;
    }

    /**
     * Presents the patient as a string.
     * @return String representation of the patient.
     */
    public String toString() {
        return "Patient" + "\nMNID: " + this.pid +
                "\nFirstname: " + this.getFirstName() +
                "\nSurname: " + this.getSurname() +
                "\nBirthday: " + this.dateOfBirth +
                "\nCarelevel: " + this.careLevel +
                "\nRoomnumber: " + this.roomNumber +
                "\nlocked: " + this.locked +
                "\n";
    }

    public void setLocked(boolean b) {
        this.locked.set(b);
    }
}