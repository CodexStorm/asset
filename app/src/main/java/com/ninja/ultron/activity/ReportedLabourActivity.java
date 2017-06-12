package com.ninja.ultron.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.ninja.ultron.R;
import com.ninja.ultron.adapter.ReportedLabourAdapter;
import com.ninja.ultron.entity.LabourAttendanceMobileDTO;
import com.ninja.ultron.entity.LabourAttendanceMobileDTOAPI;
import com.ninja.ultron.entity.LabourAttendanceTrackerEntity;
import com.ninja.ultron.entity.LabourShiftDetailEntity;
import com.ninja.ultron.functions.CommonFunctions;
import com.ninja.ultron.functions.UserDetails;
import com.ninja.ultron.restclient.RestClientImplementation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prabhu Sivanandam on 05-Jun-17.
 */

public class ReportedLabourActivity extends Activity {

    String shiftDetailObj = "";
    LabourShiftDetailEntity labourShiftDetailEntity;
    Gson gson;
    private TextView tvAbTitle;
    private ListView lvReportedLabours;
    private RelativeLayout rlLoader;
    List<LabourAttendanceMobileDTO> labourAttendanceMobileDTOList = new ArrayList<>();
    ReportedLabourAdapter reportedLabourAdapter;
    AlertDialog.Builder alertDialogBuilder = null;
    AlertDialog alertDialog = null;
    String shiftName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_reported_labour);
        shiftDetailObj = getIntent().getExtras().getString("shiftDetailObj");
        gson = new Gson();
        try {
            labourShiftDetailEntity = gson.fromJson(shiftDetailObj, LabourShiftDetailEntity.class);
        }catch (Exception e){
            CommonFunctions.toastString("Casting exception", ReportedLabourActivity.this);
            finish();
        }
        shiftName = labourShiftDetailEntity.getShiftName();
        setViewId();
        initListAdapter();
        loadReportedLabour();
    }

    public void initListAdapter(){
        labourAttendanceMobileDTOList.clear();
        reportedLabourAdapter = new ReportedLabourAdapter(ReportedLabourActivity.this, R.layout.adapter_reported_labour, labourAttendanceMobileDTOList);
        lvReportedLabours.setAdapter(reportedLabourAdapter);
        reportedLabourAdapter.notifyDataSetChanged();
    }

    public void setViewId(){
        tvAbTitle = (TextView)findViewById(R.id.tvAbTitle);
        tvAbTitle.setText("Reported Labour" + "\n" + shiftName);
        lvReportedLabours = (ListView) findViewById(R.id.lvReportedLabours);
        rlLoader = (RelativeLayout) findViewById(R.id.rlLoader);
    }

    public void icnBackPressed(View v){
        super.onBackPressed();
    }

    public void loadReportedLabour(){
        rlLoader.setVisibility(View.VISIBLE);
        LabourAttendanceMobileDTOAPI labourAttendanceMobileDTOAPI = new LabourAttendanceMobileDTOAPI(labourShiftDetailEntity.getId());
        RestClientImplementation.getReportedLabourDetail(labourAttendanceMobileDTOAPI, new LabourAttendanceMobileDTOAPI.FlashRestClientInterface() {
            @Override
            public void onLabourAttendanceMobileDTO(LabourAttendanceMobileDTOAPI labourAttendanceMobileDTOAPI, VolleyError error) {
                if(error==null){
                    if(labourAttendanceMobileDTOAPI.getLabourAttendanceMobileDTOList()!=null && labourAttendanceMobileDTOAPI.getLabourAttendanceMobileDTOList().size()>0){
                        labourAttendanceMobileDTOList.clear();
                        labourAttendanceMobileDTOList.addAll(labourAttendanceMobileDTOAPI.getLabourAttendanceMobileDTOList());
                        reportedLabourAdapter.notifyDataSetChanged();
                    }else{
                        CommonFunctions.toastString("Labour not reported yet", ReportedLabourActivity.this);
                    }
                }else  if(error!=null){
                    labourAttendanceMobileDTOList.clear();
                    reportedLabourAdapter.notifyDataSetChanged();
                    CommonFunctions.restClientErrorValidation(labourAttendanceMobileDTOAPI.getCode(), labourAttendanceMobileDTOAPI.getMessage(), ReportedLabourActivity.this);
                }
                rlLoader.setVisibility(View.GONE);
            }
        },ReportedLabourActivity.this);
    }

    public void deleteReportedLabour(final int position){
        String labourId = labourAttendanceMobileDTOList.get(position).getLabourAgencyCode() + " "
                + String.valueOf(labourAttendanceMobileDTOList.get(position).getLabourId());
        alertDialogBuilder = new AlertDialog.Builder(ReportedLabourActivity.this, R.style.AlertDialogBackground);
        alertDialogBuilder
                .setMessage("Do you want to delete this labour(" + labourId +") attendance? ")
                .setCancelable(true)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteReportLabourAPI(position);
                                alertDialog.dismiss();
                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                alertDialog.dismiss();
                            }
                        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        alertDialog.show();
    }

    public void deleteReportLabourAPI(final int position){
        int labourId = labourAttendanceMobileDTOList.get(position).getLabourId().intValue();
        int facilityId = Integer.parseInt(String.valueOf(UserDetails.getFacilityId(ReportedLabourActivity.this)));
        int shiftDetailId = labourShiftDetailEntity.getId();
        LabourAttendanceTrackerEntity labourAttendanceTrackerEntity = new LabourAttendanceTrackerEntity();
        labourAttendanceTrackerEntity.setDeleted((byte)1);
        labourAttendanceTrackerEntity.setLabourId(labourId);
        labourAttendanceTrackerEntity.setFacilityId(facilityId);
        labourAttendanceTrackerEntity.setShiftDetailId(shiftDetailId);
        rlLoader.setVisibility(View.VISIBLE);
        /*RestClientImplementation.postLabourAttendance(labourAttendanceTrackerEntity, new LabourAttendanceTrackerEntity.FlashRestClientInterface() {
            @Override
            public void onLabourAttendanceTracker(LabourAttendanceTrackerEntity labourAttendanceTrackerEntity, VolleyError error) {
                if(error==null){
                    CommonFunctions.toastString("Done",ReportedLabourActivity.this );
                    labourAttendanceMobileDTOList.remove(position);
                    reportedLabourAdapter.notifyDataSetChanged();
                }else if(error!=null){
                    CommonFunctions.restClientErrorValidation(labourAttendanceTrackerEntity.getCode(), labourAttendanceTrackerEntity.getMessage(), ReportedLabourActivity.this);
                }
                rlLoader.setVisibility(View.GONE);
            }
        }, ReportedLabourActivity.this);*/
        RestClientImplementation.revokeAttendance(labourAttendanceTrackerEntity,new LabourAttendanceTrackerEntity.FlashRestClientInterface(){
            @Override
            public void onLabourAttendanceTracker(LabourAttendanceTrackerEntity labourAttendanceTrackerEntity, VolleyError error) {
                if(error==null){
                    CommonFunctions.toastString("Done",ReportedLabourActivity.this);
                    labourAttendanceMobileDTOList.remove(position);
                    reportedLabourAdapter.notifyDataSetChanged();
                }
                else if(error!=null){
                    CommonFunctions.restClientErrorValidation(labourAttendanceTrackerEntity.getCode(),labourAttendanceTrackerEntity.getMessage(),ReportedLabourActivity.this);
                }
                rlLoader.setVisibility(View.GONE);
            }
        },ReportedLabourActivity.this);
    }
}
