package com.econnect.client.Profile;

import android.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.econnect.API.ProfileService;
import com.econnect.Utilities.CustomFragment;
import com.econnect.Utilities.PopupMessage;
import com.econnect.client.R;

public class LoggedUserProfileFragment extends ProfileFragment {

    private final ProfileController ctrl = new ProfileController(this);
    private AlertDialog.Builder deleterBuilder;
    private AlertDialog deleter;
    private TextView passwordDelete, acceptDelete;
    private Button deleteButton, cancelButton;

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
        deleterBuilder = new AlertDialog.Builder(getContext());

        final View deleterPopupView = getLayoutInflater().inflate(R.layout.delete_account, null);

        deleteButton = deleterPopupView.findViewById(R.id.deleteAccountButton);
        cancelButton = deleterPopupView.findViewById(R.id.deleteAccountCancel);

        passwordDelete = deleterPopupView.findViewById(R.id.deleteAccountPassword);
        acceptDelete = deleterPopupView.findViewById(R.id.deleteAccountText);

        deleterBuilder.setView(deleterPopupView);
        deleter = deleterBuilder.create();
        deleter.show();

        deleteButton.setOnClickListener(view -> {
            if(acceptDelete.getText().toString().equals("I ACCEPT")) {
                ctrl.deleteAccount(passwordDelete.getText());
                deleter.dismiss();
            }
            else {
                PopupMessage.warning(this, "You didn't write I ACCEPT");
            }
        });

        cancelButton.setOnClickListener(view -> {
            deleter.dismiss();
        });
    }

}









