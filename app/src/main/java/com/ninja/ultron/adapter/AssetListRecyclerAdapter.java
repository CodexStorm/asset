package com.ninja.ultron.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ninja.ultron.R;
import com.ninja.ultron.entity.CodeDecodeEntity;
import com.ninja.ultron.functions.CommonFunctions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prabhu Sivanandam on 17-May-17.
 */

public class AssetListRecyclerAdapter extends RecyclerView.Adapter<AssetListRecyclerAdapter.ViewHolder>{

    List<CodeDecodeEntity> assetsList=new ArrayList<>();
    CodeDecodeEntity asset;
    Context context;
    private CallBack mCallBack;
    public interface CallBack{
        void CallAssetDetailsFragment();

    }


    public AssetListRecyclerAdapter(List<CodeDecodeEntity> assetList, Context context,CallBack callback) {
        this.assetsList=assetList;
        this.context = context;
        this.mCallBack = callback;
    }


        public interface OnItemClickListener {
        void OnItemClick(CodeDecodeEntity codeDecodeEntity);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.asset_cards,parent,false);
        ViewHolder holder=new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        asset=assetsList.get(position);
        holder.AssetName.setText(asset.getName());
        holder.AssetId.setText(asset.getId()+"");
        holder.ivMoreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mCallBack.CallAssetDetailsFragment();
                //CommonFunctions.toastString("View ",context);
            }
        });

    }
    @Override
    public int getItemCount() {
        return assetsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView AssetId,AssetName;
        ImageView ivMoreDetails;
        public ViewHolder(View itemView) {
            super(itemView);
            AssetId=(TextView)itemView.findViewById(R.id.asset_id);
            AssetName=(TextView)itemView.findViewById(R.id.asset_name);
            ivMoreDetails = (ImageView)itemView.findViewById(R.id.ivMoreDetails);

        }
    }
}
