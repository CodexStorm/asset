package com.ninja.ultron.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ninja.ultron.R;
import com.ninja.ultron.entity.NewAssetEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manoj on 17-06-2017.
 */

public class CheckAdapter extends RecyclerView.Adapter<CheckAdapter.ViewHolder> {

    List<NewAssetEntity> assetList = new ArrayList<>();

    public CheckAdapter(List<NewAssetEntity> assetList) {
        this.assetList = assetList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_asset_card,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final NewAssetEntity asset = assetList.get(position);
        holder.tvQuantity.setText(asset.getQuantity()+"");
        holder.tvAssetType.setText(asset.getAssetType());

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
