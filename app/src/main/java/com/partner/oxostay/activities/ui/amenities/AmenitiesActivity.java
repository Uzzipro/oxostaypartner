package com.partner.oxostay.activities.ui.amenities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.partner.oxostay.R;
import com.partner.oxostay.adapters.AmenitiesAdapter;
import com.partner.oxostay.dtos.AmenetiesDto;
import com.partner.oxostay.dtos.AmenitiesDtoList;
import com.partner.oxostay.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class AmenitiesActivity extends AppCompatActivity {
    private String TAG = "AmenitiesActivity";
    private Toolbar toolbar;
    private Button btUpcomingBookings, btBookingsHistory;
    private RecyclerView rvAmeneties;
    private AmenitiesAdapter amenitiesAdapter;
    private ImageView ivBack, ivSave;
    private ProgressDialog progressDialog;
    private List<AmenetiesDto> amenitiesLabellist;
    private List<AmenetiesDto> amenetiesDtos;

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
        amenetiesDtos = new ArrayList<>();
        amenitiesAdapter = new AmenitiesAdapter(this, amenetiesDtos);
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

        DatabaseReference dbf;
        dbf = FirebaseDatabase.getInstance().getReference(Constants.OXO_STAY_PARTNER);

        dbf.child("amenities").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot != null)
                {
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                    {
                        AmenetiesDto amenitiesDtoList = dataSnapshot1.getValue(AmenetiesDto.class);
                        amenetiesDtos.add(amenitiesDtoList);
                    }
                    setData();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setData() {
//        String amenitiesLabelsarray[] = {"Parking","Pets","Wifi", "Banquet Hall", "Elevator", "Laundry", "Dining", "Refrigerator", "Breakfast"};
//        int amenitiesImage[] = {R.drawable.ic_parking, R.drawable.ic_pets, R.drawable.ic_wifi, R.drawable.ic_banquet, R.drawable.ic_elevator, R.drawable.ic_laundry, R.drawable.ic_dining, R.drawable.ic_fridge, R.drawable.ic_breakfast};
//        for(int p = 0; p < amenitiesLabelsarray.length; p++)
//        {
//            AmenetiesDto amenetiesDto = new AmenetiesDto();
//            amenetiesDto.setAmenetiesLabel(amenitiesLabelsarray[p]);
//            amenetiesDto.setAmenitiesImage(amenitiesImage[p]);
            amenitiesAdapter.notifyDataSetChanged();
            progressDialog.dismiss();

//        }
    }
}