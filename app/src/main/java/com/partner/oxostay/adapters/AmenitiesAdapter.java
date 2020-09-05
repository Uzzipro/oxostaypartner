package com.partner.oxostay.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.partner.oxostay.R;
import com.partner.oxostay.dtos.AmenetiesDto;
import com.partner.oxostay.dtos.RegisterDto;
import com.partner.oxostay.utils.Constants;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class AmenitiesAdapter extends RecyclerView.Adapter<AmenitiesAdapter.MyViewHolder> {

    private String TAG = "AmenitiesAdapter";
    private Context context;
    private List<AmenetiesDto> cardinfoList;
    private LayoutInflater inflater;
    private int amenitiesImage[];
    private DatabaseReference dbRef;
//    private ArrayList<String> amenitiesSelected;


    public AmenitiesAdapter(Context mContext, List<AmenetiesDto> cardinfoList) {
        this.context = mContext;
        this.cardinfoList = cardinfoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ameneties_ui, parent, false);
        return new MyViewHolder(itemView);

    }


    public void clear() {
        cardinfoList.clear();
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final AmenetiesDto cardData = cardinfoList.get(position);
        String hotel_id = context.getSharedPreferences(Constants.ACCESS_PREFS, MODE_PRIVATE).getString(
                Constants.HOTEL_ID, "notfound");
        dbRef = FirebaseDatabase.getInstance().getReference().child(Constants.OXO_STAY_PARTNER).child(Constants.HOTELS_APPROVED_KEY).child(hotel_id);
        holder.amenitiesLabel.setText(cardData.getAmenetiesLabel());
        holder.amenitiesImage.setImageResource(cardData.getAmenitiesImage());

        dbRef.child("amenities").orderByChild("amenetiesLabel").equalTo(cardData.getAmenetiesLabel()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        AmenetiesDto amenetiesDto = dataSnapshot1.getValue(AmenetiesDto.class);
                        Log.e(TAG, "onDataChange: " + amenetiesDto.getAmenetiesLabel());
                        if (cardData.getAmenetiesLabel().equals(amenetiesDto.getAmenetiesLabel())) {
                            holder.rbYes.setChecked(true);

                        } else {
                            holder.rbNo.setChecked(true);

                        }

                    }
                } else {
                }
                setupViews(holder, cardData);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void setupViews(MyViewHolder holder, AmenetiesDto cardData) {
        holder.rbYes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                holder.rbNo.setChecked(false);
                AmenetiesDto a = new AmenetiesDto();
                a.setAmenetiesLabel(cardData.getAmenetiesLabel());
                RegisterDto s = new RegisterDto();
                s.setAmenetiesDto(a);
                dbRef.child("amenities").push().setValue(a);
//                    amenitiesSelected.add(setAmen

            }

        });

        holder.rbNo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                holder.rbYes.setChecked(false);
//                    AmenetiesDto a = new AmenetiesDto();
//                    a.setAmenetiesLabel(cardData.getAmenetiesLabel());
//                    dbRef.push().setValue(a);

                dbRef.child("amenities").orderByChild("amenetiesLabel").equalTo(cardData.getAmenetiesLabel()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                AmenetiesDto a = dataSnapshot1.getValue(AmenetiesDto.class);
//                                    Log.e(TAG, "onDataChange: "+a.getAmenetiesLabel());
                                if (cardData.getAmenetiesLabel().equals(a.getAmenetiesLabel())) {
                                    Log.e(TAG, "onDataChange: " + dataSnapshot1.getKey());
                                    dbRef.child("amenities").child(dataSnapshot1.getKey()).removeValue();
                                }
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        });
    }

    @Override
    public int getItemCount() {
        return cardinfoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView amenitiesLabel;
        private ImageView amenitiesImage;
        private RadioButton rbYes, rbNo;

        public MyViewHolder(View view) {
            super(view);

            amenitiesLabel = view.findViewById(R.id.tvLabel);
            amenitiesImage = view.findViewById(R.id.ivAmenities);
            rbYes = view.findViewById(R.id.rbParkingYes);
            rbNo = view.findViewById(R.id.rbParkingno);
//            amenitiesSelected = new ArrayList<>();

        }
    }
}



