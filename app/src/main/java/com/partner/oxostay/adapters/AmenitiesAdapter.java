package com.partner.oxostay.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
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
import com.partner.oxostay.utils.Constants;

import java.util.List;

public class AmenitiesAdapter extends RecyclerView.Adapter<AmenitiesAdapter.MyViewHolder> {

    private String TAG = "AmenitiesAdapter";
    private Context context;
    private List<AmenetiesDto> cardinfoList;
    private LayoutInflater inflater;
    private int amenitiesImage[];
    private DatabaseReference dbRef;


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

        dbRef = FirebaseDatabase.getInstance().getReference().child(Constants.OXO_STAY_PARTNER).child("amenities");
        holder.amenitiesLabel.setText(cardData.getAmenetiesLabel());
        holder.amenitiesImage.setImageResource(cardData.getAmenitiesImage());

//        dbRef.orderByChild("amenetiesLabel").equalTo(cardData.getAmenetiesLabel()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot != null)
//                {
//
//                }
//                else
//                {
//                    holder.rbNo.setChecked(true);
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        holder.rbYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.rbNo.setChecked(false);
                    AmenetiesDto a = new AmenetiesDto();
                    a.setAmenetiesLabel(cardData.getAmenetiesLabel());
                    dbRef.push().setValue(a);

                }

            }
        });

        holder.rbNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.rbYes.setChecked(false);
//                    AmenetiesDto a = new AmenetiesDto();
//                    a.setAmenetiesLabel(cardData.getAmenetiesLabel());
//                    dbRef.push().setValue(a);

                    dbRef.orderByChild(cardData.getAmenetiesLabel()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot != null) {
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    AmenetiesDto a = dataSnapshot1.getValue(AmenetiesDto.class);
//                                    Log.e(TAG, "onDataChange: "+a.getAmenetiesLabel());
                                    if (cardData.getAmenetiesLabel().equals(a.getAmenetiesLabel())) {
                                        Log.e(TAG, "onDataChange: " + dataSnapshot1.getKey());
                                        dbRef.child(dataSnapshot1.getKey()).removeValue();
                                    }
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

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

        }
    }
}



