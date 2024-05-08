package de.hitec.nhplus.model;

import de.hitec.nhplus.utils.DateConverter;
import javafx.beans.property.SimpleLongProperty;

import java.time.LocalDate;

public class Caregiver extends Person {

    private long cid;
    /** This is a string to support thing like the "+" in mobile numbers */
    private String phoneNumber;
    private boolean locked;
    /** To track the total time the data has been stored */
    private String dateCreated;

    public long getCid() {
        return cid;
    }

    public long cidProperty() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
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

    /** Caregiver with id */
    public Caregiver(long cid, String firstName, String surname, String phoneNumber, boolean locked, String dateCreated) {
        super(firstName, surname);
        this.cid = cid;
        this.phoneNumber = phoneNumber;
        this.locked = locked;
        this.dateCreated = dateCreated;
    }

    /** Caregiver without id */
    public Caregiver(String firstName, String surname, String phoneNumber, boolean locked) {
        super(firstName, surname);
        this.phoneNumber = phoneNumber;
        this.locked = locked;
        this.dateCreated = DateConverter.convertLocalDateToString(LocalDate.now());
    }

    @Override
    public String toString() {
        return "Caregiver{" +
                "cid=" + cid +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", locked=" + locked +
                '}';
    }
}
