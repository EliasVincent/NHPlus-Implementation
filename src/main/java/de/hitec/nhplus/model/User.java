package de.hitec.nhplus.model;

public class User {
    private int id;
    private String email;
    private String password;
    private int status;

    public User(String email, String password, int status) {
        this.email = email;
        this.password = password;
        this.status = status;
    }

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
