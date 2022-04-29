package com.econnect.client.Profile;

import static com.econnect.API.ProfileService.*;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import androidx.core.content.ContextCompat;

import com.econnect.API.ProductService;
import com.econnect.API.ProfileService;
import com.econnect.Utilities.CustomFragment;
import com.econnect.Utilities.PopupMessage;
import com.econnect.client.ItemDetails.DetailsActivity;
import com.econnect.client.R;
import com.econnect.client.databinding.FragmentProfileBinding;


public class ProfileFragment extends CustomFragment<FragmentProfileBinding> {
    
    protected final ProfileController ctrl = new ProfileController(this);
    private AlertDialog review;
    private Button yes_option, no_option;

    public ProfileFragment() {
        super(FragmentProfileBinding.class);
    }

    @Override
    protected void addListeners() {
        // Hide floating button for non-logged user profile
        binding.profileMenuButton.setVisibility(View.GONE);
        binding.medalsList.setOnItemClickListener(createActiveDialog());
        ctrl.getInfoUser();
    }

    private AdapterView.OnItemClickListener createActiveDialog() {

        return (parent, view, position, id) -> {
            AlertDialog.Builder medalBuilder = new AlertDialog.Builder(getContext());
            final View medalPopupView = getLayoutInflater().inflate(R.layout.set_active_medal, null);
            medalBuilder.setView(medalPopupView);
            review = medalBuilder.create();
            review.show();

            yes_option = medalPopupView.findViewById(R.id.yesChangeActiveMedal);
            no_option = medalPopupView.findViewById(R.id.noChangeActiveMedal);

            no_option.setOnClickListener(View -> review.dismiss());
            yes_option.setOnClickListener(View -> {
                ProfileService.User.Medal m = (ProfileService.User.Medal) parent.getItemAtPosition(position);
                ctrl.changeActiveMedal(m.idmedal);
                review.dismiss();
                ctrl.getInfoUser();
            });

        };
    }

    void enableInput(boolean enabled) {
        // Nothing to enable
    }

    void setActiveMedal(User u) {
        if (u != null) {
            int name = u.activeMedal;
            binding.idMedalText.setText(String.valueOf(name));
        }
    }

    void setEmail(User u) {
        if (u != null) {
            binding.emailText.setText(u.email);
        }
    }

    void setUsername(User u) {
        if (u != null) {
            binding.usernameText.setText(u.username);
        }
    }

    void setMedals(User u) {
        Drawable defaultImage = ContextCompat.getDrawable(requireContext(), R.drawable.ic_medal_24);
        MedalListAdapter medals_adapter = new MedalListAdapter(this, defaultImage, u.medals);
        binding.medalsList.setAdapter(medals_adapter);
        binding.medalsList.refreshDrawableState();
    }
}