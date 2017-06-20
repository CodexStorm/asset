package com.ninja.ultron.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ninja.ultron.R;
import com.ninja.ultron.constant.Constants;
import com.ninja.ultron.entity.PendingRequestEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prabhu Sivanandam on 18-May-17.
 */

public class PendingRequestAdapter extends RecyclerView.Adapter<PendingRequestAdapter.ViewHolder> {

    List<PendingRequestEntity> pendingRequestEntityArrayList=new ArrayList<>();

    private mCallback callback;
    public interface mCallback
    {
        public void callDetailsFragment();
    }

    public PendingRequestAdapter(List<PendingRequestEntity> pendingRequestEntityArrayList,mCallback callback)
    {
        this.pendingRequestEntityArrayList=pendingRequestEntityArrayList;
        this.callback=callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_request_cards,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final PendingRequestEntity pendingRequestEntity=pendingRequestEntityArrayList.get(position);
        holder.tvAssetStatus.setText(pendingRequestEntity.getStatus());
        holder.tvAssetRequestId.setText(pendingRequestEntity.getRequestId()+"");
        holder.tvAssetCategory.setText(pendingRequestEntity.getRequestType());
        holder.pendingRequestCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlterPendingRequestDetailsURL(""+pendingRequestEntity.getRequestId());
                callback.callDetailsFragment();
            }
        });
    }

    private void AlterPendingRequestDetailsURL(String s) {
        Constants.PENDING_REQUESTS_DETAILS_URL=Constants.PENDING_REQUESTS_DETAILS_MODEL_URL+s;
    }

    @Override
    public int getItemCount()
    {
        return pendingRequestEntityArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvAssetStatus,tvAssetRequestId,tvAssetCategory;
        RelativeLayout layout;
        View pendingRequestCardView;
        public ViewHolder(View itemView) {
            super(itemView);
            pendingRequestCardView=itemView;
            tvAssetCategory = (TextView)itemView.findViewById(R.id.tvRequestType);
            tvAssetStatus=(TextView)itemView.findViewById(R.id.tvAssetStatus);
            tvAssetRequestId=(TextView)itemView.findViewById(R.id.tvAssetRequestId);
            layout=(RelativeLayout)itemView.findViewById(R.id.layout);
        }
    }

}
