package com.econnect.client.Profile;

import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.econnect.Utilities.PopupMessage;
import com.econnect.client.R;

public class LoggedUserProfileFragment extends ProfileFragment {

    @Override
    protected void addListeners() {
        super.addListeners();
        // Show floating button for logged user profile
        binding.profileMenuButton.setVisibility(View.VISIBLE);
        binding.profileMenuButton.setOnClickListener(view -> profileMenuClicked());
    }

    @Override
    void enableInput(boolean enabled) {
        super.enableInput(enabled);
        binding.profileMenuButton.setEnabled(true);
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
