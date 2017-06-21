package com.ninja.ultron.adapter;

import android.icu.text.LocaleDisplayNames;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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



    int requestTypeId;
    private mCallback callback;

    public interface mCallback
    {
        public void callAssetDetailsFragment();
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
        holder.tvRequestType.setText(pendingRequestEntity.getRequestType());
        holder.statusId = pendingRequestEntity.getStatusId();
        holder.pendingRequestCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestTypeId = 0;
                Log.d("RequestType",pendingRequestEntity.getRequestType());
                if(pendingRequestEntity.getRequestType().equals("NEW ASSET")) {
                    requestTypeId = 2;
                }
                else if(pendingRequestEntity.getRequestType().equals("TRANSFER")){
                    requestTypeId = 1;
                }
                else
                    requestTypeId =3;
                AlterPendingRequestDetailsURL(pendingRequestEntity.getRequestId(),requestTypeId);
                callback.callAssetDetailsFragment();
            }
        });
    }

    private void AlterPendingRequestDetailsURL(int RequestId, int RequestTypeId) {
        Constants.PENDING_REQUESTS_DETAILS_URL=Constants.PENDING_REQUESTS_DETAILS_MODEL_URL+RequestId+"&requestTypeId="+RequestTypeId;
    }

    @Override
    public int getItemCount()
    {
        return pendingRequestEntityArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvAssetStatus,tvAssetRequestId, tvRequestType;
        RelativeLayout layout;
        View pendingRequestCardView;
        int statusId;
        public ViewHolder(View itemView) {
            super(itemView);
            pendingRequestCardView=itemView;
            tvRequestType = (TextView)itemView.findViewById(R.id.tvRequestType);
            tvAssetStatus=(TextView)itemView.findViewById(R.id.tvAssetStatus);
            tvAssetRequestId=(TextView)itemView.findViewById(R.id.tvAssetRequestId);
            layout=(RelativeLayout)itemView.findViewById(R.id.layout);
        }
    }
    public int getRequestId() {
        return requestTypeId;
    }
}
