package com.partner.oxostay.activities.ui.manageratesroom;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.partner.oxostay.R;

import java.util.Calendar;
import java.util.Locale;

public class ManageActivity extends AppCompatActivity {
    private ImageView ivBack;
    private TextView etFrom, etTo, tv3HoursFirstcheck, tv3HoursLastcheck, tv6HoursFirstcheck, tv6Hourslastcheck, tv12HoursFirstcheck, tv12HoursLastcheck;
    private LinearLayoutCompat ll3HoursFirstCheck, ll3HoursLastCheck, ll6HoursFirstCheck, ll6HoursLastCheck, ll12HoursFirstCheck, ll12HoursLastCheck;

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
        etFrom.setText("Select Date");

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
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String month = myCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

                etTo.setText(month + " " + dayOfMonth + "," + " " + year);
            }

        };


//        etTo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new DatePickerDialog(ManageActivity.this, date2, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//
//
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String month = myCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

                etFrom.setText(month + " " + dayOfMonth + "," + " " + year);
            }

        };
        etFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ManageActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        ll3HoursFirstCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ManageActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (Integer.toString(selectedMinute).length() == 1) {
                            tv3HoursFirstcheck.setText(selectedHour + ":"  + "0" + selectedMinute);
                        } else {
                            tv3HoursFirstcheck.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


        ll3HoursLastCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ManageActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (Integer.toString(selectedMinute).length() == 1) {
                            tv3HoursLastcheck.setText(selectedHour + ":"  + "0" + selectedMinute);
                        } else {
                            tv3HoursLastcheck.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        ll6HoursFirstCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ManageActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (Integer.toString(selectedMinute).length() == 1) {
                            tv6HoursFirstcheck.setText(selectedHour + ":"  + "0" + selectedMinute);
                        } else {
                            tv6HoursFirstcheck.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        ll6HoursLastCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ManageActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (Integer.toString(selectedMinute).length() == 1) {
                            tv6Hourslastcheck.setText(selectedHour + ":"  + "0" + selectedMinute);
                        } else {
                            tv6Hourslastcheck.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        ll12HoursFirstCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ManageActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (Integer.toString(selectedMinute).length() == 1) {
                            tv12HoursFirstcheck.setText(selectedHour + ":"  + "0" + selectedMinute);
                        } else {
                            tv12HoursFirstcheck.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        ll12HoursLastCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ManageActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (Integer.toString(selectedMinute).length() == 1) {
                            tv12HoursLastcheck.setText(selectedHour + ":"  + "0" + selectedMinute);
                        } else {
                            tv12HoursLastcheck.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });



    }
}