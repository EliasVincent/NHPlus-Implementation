// src/de/hitec/nhplus/service/AuthenticationService.java
package de.hitec.nhplus.service;

import de.hitec.nhplus.datastorage.UserDao;
import de.hitec.nhplus.model.User;

import java.sql.Connection;
import java.sql.SQLException;

public class AuthenticationService {
    private final UserDao userDao;

    public AuthenticationService(Connection connection) {
        this.userDao = new UserDao(connection);
    }

    public User authenticate(String email, String password) throws SQLException {
        for (User user : userDao.readAll()) {
            if (user.getEmail().equals(email) && user.getPassword().equals(hashPassword(password))) {
                return user;
            }
        }
        return null;
    }

    private String hashPassword(String password) {

        return password;
    }
}
