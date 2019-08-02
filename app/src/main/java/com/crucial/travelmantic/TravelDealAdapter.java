package com.crucial.travelmantic;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TravelDealAdapter extends RecyclerView.Adapter<TravelDealAdapter.DealViewHolder> {

    private List<TravelDeal> mTravelDeals;


    @NonNull
    @Override
    public TravelDealAdapter.DealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TravelDealAdapter.DealViewHolder holder, int position) {

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
    }
}
