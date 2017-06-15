package com.ninja.ultron.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.ninja.ultron.R;
import com.ninja.ultron.activity.PendingRequestDetailsActivity;
import com.ninja.ultron.adapter.PendingRequestAdapter;
import com.ninja.ultron.entity.PendingRequestEntity;
import com.ninja.ultron.entity.PendingRequestMiniEntity;
import com.ninja.ultron.functions.CommonFunctions;
import com.ninja.ultron.restclient.RestClientImplementation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prabhu Sivanandam on 20-May-17.
 */

public class PendingRequestsFragment extends Fragment {

    RecyclerView recyclerView;
    PendingRequestAdapter adapter;
    List<PendingRequestEntity> pendingRequestEntities=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_pending_requests,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.rvMyPendingRequests);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        callMyPendingRequestsApi();
        return view;
    }

    private void callMyPendingRequestsApi() {
        PendingRequestMiniEntity entity=new PendingRequestMiniEntity();
        RestClientImplementation.pendingRequestsApi(entity, new PendingRequestMiniEntity.UltronRestClientInterface() {
            @Override
            public void onInitialize(PendingRequestMiniEntity pendingRequestMiniEntity, VolleyError error) {

                if(error==null)
                {
                    if(pendingRequestMiniEntity.getResponse()!=null)
                    {
                        pendingRequestEntities=pendingRequestMiniEntity.getResponse();
                        Gson gson=new Gson();
                        String myPendingRequestsString=gson.toJson(pendingRequestEntities);
                        adapter=new PendingRequestAdapter(pendingRequestEntities,new PendingRequestAdapter.mCallback()
                        {
                            @Override
                            public void callDetailsFragment() {
                                //PendingRequestDetailsFragment pendingRequestDetailsFragment=new PendingRequestDetailsFragment();
                                // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rlPendingRequestList,pendingRequestDetailsFragment).addToBackStack(null).commit();
                                Intent intent=new Intent(getActivity(), PendingRequestDetailsActivity.class);
                                startActivity(intent);
                            }
                        });
                        recyclerView.setAdapter(adapter);
                        recyclerView.hasFixedSize();
                        adapter.notifyDataSetChanged();
                    }
                    else
                    {
                        Log.d("as","committed");
                    }
                }
                else
                {
                    if(pendingRequestMiniEntity.getStatusCode()==401)
                    {
                        CommonFunctions.toastString("Unauthorized",getContext());
                    }
                }

            }
        },getContext());
    }
}
