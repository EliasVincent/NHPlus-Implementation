package de.hitec.nhplus.model;

import java.util.Objects;

/**
 * Represents a User entity with an ID, email, and password.
 */
public class User {
    private int id;
    private String email;
    private String password;

    /**
     * Constructs a new User object with the given parameters.
     *
     * @param id       the unique identifier of the user.
     * @param email    the email address of the user.
     * @param password the password of the user.
     */
    public User(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    /**
     * Constructs a new User object without an ID (used for new user creation).
     *
     * @param email    the email address of the user.
     * @param password the password of the user.
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Returns the unique identifier of the user.
     *
     * @return the user ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the user.
     *
     * @param id the new user ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the email address of the user.
     *
     * @return the user email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email the new user email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the password of the user.
     *
     * @return the user password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the new user password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Provides a string representation of the user object, showing ID, email, and a masked password.
     *
     * @return the string representation of the user object.
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='******'" +
                '}';
    }

    /**
     * Checks equality between this user and another object based on ID, email, and password.
     *
     * @param o the object to compare.
     * @return true if they are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    /**
     * Returns a hash code value based on ID, email, and password.
     *
     * @return the hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, email, password);
    }
}
