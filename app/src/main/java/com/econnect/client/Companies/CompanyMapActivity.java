package com.econnect.client.Companies;

import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.econnect.Utilities.FragmentContainerActivity;

public class CompanyMapActivity extends FragmentContainerActivity {

    public CompanyMapActivity() {
        super("Nearby companies");
    }

    @Override
    protected Fragment initializeFragment(Intent intent) {
        return new CompanyMapFragment();
    }
}
