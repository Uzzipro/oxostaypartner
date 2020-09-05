package com.partner.oxostay.activities.ui.changepassword;

import android.app.PendingIntent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.partner.oxostay.R;
import com.partner.oxostay.utils.Constants;

public class ChangepasswordActivity extends AppCompatActivity {
    private static final String TAG = "ChangepasswordActivity";
    private ImageView ivBack;
    private AppCompatEditText etEnterNewPassword, etConfirmnewPassword;
    private Button btChangePass;
    private DatabaseReference dbRef;
    private String strEnterNewPassword, strConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        setUpViews();
    }

    private void setUpViews() {
        etEnterNewPassword = findViewById(R.id.etEnterNewPassword);
        etConfirmnewPassword = findViewById(R.id.etConfirmnewPassword);
        dbRef = FirebaseDatabase.getInstance().getReference(Constants.OXO_STAY_PARTNER);
        btChangePass = findViewById(R.id.btChangePass);
        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(v -> onBackPressed());

        btChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePass();

            }
        });
    }

    private void changePass()
    {
        String userKey = this.getSharedPreferences(Constants.ACCESS_PREFS, MODE_PRIVATE).getString(
                Constants.USER_KEY, "userKeyNotFound");
        strEnterNewPassword = etEnterNewPassword.getText().toString().trim();
        strConfirmPassword = etConfirmnewPassword.getText().toString().trim();

        if(TextUtils.isEmpty(strEnterNewPassword) && TextUtils.isEmpty(strConfirmPassword))
        {
            makeToast("Please enter your new Password and Confirm that password");
        }
        {
            if(TextUtils.equals(strEnterNewPassword, strConfirmPassword))
            {
             makeToast("Done");
            }
            else
            {
                makeToast("New password and the password you confirm do not match");
            }
        }
//        dbRef.child("users").child(userKey)
    }

    private void makeToast(String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}