package com.ninja.ultron.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ninja.ultron.R;
import com.ninja.ultron.entity.PendingRequestEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prabhu Sivanandam on 18-May-17.
 */

public class PendingRequestAdapter extends RecyclerView.Adapter<PendingRequestAdapter.ViewHolder> {

    List<PendingRequestEntity> pendingRequestEntityArrayList=new ArrayList<>();
    PendingRequestEntity pendingRequestEntity;
    private mCallback callback;
    public interface mCallback
    {
        public void callDetailsFragment();
    }

    public PendingRequestAdapter(List<PendingRequestEntity> pendingRequestEntityArrayList)
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
        holder.tvAssetStatus.setText(pendingRequestEntity.getStatus());
        holder.tvAssetMake.setText(pendingRequestEntity.getAssetName());
        holder.tvAssetRequestId.setText(pendingRequestEntity.getRequestId()+"");
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.callDetailsFragment();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pendingRequestEntityArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvAssetMake,tvAssetStatus,tvAssetRequestId;
        RelativeLayout layout;
        public ViewHolder(View itemView) {
            super(itemView);
            tvAssetMake=(TextView)itemView.findViewById(R.id.tvAssetMake);
            tvAssetStatus=(TextView)itemView.findViewById(R.id.tvAssetStatus);
            tvAssetRequestId=(TextView)itemView.findViewById(R.id.tvAssetRequestId);
            layout=(RelativeLayout)itemView.findViewById(R.id.layout);
        }
    }

}
