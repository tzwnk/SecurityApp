package com.example.securityapp.data.model;

import com.example.securityapp.ui.login.AccessLevel;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String displayName;
    private AccessLevel accessLevel;

    public LoggedInUser(String userId, String displayName, AccessLevel accessLevel) {
        this.userId = userId;
        this.displayName = displayName;
        this.accessLevel = accessLevel;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public AccessLevel getAccessLevel() {return accessLevel; }
}
