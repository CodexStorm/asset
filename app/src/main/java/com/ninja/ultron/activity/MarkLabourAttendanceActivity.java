package com.ninja.ultron.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ninja.ultron.R;
import com.ninja.ultron.adapter.MarkLabourAttendanceAdapter;
import com.ninja.ultron.constant.Constants;
import com.ninja.ultron.entity.LabourAttendanceMobileDTO;
import com.ninja.ultron.entity.LabourAttendanceMobileDTOAPI;
import com.ninja.ultron.entity.LabourAttendanceTrackerEntity;
import com.ninja.ultron.entity.LabourShiftDetailEntity;
import com.ninja.ultron.entity.PostBulkLaboursAttendanceAPIEntity;
import com.ninja.ultron.functions.CommonFunctions;
import com.ninja.ultron.functions.UserDetails;
import com.ninja.ultron.restclient.RestClientImplementation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Sangameswaran on 05-Jun-17.
 */

public class MarkLabourAttendanceActivity extends Activity {
    String shiftDetailObj = "";
    LabourShiftDetailEntity labourShiftDetailEntity;
    Gson gson;
    public RelativeLayout rlLoader, rlLabourLayout, rlLabourSearchLayout, rlAbSearch, rlClearSearchEditText;
    private ListView lvLabour;
    LinearLayout llAttendance;
    Button btnPresent,btnAbsent;
    AlertDialog.Builder infoWindow;
    int searchFetchOffset = 0;
    int searchFetchLimit = Constants.NUMBER_FEED_FETECHED_PER_PULL;
    int searchNext_load_flag = 0;
    int searchFeedLoadComplete = 0;
    String searchString = "";
    int fetchOffset = Constants.YOUR_ORDERS_FETCH_OFFSET;
    int fetchLimit = Constants.YOUR_ORDERS_FETCH_LIMIT;
    int feedLoadComplete = 0;
    View footerView;
    int next_load_flag = 0;
    private EditText etSearchLabour;
    MarkLabourAttendanceAdapter markLabourAttendanceAdapter;
    List<LabourAttendanceMobileDTO> labourAttendanceMobileDTOList = new ArrayList<>();
    boolean isSearchLabourOpened = false;
    private InputMethodManager imm;
    private TextView tvAbTitle;

    AlertDialog.Builder alertDialogBuilder = null;
    AlertDialog alertDialog = null;

    public String shiftName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mark_labour_attendance);
        llAttendance=(LinearLayout)findViewById(R.id.llAttendance);
        btnAbsent=(Button)findViewById(R.id.btnAbsent);
        infoWindow=new AlertDialog.Builder(this);
        btnPresent=(Button)findViewById(R.id.btnPresent);
        shiftDetailObj = getIntent().getExtras().getString("shiftDetailObj");
        gson = new Gson();
        try {
            labourShiftDetailEntity = gson.fromJson(shiftDetailObj, LabourShiftDetailEntity.class);
        }catch (Exception e){
            CommonFunctions.toastString("Casting exception", MarkLabourAttendanceActivity.this);
            finish();
        }
        shiftName = labourShiftDetailEntity.getShiftName();
        setViewId();
        rlLoader.setVisibility(View.VISIBLE);
        loadLabour();

        etSearchLabour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                searchString = arg0.toString();
                if(searchString.equals("")){
                    markLabourAttendanceAdapter.setFilter(labourAttendanceMobileDTOList);
                }
                else {
               List<LabourAttendanceMobileDTO> searchEntityList=new ArrayList<LabourAttendanceMobileDTO>();
                for(LabourAttendanceMobileDTO iterator:labourAttendanceMobileDTOList){
                    if(iterator.getLabourId().toString().contains(searchString)){
                        searchEntityList.add(iterator);
                    }
                }
                markLabourAttendanceAdapter.setFilter(searchEntityList);

              }
            }
        });
        /*lvLabour.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount
                        - Constants.FEED_LEFT_BEFORE_NEW_ORDER_FETCH;

                if (loadMore && (next_load_flag == 1) && (feedLoadComplete == 0)) {
                    Log.d("on scroll ", " load more " + loadMore);
                    fetchOffset = fetchOffset + fetchLimit;
                    next_load_flag = 0;
                    if (!isSearchLabourOpened) {
                        loadLabour();
                    }
                }
            }
        });
*/
    }

    private void searchLocally(String searchString) {
        try{

        }catch (Exception e){}
    }

    public void setViewId(){
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        rlLoader = (RelativeLayout)findViewById(R.id.rlLoader);
        rlLabourLayout  = (RelativeLayout)findViewById(R.id.rlLabourLayout);
        rlLabourSearchLayout  = (RelativeLayout)findViewById(R.id.rlLabourSearchLayout);
        rlAbSearch  = (RelativeLayout)findViewById(R.id.rlAbSearch);
        rlClearSearchEditText  = (RelativeLayout)findViewById(R.id.rlClearSearchEditText);
        lvLabour  = (ListView) findViewById(R.id.lvLabour);
        etSearchLabour  = (EditText) findViewById(R.id.etSearchLabour);
        tvAbTitle  = (TextView) findViewById(R.id.tvAbTitle);
        tvAbTitle.setText("Labour Attendance" + "\n" + shiftName);
        footerView = this.getLayoutInflater().inflate(R.layout.progress_layout, null, false);
        lvLabour.addFooterView(footerView);
        showLabourLayout();
        initListAdapter();
    }

    public void initListAdapter(){
        labourAttendanceMobileDTOList.clear();
        markLabourAttendanceAdapter = new MarkLabourAttendanceAdapter(MarkLabourAttendanceActivity.this, R.layout.adapter_reported_labour, labourAttendanceMobileDTOList);
        lvLabour.setAdapter(markLabourAttendanceAdapter);
        markLabourAttendanceAdapter.notifyDataSetChanged();
    }
    public void showLabourLayout(){
        rlLabourLayout.setVisibility(View.VISIBLE);
        rlLabourSearchLayout.setVisibility(View.GONE);
    }

    public void showLabourSearchLayout(){
        rlLabourLayout.setVisibility(View.VISIBLE);
        rlLabourSearchLayout.setVisibility(View.VISIBLE);
        rlClearSearchEditText.setVisibility(View.GONE);
    }

    public void icnSearchClick(View v){
        isSearchLabourOpened = true;
        showLabourSearchLayout();
        clearEditTextView();//
        imm.toggleSoftInputFromWindow(etSearchLabour.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
        etSearchLabour.requestFocus();
    }

    @Override
    public void onBackPressed() {
        handleBackPress();
    }

    public void icnBackPressed(View v){
        handleBackPress();
    }
    public void icnBackPressInSearch(View v){
        handleBackPress();
    }

    public void handleBackPress(){
        if(isSearchLabourOpened){
            isSearchLabourOpened = false;
            rlClearSearchEditText.setVisibility(View.GONE);
            showLabourLayout();
            closeKeyboard();
        }else{
            super.onBackPressed();
        }
    }

    public void clearSearchEditText(View v){
        clearEditTextView();
    }
    public void clearEditTextView() {
       // etSearchLabour.setText("");
        rlClearSearchEditText.setVisibility(View.GONE);
    }

    /*public void markAttendanceForLabour(final int position){
        LabourAttendanceMobileDTO labourAttendanceMobileDTO;
        labourAttendanceMobileDTO = labourAttendanceMobileDTOList.get(position);
        final int labourId = labourAttendanceMobileDTO.getLabourId().intValue();
        String labourCode = labourAttendanceMobileDTO.getLabourAgencyCode() + " " + String.valueOf(labourId);
        *//*alertDialogBuilder = new AlertDialog.Builder(MarkLabourAttendanceActivity.this, R.style.AlertDialogBackground);
        alertDialogBuilder
                .setMessage("Do you want to report Labour(" + labourCode + ") ?")
                .setCancelable(true)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                postLabourAttendance(position);
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
        alertDialog.show();*//*
    }
*/
    /*public void postLabourAttendance(final int position){
        LabourAttendanceMobileDTO labourAttendanceMobileDTO;
        // labourAttendanceMobileDTO = searchLabourAttendanceMobileDTOList.get(position);
        labourAttendanceMobileDTO = labourAttendanceMobileDTOList.get(position);


        final int labourId = labourAttendanceMobileDTO.getLabourId().intValue();
        int facilityId = Integer.parseInt(String.valueOf(UserDetails.getFacilityId(MarkLabourAttendanceActivity.this)));
        int shiftDetailId = labourShiftDetailEntity.getId();
        int hours = labourShiftDetailEntity.getHours();
        LabourAttendanceTrackerEntity labourAttendanceTrackerEntity = new LabourAttendanceTrackerEntity();
        labourAttendanceTrackerEntity.setDeleted((byte)0);
        labourAttendanceTrackerEntity.setLabourId(labourId);
        labourAttendanceTrackerEntity.setFacilityId(facilityId);
        labourAttendanceTrackerEntity.setShiftDetailId(shiftDetailId);
        labourAttendanceTrackerEntity.setShiftHours(hours);
        rlLoader.setVisibility(View.VISIBLE);

        RestClientImplementation.postLabourAttendance(labourAttendanceTrackerEntity, new LabourAttendanceTrackerEntity.FlashRestClientInterface() {
            @Override
            public void onLabourAttendanceTracker(LabourAttendanceTrackerEntity labourAttendanceTrackerEntity, VolleyError error) {
                if(error==null){
                    CommonFunctions.toastString("Success",MarkLabourAttendanceActivity.this );
                    //removeLabourFromList(position);
                }else if(error!=null){
                    CommonFunctions.restClientErrorValidation(labourAttendanceTrackerEntity.getCode(), labourAttendanceTrackerEntity.getMessage(), MarkLabourAttendanceActivity.this);
                }
                rlLoader.setVisibility(View.GONE);
            }
        }, MarkLabourAttendanceActivity.this,UserDetails.getAsgardUserId(MarkLabourAttendanceActivity.this));

    }*/

   /* public void reset() {
        fetchOffset = Constants.YOUR_ORDERS_FETCH_OFFSET;
        fetchLimit = Constants.YOUR_ORDERS_FETCH_LIMIT;
        feedLoadComplete = 0;
        next_load_flag = 0;
    }*/

   /* public void resetSearchList() {
        searchFetchOffset = 0;
        searchNext_load_flag = 0;
        searchFeedLoadComplete = 0;
    }*/

    public void closeKeyboard() {
        imm.hideSoftInputFromWindow(etSearchLabour.getWindowToken(), 0);
    }

    public void loadLabour(){
        int userId=UserDetails.getAsgardUserId(this);
        LabourAttendanceMobileDTOAPI labourAttendanceMobileDTOAPI = new LabourAttendanceMobileDTOAPI(0,0,fetchOffset, fetchLimit);
        RestClientImplementation.getLabourForMarkingAttendance(labourAttendanceMobileDTOAPI, new LabourAttendanceMobileDTOAPI.FlashRestClientInterface() {
            @Override
            public void onLabourAttendanceMobileDTO(LabourAttendanceMobileDTOAPI labourAttendanceMobileDTOAPI, VolleyError error) {
                if(error==null){
                    if(labourAttendanceMobileDTOAPI.getLabourAttendanceMobileDTOList()!=null && labourAttendanceMobileDTOAPI.getLabourAttendanceMobileDTOList().size()>0){
                        List<LabourAttendanceMobileDTO> tempLabourAttendanceMobileDTOList = labourAttendanceMobileDTOAPI.getLabourAttendanceMobileDTOList();
                        labourAttendanceMobileDTOList.addAll(tempLabourAttendanceMobileDTOList);
                        markLabourAttendanceAdapter.notifyDataSetChanged();
                    }else if(labourAttendanceMobileDTOList.size()==0){
                        try {
                            CommonFunctions.toastString("No Items", MarkLabourAttendanceActivity.this);
                        } catch (Exception e) {
                            finish();
                        }
                    }
                }else if (error != null) {
                    if(labourAttendanceMobileDTOList==null || labourAttendanceMobileDTOList.size()==0) {
                        CommonFunctions.restClientErrorValidation(labourAttendanceMobileDTOAPI.getCode(), labourAttendanceMobileDTOAPI.getMessage(), MarkLabourAttendanceActivity.this);
                    }
                }
                rlLoader.setVisibility(View.GONE);
            }
        }, MarkLabourAttendanceActivity.this, null,userId);
    }

    /*public void loadSearchLabour(){
        *//*int labourId = Integer.parseInt(searchString);
        int userId=UserDetails.getAsgardUserId(this);
        LabourAttendanceMobileDTOAPI labourAttendanceMobileDTOAPI = new LabourAttendanceMobileDTOAPI(0,labourId,searchFetchOffset, searchFetchLimit);
        RestClientImplementation.getLabourForMarkingAttendance(labourAttendanceMobileDTOAPI, new LabourAttendanceMobileDTOAPI.FlashRestClientInterface() {
            @Override
            public void onLabourAttendanceMobileDTO(LabourAttendanceMobileDTOAPI labourAttendanceMobileDTOAPI, VolleyError error) {
                if(error==null){
                    if(labourAttendanceMobileDTOAPI.getLabourAttendanceMobileDTOList()!=null && labourAttendanceMobileDTOAPI.getLabourAttendanceMobileDTOList().size()>0){
                        List<LabourAttendanceMobileDTO> tempLabourAttendanceMobileDTOList = labourAttendanceMobileDTOAPI.getLabourAttendanceMobileDTOList();
                        if (searchFetchOffset == 0) {
                            searchLabourAttendanceMobileDTOList.clear();
                        }
                        searchLabourAttendanceMobileDTOList.addAll(tempLabourAttendanceMobileDTOList);
                        searchNext_load_flag = 1;
                        if (searchLabourAttendanceMobileDTOList.size() < searchFetchLimit) {
                            try {
                            } catch (Exception e) {}
                            searchFeedLoadComplete = 1;
                        }
                        lvLabourSearch.setVisibility(View.VISIBLE);
                        searchMarkLabourAttendanceAdapter.notifyDataSetChanged();
                    } else if (searchLabourAttendanceMobileDTOList.size() == 0) {
                        searchLabourAttendanceMobileDTOList.clear();
                        searchMarkLabourAttendanceAdapter.notifyDataSetChanged();
                        lvLabourSearch.setVisibility(View.GONE);
                        CommonFunctions.toastString("Labour not found", MarkLabourAttendanceActivity.this);
                    } else {
                        try {
                        } catch (Exception e) {
                        }
                        searchFeedLoadComplete = 1;
                    }
                }else if(error!=null){
                    if(searchLabourAttendanceMobileDTOList==null || searchLabourAttendanceMobileDTOList.size()==0) {
                        CommonFunctions.restClientErrorValidation(labourAttendanceMobileDTOAPI.getCode(), labourAttendanceMobileDTOAPI.getMessage(), MarkLabourAttendanceActivity.this);
                    }
                }
                rlLoader.setVisibility(View.GONE);
                pbSmall.setVisibility(View.GONE);
            }
        }, MarkLabourAttendanceActivity.this, "SEARCH_LABOUR",userId);
*//*    }*/
    public void notifyCheckCount(int checkCount){
        if(checkCount>0){
            llAttendance.setVisibility(View.VISIBLE);
            btnPresent.setVisibility(View.VISIBLE);
            btnAbsent.setVisibility(View.VISIBLE);
            btnPresent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final List<LabourAttendanceMobileDTO> checkedLabours=markLabourAttendanceAdapter.getCheckedLabours();
                    Gson gson=new Gson();
                    PostBulkLaboursAttendanceAPIEntity entity=new PostBulkLaboursAttendanceAPIEntity();
                    entity.setLabours(checkedLabours);
                    entity.setAttendanceStatus(2);
                    entity.setFacilityId(UserDetails.getFacilityId(MarkLabourAttendanceActivity.this));
                    entity.setUserId(UserDetails.getAsgardUserId(MarkLabourAttendanceActivity.this));
                    entity.setShiftDetailId(labourShiftDetailEntity.getId());
                    entity.setShiftHours(labourShiftDetailEntity.getHours());
                    RestClientImplementation.bulkAttendanceMarker(entity, new PostBulkLaboursAttendanceAPIEntity.UltronRestClientInterface() {
                        @Override
                        public void postBulkAttendance(PostBulkLaboursAttendanceAPIEntity entity, VolleyError error) {
                            if(error==null) {
                                infoWindow.setTitle("Success").setMessage("Attendance Marked Successfully");
                                infoWindow.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                infoWindow.create().show();
                                markLabourAttendanceAdapter.notifyAndRefreshUI(checkedLabours,2);
                            }
                            else {
                                Log.d("ERROR",error.toString());
                                infoWindow.setTitle("Server Error").setMessage("Attendance marking failed, Try again");
                                infoWindow.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                markLabourAttendanceAdapter.notifyAndRefreshUI(checkedLabours,0);
                                infoWindow.create().show();
                            }
                        }
                    },MarkLabourAttendanceActivity.this);
                }
            });
            btnAbsent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final List<LabourAttendanceMobileDTO> checkedLabours=markLabourAttendanceAdapter.getCheckedLabours();
                    Gson gson=new Gson();
                    PostBulkLaboursAttendanceAPIEntity entity=new PostBulkLaboursAttendanceAPIEntity();
                    entity.setLabours(checkedLabours);
                    entity.setAttendanceStatus(1);
                    entity.setFacilityId(UserDetails.getFacilityId(MarkLabourAttendanceActivity.this));
                    entity.setUserId(UserDetails.getAsgardUserId(MarkLabourAttendanceActivity.this));
                    entity.setShiftDetailId(labourShiftDetailEntity.getId());
                    entity.setShiftHours(labourShiftDetailEntity.getHours());
                    RestClientImplementation.bulkAttendanceMarker(entity, new PostBulkLaboursAttendanceAPIEntity.UltronRestClientInterface() {
                        @Override
                        public void postBulkAttendance(PostBulkLaboursAttendanceAPIEntity entity, VolleyError error) {
                            if(error==null) {
                                infoWindow.setTitle("Success").setMessage("Attendance Marked Successfully");
                                infoWindow.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                infoWindow.create().show();
                                markLabourAttendanceAdapter.notifyAndRefreshUI(checkedLabours,1);
                            }
                            else {
                                infoWindow.setTitle("Server Error").setMessage("Attendance marking failed, Try again");
                                infoWindow.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                infoWindow.create().show();
                                markLabourAttendanceAdapter.notifyAndRefreshUI(checkedLabours,0);
                            }

                        }
                    },MarkLabourAttendanceActivity.this);
                }
            });
        }
        else {
            llAttendance.setVisibility(View.GONE);
            btnPresent.setVisibility(View.GONE);
            btnAbsent.setVisibility(View.GONE);
        }
    }

}
