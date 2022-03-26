package com.econnect.client.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.econnect.Utilities.CustomFragment;
import com.econnect.client.R;
import com.econnect.client.databinding.FragmentProductsBinding;
import com.econnect.client.databinding.FragmentProfileBinding;

public class ProfileFragment extends CustomFragment<FragmentProfileBinding> {
    
    private final ProfileController ctrl = new ProfileController(this);

    public ProfileFragment() {
        super(FragmentProfileBinding.class);
    }

    @Override
    protected void addListeners() {
        binding.buttonLogout.setOnClickListener(ctrl.logoutButton());
        binding.testButton.setOnClickListener(ctrl.testButton());
    }

    void enableInput(boolean enabled) {
        binding.buttonLogout.setEnabled(enabled);
        binding.testButton.setEnabled(false);
    }
}