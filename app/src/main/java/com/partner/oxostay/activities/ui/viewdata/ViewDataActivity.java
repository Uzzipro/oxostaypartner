package com.partner.oxostay.activities.ui.viewdata;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.partner.oxostay.R;
import com.partner.oxostay.dtos.RegisterDto;
import com.partner.oxostay.utils.Constants;

public class ViewDataActivity extends AppCompatActivity {
    private static final String TAG = "ViewDataActivity";
    private TextView tvFrom, tvTo, three_hours_rate, six_hours_rate, twelve_hours_rate, room_available, tv3Hoursfc, tv3Hourslc, tv6Hoursfc, tv6Hourslc, tv12Hoursfc, tv12Hourslc;
    private DatabaseReference dbRef;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        setupviews();
    }


    private void setupviews() {
        dbRef = FirebaseDatabase.getInstance().getReference(Constants.OXO_STAY_PARTNER);
        tvFrom = findViewById(R.id.tvDateFrom);
        tvTo = findViewById(R.id.tvDateTo);
        three_hours_rate = findViewById(R.id.et3Hours);
        six_hours_rate = findViewById(R.id.et6Hours);
        twelve_hours_rate = findViewById(R.id.et12Hours);
        room_available = findViewById(R.id.etRoomsAvailable);
        tv3Hoursfc = findViewById(R.id.tv3HoursFirstcheck);
        tv3Hourslc = findViewById(R.id.tv3HoursLastcheck);

        tv6Hoursfc = findViewById(R.id.tv6HoursFirstcheck);
        tv6Hourslc = findViewById(R.id.tv6HoursLastcheck);

        tv12Hoursfc = findViewById(R.id.tv12HoursFirstcheck);
        tv12Hourslc = findViewById(R.id.tv12HoursLastcheck);
        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        String hotel_id = this.getSharedPreferences(Constants.ACCESS_PREFS, MODE_PRIVATE).getString(
                Constants.HOTEL_ID, "notfound");
        populateData(hotel_id);
    }

    private void populateData(String hotel_id) {
        Log.e(TAG, "populateData: " + hotel_id);

        dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    RegisterDto registerDto = dataSnapshot.getValue(RegisterDto.class);
                    tvFrom.setText(registerDto.getDate_from());
                    tvTo.setText(registerDto.getDate_to());
                    three_hours_rate.setText(registerDto.getRoom_rate_3_hour());
                    six_hours_rate.setText(registerDto.getRoom_rate_6_hour());
                    twelve_hours_rate.setText(registerDto.getRoom_rate_12_hour());
                    room_available.setText(registerDto.getRooms_available());
                    tv3Hoursfc.setText(registerDto.getRoom_3h_first_checkin());
                    tv3Hourslc.setText(registerDto.getRoom_3h_last_checkin());

                    tv6Hoursfc.setText(registerDto.getRoom_6h_first_checkin());
                    tv6Hourslc.setText(registerDto.getRoom_6h_last_checkin());

                    tv12Hoursfc.setText(registerDto.getRoom_12h_first_checkin());
                    tv12Hourslc.setText(registerDto.getRoom_12h_last_checkin());


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}