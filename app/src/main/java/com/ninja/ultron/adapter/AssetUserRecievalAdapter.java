package com.ninja.ultron.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ninja.ultron.R;
import com.ninja.ultron.activity.InitiateTransferSummaryActivity;
import com.ninja.ultron.entity.AssetUserRecievalEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manoj on 24-06-2017.
 */

public class AssetUserRecievalAdapter extends RecyclerView.Adapter<AssetUserRecievalAdapter.ViewHolder> {
    List<AssetUserRecievalEntity> assetUserRecievalEntityList;
    AssetUserRecievalEntity asset;
    List<Integer> selectedAssetId = new ArrayList<>();

    public AssetUserRecievalAdapter(List<AssetUserRecievalEntity> assetUserRecievalEntityList) {
        this.assetUserRecievalEntityList = assetUserRecievalEntityList;
    }


    @Override
    public AssetUserRecievalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.asset_cards, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final AssetUserRecievalAdapter.ViewHolder holder, int position) {
        asset = assetUserRecievalEntityList.get(position);
        holder.AssetId.setText(asset.getAssetId() + "");
        holder.AssetMake.setText(asset.getAssetMake());
        holder.nomenclature.setText(asset.getNomenclature());
        holder.check.setVisibility(View.VISIBLE);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.check.isChecked()) {
                    holder.check.setChecked(false);
                } else {
                    holder.check.setChecked(true);
                }
                if (holder.check.isChecked()) {
                    selectedAssetId.add(asset.getAssetId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return assetUserRecievalEntityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView AssetId, nomenclature, AssetMake;
        View cardView;
        CheckBox check;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView;
            AssetMake = (TextView) itemView.findViewById(R.id.asset_make);
            AssetId = (TextView) itemView.findViewById(R.id.asset_id);
            nomenclature = (TextView) itemView.findViewById(R.id.asset_name);
            check = (CheckBox) itemView.findViewById(R.id.check);

        }
    }
    public  List<Integer> getSelectedAssetId()
    {
        return selectedAssetId;
    }
}
