package com.ninja.ultron.activity;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ninja.ultron.R;

public class ReportAssetActivity extends AppCompatActivity {

    public String selectedName;
    public int selectedId;
    TextView tvName;
    TextView tvId;
    TextView tvDate;
    TextView tvTime;
    ImageButton dateSetter;
    ImageButton timeSetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_asset);
        Intent i = getIntent();
        tvDate = (TextView)findViewById(R.id.tvDate);
        tvTime = (TextView)findViewById(R.id.tvTime);
        tvName = (TextView)findViewById(R.id.tvName);
        tvId = (TextView)findViewById(R.id.tvId);
        dateSetter = (ImageButton)findViewById(R.id.setDate);
        timeSetter = (ImageButton)findViewById(R.id.setTime);
        selectedId = i.getIntExtra("AssetId",0);
        selectedName = i.getStringExtra("AssetName");
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
                DatePickerDialog datePicker = new DatePickerDialog(ReportAssetActivity.this,new DatePickerDialog.OnDateSetListener() {
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


                TimePickerDialog timePicker = new TimePickerDialog(ReportAssetActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = String.valueOf(hourOfDay) + " : " + String.valueOf(minute);
                        tvTime.setText(time);
                    }
                },hh,mm,true);
                timePicker.show();
            }
        });
    }
}
