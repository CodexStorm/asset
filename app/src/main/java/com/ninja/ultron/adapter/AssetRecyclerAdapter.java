package com.ninja.ultron.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ninja.ultron.R;
import com.ninja.ultron.entity.AssetMiniEntity;

import java.util.ArrayList;

/**
 * Created by Prabhu Sivanandam on 17-May-17.
 */

public class AssetRecyclerAdapter extends RecyclerView.Adapter<AssetRecyclerAdapter.ViewHolder>{

    ArrayList<AssetMiniEntity> assetsList=new ArrayList<>();
    AssetMiniEntity asset;

    public AssetRecyclerAdapter(ArrayList<AssetMiniEntity> assetList) {
        this.assetsList=assetList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.asset_cards,parent,false);
        ViewHolder holder=new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        asset=assetsList.get(position);
        holder.AssetName.setText(asset.getName());
        holder.AssetId.setText(asset.getId()+"");
    }

    @Override
    public int getItemCount() {
        return assetsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView AssetId,AssetName;
        public ViewHolder(View itemView) {
            super(itemView);
            AssetId=(TextView)itemView.findViewById(R.id.asset_id);
            AssetName=(TextView)itemView.findViewById(R.id.asset_name);
        }
    }
}
