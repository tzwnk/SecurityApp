package com.example.securityapp.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.securityapp.R;
import com.example.securityapp.home.HomePageActivity;

public class LoginActivity extends AppCompatActivity
                        implements View.OnClickListener{

    private LoginViewModel loginViewModel;
    private String inputPassword = "";
    private int counter = 5;

    private TextView attemptsInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = new ViewModelProvider (this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        Button btn0 = findViewById(R.id.btn0);
        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);
        Button btn4 = findViewById(R.id.btn4);
        Button btn5 = findViewById(R.id.btn5);
        Button btn6 = findViewById(R.id.btn6);
        Button btn7 = findViewById(R.id.btn7);
        Button btn8 = findViewById(R.id.btn8);
        Button btn9 = findViewById(R.id.btn9);

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);

        Button btnRetry = findViewById(R.id.btnRetry);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnRetry.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        attemptsInfo = findViewById(R.id.attempsInfo);

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) return;
                if (loginResult.getError() != null)
                    showLoginFailed(loginResult.getError());
                if (loginResult.getSuccess() != null)
                    updateUiWithUser(loginResult.getSuccess());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn0:
                inputPassword = inputPassword + "0";
                break;
            case R.id.btn1:
                inputPassword = inputPassword + "1";
                break;
            case R.id.btn2:
                inputPassword = inputPassword + "2";
                break;
            case R.id.btn3:
                inputPassword = inputPassword + "3";
                break;
            case R.id.btn4:
                inputPassword = inputPassword + "4";
                break;
            case R.id.btn5:
                inputPassword = inputPassword + "5";
                break;
            case R.id.btn6:
                inputPassword = inputPassword + "6";
                break;
            case R.id.btn7:
                inputPassword = inputPassword + "7";
                break;
            case R.id.btn8:
                inputPassword = inputPassword + "8";
                break;
            case R.id.btn9:
                inputPassword = inputPassword + "9";
                break;
            case R.id.btnRetry:
                inputPassword = "";
                break;
            case R.id.btnLogin:
                loginViewModel.login(inputPassword);
                inputPassword = "";
                counter--;
                if (counter == 0) {
                    findViewById(R.id.btnLogin).setEnabled(false);
                    findViewById(R.id.btnRetry).setEnabled(false);
                }
                attemptsInfo.setText("Number of attemps remaining: " + counter);
                break;
            default:
                break;
        }
    };

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = "Welcome " + model.getDisplayName();
        Intent intent = new Intent(LoginActivity.this,
                                        HomePageActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
