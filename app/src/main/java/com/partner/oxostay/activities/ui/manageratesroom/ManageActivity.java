package com.partner.oxostay.activities.ui.manageratesroom;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.partner.oxostay.R;
import com.partner.oxostay.activities.ui.viewdata.ViewDataActivity;
import com.partner.oxostay.dtos.CityDto;
import com.partner.oxostay.utils.Constants;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ManageActivity extends AppCompatActivity {
    private static final String TAG = "ManageActivity";
    private ImageView ivBack;
    private TextView etFrom, etTo, tv3HoursFirstcheck, tv3HoursLastcheck, tv6HoursFirstcheck, tv6Hourslastcheck, tv12HoursFirstcheck, tv12HoursLastcheck;
    private LinearLayoutCompat ll3HoursFirstCheck, ll3HoursLastCheck, ll6HoursFirstCheck, ll6HoursLastCheck, ll12HoursFirstCheck, ll12HoursLastCheck;
    private Button btSaveChanges, btViewData;
    private DatabaseReference dbRef;
    private DatabaseReference dbRefTest;
    private String strDateFrom, strDateTo, three_h_price, six_h_price, twelve_h_price, rooms_available, three_h_first_checkin, three_h_last_checkin, six_h_first_checkin, six_h_last_checkin, twelve_h_first_checkin, twelve_h_last_checkin, hotel_id;
    private EditText et3Hours, et6Hours, et12Hours, etRoomsAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        setUpViews();
    }

    private void setUpViews() {
        /**XML Components*/
        ivBack = findViewById(R.id.ivBack);
        etFrom = findViewById(R.id.etFrom);
        etTo = findViewById(R.id.etTo);
        btViewData = findViewById(R.id.btViewData);
        btViewData = findViewById(R.id.btViewData);
        btSaveChanges = findViewById(R.id.btSaveChanges);
        et3Hours = findViewById(R.id.et3Hours);
        et6Hours = findViewById(R.id.et6Hours);
        et12Hours = findViewById(R.id.et12Hours);
        etRoomsAvailable = findViewById(R.id.etRoomsAvailable);
        etFrom.setText("Select Date");
        etTo.setText("Select Date");
        dbRef = FirebaseDatabase.getInstance().getReference(Constants.OXO_STAY_PARTNER);

        //3 hours First checkin
        tv3HoursFirstcheck = findViewById(R.id.tv3HoursFirstcheck);
        ll3HoursFirstCheck = findViewById(R.id.ll3HoursFirstCheck);

        //3 hours last checkin
        ll3HoursLastCheck = findViewById(R.id.ll3HoursLastCheck);
        tv3HoursLastcheck = findViewById(R.id.tv3HoursLastcheck);

        //6 hours First checkin
        ll6HoursFirstCheck = findViewById(R.id.ll6HoursFirstCheck);
        tv6HoursFirstcheck = findViewById(R.id.tv6HoursFirstcheck);

        //6 hours last checkin
        ll6HoursLastCheck = findViewById(R.id.ll6HoursLastCheck);
        tv6Hourslastcheck = findViewById(R.id.tv6HoursLastcheck);

        //12 hours First checkin
        ll12HoursFirstCheck = findViewById(R.id.ll12HoursFirstCheck);
        tv12HoursFirstcheck = findViewById(R.id.tv12HoursFirstcheck);

        //12 hours last checkin
        ll12HoursLastCheck = findViewById(R.id.ll12HoursLastCheck);
        tv12HoursLastcheck = findViewById(R.id.tv12HoursLastcheck);

        /** Java components */
        final Calendar myCalendar = Calendar.getInstance();

        /**OnClicks*/
        ivBack.setOnClickListener(v -> onBackPressed());

        final DatePickerDialog.OnDateSetListener date2 = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String month = myCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

            etTo.setText(month + " " + dayOfMonth + "," + " " + year);
        };


        etTo.setOnClickListener(v -> new DatePickerDialog(ManageActivity.this, date2, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());


        final DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String month = myCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

            etFrom.setText(month + " " + dayOfMonth + "," + " " + year);
        };
        etFrom.setOnClickListener(v -> new DatePickerDialog(ManageActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        ll3HoursFirstCheck.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(ManageActivity.this, (timePicker, selectedHour, selectedMinute) -> {
                Time time = new Time(selectedHour, selectedMinute, 0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mma");
                //format takes in a Date, and Time is a sublcass of Date
                String s = simpleDateFormat.format(time);
                tv3HoursFirstcheck.setText(s);
            }, hour, minute, false);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        });


        ll3HoursLastCheck.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(ManageActivity.this, (timePicker, selectedHour, selectedMinute) -> {


                Time time = new Time(selectedHour, selectedMinute, 0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mma");
                //format takes in a Date, and Time is a sublcass of Date
                String s = simpleDateFormat.format(time);
                tv3HoursLastcheck.setText(s);

            }, hour, minute, false);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        });

        ll6HoursFirstCheck.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(ManageActivity.this, (timePicker, selectedHour, selectedMinute) -> {
                Time time = new Time(selectedHour, selectedMinute, 0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mma");
                //format takes in a Date, and Time is a sublcass of Date
                String s = simpleDateFormat.format(time);
                tv6HoursFirstcheck.setText(s);
            }, hour, minute, false);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        });

        ll6HoursLastCheck.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(ManageActivity.this, (timePicker, selectedHour, selectedMinute) -> {
                Time time = new Time(selectedHour, selectedMinute, 0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mma");
                //format takes in a Date, and Time is a sublcass of Date
                String s = simpleDateFormat.format(time);
                tv6Hourslastcheck.setText(s);
            }, hour, minute, false);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        });

        ll12HoursFirstCheck.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(ManageActivity.this, (timePicker, selectedHour, selectedMinute) -> {
                Time time = new Time(selectedHour, selectedMinute, 0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mma");
                //format takes in a Date, and Time is a sublcass of Date
                String s = simpleDateFormat.format(time);
                tv12HoursFirstcheck.setText(s);
            }, hour, minute, false);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        });

        ll12HoursLastCheck.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(ManageActivity.this, (timePicker, selectedHour, selectedMinute) -> {
                Time time = new Time(selectedHour, selectedMinute, 0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mma");
                //format takes in a Date, and Time is a sublcass of Date
                String s = simpleDateFormat.format(time);
                tv12HoursLastcheck.setText(s);
            }, hour, minute, false);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        });

        hotel_id = this.getSharedPreferences(Constants.ACCESS_PREFS, MODE_PRIVATE).getString(
                Constants.HOTEL_ID, "notfound");
        btSaveChanges.setOnClickListener(v -> saveChanges());
        btViewData.setOnClickListener(v -> {
            Intent n = new Intent(ManageActivity.this, ViewDataActivity.class);
            startActivity(n);

        });


//        dbRefTest = FirebaseDatabase.getInstance().getReference(Constants.OXO_STAY_PARTNER);
//
//        et3Hours.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
////                Log.e(TAG, "onTextChanged: "+s.toString());
//                dbRefTest.child("cities").orderByChild("city_name").startAt(s.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Log.e(TAG, "onDataChange: "+dataSnapshot.hasChildren());
//                        if(dataSnapshot.hasChildren())
//                        {
//                            CityDto c = dataSnapshot.getValue(CityDto.class);
//                            Log.e(TAG, "onDataChange: "+c.getCity_name());
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

    }

    private void saveChanges() {
        dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).child("room_rate_3_hour").setValue(et3Hours.getText().toString().trim());
        dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).child("room_rate_6_hour").setValue(et6Hours.getText().toString().trim());
        dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).child("room_rate_12_hour").setValue(et12Hours.getText().toString().trim());
        dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).child("rooms_available").setValue(etRoomsAvailable.getText().toString().trim());

        dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).child("date_from").setValue(etFrom.getText().toString().trim());
        dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).child("date_to").setValue(etTo.getText().toString().trim());

        dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).child("room_3h_first_checkin").setValue(tv3HoursFirstcheck.getText().toString().trim());
        dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).child("room_3h_last_checkin").setValue(tv3HoursLastcheck.getText().toString().trim());

        dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).child("room_6h_first_checkin").setValue(tv6HoursFirstcheck.getText().toString().trim());
        dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).child("room_6h_last_checkin").setValue(tv6Hourslastcheck.getText().toString().trim());

        dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).child("room_12h_first_checkin").setValue(tv12HoursFirstcheck.getText().toString().trim());
        dbRef.child(Constants.HOTELS_APPROVED_KEY).child(hotel_id).child("room_12h_last_checkin").setValue(tv12HoursFirstcheck.getText().toString().trim());

        finish();


    }
}