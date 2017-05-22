package com.ninja.ultron.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ninja.ultron.R;
import com.ninja.ultron.entity.PendingRequestEntity;

import java.util.ArrayList;

/**
 * Created by Prabhu Sivanandam on 18-May-17.
 */

public class PendingRequestAdapter extends RecyclerView.Adapter<PendingRequestAdapter.ViewHolder> {

    ArrayList<PendingRequestEntity> pendingRequestEntityArrayList=new ArrayList<>();
    PendingRequestEntity pendingRequestEntity;

    public PendingRequestAdapter(ArrayList<PendingRequestEntity> pendingRequestEntityArrayList)
    {
        this.pendingRequestEntityArrayList=pendingRequestEntityArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_request_cards,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        pendingRequestEntity=pendingRequestEntityArrayList.get(position);
        holder.tvAssetStatus.setText(pendingRequestEntity.getRequestStatus());
        holder.tvAssetMake.setText(pendingRequestEntity.getAssetMakeName());
        holder.tvAssetRequestMadeDate.setText(pendingRequestEntity.getRequestRaisedDate());
    }

    @Override
    public int getItemCount() {
        return pendingRequestEntityArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvAssetMake,tvAssetStatus,tvAssetRequestMadeDate;
        public ViewHolder(View itemView) {
            super(itemView);
            tvAssetMake=(TextView)itemView.findViewById(R.id.tvAssetMake);
            tvAssetStatus=(TextView)itemView.findViewById(R.id.tvAssetStatus);
            tvAssetRequestMadeDate=(TextView)itemView.findViewById(R.id.tvAssetRequestMadeDate);
        }
    }

}
