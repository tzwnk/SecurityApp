package com.example.securityapp.ui.login;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.securityapp.R;
import com.example.securityapp.home.HomePageActivity;

public class LoginActivity extends AppCompatActivity
                        implements View.OnClickListener{

    private static final int PERMISSION_REQUEST_CODE = 1;
    private LoginViewModel loginViewModel;
    private final String defaultPhrase = "Please type password";
    private int counter = 3;
    static public boolean isAlarmActive = false;

    private TextView attemptsInfo;
    private TextView inputDisplay;

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
        inputDisplay = findViewById(R.id.inputDisplay);
        inputDisplay.setText(defaultPhrase);

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) return;
                if (loginResult.getError() != null)
                    showLoginFailed(loginResult.getError());
                if (loginResult.getSuccess() != null) {
                    counter++;
                    updateUiWithUser(loginResult.getSuccess());
                }
            }
        });

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.SEND_SMS)
                    == PackageManager.PERMISSION_DENIED) {

                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.SEND_SMS};
                requestPermissions(permissions, PERMISSION_REQUEST_CODE);

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn0:
                updateDisplayPassword("0");
                break;
            case R.id.btn1:
                updateDisplayPassword("1");
                break;
            case R.id.btn2:
                updateDisplayPassword("2");
                break;
            case R.id.btn3:
                updateDisplayPassword("3");
                break;
            case R.id.btn4:
                updateDisplayPassword("4");
                break;
            case R.id.btn5:
                updateDisplayPassword("5");
                break;
            case R.id.btn6:
                updateDisplayPassword("6");
                break;
            case R.id.btn7:
                updateDisplayPassword("7");
                break;
            case R.id.btn8:
                updateDisplayPassword("8");
                break;
            case R.id.btn9:
                updateDisplayPassword("9");
                break;
            case R.id.btnRetry:
                resetDisplayPassword();
                break;
            case R.id.btnLogin:
                loginViewModel.login((String) inputDisplay.getText());
                resetDisplayPassword();
                counter--;

                switch (counter) {
                    case 2:
                        attemptsInfo.setBackgroundColor(Color.YELLOW);
                        break;
                    case 1:
                        attemptsInfo.setBackgroundColor(Color.RED);
                        attemptsInfo.setTextColor(Color.BLACK);
                        break;
                }

                if (counter == 0) {
                    findViewById(R.id.btnLogin).setEnabled(false);
                    findViewById(R.id.btnRetry).setEnabled(false);
                    notifySystemAdmin();
                }
                attemptsInfo.setText("Number of attemps remaining: " + counter);
                break;
            default:
                break;
        }
    }

    private void updateDisplayPassword (String newDigit) {
        if (inputDisplay.getText().equals(defaultPhrase))
            inputDisplay.setText("");
        inputDisplay.setText(inputDisplay.getText() + newDigit);
    }

    private void resetDisplayPassword () {
        inputDisplay.setText(defaultPhrase);
    }

    private void updateUiWithUser (LoggedInUserView model) {
        String welcome = "Welcome " + model.getDisplayName();
        Intent intent = new Intent (LoginActivity.this,
                                        HomePageActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void showLoginFailed (@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void notifySystemAdmin () {
        String messageToSend = "I need help with the alarm";
        String number = "00306979456765";
        SmsManager.getDefault().sendTextMessage(number,null,
                messageToSend, null,null);
        Toast.makeText(getApplicationContext(), "Emergency message sent to admin",
                Toast.LENGTH_LONG).show();
    }
}
