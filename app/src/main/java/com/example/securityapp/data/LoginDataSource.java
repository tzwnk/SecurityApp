package com.example.securityapp.data;

import com.example.securityapp.data.model.LoggedInUser;
import com.example.securityapp.ui.login.AccessLevel;
import com.example.securityapp.ui.login.LoginActivity;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    protected String adminPassword = "12345";
    protected String userPassword = "1234";

    public Result<LoggedInUser> login(String password) {
        LoggedInUser validUser = null;

        try {
            if (password.equals(adminPassword)) {
                validUser = new LoggedInUser("1","Admin", AccessLevel.FULL);
            } else if (password.equals(userPassword)) {
                validUser = new LoggedInUser("2","User", AccessLevel.NORMAL);
            } else {
                return new Result.Fail("Unauthorised attempt");
            }

            return new Result.Success<>(validUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
