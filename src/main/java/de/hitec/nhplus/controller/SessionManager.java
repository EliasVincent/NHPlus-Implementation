package de.hitec.nhplus.controller;

import de.hitec.nhplus.model.User;

public class SessionManager {
    private static SessionManager instance;
    private User userSession;

    private SessionManager() {

    }

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

    public void clearSession() {
        this.userSession = null;
    }
    public boolean isLoggedIn() {
        return userSession != null;
    }

    public void logout() {
        userSession = null;
    }
}

