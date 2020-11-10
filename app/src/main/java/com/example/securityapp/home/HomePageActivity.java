package com.example.securityapp.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.Toast;

import com.example.securityapp.R;
import com.example.securityapp.ui.login.LoginActivity;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        Toast.makeText(getApplicationContext(), "Resetting app in 3 secs.",
                Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(HomePageActivity.this, LoginActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 2500);

    }
}