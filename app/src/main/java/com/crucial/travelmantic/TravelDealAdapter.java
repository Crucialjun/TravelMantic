package com.crucial.travelmantic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class TravelDealAdapter extends RecyclerView.Adapter<TravelDealAdapter.DealViewHolder> {

    private List<TravelDeal> mTravelDeals;

    public TravelDealAdapter() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Travel Deals");
        mTravelDeals = new ArrayList<TravelDeal>();
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                TravelDeal travelDeal = dataSnapshot.getValue(TravelDeal.class);
                assert travelDeal != null;
                travelDeal.setId(dataSnapshot.getKey());
                mTravelDeals.add(travelDeal);
                notifyItemInserted(mTravelDeals.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @NonNull
    @Override
    public TravelDealAdapter.DealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View dealView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.deal_layout,parent,false);

        return new DealViewHolder(dealView);
    }

    @Override
    public void onBindViewHolder(@NonNull TravelDealAdapter.DealViewHolder holder, int position) {
        TravelDeal travelDeal = mTravelDeals.get(position);
        holder.bind(travelDeal);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class DealViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mImageView;
        public TextView mDealTitle;
        public TextView mDealDescription;
        public TextView mDealPrice;
        public DealViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.list_deal_image);
            mDealTitle = itemView.findViewById(R.id.list_deal_title);
            mDealDescription = itemView.findViewById(R.id.list_deal_description);
            mDealPrice = itemView.findViewById(R.id.list_deal_price);

        }

        @Override
        public void onClick(View v) {

        }

        public void bind(TravelDeal travelDeal) {
            mDealTitle.setText(travelDeal.getTitle());
            mDealDescription.setText(travelDeal.getDescription());
            mDealPrice.setText(travelDeal.getPrice());

        }
    }
}
