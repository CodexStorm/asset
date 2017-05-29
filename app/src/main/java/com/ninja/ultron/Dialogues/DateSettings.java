package com.ninja.ultron.Dialogues;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.*;
import android.widget.DatePicker;

/**
 * Created by manoj on 29-05-2017.
 */

public class DateSettings implements DatePickerDialog.OnDateSetListener {

    Context context;
    int Ryear;
    int Rmonth;
    int Rday;

    public DateSettings(Context context) {
        this.context = context;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Ryear = year;
        Rmonth = month;
        Rday = dayOfMonth;
    }

    int getRyear(){
        return Ryear;
    }

    int getRmonth(){
        return Rmonth;
    }

    int getRday(){
        return Rday;
    }
}

