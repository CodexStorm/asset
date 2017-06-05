package com.ninja.ultron.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.ninja.ultron.R;
import com.ninja.ultron.activity.LabourAttendanceMainActivity;
import com.ninja.ultron.entity.LabourShiftDetailEntity;

import java.util.List;

/**
 * Created by Prabhu Sivanandam on 05-Jun-17.
 */

public class LabourShiftDetailAdapter extends ArrayAdapter<LabourShiftDetailEntity> {
    private Activity activity;
    private List<LabourShiftDetailEntity> labourShiftDetailEntityList = null;
    private LabourShiftDetailEntity labourShiftDetailEntity;
    private int row;


    public LabourShiftDetailAdapter(Activity act, int row, List<LabourShiftDetailEntity> labourShiftDetailEntityList) {
        super(act, row, labourShiftDetailEntityList);
        this.activity = act;
        this.row = row;
        this.labourShiftDetailEntityList = labourShiftDetailEntityList;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(row, null);
            holder = new ViewHolder();
            holder.tvShiftDetail = (TextView) view.findViewById(R.id.tvShiftDetail);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        if ((labourShiftDetailEntityList == null) || ((position + 1) > labourShiftDetailEntityList.size()))
            return view;

        labourShiftDetailEntity = labourShiftDetailEntityList.get(position);

        holder.tvShiftDetail.setText("" + labourShiftDetailEntityList.get(position).getShiftName());

        holder.tvShiftDetail.setTag(position);
        holder.tvShiftDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LabourAttendanceMainActivity)activity).shiftOnClick(position);
            }
        });
        return view;
    }

    private class ViewHolder {
        TextView tvShiftDetail;
    }


}
