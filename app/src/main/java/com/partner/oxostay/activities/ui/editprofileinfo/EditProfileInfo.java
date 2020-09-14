package com.partner.oxostay.activities.ui.editprofileinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.partner.oxostay.R;
import com.partner.oxostay.activities.ui.maps.MapsActivity;
import com.partner.oxostay.dtos.RegisterDto;
import com.partner.oxostay.utils.Constants;

public class EditProfileInfo extends AppCompatActivity {
    private String TAG = "EditProfileInfo";
    private String hotel_id;
    private AppCompatEditText tvHotelName, tvLocation, tvHotelContactNumber, tvManagerName, tvHotelAddress, tvHotelEmailAddress, tvHotelSecondaryEmailAddress;
    private DatabaseReference dbRef;
    private Button btSaveChanges, btLongLat;
    private ImageView ivBack;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_info);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading your Account Information");
        progressDialog.setCancelable(false);
        progressDialog.show();
        setupViews();
    }

    private void setupViews()
    {
        hotel_id = this.getSharedPreferences(Constants.ACCESS_PREFS, MODE_PRIVATE).getString(
                Constants.HOTEL_ID, "noHotelId");

        tvHotelName = findViewById(R.id.tvHotelName);
        tvLocation = findViewById(R.id.tvLocation);
        tvHotelContactNumber = findViewById(R.id.tvHotelContactNumber);
        tvManagerName = findViewById(R.id.tvManagerName);
        tvManagerName.setVisibility(View.GONE);
        tvHotelAddress = findViewById(R.id.tvHotelAddress);
        tvHotelEmailAddress = findViewById(R.id.tvHotelEmailAddress);
        tvHotelSecondaryEmailAddress = findViewById(R.id.tvHotelSecondaryEmailAddress);
        tvHotelAddress = findViewById(R.id.tvHotelAddress);
        btSaveChanges = findViewById(R.id.btSaveChanges);
        ivBack = findViewById(R.id.ivBack);
        btLongLat = findViewById(R.id.btLongLat);
        dbRef = FirebaseDatabase.getInstance().getReference(Constants.OXO_STAY_PARTNER);

        dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren());
                {
                    RegisterDto registerDto = dataSnapshot.getValue(RegisterDto.class);
                    Log.e(TAG, "onDataChange: "+registerDto.getAddress());
                    tvHotelName.setText(registerDto.getHotel_name());
                    tvLocation.setText(registerDto.getHotel_address());
                    tvHotelContactNumber.setText(registerDto.getPhNumber());
                    tvHotelEmailAddress.setText(registerDto.getHotel_email());
                    tvHotelSecondaryEmailAddress.setText(registerDto.getHotel_secondary_email());
                    tvHotelAddress.setText(registerDto.getHotel_address());
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btSaveChanges.setOnClickListener(v -> {
            dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).child("phNumber").setValue(tvHotelContactNumber.getText().toString().trim());
            dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).child("hotel_name").setValue(tvHotelName.getText().toString().trim());
            dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).child("hotel_address").setValue(tvLocation.getText().toString().trim());
            dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).child("hotel_email").setValue(tvHotelEmailAddress.getText().toString().trim());
            dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).child("hotel_secondary_email").setValue(tvHotelSecondaryEmailAddress.getText().toString().trim());
            finish();

        });
        ivBack.setOnClickListener(v -> onBackPressed());

        btLongLat.setOnClickListener(v -> {
            Intent mapsActivityIntent = new Intent(EditProfileInfo.this, MapsActivity.class);
            startActivity(mapsActivityIntent);

        });
    }
}