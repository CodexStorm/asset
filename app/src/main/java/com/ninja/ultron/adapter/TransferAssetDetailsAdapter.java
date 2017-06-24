package com.ninja.ultron.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ninja.ultron.R;
import com.ninja.ultron.entity.TransferAssetTypeDetailsEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manoj on 22-06-2017.
 */

public class
TransferAssetDetailsAdapter extends RecyclerView.Adapter<TransferAssetDetailsAdapter.ViewHolder> {
    List<TransferAssetTypeDetailsEntity> transferAssetTypeDetailsEntities;
    TransferAssetTypeDetailsEntity assetDetails;
    List<Integer> selectedId = new ArrayList<>();

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
    public void onBindViewHolder(final TransferAssetDetailsAdapter.ViewHolder holder, int position) {
        assetDetails = transferAssetTypeDetailsEntities.get(position);
        holder.nomenclature.setText(assetDetails.getNomenclature());
      //  holder.AssetMake.setText(assetDetails.getAssetMake());
        holder.AssetType.setText(assetDetails.getAssetType());
        holder.check.setVisibility(View.VISIBLE);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.check.isChecked()){
                    holder.check.setChecked(false);
                       // /*selectedAssetList.remove(asset);
                }
                else{
                       // /*selectedAssetList.add(asset);
                    Log.d("zxcvb",assetDetails.getAssetId()+"");
                        holder.check.setChecked(true);
                }
                if (holder.check.isChecked())
                {
                    selectedId.add(assetDetails.getAssetId());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return transferAssetTypeDetailsEntities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nomenclature,AssetType;
        View cardView;
        CheckBox check;
        public ViewHolder(View itemView) {

            super(itemView);
           //  AssetMake = (TextView)itemView.findViewById(R.id.asset_make) ;
            nomenclature=(TextView)itemView.findViewById(R.id.asset_id);
            AssetType =(TextView)itemView.findViewById(R.id.asset_name);
            cardView=itemView;
            check = (CheckBox)itemView.findViewById(R.id.checkBox);
        }
    }

    public List<Integer> getSelectedId(){
        Log.d("lkjh",selectedId.toString());
        return selectedId;
    }
}
