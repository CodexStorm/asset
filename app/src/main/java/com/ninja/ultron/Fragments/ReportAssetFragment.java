package com.ninja.ultron.Fragments;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ninja.ultron.R;

/**
 * Created by manoj on 29-05-2017.
 */

public class ReportAssetFragment extends Fragment {


    public String selectedName;
    public int selectedId;
    TextView tvName;
    TextView tvId;
    TextView tvDate;
    TextView tvTime;
    ImageButton dateSetter;
    ImageButton timeSetter;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_report_asset,container,false);
        tvDate = (TextView) view.findViewById(R.id.tvDate);
        tvTime = (TextView) view.findViewById(R.id.tvTime);
        tvName = (TextView)view.findViewById(R.id.tvName);
        tvId = (TextView)view.findViewById(R.id.tvId);
        dateSetter = (ImageButton)view.findViewById(R.id.setDate);
        timeSetter = (ImageButton)view.findViewById(R.id.setTime);

        tvId.setText(selectedId+"");
        tvName.setText(selectedName);

        dateSetter.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date =String.valueOf(year) +"/"+String.valueOf(monthOfYear)
                                +"/"+String.valueOf(dayOfMonth);
                        tvDate.setText(date);

                    }
                }, yy, mm, dd);

                datePicker.show();
            }
        });

        timeSetter.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int hh = calendar.get(Calendar.HOUR_OF_DAY);
                int mm = calendar.get(Calendar.MINUTE);


                TimePickerDialog timePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                      String time = String.valueOf(hourOfDay) + " : " + String.valueOf(minute);
                        tvTime.setText(time);
                    }
                },hh,mm,true);
                timePicker.show();
            }
        });

        return view;
    }


}
