package com.ninja.ultron.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public AssetDetailsAdapter(List<NewAssetTypeDetailsEntity> assetTypeDetailsEntityList) {
        this.assetTypeDetailsEntityList = assetTypeDetailsEntityList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_asset_card,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        assetDetail = assetTypeDetailsEntityList.get(position);
        holder.tvAssetType.setText(assetDetail.getSkuName());
        holder.tvQuantity.setText(assetDetail.getSkuQuantity()+"");
    }

    @Override
    public int getItemCount() {
        return assetTypeDetailsEntityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuantity,tvAssetType;


        public ViewHolder(View itemView) {
            super(itemView);
            tvQuantity = (TextView)itemView.findViewById(R.id.tvQuantity);
            tvAssetType = (TextView)itemView.findViewById(R.id.tvAssetType);
        }
    }
}
