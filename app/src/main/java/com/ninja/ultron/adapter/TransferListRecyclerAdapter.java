package com.ninja.ultron.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

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
        if (showCheckbox == true) {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.checkBox.isChecked())
                        holder.checkBox.setChecked(false);
                    else
                        holder.checkBox.setChecked(true);
                }
            });
        }

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showCheckbox=true;
                notifyDataSetChanged();
                return false;
            }
        });
    }
    @Override
    public int getItemCount() {
        return assetsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView AssetId, nomenclature,AssetMake;
        View cardView;
        CheckBox checkBox;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView=itemView;
            AssetMake = (TextView)itemView.findViewById(R.id.asset_make) ;
            AssetId=(TextView)itemView.findViewById(R.id.asset_id);
            nomenclature =(TextView)itemView.findViewById(R.id.asset_name);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
        }

    }


}


