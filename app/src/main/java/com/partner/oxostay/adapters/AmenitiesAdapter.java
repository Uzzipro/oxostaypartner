package com.partner.oxostay.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.partner.oxostay.R;
import com.partner.oxostay.dtos.AmenetiesDto;

import java.util.List;

public class AmenitiesAdapter extends RecyclerView.Adapter<AmenitiesAdapter.MyViewHolder> {

    private Context context;
    private List<AmenetiesDto> cardinfoList;
    private LayoutInflater inflater;
    private int amenitiesImage[];


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

        holder.amenitiesLabel.setText(cardData.getAmenetiesLabel());
        holder.amenitiesImage.setImageResource(cardData.getAmenitiesImage());

        holder.rbYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    holder.rbNo.setChecked(false);
                }

            }
        });

        holder.rbNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    holder.rbYes.setChecked(false);
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



