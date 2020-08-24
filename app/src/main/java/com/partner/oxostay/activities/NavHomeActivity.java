package com.partner.oxostay.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.partner.oxostay.R;
import com.partner.oxostay.activities.ui.amenities.AmenitiesActivity;
import com.partner.oxostay.activities.ui.changepassword.ChangepasswordActivity;
import com.partner.oxostay.activities.ui.policy.PolicyActivity;
import com.partner.oxostay.activities.ui.profile.ProfileActivity;
import com.partner.oxostay.utils.Constants;

public class NavHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = "NavHomeActivity";
    private AppBarConfiguration mAppBarConfiguration;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private Button btUpcomingBookings, btBookingsHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_bookings, R.id.nav_manageratesroom, R.id.nav_amenities)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);

        drawer = findViewById(R.id.drawer_layout);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setTitle("My Bookings");
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        setUpViews();
    }


    private void setUpViews() {
        btUpcomingBookings = findViewById(R.id.btUpcomingBookings);
        btBookingsHistory = findViewById(R.id.btBookingsHistory);

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_home, menu);
        return true;
    }


//
//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        Intent activityIntent;

        switch (id) {
            case R.id.nav_bookings:
                activityIntent = new Intent(NavHomeActivity.this, NavHomeActivity.class);
                startActivity(activityIntent);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_amenities:
                activityIntent = new Intent(NavHomeActivity.this, AmenitiesActivity.class);
                startActivity(activityIntent);
                break;

            case R.id.nav_manageratesroom:
            case R.id.nav_user_access:
            case R.id.nav_logout:
                makeToast("Coming soon!");
                break;
            case R.id.nav_change_password:
                activityIntent = new Intent(NavHomeActivity.this, ChangepasswordActivity.class);
                startActivity(activityIntent);
                break;
            case R.id.nav_profile:
                activityIntent = new Intent(NavHomeActivity.this, ProfileActivity.class);
                startActivity(activityIntent);
                break;
            case R.id.nav_policy_updates:
                activityIntent = new Intent(NavHomeActivity.this, PolicyActivity.class);
                startActivity(activityIntent);
                break;
        }
        return true;
    }

    private void makeToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}