package com.example.securityapp.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.securityapp.R;
import com.example.securityapp.ui.login.LoginActivity;

public class HomePageActivity extends AppCompatActivity
                            implements View.OnClickListener {

    static String lockingDelayValue = "5";
    ImageButton btnAlarm;
    ImageButton btnSOSCall;
    ImageButton btnSettings;
    ImageButton btnLogout;
    TextView txtSettings;
    TextView txtAlarm;
    TextView txtLogout;
    TextView txtStatusTitle;
    TextView txtStatusAlarm;
    TextView txtStatusAlarmValue;
    TextView txtStatusPower;
    TextView txtStatusPowerValue;
    TextView txtStatusBattery;
    TextView txtStatusBatteryValue;
    TextView txtStatusPhone;
    TextView txtStatusPhoneValue;
    String loggedUser;

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
        txtSettings = findViewById(R.id.txtSettings);
        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);
        txtLogout = findViewById(R.id.txtLogout);
        txtStatusTitle = findViewById(R.id.txtStatusTitle);
        txtStatusAlarm = findViewById(R.id.txtStatusAlarm);
        txtStatusAlarmValue = findViewById(R.id.txtStatusAlarmValue);
        txtStatusPower = findViewById(R.id.txtStatusPower);
        txtStatusPowerValue = findViewById(R.id.txtStatusPowerValue);
        txtStatusBattery = findViewById(R.id.txtStatusBattery);
        txtStatusBatteryValue = findViewById(R.id.txtStatusBatteryValue);
        txtStatusPhone = findViewById(R.id.txtStatusPhone);
        txtStatusPhoneValue = findViewById(R.id.txtStatusPhoneValue);

        Bundle bundle = getIntent().getExtras();
        loggedUser = bundle.getString("loggedUser");
        toggleVisibleButtons();
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
            case R.id.btnLogout:
                logout();
                break;
        }
    }

    private void promptVerification(Boolean isAlarmActive) {
        String actionWord = "ενεργοποιήσετε";
        String resultWord = "ενεργοποιήθηκε";

        if (isAlarmActive) {
            actionWord = "απενεργοποιήσετε";
            resultWord = "απενεργοποιήθηκε";
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
            btnAlarm.setImageResource(R.drawable.unlock);
            txtAlarm.setText("ΕΝΕΡΓΟΠΟΙΗΣΗ ΣΥΝΑΓΕΡΜΟΥ");
            txtStatusAlarmValue.setText("  Ανενεργός");
            txtStatusAlarmValue.setTextColor(Color.RED);
        } else {
            btnAlarm.setImageResource(R.drawable.lock);
            txtAlarm.setText("ΑΠΕΝΕΡΓΟΠΟΙΗΣΗ ΣΥΝΑΓΕΡΜΟΥ");
            txtStatusAlarmValue.setText("  Ενεργός");
            txtStatusAlarmValue.setTextColor(Color.GREEN);
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

    private void logout () {
        this.finish();
    }

    private boolean isUserAdmin () {
        return loggedUser.equals("Admin");
    }

    private void toggleVisibleButtons() {
        if (!isUserAdmin()) {
            btnSettings.setEnabled(false);
            btnSettings.setVisibility(View.GONE);
            txtSettings.setVisibility(View.GONE);

            txtStatusPower.setVisibility(View.GONE);
            txtStatusPowerValue.setVisibility(View.GONE);
            txtStatusBattery.setVisibility(View.GONE);
            txtStatusBatteryValue.setVisibility(View.GONE);
            txtStatusPhone.setVisibility(View.GONE);
            txtStatusPhoneValue.setVisibility(View.GONE);

            ViewTreeObserver vto = txtStatusTitle.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    txtStatusTitle.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    btnLogout.setX(txtStatusTitle.getX());
                    txtLogout.setX(txtStatusTitle.getX());
                }
            });
        }
    }

}