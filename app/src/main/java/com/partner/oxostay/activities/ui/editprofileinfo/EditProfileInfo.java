package com.partner.oxostay.activities.ui.editprofileinfo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.partner.oxostay.R;
import com.partner.oxostay.activities.ui.maps.MapsActivity;
import com.partner.oxostay.activities.ui.maps.MapsActivity2;
import com.partner.oxostay.dtos.CityDto;
import com.partner.oxostay.dtos.RegisterDto;
import com.partner.oxostay.utils.Constants;

import java.util.ArrayList;

public class EditProfileInfo extends AppCompatActivity {
    private String TAG = "EditProfileInfo";
    private String hotel_id;
    private AppCompatEditText tvHotelName, tvLocation, tvHotelContactNumber, tvManagerName, tvHotelAddress, tvHotelEmailAddress, tvHotelSecondaryEmailAddress, etHotelDesc;
    private DatabaseReference dbRef, dbRefCity;
    private Button btSaveChanges, btLongLat;
    private ImageView ivBack;
    private Spinner spinnerCity;
    private ProgressDialog progressDialog;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private ArrayList<String> cityList;
    private String[] locationPermission = {Manifest.permission.ACCESS_FINE_LOCATION};



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

    @RequiresApi(api = Build.VERSION_CODES.M)
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
        etHotelDesc = findViewById(R.id.etHotelDesc);
        btSaveChanges = findViewById(R.id.btSaveChanges);
        ivBack = findViewById(R.id.ivBack);
        btLongLat = findViewById(R.id.btLongLat);
        spinnerCity = findViewById(R.id.spinnerCity);
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
                    etHotelDesc.setText(registerDto.getHotel_desc());
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btSaveChanges.setOnClickListener(v -> {
//            Select City that you want to change


            String cityChange = spinnerCity.getSelectedItem().toString();
            Log.e(TAG, "setupViews: "+cityChange);

            dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).child("phNumber").setValue(tvHotelContactNumber.getText().toString().trim());
            dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).child("hotel_name").setValue(tvHotelName.getText().toString().trim());
            dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).child("hotel_address").setValue(tvLocation.getText().toString().trim());
            dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).child("hotel_email").setValue(tvHotelEmailAddress.getText().toString().trim());
            dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).child("hotel_secondary_email").setValue(tvHotelSecondaryEmailAddress.getText().toString().trim());
            dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).child("hotel_desc").setValue(etHotelDesc.getText().toString().trim());
            dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).child("city_name").setValue(cityChange);
            finish();

        });
        ivBack.setOnClickListener(v -> onBackPressed());

        btLongLat.setOnClickListener(v -> {
            if (!hasPermissions(getApplicationContext(), locationPermission))
            {
                requestPermissions(locationPermission,
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else
                {
                Intent mapsActivityIntent = new Intent(EditProfileInfo.this, MapsActivity2.class);
                startActivity(mapsActivityIntent);
            }


        });
        getCityList();
    }


    private void getCityList()
    {
        cityList = new ArrayList<>();
        cityList.add("Select City that you want to change");

        dbRef.child("cities").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    CityDto c = dataSnapshot1.getValue(CityDto.class);
                    Log.e(TAG, "onDataChange: "+c.getCity_name());
                    cityList.add(c.getCity_name());
                }

                ArrayAdapter<String> machineSpinnerAdapter = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item, cityList);
                spinnerCity.setAdapter(machineSpinnerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
            case 2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent mapsActivityIntent = new Intent(EditProfileInfo.this, MapsActivity2.class);
                    startActivity(mapsActivityIntent);
                } else {
                    Toast.makeText(this, "Permission denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

}