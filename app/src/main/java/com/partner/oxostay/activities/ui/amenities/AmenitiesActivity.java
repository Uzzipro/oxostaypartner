package com.partner.oxostay.activities.ui.amenities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.partner.oxostay.R;
import com.partner.oxostay.adapters.AmenitiesAdapter;
import com.partner.oxostay.dtos.AmenetiesDto;

import java.util.ArrayList;
import java.util.List;

public class AmenitiesActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button btUpcomingBookings, btBookingsHistory;
    private RecyclerView rvAmeneties;
    private List<AmenetiesDto> amenitiesLabellist;
    private AmenitiesAdapter amenitiesAdapter;
    private ImageView ivBack, ivSave;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amenities);
        setUpViews();

    }

    private void setUpViews() {

        rvAmeneties = findViewById(R.id.rvAmeneties);
        ivSave = findViewById(R.id.ivSave);
        ivBack = findViewById(R.id.ivBack);
        amenitiesLabellist = new ArrayList<>();
        amenitiesAdapter = new AmenitiesAdapter(this, amenitiesLabellist);
        rvAmeneties.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvAmeneties.setAdapter(amenitiesAdapter);

        ivBack.setOnClickListener(v -> onBackPressed());
        ivSave.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Amenities Saved", Toast.LENGTH_LONG).show();
            finish();

        });
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Sending information");
        progressDialog.setCancelable(false);
        progressDialog.show();
        setData();

    }

    private void setData() {
        String amenitiesLabelsarray[] = {"Parking","Pets","Wifi", "Banquet Hall", "Elevator", "Laundry", "Dining", "Refrigerator", "Breakfast"};
        int amenitiesImage[] = {R.drawable.ic_parking, R.drawable.ic_pets, R.drawable.ic_wifi, R.drawable.ic_banquet, R.drawable.ic_elevator, R.drawable.ic_laundry, R.drawable.ic_dining, R.drawable.ic_fridge, R.drawable.ic_breakfast};
        for(int p = 0; p < amenitiesLabelsarray.length; p++)
        {
            AmenetiesDto amenetiesDto = new AmenetiesDto();
            amenetiesDto.setAmenetiesLabel(amenitiesLabelsarray[p]);
            amenetiesDto.setAmenitiesImage(amenitiesImage[p]);
            amenitiesLabellist.add(amenetiesDto);
            amenitiesAdapter.notifyDataSetChanged();
            progressDialog.dismiss();

        }
    }
}