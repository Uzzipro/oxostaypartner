package com.partner.oxostay.adapters;

import android.content.Context;
import android.content.pm.PackageManager;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.TELECOM_SERVICE;

public class AmenitiesAdapter extends RecyclerView.Adapter<AmenitiesAdapter.MyViewHolder> {

    private String TAG = "AmenitiesAdapter";
    private Context context;
    private List<AmenetiesDto> cardinfoList;
    private LayoutInflater inflater;
    private int amenitiesImage[];
    private DatabaseReference dbRef;
    private ArrayList<String> amenitiesSelected;


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
//        dbRef = FirebaseDatabase.getInstance().getReference().child(Constants.OXO_STAY_PARTNER);
        holder.amenitiesLabel.setText(cardData.getAmenetiesLabel());

        if(cardData.getAmenitiesImage() == null || cardData.getAmenitiesImage().equals("0"))
        {

        holder.amenitiesImage.setImageResource(R.drawable.no_icon);
        }
        else
        {
            Picasso.get().load(cardData.getAmenitiesImage()).into(holder.amenitiesImage);
        }

        dbRef.child("amenities").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    amenitiesSelected = (ArrayList<String>) dataSnapshot.getValue();
                    if(amenitiesSelected != null)
                    {

//                    if(amenitiesSelected.get(
                    for(int x = 0; x < amenitiesSelected.size(); x++)
                    {
                        if(amenitiesSelected.get(x).equals(String.valueOf(position)))
                        {
                            holder.rbYes.setChecked(true);
                        }
                        else
                        {
                            holder.rbNo.setChecked(false);
                        }

                    }
                    }
                    else
                    {
                        amenitiesSelected = new ArrayList<>();
                    }


//                    try
//                    {
//
//                    if(amenitiesSelected.get(position).equals(String.valueOf(position)))
//                    {
//                        holder.rbYes.setChecked(true);
//                        amenitiesSelected.add(position);
//                    }
//                    else
//                    {
//                        holder.rbNo.setChecked(false);
//                    }
//                    }
//
//                    catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                        RegisterDto registerDto = dataSnapshot1.getValue(RegisterDto.class);
//                        Log.e(TAG, "onDataChange: " + amenetiesDto.getAmenetiesLabel());
//                        if (cardData.getAmenetiesLabel().equals(registerDto.getAmenitiesSelected().getAmenetiesLabel())) {
//                            holder.rbYes.setChecked(true);
//
//                        } else {
//                            holder.rbNo.setChecked(true);
//
//                        }
//
//                        if(registerDto.getAmenities().get(position).equals(String.valueOf(position)))
//                        {
//                            holder.rbYes.setChecked(true);
//                        }
//                        else
//                        {
//                            holder.rbNo.setChecked(false);
//                        }
//
//                    }
//                } else {
                    setupViews(holder, cardData, position);

                }
//                setupViews(holder, cardData);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setupViews(MyViewHolder holder, AmenetiesDto cardData, int pos) {
        holder.rbYes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                holder.rbNo.setChecked(false);
                Log.e(TAG, "setupViews: "+cardData.getId());
//                AmenetiesDto a = new AmenetiesDto();
//                a.setId(String.valueOf(pos));
                amenitiesSelected.add(cardData.getId());
//                RegisterDto s = new RegisterDto();
//                s.setAmenetiesDto(a);
                dbRef.child("amenities").setValue(amenitiesSelected);
//                    amenitiesSelected.add(setAmen

            }

        });

        holder.rbNo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                holder.rbYes.setChecked(false);
//                    AmenetiesDto a = new AmenetiesDto();
//                    a.setAmenetiesLabel(cardData.getAmenetiesLabel());
//                    dbRef.child("amenities").push().setValue(a);

//                dbRef.child("amenities").orderByChild("amenetiesLabel").equalTo(cardData.getAmenetiesLabel()).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if (dataSnapshot != null) {
//                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                                AmenetiesDto a = dataSnapshot1.getValue(AmenetiesDto.class);
////                                    Log.e(TAG, "onDataChange: "+a.getAmenetiesLabel());
//                                if (cardData.getAmenetiesLabel().equals(a.getAmenetiesLabel())) {
//                                    Log.e(TAG, "onDataChange: " + dataSnapshot1.getKey());
//                                    dbRef.child("amenities").child(dataSnapshot1.getKey()).removeValue();
//                                }
//                            }
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
                amenitiesSelected.remove(cardData.getId());
                dbRef.child("amenities").setValue(amenitiesSelected);
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
            amenitiesSelected = new ArrayList<>();

        }
    }
}



