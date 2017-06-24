package com.ninja.ultron.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.ninja.ultron.Fragments.FacilityAssetTransferFragment;
import com.ninja.ultron.Fragments.ProfileAssetTransferFragment;
import com.ninja.ultron.R;
import com.ninja.ultron.activity.InitiateTransferSummaryActivity;
import com.ninja.ultron.constant.Constants;
import com.ninja.ultron.entity.CodeDecodeEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by manoj on 19-06-2017.
 */

public class TransferListRecyclerAdapter extends RecyclerView.Adapter<TransferListRecyclerAdapter.ViewHolder> {
    List<CodeDecodeEntity> assetsList=new ArrayList<>();
    Context context;
    boolean showCheckbox=false;
    int type;
    List<CodeDecodeEntity> selectedAssetList = new ArrayList<>();
    List<Integer> selectedAssetId = new ArrayList<>();


    public TransferListRecyclerAdapter(List<CodeDecodeEntity> assetList, Context context, int type) {
        this.assetsList=assetList;
        this.context = context;
        this.type = type;
    }



    @Override
    public TransferListRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.transfer_asset_card,parent,false);
        TransferListRecyclerAdapter.ViewHolder holder=new TransferListRecyclerAdapter.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final TransferListRecyclerAdapter.ViewHolder holder, final int position) {

        final CodeDecodeEntity asset =assetsList.get(position);
        holder.nomenclature.setText(asset.getNomenclature());
        holder.AssetId.setText(asset.getId()+"");
        holder.AssetMake.setText(asset.getAssetMake());
        holder.status.setText(asset.getStatus());
            if(!asset.getStatus().equals("IN TRANSIT")) {
                holder.checkBox.setVisibility(View.VISIBLE);
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.checkBox.isChecked()) {
                            holder.checkBox.setChecked(false);
                        } else {
                            holder.checkBox.setChecked(true);
                        }
                        if (holder.checkBox.isChecked()) {
                            selectedAssetList.add(asset);
                            selectedAssetId.add(asset.getId());
                        }
                    }

                });
            }
            else
            {
                holder.checkBox.setEnabled(false);
                holder.status.setTextColor(Color.RED);
            }

    }

    @Override
    public int getItemCount() {
        return assetsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView AssetId, nomenclature,AssetMake,status;
        View cardView;
        CheckBox checkBox;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView=itemView;
            AssetMake = (TextView)itemView.findViewById(R.id.asset_make) ;
            AssetId=(TextView)itemView.findViewById(R.id.asset_id);
            nomenclature =(TextView)itemView.findViewById(R.id.asset_name);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            status = (TextView)itemView.findViewById(R.id.asset_status);
        }

    }

    public List<CodeDecodeEntity> getSelectedAssetList()
    {
        return selectedAssetList;
    }

    public  List<Integer> getSelectedAssetId()
    {
        return selectedAssetId;
    }

}


