package com.econnect.client.Profile;

import static com.econnect.API.ProfileService.*;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;

import androidx.core.content.ContextCompat;

import com.econnect.Utilities.CustomFragment;
import com.econnect.Utilities.PopupMessage;
import com.econnect.client.R;
import com.econnect.client.databinding.FragmentProfileBinding;


public class ProfileFragment extends CustomFragment<FragmentProfileBinding> {
    
    private final ProfileController ctrl = new ProfileController(this);

    public ProfileFragment() {
        super(FragmentProfileBinding.class);
    }

    @Override
    protected void addListeners() {
        binding.profileMenuButton.setOnClickListener(view -> profileMenuClicked());
        ctrl.getInfoUser();
    }

    void enableInput() {
        binding.profileMenuButton.setEnabled(true);
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


    void profileMenuClicked(){
        PopupMessage.showPopupMenu(R.menu.profile_menu, binding.profileMenuButton, menuItem -> {
            // Called when an item is selected
            switch (menuItem.getItemId()){
                case R.id.profile_logout:
                    ctrl.logoutButtonClick();
                    break;
                case R.id.profile_edit:
                    ctrl.editButtonClick();
                    break;
                case R.id.profile_delete_account:
                    createDeleteAccountDialog();
                    break;
                default:
                    break;
            }
            return true;
        });
    }

    public void createDeleteAccountDialog() {
        AlertDialog.Builder deleterBuilder = new AlertDialog.Builder(requireContext());

        final View deleterPopupView = getLayoutInflater().inflate(R.layout.delete_account, null);

        Button deleteButton = deleterPopupView.findViewById(R.id.deleteAccountButton);
        Button cancelButton = deleterPopupView.findViewById(R.id.deleteAccountCancel);

        deleterBuilder.setView(deleterPopupView);
        AlertDialog deleter = deleterBuilder.create();
        deleter.show();

        deleteButton.setOnClickListener(view -> {
            deleter.dismiss();
        });

        cancelButton.setOnClickListener(view -> {
            deleter.dismiss();
        });
    }
}