package com.ninja.ultron.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ninja.ultron.R;
import com.ninja.ultron.activity.ReportedLabourActivity;
import com.ninja.ultron.entity.LabourAttendanceMobileDTO;

import java.util.List;

/**
 * Created by Prabhu Sivanandam on 05-Jun-17.
 */

public class ReportedLabourAdapter  extends ArrayAdapter<LabourAttendanceMobileDTO> {
    private Activity activity;
    private List<LabourAttendanceMobileDTO> labourAttendanceMobileDTOList = null;
    private LabourAttendanceMobileDTO labourAttendanceMobileDTO;
    private int row;


    public ReportedLabourAdapter(Activity act, int row, List<LabourAttendanceMobileDTO> labourAttendanceMobileDTOList) {
        super(act, row, labourAttendanceMobileDTOList);
        this.activity = act;
        this.row = row;
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
            holder.llLabour = (LinearLayout) view.findViewById(R.id.llLabour);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

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

        holder.llLabour.setTag(position);
        holder.tvLabourId.setTag(position);
        holder.tvLabourName.setTag(position);

        holder.llLabour.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ((ReportedLabourActivity)activity).deleteReportedLabour(position);
                return false;
            }
        });
        return view;
    }

    private class ViewHolder {
        LinearLayout llLabour;
        TextView tvLabourId, tvLabourName;
    }

}

