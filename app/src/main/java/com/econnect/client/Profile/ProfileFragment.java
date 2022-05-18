package com.econnect.client.Profile;

import static com.econnect.API.ProfileService.User;
import static com.econnect.Utilities.BitmapLoader.fromURL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.econnect.API.ProfileService;
import com.econnect.Utilities.CustomFragment;
import com.econnect.client.Profile.Medals.MedalListAdapter;
import com.econnect.client.Profile.Medals.MedalUtils;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.client.R;
import com.econnect.client.databinding.FragmentProfileBinding;
import com.google.android.material.imageview.ShapeableImageView;


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
        binding.idMedalText.setText(MedalUtils.medalName(this, u.activeMedal));
        binding.medalActiveImage.setImageDrawable(MedalUtils.medalIcon(this, u.activeMedal));
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

        // set image

        Drawable userDefaultImage = ContextCompat.getDrawable(requireContext(), R.drawable.ic_profile_24);
        ImageView image = binding.userImage;
        ExecutionThread.nonUI(()->{
            Bitmap bmp = fromURL(u.pictureURL);
            ExecutionThread.UI(_ctrl._fragment, ()-> {
                // TODO: set defaultimage for users
                if (bmp == null) image.setImageDrawable(userDefaultImage);
                else image.setImageBitmap(bmp);
            });
        });

    }
}