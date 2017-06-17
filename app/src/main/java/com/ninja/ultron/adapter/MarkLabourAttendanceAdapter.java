package com.ninja.ultron.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.ninja.ultron.R;
import com.ninja.ultron.activity.MarkLabourAttendanceActivity;
import com.ninja.ultron.activity.MultiSelectionSpinner;
import com.ninja.ultron.entity.GetPenaltyApiEntity;
import com.ninja.ultron.entity.LabourAttendanceMobileDTO;
import com.ninja.ultron.entity.LabourTimeEntity;
import com.ninja.ultron.entity.PenaltyEntity;
import com.ninja.ultron.functions.CommonFunctions;
import com.ninja.ultron.restclient.RestClientImplementation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sangameswaran on 05-Jun-17.
 */

public class MarkLabourAttendanceAdapter extends ArrayAdapter<LabourAttendanceMobileDTO> {
    static int checkCount=0;
    private Activity activity;
    private List<LabourAttendanceMobileDTO> labourAttendanceMobileDTOList = null;
    private LabourAttendanceMobileDTO labourAttendanceMobileDTO;
    private int row;
    LayoutInflater dialogInflator;
    AlertDialog.Builder penalityDialog;

    public MarkLabourAttendanceAdapter(Activity act, int row, List<LabourAttendanceMobileDTO> labourAttendanceMobileDTOList) {
        super(act, row, labourAttendanceMobileDTOList);
        this.activity = act;
        this.row = row;
        dialogInflator=activity.getLayoutInflater();
        penalityDialog=new AlertDialog.Builder(activity);
        this.labourAttendanceMobileDTOList = labourAttendanceMobileDTOList;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(row, null);
            holder = new ViewHolder();
            holder.tvLabourName = (TextView) view.findViewById(R.id.tvLabourName);
            holder.tvLabourId = (TextView) view.findViewById(R.id.tvLabourId);
            holder.cbIsSelected=(CheckBox)view.findViewById(R.id.cbIsSelected);
            holder.rlLabour = (RelativeLayout) view.findViewById(R.id.rlLabour);
            holder.cbIsSelected.setClickable(false);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.cbIsSelected.setVisibility(View.GONE);
        if ((labourAttendanceMobileDTOList == null) || ((position + 1) > labourAttendanceMobileDTOList.size()))
            return view;

        labourAttendanceMobileDTO = labourAttendanceMobileDTOList.get(position);

        if(labourAttendanceMobileDTOList.get(position).getLabourId()!=null && labourAttendanceMobileDTOList.get(position).getLabourId().intValue()!=0){
            String labourAgencyCode = labourAttendanceMobileDTOList.get(position).getLabourAgencyCode();
            holder.tvLabourId.setText("" + labourAgencyCode +" " + labourAttendanceMobileDTOList.get(position).getLabourId());
        }else{
            holder.tvLabourId.setText("");
        }

        if(labourAttendanceMobileDTOList.get(position).getLabourName()!=null && !labourAttendanceMobileDTOList.get(position).getLabourName().isEmpty()){
            holder.tvLabourName.setText("" + labourAttendanceMobileDTOList.get(position).getLabourName());
        }else {
            holder.tvLabourName.setText("");
        }

        holder.rlLabour.setTag(position);
        holder.tvLabourId.setTag(position);
        holder.tvLabourName.setTag(position);
        holder.cbIsSelected.setTag(position);
        if(labourAttendanceMobileDTOList.get(position).getAttendanceStatus()==2) {
            holder.rlLabour.setBackgroundColor(Color.parseColor("#309229"));
        }
        else if(labourAttendanceMobileDTOList.get(position).getAttendanceStatus()==1){
            holder.rlLabour.setBackgroundColor(Color.parseColor("#ff0000"));
        }
        else {
            holder.rlLabour.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        if (labourAttendanceMobileDTOList.get(position).getIsChecked()==1){
            holder.cbIsSelected.setChecked(true);
            holder.cbIsSelected.setVisibility(View.VISIBLE);
        }

        holder.rlLabour.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(labourAttendanceMobileDTOList.get(position).getIsChecked()==1){

                }
                else {
                    if (labourAttendanceMobileDTOList.get(position).getAttendanceStatus()==2){
                    ((MarkLabourAttendanceActivity)activity).rlLoader.setVisibility(View.VISIBLE);
                    GetPenaltyApiEntity entity=new GetPenaltyApiEntity();
                    RestClientImplementation.getPenaltyApi(entity, new GetPenaltyApiEntity.UltronRestClientInterface() {
                        @Override
                        public void onGetPenalties(GetPenaltyApiEntity getPenaltyApiEntity, VolleyError error) {
                            if(error==null){
                                final List<PenaltyEntity> penaltyList=new ArrayList<PenaltyEntity>();
                                penaltyList.addAll(getPenaltyApiEntity.getResponse());
                                LabourTimeEntity entity=new LabourTimeEntity();
                                entity.setLabourId(labourAttendanceMobileDTOList.get(position).getLabourId());
                                RestClientImplementation.getShiftTimeApi(entity, new LabourTimeEntity.UltronRestClientInterface() {
                                    @Override
                                    public void onGetTimeEntity(LabourTimeEntity entity, VolleyError error) {
                                        ((MarkLabourAttendanceActivity)activity).rlLoader.setVisibility(View.GONE);
                                        inflatePenaltyDialog(position,penaltyList,entity);
                                    }
                                },activity);
                            }
                            else {
                                Toast.makeText(activity,getPenaltyApiEntity.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    },activity);

                }
                else {
                        CommonFunctions.toastString("Mark Present to update",activity);
                  }
                }
                return true;
            }
        });
        holder.rlLabour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //((MarkLabourAttendanceActivity)activity).markAttendanceForLabour(position);
                //holder.rlLabour.setBackgroundColor(Color.parseColor("#90f690"));\
                try{
                if (labourAttendanceMobileDTOList.get(position).getIsChecked()==1){
                    labourAttendanceMobileDTOList.get(position).setIsChecked(0);
                    holder.cbIsSelected.setChecked(false);
                    checkCount--;
                    ((MarkLabourAttendanceActivity)activity).notifyCheckCount(checkCount);
                    holder.cbIsSelected.setVisibility(View.GONE);
                }
                else {
                    labourAttendanceMobileDTOList.get(position).setIsChecked(1);
                    holder.cbIsSelected.setChecked(true);
                    checkCount++;
                    ((MarkLabourAttendanceActivity)activity).notifyCheckCount(checkCount);
                    holder.cbIsSelected.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){
                    Log.d("ERROR-Adapter",e.getMessage());
                }
            }
        });

        return view;
    }

    private void inflatePenaltyDialog(int position, List<PenaltyEntity> penaltyList, LabourTimeEntity entity) {
        final View dialogView=dialogInflator.inflate(R.layout.alert_add_penality,null,false);
        penalityDialog.setView(dialogView);
        TextView tvPopupLabourId=(TextView)dialogView.findViewById(R.id.tvPopupLabourId);
        TextView tvPopupLabourName=(TextView)dialogView.findViewById(R.id.tvPopupLabourName);
        TextView tvPopupLabourAgency=(TextView)dialogView.findViewById(R.id.tvPopupLabourAgency);
        TextView tvStartTime=(TextView) dialogView.findViewById(R.id.tvStartTime);
        final TextView tvEndTime=(TextView) dialogView.findViewById(R.id.tvEndTime);
        ImageView ivPlusIcon=(ImageView)dialogView.findViewById(R.id.ivPlusIcon);
        ImageView ivMinusIcon=(ImageView)dialogView.findViewById(R.id.ivMinusIcon);
        final MultiSelectionSpinner spinner=(MultiSelectionSpinner) dialogView.findViewById(R.id.spinnerPenalty);
        List<String> list = new ArrayList<String>();
        for (PenaltyEntity iterator :penaltyList){
            list.add(iterator.getName());
        }
        String start=entity.getResponse().get(0).getStartTime();
        String s[]=start.split("T");
        String s1[]=s[1].split(":");
        String end=entity.getResponse().get(0).getStartTime();
        String b[]=end.split("T");
        String b1[]=b[1].split(":");
        try{
            int startint=Integer.parseInt(s1[0]);
            int endint=Integer.parseInt(b1[0]);
            tvStartTime.setText(validateAndConstructTime(startint));
            tvEndTime.setText(validateAndConstructTime(endint));
        }catch (Exception e){
            CommonFunctions.toastString("Error in parsing",activity);
            ((MarkLabourAttendanceActivity)activity).finish();
        }
        spinner.setItems(list);
        ivPlusIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String changedTime=incrementTime(tvEndTime.getText().toString());
                tvEndTime.setText(changedTime);
            }
        });
        ivMinusIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String changedTime=decrementTime(tvEndTime.getText().toString());
                tvEndTime.setText(changedTime);
            }
        });
        tvPopupLabourId.setText(labourAttendanceMobileDTOList.get(position).getLabourId()+"");
        tvPopupLabourAgency.setText(labourAttendanceMobileDTOList.get(position).getLabourAgencyCode());
         tvPopupLabourName.setText(labourAttendanceMobileDTOList.get(position).getLabourName());
        penalityDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getContext(),"Posting",Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(),spinner.getSelectedItemsAsString(),Toast.LENGTH_LONG).show();
            }
        });
        penalityDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog=penalityDialog.create();
        dialog.show();
    }

    public List<LabourAttendanceMobileDTO> getCheckedLabours(){
        List<LabourAttendanceMobileDTO> checkedLabours=new ArrayList<>();

        for(LabourAttendanceMobileDTO iterator:labourAttendanceMobileDTOList){
            if(iterator.getIsChecked()==1){
                checkedLabours.add(iterator);
                }
            }

        return  checkedLabours;
    }

    public void notifyAndRefreshUI(List<LabourAttendanceMobileDTO> checkedEntities,int attendanceFlag) {
        checkCount=0;
        ((MarkLabourAttendanceActivity)activity).notifyCheckCount(checkCount);
        for(int iterator=0;iterator<checkedEntities.size();iterator++){
            for(int innerIterator=0;innerIterator<labourAttendanceMobileDTOList.size();innerIterator++){
                if (labourAttendanceMobileDTOList.get(innerIterator).equals(checkedEntities.get(iterator))){
                    labourAttendanceMobileDTOList.get(innerIterator).setIsChecked(0);
                    labourAttendanceMobileDTOList.get(innerIterator).setAttendanceStatus(attendanceFlag);
                }
            }
        }
        notifyDataSetChanged();
    }
    public void setFilter(List<LabourAttendanceMobileDTO> newList){
        labourAttendanceMobileDTOList.clear();
        labourAttendanceMobileDTOList.addAll(newList);
        notifyDataSetChanged();
    }
    public int getAbsoluteTime(String time){
        int t=0;
        try{
            if(time.contains("am")){
                time=time.replace("am","");
                t=Integer.parseInt(time);
                if(t==12){
                    t=0;
                }
            }
            else {
                time=time.replace("pm","");
                t=Integer.parseInt(time);
                if(t==12){}
                else {
                    t+=12;
                }
            }
        }catch (Exception e){
            Log.d("NUMBER","number format exception");
        }
        return t;
    }
    public String validateAndConstructTime(int hours){
        if(hours>=12){
            if(hours==12)
                return 12+"pm";
            if(hours==24){
                return 12+"am";
            }
            if(hours==0)
                return 12+"am";
            else{
                hours-=12;
                return hours+"pm";
            }
        }
        else {
           return hours+"am";
        }
    }
    public String incrementTime(String time){
        int a=getAbsoluteTime(time);
        a++;
        return validateAndConstructTime(a);
    }
    public String decrementTime(String time){
        int a=getAbsoluteTime(time);
        a--;
        return validateAndConstructTime(a);
    }

    private class ViewHolder {
        RelativeLayout rlLabour;
        TextView tvLabourId, tvLabourName;
        CheckBox cbIsSelected;
    }

}