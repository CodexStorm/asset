package com.ninja.ultron.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ninja.ultron.R;
import com.ninja.ultron.activity.MarkLabourAttendanceActivity;
import com.ninja.ultron.activity.MultiSelectionSpinner;
import com.ninja.ultron.entity.LabourAttendanceMobileDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prabhu Sivanandam on 05-Jun-17.
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
                    Toast.makeText(getContext(),"Inflating Fragment",Toast.LENGTH_LONG).show();
                    inflatePenaltyDialog(position);
                }
                return false;
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
                }}catch (Exception e){
                    Log.d("ERROR-Adapter",e.getMessage());
                }
            }
        });

        return view;
    }

    private void inflatePenaltyDialog(int position) {
        final View dialogView=dialogInflator.inflate(R.layout.alert_add_penality,null,false);
        penalityDialog.setView(dialogView);
        TextView tvPopupLabourId=(TextView)dialogView.findViewById(R.id.tvPopupLabourId);
        TextView tvPopupLabourName=(TextView)dialogView.findViewById(R.id.tvPopupLabourName);
        TextView tvPopupLabourAgency=(TextView)dialogView.findViewById(R.id.tvPopupLabourAgency);
        EditText etStartTime=(EditText)dialogView.findViewById(R.id.etStartTime);
        EditText etEndTime=(EditText)dialogView.findViewById(R.id.etEndTime);
        final MultiSelectionSpinner spinner=(MultiSelectionSpinner) dialogView.findViewById(R.id.spinnerPenalty);
        List<String> list = new ArrayList<String>();
        list.add("Penality1");
        list.add("Penality2");
        list.add("Penality3");
        list.add("Penality4");
        list.add("Pe5");
        spinner.setItems(list);
        tvPopupLabourId.setText(labourAttendanceMobileDTOList.get(position).getLabourId()+"");
        tvPopupLabourAgency.setText(labourAttendanceMobileDTOList.get(position).getLabourAgencyCode());
        tvPopupLabourName.setText(labourAttendanceMobileDTOList.get(position).getLabourName());
        etStartTime.setText(labourAttendanceMobileDTOList.get(position).getShiftName());
        etEndTime.setText("End Time");
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


    private class ViewHolder {
        RelativeLayout rlLabour;
        TextView tvLabourId, tvLabourName;
        CheckBox cbIsSelected;
    }

}