package com.ninja.ultron.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ninja.ultron.R;
import com.ninja.ultron.entity.NewAssetEntity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manoj on 16-06-2017.
 */

public class NewAssetAdapter extends RecyclerView.Adapter<NewAssetAdapter.ViewHolder> {

    private List<NewAssetEntity> newAssetEntityList= new ArrayList<>();


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvAssetType;
        public TextView tvQuantity;

        public ViewHolder(View v){
            super(v);
            tvAssetType = (TextView)v.findViewById(R.id.tvAssetType);
            tvQuantity = (TextView)v.findViewById(R.id.tvQuantity);
        }
    }

    public NewAssetAdapter(List<NewAssetEntity> newAssetEntityList) {
        this.newAssetEntityList = newAssetEntityList;
    }

    public NewAssetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_asset_card,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void onBindViewHolder(NewAssetAdapter.ViewHolder holder, int position){
        NewAssetEntity newAssetEntity = newAssetEntityList.get(position);
        holder.tvAssetType.setText(newAssetEntity.getAssetType());
        holder.tvAssetType.setText(String.valueOf(newAssetEntity.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return newAssetEntityList.size();
    }
}
