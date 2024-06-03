package de.hitec.nhplus.controller;

import de.hitec.nhplus.model.User;

/**
 * The SessionManager class is a singleton class that manages the user session.
 * It stores the user session and provides methods to access and clear the session.
 */
public class SessionManager {
    private static SessionManager instance;
    private User userSession;

    /**
     * Empty constructor.
     */
    private SessionManager() {

    }

    /**
     * Returns the instance of the SessionManager class.
     * If the instance is null, a new instance is created.
     * @return the instance of the SessionManager class
     */
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setUserSession(User user) {
        this.userSession = user;
    }

    public User getUserSession() {
        return userSession;
    }

    /**
     * Sets the user session to null.
     */
    public void clearSession() {
        this.userSession = null;
    }

    /**
     * Checks if a user is logged in by a null check.
     * @return true if a user is logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return userSession != null;
    }

    /**
     * Logs out the user by setting the user session to null.
     */
    public void logout() {
        userSession = null;
    }
}

