package com.econnect.client.Profile;

import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.econnect.Utilities.FragmentContainerActivity;

public class SetHomeActivity extends FragmentContainerActivity {
    public SetHomeActivity() {
        super("Set Home");
    }

    @Override
    protected Fragment initializeFragment(Intent intent) {
        return new SetHomeFragment();
    }
}
