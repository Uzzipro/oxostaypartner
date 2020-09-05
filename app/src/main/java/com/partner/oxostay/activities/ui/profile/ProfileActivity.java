package com.partner.oxostay.activities.ui.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.partner.oxostay.R;
import com.partner.oxostay.activities.ui.editprofileinfo.EditProfileInfo;
import com.partner.oxostay.dtos.RegisterDto;
import com.partner.oxostay.utils.Constants;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    private ImageView ivBack;
    private Button btEdit;
    private AppCompatTextView tvHotelName, tvLocation, tvHotelContact, tvManagerName, tvHotelAddress, tvHotelEmail, tvHotelSecondaryEmail;
    private DatabaseReference dbRef;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading your Account Information");
        progressDialog.setCancelable(false);
        progressDialog.show();
        setUpViews();

    }

    private void setUpViews() {
        ivBack = findViewById(R.id.ivBack);
        btEdit = findViewById(R.id.btEdit);
        tvHotelName = findViewById(R.id.tvHotelName);
        tvLocation = findViewById(R.id.tvLocation);
        tvHotelContact = findViewById(R.id.tvHotelContactNumber);
        tvHotelAddress = findViewById(R.id.tvHotelAddress);
        tvHotelEmail = findViewById(R.id.tvHotelEmailAddress);
        tvHotelSecondaryEmail = findViewById(R.id.tvHotelSecondaryEmailAddress);
        dbRef = FirebaseDatabase.getInstance().getReference(Constants.OXO_STAY_PARTNER);
        btEdit.setOnClickListener(v -> {
            Intent n = new Intent(ProfileActivity.this, EditProfileInfo.class);
            startActivity(n);
        });
        ivBack.setOnClickListener(v -> onBackPressed());
        String hotel_id = this.getSharedPreferences(Constants.ACCESS_PREFS, MODE_PRIVATE).getString(
                Constants.HOTEL_ID, "noHotelId");

        dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren());
                {
                    RegisterDto registerDto = dataSnapshot.getValue(RegisterDto.class);
                    Log.e(TAG, "onDataChange: "+registerDto.getAddress());
                    tvHotelName.setText(registerDto.getHotel_name());
                    tvLocation.setText(registerDto.getHotel_address());
                    tvHotelContact.setText(registerDto.getPhNumber());
                    tvHotelEmail.setText(registerDto.getHotel_email());
                    tvHotelSecondaryEmail.setText(registerDto.getHotel_secondary_email());
                    tvHotelAddress.setText(registerDto.getHotel_address());
                    progressDialog.dismiss();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}