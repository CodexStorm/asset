package com.ninja.ultron.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ninja.ultron.R;
import com.ninja.ultron.constant.Constants;
import com.ninja.ultron.entity.CodeDecodeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prabhu Sivanandam on 17-May-17.
 */

public class AssetListRecyclerAdapter extends RecyclerView.Adapter<AssetListRecyclerAdapter.ViewHolder>{

    List<CodeDecodeEntity> assetsList=new ArrayList<>();
    Context context;
    private CallBack mCallBack;


    public interface CallBack{
        void CallAssetDetailsFragment(int id,String name,String toName);

    }


    public AssetListRecyclerAdapter(List<CodeDecodeEntity> assetList, Context context,CallBack callback) {
        this.assetsList=assetList;
        this.context = context;
        this.mCallBack = callback;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.asset_cards,parent,false);
        ViewHolder holder=new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final CodeDecodeEntity asset =assetsList.get(position);
        holder.nomenclature.setText(asset.getNomenclature());
        holder.AssetId.setText(asset.getId()+"");
        holder.AssetMake.setText(asset.getAssetMake());
        Log.d("ASSET",""+asset.getId());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Check AssetID",asset.getId()+"");
                AlterMyAssetDetailURL(""+asset.getId());
                Log.d("pos ",position + "");
               mCallBack.CallAssetDetailsFragment(assetsList.get(position).getId(),assetsList.get(position).getNomenclature(),"Admin");
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
        public ViewHolder(View itemView) {
            super(itemView);
            cardView=itemView;
            AssetMake = (TextView)itemView.findViewById(R.id.asset_make) ;
            AssetId=(TextView)itemView.findViewById(R.id.asset_id);
            nomenclature =(TextView)itemView.findViewById(R.id.asset_name);
        }

    }
    public void AlterMyAssetDetailURL(String AssetID) {
        Constants.ASSET_DETAILS_URL = Constants.ASSET_DETAILS_MODEL_URL + AssetID;
        Log.d("Check Asset_detail_url",Constants.ASSET_DETAILS_URL);
    }

}
