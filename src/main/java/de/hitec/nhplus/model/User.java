package de.hitec.nhplus.model;

/**
 * A user has an email and a password. This class is used to represent a user in the system.
 */
public class User {
    private int id;
    private String email;
    private String password;
    private int status;

    /**
     * Constructor to initiate an object of class <code>User</code> with the given parameters.
     * @param email Email of the user.
     * @param password Password of the user.
     * @param status Status of the user (0 or 1).
     */
    public User(String email, String password, int status) {
        this.email = email;
        this.password = password;
        this.status = status;
    }

    /**
     * Constructor to initiate an object of class <code>User</code> with the given parameters.
     * @param id Id of the user.
     * @param email Email of the user.
     * @param password Password of the user.
     * @param status Status of the user (0 or 1).
     */
    public User(int id, String email, String password, int status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
