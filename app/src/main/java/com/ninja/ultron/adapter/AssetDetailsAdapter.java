package com.ninja.ultron.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ninja.ultron.R;
import com.ninja.ultron.entity.NewAssetTypeDetailsEntity;

import java.util.List;

/**
 * Created by manoj on 22-06-2017.
 */

public class AssetDetailsAdapter extends RecyclerView.Adapter<AssetDetailsAdapter.ViewHolder> {
    List<NewAssetTypeDetailsEntity> assetTypeDetailsEntityList;
    NewAssetTypeDetailsEntity assetDetail;
    boolean showCheckbox=true;

    public AssetDetailsAdapter(List<NewAssetTypeDetailsEntity> assetTypeDetailsEntityList) {
        this.assetTypeDetailsEntityList = assetTypeDetailsEntityList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_asset_details_card,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        assetDetail = assetTypeDetailsEntityList.get(position);
        holder.tvAssetType.setText(assetDetail.getSkuName());
        holder.tvQuantity.setText(assetDetail.getSkuQuantity()+"");
       // if (showCheckbox == true) {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.checkBox.isChecked()){
                        holder.checkBox.setChecked(false);
                        /*selectedAssetList.remove(asset);
                        selectedAssetId.remove(asset.getId());*/
                    }
                    else{
                        /*selectedAssetList.add(asset);
                        selectedAssetId.add(asset.getId());*/
                        holder.checkBox.setChecked(true);
                    }

                }
            });
      //  }
    }

    @Override
    public int getItemCount() {
        return assetTypeDetailsEntityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuantity,tvAssetType;
        View cardView;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            tvQuantity = (TextView)itemView.findViewById(R.id.tvQuantity);
            tvAssetType = (TextView)itemView.findViewById(R.id.tvAssetType);
            cardView = itemView;
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);

        }
    }
}
