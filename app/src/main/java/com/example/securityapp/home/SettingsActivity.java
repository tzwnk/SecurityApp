package com.example.securityapp.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.securityapp.R;
import com.example.securityapp.data.LoginDataSource;
import com.example.securityapp.ui.login.LoginActivity;

public class SettingsActivity extends AppCompatActivity
                            implements View.OnClickListener{

    Button btnSave;
    Button btnFactorySettingsReset;
    EditText adminPass;
    EditText userPass;

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
        updateTextsWithCurrentPasswords();
    }

    public void onClick(View v) {
        switch (v.getId()) { //todo: change to if with one case
            case R.id.btnSave:
                saveNewPasswords();
                break;
            case R.id.btnFactorySettingsReset:
                factoryResetPasswords();
                break;
        }
    }

    private void updateTextsWithCurrentPasswords() {
        adminPass.setText(LoginDataSource.adminPassword);
        userPass.setText(LoginDataSource.userPassword);
    }

    private void saveNewPasswords() {
        LoginDataSource.adminPassword = String.valueOf(adminPass.getText());
        LoginDataSource.userPassword = String.valueOf(userPass.getText());
        Toast.makeText(getApplicationContext(), "Οι κωδικοί ενημερώθηκαν",
                Toast.LENGTH_SHORT).show();
    }

    private void factoryResetPasswords() {
        LoginDataSource.adminPassword = "12345";
        LoginDataSource.userPassword = "1234";
        updateTextsWithCurrentPasswords();
        Toast.makeText(getApplicationContext(), "Έγινε επαναφορά κωδικών",
                Toast.LENGTH_SHORT).show();
    }

}