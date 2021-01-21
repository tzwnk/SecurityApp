package com.example.securityapp.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.securityapp.R;
import com.example.securityapp.data.LoginDataSource;

public class SettingsActivity extends AppCompatActivity
                            implements View.OnClickListener{

    Button btnSave;
    Button btnFactorySettingsReset;
    EditText adminPass;
    EditText userPass;
    EditText lockingDelay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        btnFactorySettingsReset = findViewById(R.id.btnFactorySettingsReset);
        btnFactorySettingsReset.setOnClickListener(this);
        adminPass = findViewById(R.id.adminPass);
        userPass = findViewById(R.id.userPass);

        lockingDelay = findViewById(R.id.lockingDelay);
        lockingDelay.setText(HomePageActivity.lockingDelayValue);
        updateFieldsWithCurrentValues();
    }

    public void onClick(View v) {
        switch (v.getId()) { //todo: change to if with one case
            case R.id.btnSave:
                saveNewValues();
                break;
            case R.id.btnFactorySettingsReset:
                factoryResetPasswords();
                break;
        }
    }

    private void updateFieldsWithCurrentValues() {
        adminPass.setText(LoginDataSource.adminPassword);
        userPass.setText(LoginDataSource.userPassword);
        lockingDelay.setText(HomePageActivity.lockingDelayValue);
    }

    private void saveNewValues() {
        LoginDataSource.adminPassword = String.valueOf(adminPass.getText());
        LoginDataSource.userPassword = String.valueOf(userPass.getText());
        HomePageActivity.lockingDelayValue = String.valueOf(lockingDelay.getText());
        Toast.makeText(getApplicationContext(), "Οι τιμές ενημερώθηκαν",
                Toast.LENGTH_SHORT).show();
    }

    private void factoryResetPasswords() {
        LoginDataSource.adminPassword = "12345";
        LoginDataSource.userPassword = "1234";
        HomePageActivity.lockingDelayValue = "5";
        updateFieldsWithCurrentValues();
        Toast.makeText(getApplicationContext(), "Έγινε επαναφορά κωδικών",
                Toast.LENGTH_SHORT).show();
    }

}