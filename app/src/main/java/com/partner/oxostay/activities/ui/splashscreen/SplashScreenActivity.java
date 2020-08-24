package com.partner.oxostay.activities.ui.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.partner.oxostay.R;
import com.partner.oxostay.activities.LoginActivity;
import com.partner.oxostay.activities.NavHomeActivity;
import com.partner.oxostay.utils.Constants;

public class SplashScreenActivity extends AppCompatActivity {
    private String TAG = "SplashScreenActivity";
    private SharedPreferences.Editor editor;
    private boolean loggedinstatus;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        loggedinstatus = this.getSharedPreferences(Constants.ACCESS_PREFS, MODE_PRIVATE).getBoolean(
                Constants.LOGGED_IN, false);

        if(loggedinstatus)
        {
            Intent n = new Intent(SplashScreenActivity.this, NavHomeActivity.class);
            startActivity(n);
        }
        else
        {
            Intent n = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(n);
        }

    }
}