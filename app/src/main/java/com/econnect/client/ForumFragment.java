package com.econnect.client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ForumFragment extends Fragment {

    CtrlVistes ctrlVistes;
    public ForumFragment(CtrlVistes ctrlVistes) {
        // Required empty public constructor
        this.ctrlVistes = ctrlVistes;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forum, container, false);
    }
}