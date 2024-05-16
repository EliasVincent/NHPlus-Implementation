package de.hitec.nhplus.model;

import de.hitec.nhplus.utils.DateConverter;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;

import java.time.LocalDate;

/**
 * Caregivers are responsible for the care of the patients.
 */
public class Caregiver extends Person {

    private LongProperty cid;
    /** This is a string to support thing like the "+" in mobile numbers */
    private String phoneNumber;
    private boolean locked;
    /** To track the total time the data has been stored */
    private String dateCreated;

    public long getCid() {
        return cid.get();
    }

    public LongProperty cidProperty() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid.set(cid);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Constructor to initiate an object of class <code>Caregiver</code> with the given parameter. Use this constructor
     * @param cid Caregiver id.
     * @param firstName First name of the caregiver.
     * @param surname Last name of the caregiver.
     * @param phoneNumber Phone number of the caregiver.
     * @param locked Boolean to indicate if the caregiver is locked. (data privacy)
     * @param dateCreated Date when the caregiver was created. (data privacy)
     */
    public Caregiver(long cid, String firstName, String surname, String phoneNumber, boolean locked, String dateCreated) {
        super(firstName, surname);
        this.cid = new SimpleLongProperty(cid);
        this.phoneNumber = phoneNumber;
        this.locked = locked;
        this.dateCreated = dateCreated;
    }

    /**
     * Constructor to initiate an object of class <code>Caregiver</code> with the given parameter. Use this constructor
     * @param firstName First name of the caregiver.
     * @param surname Last name of the caregiver.
     * @param phoneNumber Phone number of the caregiver.
     * @param locked Boolean to indicate if the caregiver is locked. (data privacy)
     */
    public Caregiver(String firstName, String surname, String phoneNumber, boolean locked) {
        super(firstName, surname);
        this.phoneNumber = phoneNumber;
        this.locked = locked;
        this.dateCreated = DateConverter.convertLocalDateToString(LocalDate.now());
    }

    /**
     * toString method for debugging purposes.
     * @return String representation of the Java Caregiver object.
     */
    @Override
    public String toString() {
        return "Caregiver{" +
                "cid=" + cid +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", locked=" + locked +
                '}';
    }
}
