package com.ninja.ultron.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.ninja.ultron.R;
import com.ninja.ultron.adapter.LabourShiftDetailAdapter;
import com.ninja.ultron.entity.LabourShiftDetailAPI;
import com.ninja.ultron.entity.LabourShiftDetailEntity;
import com.ninja.ultron.functions.CommonFunctions;
import com.ninja.ultron.functions.StartIntent;
import com.ninja.ultron.functions.UserDetails;
import com.ninja.ultron.restclient.RestClientImplementation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prabhu Sivanandam on 05-Jun-17.
 */

public class LabourAttendanceMainActivity extends AppCompatActivity{
    private TextView tvAbTitle;
    private RelativeLayout rlMain, rlLoader;
    private LinearLayout llLabourAttendanceShiftDetail, llLabourAttendanceGateway;
    private ListView lvLabourShiftDetails;

    boolean labourAttendanceGatewayOpened = false;
    List<LabourShiftDetailEntity> labourShiftDetailEntityList = new ArrayList<>();
    LabourShiftDetailEntity labourShiftDetailEntity;
    LabourShiftDetailAdapter labourShiftDetailAdapter;

    String shiftName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_labour_attendance_main);
        setViewId();
        initListAdapter();
        loadShiftDetail();
    }

    public void initListAdapter(){
        labourShiftDetailEntityList.clear();
        labourShiftDetailAdapter = new LabourShiftDetailAdapter(LabourAttendanceMainActivity.this, R.layout.adapter_labour_shiftdetail, labourShiftDetailEntityList);
        lvLabourShiftDetails.setAdapter(labourShiftDetailAdapter);
        labourShiftDetailAdapter.notifyDataSetChanged();
    }


    public void setViewId(){
        tvAbTitle = (TextView)findViewById(R.id.tvAbTitle);
        rlMain = (RelativeLayout) findViewById(R.id.rlMain);
        llLabourAttendanceShiftDetail = (LinearLayout) findViewById(R.id.llLabourAttendanceShiftDetail);
        llLabourAttendanceGateway = (LinearLayout) findViewById(R.id.llLabourAttendanceGateway);
        lvLabourShiftDetails = (ListView) findViewById(R.id.lvLabourShiftDetails);
        rlLoader = (RelativeLayout) findViewById(R.id.rlLoader);
        showLabourShiftDetailView();
    }

    public void loadShiftDetail(){
        rlLoader.setVisibility(View.VISIBLE);
        int userId=UserDetails.getAsgardUserId(this);
        LabourShiftDetailAPI labourShiftDetailAPI = new LabourShiftDetailAPI();
        RestClientImplementation.getLabourShiftDetail(labourShiftDetailAPI, new LabourShiftDetailAPI.FlashRestClientInterface() {
            @Override
            public void onLabourShiftDetail(LabourShiftDetailAPI labourShiftDetailAPI, VolleyError error) {
                if(error==null){
                    labourShiftDetailEntityList.clear();
                    if(labourShiftDetailAPI.getResponse()!=null && labourShiftDetailAPI.getResponse().size()>0){
                        labourShiftDetailEntityList.addAll(labourShiftDetailAPI.getResponse());
                        labourShiftDetailAdapter.notifyDataSetChanged();
                    }else{
                        CommonFunctions.toastString("No shift detail", LabourAttendanceMainActivity.this);
                    }
                }else if(error!=null){
                    labourShiftDetailEntityList.clear();
                    labourShiftDetailAdapter.notifyDataSetChanged();
                    CommonFunctions.restClientErrorValidation(labourShiftDetailAPI.getStatusCode(), labourShiftDetailAPI.getMessage(), LabourAttendanceMainActivity.this);
                }
                rlLoader.setVisibility(View.GONE);
            }
        }, LabourAttendanceMainActivity.this,userId);
    }

    public void showLabourAttendanceGatewayView(){
        llLabourAttendanceGateway.setVisibility(View.VISIBLE);
        llLabourAttendanceShiftDetail.setVisibility(View.GONE);
        tvAbTitle.setText("Labour Attendance" + "\n" + shiftName);
    }

    public void showLabourShiftDetailView(){
        llLabourAttendanceShiftDetail.setVisibility(View.VISIBLE);
        llLabourAttendanceGateway.setVisibility(View.GONE);
        tvAbTitle.setText("Shift Detail");
    }
    public void handleBackPress(){
        if(labourAttendanceGatewayOpened){
            labourAttendanceGatewayOpened = false;
            showLabourShiftDetailView();
        }else{
            super.onBackPressed();
        }
    }
    @Override
    public void onBackPressed() {
        handleBackPress();
    }

    public void icnBackPressed(View v){
        handleBackPress();
    }
    public void moveToReportedLabourActivity(View v){
        Bundle bundle = new Bundle();
        Gson gs = new Gson();
        String shiftDetailObj = gs.toJson(labourShiftDetailEntity);
        bundle.putString("shiftDetailObj", shiftDetailObj);
        StartIntent.commonStartActivity(LabourAttendanceMainActivity.this, ReportedLabourActivity.class, bundle);
    }
    public void moveToMarkAttendanceActivity(View v){
        Bundle bundle = new Bundle();
        Gson gs = new Gson();
        String shiftDetailObj = gs.toJson(labourShiftDetailEntity);
        bundle.putString("shiftDetailObj", shiftDetailObj);
        StartIntent.commonStartActivity(LabourAttendanceMainActivity.this, MarkLabourAttendanceActivity.class, bundle);
    }
    public void shiftOnClick(int position){
        labourAttendanceGatewayOpened = true;
        labourShiftDetailEntity = labourShiftDetailEntityList.get(position);
        shiftName = labourShiftDetailEntity.getShiftName();
        showLabourAttendanceGatewayView();
    }

}

