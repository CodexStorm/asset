package com.ninja.ultron.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ninja.ultron.R;
import com.ninja.ultron.activity.RequestNewAssetActivity;
import com.ninja.ultron.entity.NewAssetEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manoj on 17-06-2017.
 */

public class NewAssetAdapter extends RecyclerView.Adapter<NewAssetAdapter.ViewHolder> {

    Activity activity;
    List<NewAssetEntity> assetList = new ArrayList<>();
    boolean showCheckbox=false;

    public NewAssetAdapter(Activity activity, List<NewAssetEntity> assetList) {
        this.activity = activity;
        this.assetList = assetList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_asset_card,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final NewAssetEntity asset = assetList.get(position);
        holder.tvQuantity.setText(asset.getQuantity()+"");
        holder.tvAssetType.setText(asset.getAssetType());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RequestNewAssetActivity)activity).deleteSelectedAsset(position,asset.getQuantity(),asset.getAssetType());
            }
        });

    }

    @Override
    public int getItemCount() {
        return assetList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvAssetType,tvQuantity;
        View cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView =itemView;
            tvAssetType = (TextView)itemView.findViewById(R.id.tvAssetType);
            tvQuantity = (TextView)itemView.findViewById(R.id.tvQuantity);
        }
    }
}
