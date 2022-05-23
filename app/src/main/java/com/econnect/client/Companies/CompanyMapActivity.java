package com.econnect.client.Companies;

import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.econnect.Utilities.FragmentContainerActivity;
import com.econnect.Utilities.Translate;
import com.econnect.client.R;

public class CompanyMapActivity extends FragmentContainerActivity {

    public CompanyMapActivity() {
        super(Translate.id(R.string.nearby_companies));
    }

    @Override
    protected Fragment initializeFragment(Intent intent) {
        return new CompanyMapFragment();
    }
}
