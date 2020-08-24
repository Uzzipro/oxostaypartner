package com.partner.oxostay.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.partner.oxostay.R;
import com.partner.oxostay.dtos.LoginDto;
import com.partner.oxostay.utils.Constants;

public class LoginActivity extends AppCompatActivity {
    private String TAG = "LoginActivity";
    private Button btLogin;
    private DatabaseReference dbRefLogin;
    private AppCompatEditText etPhNumber, etPassword;
    private String phNumberstr, passwordstr;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //xml contents
        btLogin = findViewById(R.id.btLogin);
        etPhNumber = findViewById(R.id.etPhNumber);
        etPassword = findViewById(R.id.etPassword);

        //Shared prefs
        editor = getSharedPreferences(Constants.ACCESS_PREFS, MODE_PRIVATE).edit();

        //Database
        dbRefLogin = FirebaseDatabase.getInstance().getReference().child(Constants.OXO_STAY_PARTNER).child("users");

        //making database call
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phNumberstr = etPhNumber.getText().toString().trim();
                passwordstr = etPassword.getText().toString().trim();

                if(!TextUtils.isEmpty(phNumberstr) && !TextUtils.isEmpty(passwordstr))
                {
                    dbRefLogin.orderByChild("phNumber").equalTo(phNumberstr).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot != null)
                            {
                                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                                {
                                    LoginDto loginDto = dataSnapshot1.getValue(LoginDto.class);
                                    if(loginDto.getPassword().equals(passwordstr))
                                    {
                                        editor.putBoolean(Constants.LOGGED_IN, true);
                                        editor.putString(Constants.USER_KEY, dataSnapshot1.getKey());
                                        editor.apply();
                                        Intent n = new Intent(LoginActivity.this, NavHomeActivity.class);
                                        startActivity(n);
                                    }
                                    else
                                    {
                                        makeToast("Password is wrong");
                                    }

                                }


                            }
                            else
                            {
                                Log.e(TAG, "onDataChange: no Data found from this phone number");
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });                }
                else
                {
                    makeToast("Please fill both the fields to login");
                }




            }
        });
    }
    private void makeToast(String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}