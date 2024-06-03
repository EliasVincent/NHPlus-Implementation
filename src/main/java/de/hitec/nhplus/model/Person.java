package de.hitec.nhplus.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * A person has a first name and a surname. This class is used as a base class for any persons in the system.
 */
public abstract class Person {
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty surname;

    /**
     * Constructor to initiate an object of class <code>Person</code> with the given parameters.
     *
     * @param firstName First name of the person.
     * @param surname   Last name of the person.
     */
    public Person(String firstName, String surname) {
        this.firstName = new SimpleStringProperty(firstName);
        this.surname = new SimpleStringProperty(surname);
    }

    public String getFirstName() {
        return firstName.get();
    }

    /**
     * Property to get the first name of the person.
     * @return SimpleStringProperty of the first name.
     */
    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getSurname() {
        return surname.get();
    }

    /**
     * Property to get the surname of the person.
     * @return SimpleStringProperty of the surname.
     */
    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }
}
