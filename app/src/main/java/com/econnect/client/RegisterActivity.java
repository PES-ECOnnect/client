package com.econnect.client;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentActivity;

public class RegisterActivity extends Activity {

    CtrlVistes ctrlVistes;
    public RegisterActivity(CtrlVistes ctrlVistes) {
        // Required empty public constructor
        this.ctrlVistes = ctrlVistes;
    }

    public void navigateToLogin(View view) {
        ctrlVistes.RegisterToLogin();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
