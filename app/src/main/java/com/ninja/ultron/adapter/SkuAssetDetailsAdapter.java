package com.ninja.ultron.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ninja.ultron.R;
import com.ninja.ultron.activity.TransferAssetRequestDetailsActivity;
import com.ninja.ultron.entity.SkuAssetDetailsEntity;

import java.util.List;

/**
 * Created by manoj on 21-06-2017.
 */

public class SkuAssetDetailsAdapter extends RecyclerView.Adapter<SkuAssetDetailsAdapter.ViewHolder>{
    List<SkuAssetDetailsEntity> assetList;
    SkuAssetDetailsEntity asset;
    Context context;
    String details[] = new String[3];

    public SkuAssetDetailsAdapter(Context context,List<SkuAssetDetailsEntity> assetList){
        this.assetList = assetList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transfer_request_details_asset_card,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        asset = assetList.get(position);
        holder.tvAssetType.setText(asset.getSkuName());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_expandable_list_item_1,asset.getList());
        holder.lvAssets.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return assetList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAssetType;
        List<String> list;
        ListView lvAssets;

        public ViewHolder(View itemView) {
            super(itemView);
            tvAssetType = (TextView)itemView.findViewById(R.id.tvAssetType);
            lvAssets = (ListView)itemView.findViewById(R.id.lvAssets);
        }
    }
}
