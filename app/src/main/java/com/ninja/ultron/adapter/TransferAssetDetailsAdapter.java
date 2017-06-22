package com.ninja.ultron.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ninja.ultron.entity.TransferAssetTypeDetailsEntity;

import java.util.List;

/**
 * Created by manoj on 22-06-2017.
 */

public class TransferAssetDetailsAdapter extends RecyclerView.Adapter<TransferAssetDetailsAdapter.ViewHolder> {
    List<TransferAssetTypeDetailsEntity> transferAssetTypeDetailsEntities;
    TransferAssetDetailsAdapter assetDetails;


    @Override
    public TransferAssetDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(TransferAssetDetailsAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView asset;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
