package com.partner.oxostay.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.partner.oxostay.R;
import com.partner.oxostay.dtos.AmenetiesDto;
import com.partner.oxostay.dtos.BookingsDto;
import com.partner.oxostay.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.MyViewHolder> {

    private String TAG = "BookingsAdapter";
    private Context context;
    private List<BookingsDto> cardinfoList;
    private LayoutInflater inflater;
    private DatabaseReference dbRef;


    public BookingsAdapter(Context mContext, List<BookingsDto> cardinfoList) {
        this.context = mContext;
        this.cardinfoList = cardinfoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bookings_ui, parent, false);
        return new MyViewHolder(itemView);

    }

    public void clear() {
        cardinfoList.clear();
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final BookingsDto cardData = cardinfoList.get(position);
        String hotel_id = context.getSharedPreferences(Constants.ACCESS_PREFS, MODE_PRIVATE).getString(
                Constants.HOTEL_ID, "notfound");
        dbRef = FirebaseDatabase.getInstance().getReference();

        holder.tvName.setText(cardData.getUsername());
        holder.tvCheckIn.setText(cardData.getCheck_in_date() +" at " + cardData.getCheck_in_time());
        holder.tvCheckOut.setText(cardData.getCheck_out_time());
        holder.tvTotalRooms.setText(cardData.getRooms_booked() + " Room(s)");
        holder.tvTotalAmount.setText(cardData.getTotal_amount());
        holder.tvBookingId.setText(cardData.getTransaction_id());
        holder.tvGuestContactNo.setText("Guest contact no. "+cardData.getUser_phnno());
        holder.btYes.setOnClickListener(v -> {
            dbRef.child("bookingsPartner").child(hotel_id).child(cardData.getBookingsKey()).child("booking_Status").setValue("2");
        });
        holder.btNo.setOnClickListener(v -> {
            dbRef.child("bookingsPartner").child(hotel_id).child(cardData.getBookingsKey()).child("booking_Status").setValue("0");
        });

    }
    @Override
    public int getItemCount() {
        return cardinfoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName, tvBookingId, tvGuestContactNo, tvCheckIn, tvCheckOut, tvTotalRooms, tvTotalAmount;
        private Button btYes, btNo;

        public MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvBookingId = view.findViewById(R.id.tvBookingId);
            tvGuestContactNo = view.findViewById(R.id.tvGuestContactNo);
            tvCheckIn = view.findViewById(R.id.tvCheckIn);
            tvCheckOut = view.findViewById(R.id.tvCheckOut);
            tvTotalAmount = view.findViewById(R.id.tvTotalAmount);
            tvTotalRooms = view.findViewById(R.id.tvTotalRooms);
            btYes = view.findViewById(R.id.btYes);
            btNo = view.findViewById(R.id.btNo);

        }
    }
}



