package com.ninja.ultron.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.ninja.ultron.R;
import com.ninja.ultron.adapter.PendingRequestCommentsAdapter;
import com.ninja.ultron.entity.CommentEntity;
import com.ninja.ultron.entity.PendingRequestDetailsEntity;
import com.ninja.ultron.entity.PendingRequestDetailsMiniEntity;
import com.ninja.ultron.entity.PendingRequestsCommentsEntity;
import com.ninja.ultron.functions.CommonFunctions;
import com.ninja.ultron.restclient.RestClientImplementation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prabhu Sivanandam on 24-May-17.
 */

public class PendingRequestDetailsFragment extends Fragment {

    List<PendingRequestDetailsEntity> pendingRequestDetailsEntities;
    PendingRequestDetailsEntity entity;
    List<PendingRequestsCommentsEntity> comments;
    TextView tvPendingAssetId,tvPendingAssetName,tvPendingRequestType,tvPendingRequestTo,tvPendingRequestDate,tvPendingReason,tvPendingStatus;
    RecyclerView rvComments;
    PendingRequestCommentsAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_pending_request_details,container,false);
        tvPendingAssetId=(TextView)view.findViewById(R.id.tvPendingAssetId);
        tvPendingAssetName=(TextView)view.findViewById(R.id.tvPendingAssetName);
        tvPendingRequestType=(TextView)view.findViewById(R.id.tvPendingRequestType);
        tvPendingRequestTo=(TextView)view.findViewById(R.id.tvPendingRequestTo);
        tvPendingRequestDate=(TextView)view.findViewById(R.id.tvPendingRequestDate);
        tvPendingReason=(TextView)view.findViewById(R.id.tvPendingReason);
        tvPendingStatus=(TextView)view.findViewById(R.id.tvPendingStatus);
        rvComments=(RecyclerView)view.findViewById(R.id.rvComments);
        pendingRequestDetailsEntities=new ArrayList<>();
        comments=new ArrayList<>();
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        rvComments.setLayoutManager(manager);
        callGetDetailsApi();
        return view;
    }

    public void callGetDetailsApi()
    {
        entity=new PendingRequestDetailsEntity();
        PendingRequestDetailsMiniEntity pendingRequestDetailsMiniEntity=new PendingRequestDetailsMiniEntity();
        RestClientImplementation.getPendingRequestDetailsApi(pendingRequestDetailsMiniEntity,new PendingRequestDetailsMiniEntity.UltronRestClientInterface()
        {
            @Override
            public void onInitialize(PendingRequestDetailsMiniEntity pendingRequestDetailsMiniEntity, VolleyError error) {

                if(error==null)
                {
                    if(pendingRequestDetailsMiniEntity.getResponse()!=null)
                    {
                        pendingRequestDetailsEntities=pendingRequestDetailsMiniEntity.getResponse();
                        entity=pendingRequestDetailsEntities.get(0);
                        Log.d("tag",entity.getAssetName()+"  "+entity.getAssetId()+"  "+entity.getDateOfRequest()+"  "+entity.getReason()+"  "+entity.getStatus());
                        tvPendingAssetId.setText(""+(entity.getAssetId()));
                        tvPendingAssetName.setText(entity.getAssetName());
                        tvPendingRequestType.setText(""+entity.getAssetRequestId());
                        tvPendingRequestTo.setText(""+(entity.getRequestTo()));
                        tvPendingRequestDate.setText(""+(entity.getDateOfRequest()));
                        tvPendingReason.setText(entity.getReason());
                        tvPendingStatus.setText(entity.getStatus());
                        comments=entity.getComment();
                        Log.d("tag2",comments.size()+"");
                        if(comments.size()==0)
                        {

                        }
                        else
                        {
                            adapter = new PendingRequestCommentsAdapter(comments);
                            rvComments.setAdapter(adapter);
                            rvComments.hasFixedSize();
                            adapter.notifyDataSetChanged();
                        }
                    }

                    else
                    {
                        Log.d("tag","null response");
                    }
                }

                else
                {
                    if(pendingRequestDetailsMiniEntity.getStatusCode()==401)
                    {
                        CommonFunctions.toastString("Unauthorized",getContext());
                    }
                }


            }
        },getContext());
    }

}
