package com.ninja.ultron.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ninja.ultron.R;
import com.ninja.ultron.entity.CodeDecodeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manoj on 19-06-2017.
 */

public class TransferAssetAdapter extends RecyclerView.Adapter<TransferAssetAdapter.ViewHolder> {
    Activity activity;
    List<CodeDecodeEntity> assetList = new ArrayList<>();
    boolean isChecked;

    public TransferAssetAdapter(Activity activity, List<CodeDecodeEntity> assetList) {
        this.activity = activity;
        this.assetList = assetList;
        isChecked = true;
    }

    public TransferAssetAdapter(Activity activity, List<CodeDecodeEntity> assetList, boolean isChecked) {
        this.activity = activity;
        this.assetList = assetList;
        this.isChecked=isChecked;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.transfer_asset_card,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CodeDecodeEntity asset = assetList.get(position);
        holder.nomenclature.setText(asset.getNomenclature());
        holder.AssetId.setText(asset.getId()+"");
        holder.AssetMake.setText(asset.getAssetMake());
        holder.Status.setVisibility(View.GONE);
        holder.check.setVisibility(View.GONE);


    }

    @Override
    public int getItemCount() {
        return assetList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView AssetId, nomenclature,AssetMake,Status;
        CheckBox check;
        View cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView=itemView;
            check = (CheckBox)itemView.findViewById(R.id.checkBox);
            Status = (TextView)itemView.findViewById(R.id.asset_status);
            AssetMake = (TextView)itemView.findViewById(R.id.asset_make) ;
            AssetId=(TextView)itemView.findViewById(R.id.tvAssetNomenclature);
            nomenclature =(TextView)itemView.findViewById(R.id.tvAssetType);
        }
    }
}
