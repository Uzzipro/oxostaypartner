package com.partner.oxostay.activities.ui.maps;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.partner.oxostay.R;
import com.partner.oxostay.utils.Constants;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String TAG = "MapsActivity2";
    private GPSTracker gpsTracker;
    private String hotel_id;
    private DatabaseReference dbRef;
    private ProgressDialog progressDialog;
    private ImageView btBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        gpsTracker = new GPSTracker(this);

        btBack = findViewById(R.id.btBack);
        hotel_id = this.getSharedPreferences(Constants.ACCESS_PREFS, MODE_PRIVATE).getString(
                Constants.HOTEL_ID, "noHotelId");
        dbRef = FirebaseDatabase.getInstance().getReference(Constants.OXO_STAY_PARTNER);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Setting the location");
        progressDialog.setCancelable(false);


        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera


        if (gpsTracker.getIsGPSTrackingEnabled()) {
            String stringLatitude = String.valueOf(gpsTracker.latitude);

            String stringLongitude = String.valueOf(gpsTracker.longitude);
            LatLng customLocation = new LatLng(Double.valueOf(stringLatitude), Double.valueOf(stringLongitude));
            mMap.addMarker(new MarkerOptions().position(customLocation).draggable(true).title(gpsTracker.getAddressLine(this)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(customLocation));
            mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {
                    Log.e(TAG, "onMarkerDragEnd: drag Started");


                }

                @Override
                public void onMarkerDrag(Marker marker) {
                    Log.e(TAG, "onMarkerDragEnd: drag Dragging");


                }

                @Override
                public void onMarkerDragEnd(Marker marker) {
                    progressDialog.show();

                    Toast.makeText(getApplicationContext(), "Location changed", Toast.LENGTH_LONG).show();
                    dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).child("location_lat").setValue(marker.getPosition().latitude);
                    dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).child("location_long").setValue(marker.getPosition().longitude);
                    progressDialog.dismiss();


                }
            });
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(customLocation)
                    .zoom(17).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gpsTracker.showSettingsAlert();
        }
    }
}