package com.ninja.ultron.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.ninja.ultron.R;
import com.ninja.ultron.adapter.PendingRequestCommentsAdapter;
import com.ninja.ultron.entity.PendingRequestDetailsEntity;
import com.ninja.ultron.entity.PendingRequestDetailsMiniEntity;
import com.ninja.ultron.entity.PendingRequestsCommentsEntity;
import com.ninja.ultron.functions.CommonFunctions;
import com.ninja.ultron.restclient.RestClientImplementation;

import java.util.ArrayList;
import java.util.List;

public class PendingRequestDetailsActivity extends AppCompatActivity {

    List<PendingRequestDetailsEntity> pendingRequestDetailsEntities;
    PendingRequestDetailsEntity entity;
    List<PendingRequestsCommentsEntity> comments;
    TextView tvPendingAssetId;
    TextView tvPendingAssetName;
    TextView tvPendingRequestType;
    TextView tvPendingRequestTo;
    TextView tvPendingRequestDate;
    TextView tvPendingReason;
    TextView tvPendingStatus;
    RecyclerView rvComments;
    PendingRequestCommentsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_request_details);

        tvPendingAssetId=(TextView)findViewById(R.id.tvPendingAssetId);
        tvPendingAssetName=(TextView)findViewById(R.id.tvPendingAssetName);
        tvPendingRequestType=(TextView)findViewById(R.id.tvPendingRequestType);
        tvPendingRequestTo=(TextView)findViewById(R.id.tvPendingRequestTo);
        tvPendingRequestDate=(TextView)findViewById(R.id.tvPendingRequestDate);
        tvPendingReason=(TextView)findViewById(R.id.tvPendingReason);
        tvPendingStatus=(TextView)findViewById(R.id.tvPendingStatus);
        rvComments=(RecyclerView)findViewById(R.id.rvComments);
        pendingRequestDetailsEntities=new ArrayList<>();
        comments=new ArrayList<>();
        LinearLayoutManager manager=new LinearLayoutManager(PendingRequestDetailsActivity.this);
        rvComments.setLayoutManager(manager);
        callGetDetailsApi();
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
                        CommonFunctions.toastString("Unauthorized",PendingRequestDetailsActivity.this);
                    }
                }


            }
        },PendingRequestDetailsActivity.this);
    }

}


