package com.econnect.client;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class LogInActivity extends Activity {

    CtrlVistes ctrlVistes;

    public LogInActivity(CtrlVistes ctrlVistes) {
        // Required empty public constructor
        this.ctrlVistes = ctrlVistes;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
