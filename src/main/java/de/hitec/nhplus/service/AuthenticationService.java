// src/de/hitec/nhplus/service/AuthenticationService.java
package de.hitec.nhplus.service;

import de.hitec.nhplus.datastorage.UserDao;
import de.hitec.nhplus.model.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The AuthenticationService class provides methods to authenticate users.
 */
public class AuthenticationService {
    private final UserDao userDao;

    /**
     * Constructor to initialize an object of class <code>AuthenticationService</code> with the given parameters.
     * @param connection Connection object to execute SQL statements.
     */
    public AuthenticationService(Connection connection) {
        this.userDao = new UserDao(connection);
    }

    /**
     * Authenticates a user with the given email and password.
     *
     * @param email the email of the user to authenticate.
     * @param password the password of the user to authenticate.
     * @return the authenticated user or null if the user could not be authenticated.
     * @throws SQLException if an error occurs while querying the database.
     */
    public User authenticate(String email, String password) throws SQLException {
        for (User user : userDao.readAll()) {
            if (user.getEmail().equals(email) && user.getPassword().equals(hashPassword(password))) {
                return user;
            }
        }
        return null;
    }

    /**
     * Hashes the given password. As a placeholder, this method simply returns the password as is.
     *
     * @param password the password to hash.
     * @return the hashed password.
     */
    private String hashPassword(String password) {
        return password;
    }
}
