package com.example.securityapp.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.example.securityapp.data.LoginRepository;
import com.example.securityapp.data.Result;
import com.example.securityapp.data.model.LoggedInUser;
import com.example.securityapp.R;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

}
