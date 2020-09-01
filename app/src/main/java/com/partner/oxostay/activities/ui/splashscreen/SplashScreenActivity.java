package com.partner.oxostay.activities.ui.splashscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.partner.oxostay.R;
import com.partner.oxostay.activities.LoginActivity;
import com.partner.oxostay.activities.NavHomeActivity;
import com.partner.oxostay.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;

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



        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Log.e(TAG, "onComplete: "+token);


                    }
                });
        createNotificationChannel();

    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel request = new NotificationChannel("0", "Request",
                    NotificationManager.IMPORTANCE_HIGH);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(request);
            }
        }
    }
}