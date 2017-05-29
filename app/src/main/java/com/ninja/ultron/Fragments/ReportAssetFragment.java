package com.ninja.ultron.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ninja.ultron.R;

/**
 * Created by manoj on 29-05-2017.
 */

public class ReportAssetFragment extends Fragment {

    TextView tvDate;
    String Date;
    ImageButton dateSetter;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_report_asset,container,false);
        tvDate = (TextView) view.findViewById(R.id.tvDate);
        dateSetter = (ImageButton)view.findViewById(R.id.setDate);

        return view;
    }


}
