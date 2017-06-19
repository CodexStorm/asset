package com.ninja.ultron.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ninja.ultron.R;
import com.ninja.ultron.constant.Constants;
import com.ninja.ultron.entity.CodeDecodeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manoj on 19-06-2017.
 */

public class TransferListRecyclerAdapter extends RecyclerView.Adapter<TransferListRecyclerAdapter.ViewHolder> {
    List<CodeDecodeEntity> assetsList=new ArrayList<>();
    Context context;
    boolean showCheckbox=false;

    public TransferListRecyclerAdapter(List<CodeDecodeEntity> assetList, Context context) {
        this.assetsList=assetList;
        this.context = context;
    }


    @Override
    public TransferListRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.transfer_asset_card,parent,false);
        TransferListRecyclerAdapter.ViewHolder holder=new TransferListRecyclerAdapter.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(TransferListRecyclerAdapter.ViewHolder holder, final int position) {

        final CodeDecodeEntity asset =assetsList.get(position);
        holder.nomenclature.setText(asset.getNomenclature());
        holder.AssetId.setText(asset.getId()+"");
        holder.AssetMake.setText(asset.getAssetMake());
        if (showCheckbox == true) {
            holder.checkBox.setVisibility(View.VISIBLE);
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


