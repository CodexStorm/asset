package com.ninja.ultron.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ninja.ultron.R;
import com.ninja.ultron.entity.TransferAssetTypeDetailsEntity;

import java.util.List;

/**
 * Created by manoj on 22-06-2017.
 */

public class
TransferAssetDetailsAdapter extends RecyclerView.Adapter<TransferAssetDetailsAdapter.ViewHolder> {
    List<TransferAssetTypeDetailsEntity> transferAssetTypeDetailsEntities;
    TransferAssetTypeDetailsEntity assetDetails;

    public TransferAssetDetailsAdapter(List<TransferAssetTypeDetailsEntity> transferAssetTypeDetailsEntities) {
        this.transferAssetTypeDetailsEntities = transferAssetTypeDetailsEntities;
    }

    @Override
    public TransferAssetDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.transfer_asset_card,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(TransferAssetDetailsAdapter.ViewHolder holder, int position) {
        assetDetails = transferAssetTypeDetailsEntities.get(position);
        holder.nomenclature.setText(assetDetails.getNomenclature());
      //  holder.AssetMake.setText(assetDetails.getAssetMake());
        holder.AssetType.setText(assetDetails.getAssetType());
    }

    @Override
    public int getItemCount() {
        return transferAssetTypeDetailsEntities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nomenclature,AssetType;
        View cardView;

        public ViewHolder(View itemView) {

            super(itemView);
           //  AssetMake = (TextView)itemView.findViewById(R.id.asset_make) ;
            nomenclature=(TextView)itemView.findViewById(R.id.asset_id);
            AssetType =(TextView)itemView.findViewById(R.id.asset_name);
            cardView=itemView;
        }
    }
}
