package com.ninja.ultron.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ninja.ultron.R;

/**
 * Created by Prabhu Sivanandam on 22-May-17.
 */

public class InitiateTransferFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_initiate_transfer,container,false);
        return view;
    }
}
