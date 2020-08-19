package com.partner.oxostay.activities.ui.bookings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.partner.oxostay.R;

public class BookingsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button btUpcomingBookings, btBookingsHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);
        setUpViews();

    }


    private void setUpViews()
    {
        toolbar = findViewById(R.id.toolbar);
        btUpcomingBookings = findViewById(R.id.btUpcomingBookings);
        btBookingsHistory = findViewById(R.id.btBookingsHistory);
        setSupportActionBar(toolbar);

        btUpcomingBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btUpcomingBookings.setBackgroundResource(R.drawable.bookings_bt_left_select);
                btBookingsHistory.setBackgroundResource(R.drawable.bookings_bt_right);
            }
        });

        btBookingsHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btBookingsHistory.setBackgroundResource(R.drawable.bookings_bt_right_select);
                btUpcomingBookings.setBackgroundResource(R.drawable.bookings_bt_left);

            }
        });

    }
}