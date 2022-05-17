package com.econnect.client.Profile;

import static com.econnect.API.ProfileService.User;

import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.econnect.API.ProfileService;
import com.econnect.Utilities.CustomFragment;
import com.econnect.client.R;
import com.econnect.client.databinding.FragmentProfileBinding;


public class ProfileFragment extends CustomFragment<FragmentProfileBinding> {

    protected final ProfileController _ctrl;

    public ProfileFragment() {
        super(FragmentProfileBinding.class);
        _ctrl = instantiateController();
    }

    public ProfileFragment(int userId) {
        super(FragmentProfileBinding.class);
        _ctrl = new ProfileController(this, userId);
    }

    // Override this method to change the controller
    protected ProfileController instantiateController() {
        throw new IllegalArgumentException("Override the instantiateController() method or instantiate ProfileFragment providing a userId");
    }

    @Override
    protected void addListeners() {
        // Hide floating button for non-logged user profile
        binding.profileMenuButton.setVisibility(View.GONE);
        _ctrl.getInfoUser();
    }


    void enableInput(boolean enabled) {
        // Nothing to enable
    }

    void updateUI(ProfileService.User u) {
        // Update text
        binding.idMedalText.setText(String.valueOf(u.activeMedal));
        binding.usernameText.setText(u.username);
        binding.aboutField.setText(u.about);
        if (u.email != null) {
            binding.emailText.setText(u.email);
            binding.emailText.setVisibility(View.VISIBLE);
        }
        else {
            binding.emailText.setVisibility(View.GONE);
        }

        // Set medal list
        Drawable defaultImage = ContextCompat.getDrawable(requireContext(), R.drawable.ic_medal_24);
        MedalListAdapter medals_adapter = new MedalListAdapter(this, defaultImage, u.medals);
        binding.medalsList.setAdapter(medals_adapter);
        binding.medalsList.refreshDrawableState();
    }
}