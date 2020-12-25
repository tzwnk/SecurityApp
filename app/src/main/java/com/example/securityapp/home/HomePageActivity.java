package com.example.securityapp.home;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.securityapp.R;
import com.example.securityapp.ui.login.LoginActivity;

import java.net.URI;

public class HomePageActivity extends AppCompatActivity
                            implements View.OnClickListener {

    ImageButton btnAlarm;
    ImageButton btnSOSCall;
    ImageButton btnSettings;
    TextView txtAlarm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        btnAlarm = findViewById(R.id.btnEnableAlarm);
        btnAlarm.setOnClickListener(this);
        txtAlarm = findViewById(R.id.txtAlarm);
        btnSOSCall = findViewById(R.id.btnSOSCall);
        btnSOSCall.setOnClickListener(this);
        btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEnableAlarm:
                promptVerification(LoginActivity.isAlarmActive);
                break;
            case R.id.btnSettings:
                loadSettingsActivity();
                break;
            case R.id.btnSOSCall:
                performSOSCall();
                break;
        }
    }

    private void promptVerification(Boolean isAlarmActive) {
        String actionWord = "ενεργοποιήσετε";
        String resultWord = "ενεργοποιήθηκε";
        int style = R.style.AlertDialog_Disabled;

        if (isAlarmActive) {
            actionWord = "απενεργοποιήσετε";
            resultWord = "απενεργοποιήθηκε";
            style = R.style.AlertDialog_Enabled;
        }

        String finalResultWord = resultWord;
        new AlertDialog.Builder(HomePageActivity.this, R.style.AlertDialog_Enabled)
            .setCancelable(true)
            .setTitle("ΕΠΙΒΕΒΑΙΩΣΗ")
            .setMessage("Είστε σίγουρος ότι θέλετε να " + actionWord + " το συναγερμό;")
            .setPositiveButton("ΝΑΙ", (dialog, which) -> {
                Toast.makeText(getApplicationContext(),
                        "Ο συναγερμός " + finalResultWord + "!",
                        Toast.LENGTH_LONG).show();
                changeButtons(LoginActivity.isAlarmActive);
                LoginActivity.isAlarmActive = !LoginActivity.isAlarmActive;
                dialog.cancel();
            })
            .setNegativeButton("ΟΧΙ", (dialog, which) -> {
                dialog.cancel();
            })
            .create()
            .show();
    }

    private void changeButtons(Boolean isAlarmActive) {
        if (isAlarmActive) {
            btnAlarm.setImageResource(R.drawable.lock);
            txtAlarm.setText("ΕΝΕΡΓΟΠΟΙΗΣΗ ΣΥΝΑΓΕΡΜΟΥ");
        } else {
            btnAlarm.setImageResource(R.drawable.unlock);
            txtAlarm.setText("ΑΠΕΝΕΡΓΟΠΟΙΗΣΗ ΣΥΝΑΓΕΡΜΟΥ");
        }
    }

    private void loadSettingsActivity () {
        Intent intent = new Intent (HomePageActivity.this,
                SettingsActivity.class);
        startActivity(intent);
    }

    private void performSOSCall () {
        Intent intent = new Intent (Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:112"));
        startActivity(intent);
    }



    //TODO: 1) rythmiseis (alli selida),
    // allakse glwssa kai kwdikous

}