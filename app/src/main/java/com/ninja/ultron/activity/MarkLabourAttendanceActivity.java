package com.ninja.ultron.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.ninja.ultron.R;
import com.ninja.ultron.adapter.MarkLabourAttendanceAdapter;
import com.ninja.ultron.constant.Constants;
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

public class MarkLabourAttendanceActivity extends Activity {
    String shiftDetailObj = "";
    LabourShiftDetailEntity labourShiftDetailEntity;
    Gson gson;
    private RelativeLayout rlLoader, rlLabourLayout, rlLabourSearchLayout, rlAbSearch, rlClearSearchEditText;
    private ListView lvLabour, lvLabourSearch;

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
    private ProgressBar pbSmall;

    MarkLabourAttendanceAdapter markLabourAttendanceAdapter;
    MarkLabourAttendanceAdapter searchMarkLabourAttendanceAdapter;
    List<LabourAttendanceMobileDTO> labourAttendanceMobileDTOList = new ArrayList<>();
    List<LabourAttendanceMobileDTO> searchLabourAttendanceMobileDTOList = new ArrayList<>();

    boolean isSearchLabourOpened = false;
    private InputMethodManager imm;
    private TextView tvAbTitle;

    AlertDialog.Builder alertDialogBuilder = null;
    AlertDialog alertDialog = null;

    String shiftName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mark_labour_attendance);
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
                if (searchString.length() > 0) {
                    rlClearSearchEditText.setVisibility(View.VISIBLE);
                    searchLabourAttendanceMobileDTOList.clear();
                    searchMarkLabourAttendanceAdapter.notifyDataSetChanged();
                    resetSearchList();
                    pbSmall.setVisibility(View.VISIBLE);
                    searchString = searchString.replaceAll("\\s+", "+");
                    lvLabourSearch.setAdapter(searchMarkLabourAttendanceAdapter);
                    if (searchString != null && !searchString.equals("")) {
                        loadSearchLabour();
                    }
                } else {
                    pbSmall.setVisibility(View.GONE);
                    rlClearSearchEditText.setVisibility(View.INVISIBLE);
                    searchLabourAttendanceMobileDTOList.clear();
                    searchMarkLabourAttendanceAdapter.notifyDataSetChanged();
                }
                if (searchString.length() == 0) {
                    rlClearSearchEditText.setVisibility(View.INVISIBLE);
                }
            }
        });

        lvLabourSearch.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;
                if (loadMore && (searchNext_load_flag == 1) && (searchFeedLoadComplete == 0)) {
                    searchFetchOffset = searchFetchOffset + searchFetchLimit;
                    searchNext_load_flag = 0;
                    loadSearchLabour();
                }
            }
        });
        lvLabour.setOnScrollListener(new AbsListView.OnScrollListener() {
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

    }

    public void setViewId(){
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        rlLoader = (RelativeLayout)findViewById(R.id.rlLoader);
        rlLabourLayout  = (RelativeLayout)findViewById(R.id.rlLabourLayout);
        rlLabourSearchLayout  = (RelativeLayout)findViewById(R.id.rlLabourSearchLayout);
        rlAbSearch  = (RelativeLayout)findViewById(R.id.rlAbSearch);
        rlClearSearchEditText  = (RelativeLayout)findViewById(R.id.rlClearSearchEditText);
        lvLabour  = (ListView) findViewById(R.id.lvLabour);
        lvLabourSearch  = (ListView) findViewById(R.id.lvLabourSearch);
        etSearchLabour  = (EditText) findViewById(R.id.etSearchLabour);
        tvAbTitle  = (TextView) findViewById(R.id.tvAbTitle);
        tvAbTitle.setText("Labour Attendance" + "\n" + shiftName);
        pbSmall  = (ProgressBar) findViewById(R.id.pbSmall);
        footerView = this.getLayoutInflater().inflate(R.layout.progress_layout, null, false);
        lvLabour.addFooterView(footerView);
        pbSmall.setVisibility(View.GONE);
        showLabourLayout();
        initListAdapter();
    }

    public void initListAdapter(){
        labourAttendanceMobileDTOList.clear();
        searchLabourAttendanceMobileDTOList.clear();

        markLabourAttendanceAdapter = new MarkLabourAttendanceAdapter(MarkLabourAttendanceActivity.this, R.layout.adapter_reported_labour, labourAttendanceMobileDTOList);
        lvLabour.setAdapter(markLabourAttendanceAdapter);
        markLabourAttendanceAdapter.notifyDataSetChanged();

        searchMarkLabourAttendanceAdapter = new MarkLabourAttendanceAdapter(MarkLabourAttendanceActivity.this, R.layout.adapter_reported_labour, searchLabourAttendanceMobileDTOList);
        lvLabourSearch.setAdapter(searchMarkLabourAttendanceAdapter);
        searchMarkLabourAttendanceAdapter.notifyDataSetChanged();
    }
    public void showLabourLayout(){
        rlLabourLayout.setVisibility(View.VISIBLE);
        rlLabourSearchLayout.setVisibility(View.GONE);
        pbSmall.setVisibility(View.GONE);
    }

    public void showLabourSearchLayout(){
        rlLabourLayout.setVisibility(View.GONE);
        rlLabourSearchLayout.setVisibility(View.VISIBLE);
        rlClearSearchEditText.setVisibility(View.GONE);
    }

    public void icnSearchClick(View v){
        isSearchLabourOpened = true;
        resetSearchList();
        showLabourSearchLayout();
        clearEditTextView();
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
            searchLabourAttendanceMobileDTOList.clear();
            searchMarkLabourAttendanceAdapter.notifyDataSetChanged();
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
        etSearchLabour.setText("");
        rlClearSearchEditText.setVisibility(View.GONE);
    }

    public void markAttendanceForLabour(final int position){
        LabourAttendanceMobileDTO labourAttendanceMobileDTO;
        if(isSearchLabourOpened){
            labourAttendanceMobileDTO = searchLabourAttendanceMobileDTOList.get(position);
        }else{
            labourAttendanceMobileDTO = labourAttendanceMobileDTOList.get(position);
        }
        final int labourId = labourAttendanceMobileDTO.getLabourId().intValue();
        String labourCode = labourAttendanceMobileDTO.getLabourAgencyCode() + " " + String.valueOf(labourId);
        /*alertDialogBuilder = new AlertDialog.Builder(MarkLabourAttendanceActivity.this, R.style.AlertDialogBackground);
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
        alertDialog.show();*/
        postLabourAttendance(position);
    }

    public void postLabourAttendance(final int position){
        LabourAttendanceMobileDTO labourAttendanceMobileDTO;
        if(isSearchLabourOpened){
            labourAttendanceMobileDTO = searchLabourAttendanceMobileDTOList.get(position);
        }else{
            labourAttendanceMobileDTO = labourAttendanceMobileDTOList.get(position);
        }

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
                    removeLabourFromList(position);
                }else if(error!=null){
                    CommonFunctions.restClientErrorValidation(labourAttendanceTrackerEntity.getCode(), labourAttendanceTrackerEntity.getMessage(), MarkLabourAttendanceActivity.this);
                }
                rlLoader.setVisibility(View.GONE);
            }
        }, MarkLabourAttendanceActivity.this);

    }
    public void removeLabourFromList(final int position){
        if(isSearchLabourOpened){
            int labourIdInSearchList = searchLabourAttendanceMobileDTOList.get(position).getLabourId().intValue();
            for(int i=0; i<labourAttendanceMobileDTOList.size(); i++){
                if(labourIdInSearchList==labourAttendanceMobileDTOList.get(i).getLabourId().intValue()) {
                    labourAttendanceMobileDTOList.remove(i);
                    markLabourAttendanceAdapter.notifyDataSetChanged();
                    break;
                }
            }
            searchLabourAttendanceMobileDTOList.remove(position);
            searchMarkLabourAttendanceAdapter.notifyDataSetChanged();
        }else{
            labourAttendanceMobileDTOList.remove(position);
            markLabourAttendanceAdapter.notifyDataSetChanged();
        }
    }

    public void reset() {
        fetchOffset = Constants.YOUR_ORDERS_FETCH_OFFSET;
        fetchLimit = Constants.YOUR_ORDERS_FETCH_LIMIT;
        feedLoadComplete = 0;
        next_load_flag = 0;
    }

    public void resetSearchList() {
        searchFetchOffset = 0;
        searchNext_load_flag = 0;
        searchFeedLoadComplete = 0;
    }

    public void closeKeyboard() {
        imm.hideSoftInputFromWindow(etSearchLabour.getWindowToken(), 0);
    }

    public void loadLabour(){
        LabourAttendanceMobileDTOAPI labourAttendanceMobileDTOAPI = new LabourAttendanceMobileDTOAPI(0,0,fetchOffset, fetchLimit);
        RestClientImplementation.getLabourForMarkingAttendance(labourAttendanceMobileDTOAPI, new LabourAttendanceMobileDTOAPI.FlashRestClientInterface() {
            @Override
            public void onLabourAttendanceMobileDTO(LabourAttendanceMobileDTOAPI labourAttendanceMobileDTOAPI, VolleyError error) {
                if(error==null){
                    if(labourAttendanceMobileDTOAPI.getLabourAttendanceMobileDTOList()!=null && labourAttendanceMobileDTOAPI.getLabourAttendanceMobileDTOList().size()>0){
                        List<LabourAttendanceMobileDTO> tempLabourAttendanceMobileDTOList = labourAttendanceMobileDTOAPI.getLabourAttendanceMobileDTOList();
                        labourAttendanceMobileDTOList.addAll(tempLabourAttendanceMobileDTOList);
                        if (labourAttendanceMobileDTOList.size() < fetchLimit) {
                            lvLabour.removeFooterView(footerView);
                            feedLoadComplete = 1;
                        }
                        next_load_flag = 1;
                        lvLabour.removeFooterView(footerView);
                        markLabourAttendanceAdapter.notifyDataSetChanged();
                    }else if(labourAttendanceMobileDTOList.size()==0){
                        feedLoadComplete = 1;
                        try {
                            CommonFunctions.toastString("No Items", MarkLabourAttendanceActivity.this);
                        } catch (Exception e) {}
                        lvLabour.removeFooterView(footerView);
                    }
                }else if (error != null) {
                    if(labourAttendanceMobileDTOList==null || labourAttendanceMobileDTOList.size()==0) {
                        CommonFunctions.restClientErrorValidation(labourAttendanceMobileDTOAPI.getCode(), labourAttendanceMobileDTOAPI.getMessage(), MarkLabourAttendanceActivity.this);
                    }
                    lvLabour.removeFooterView(footerView);
                }
                rlLoader.setVisibility(View.GONE);
            }
        }, MarkLabourAttendanceActivity.this, null);
    }

    public void loadSearchLabour(){
        int labourId = Integer.parseInt(searchString);
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
        }, MarkLabourAttendanceActivity.this, "SEARCH_LABOUR");
    }
}
