package com.econnect.client.Profile;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.econnect.API.ProfileService;
import com.econnect.Utilities.CustomFragment;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;
import com.econnect.client.R;
import com.econnect.client.databinding.FragmentProductsBinding;
import com.econnect.client.databinding.FragmentProfileBinding;

import org.w3c.dom.Text;

public class ProfileFragment extends CustomFragment<FragmentProfileBinding> implements PopupMenu.OnMenuItemClickListener {
    
    private final ProfileController ctrl = new ProfileController(this);
    private AlertDialog.Builder deleterBuilder;
    private AlertDialog deleter;
    private TextView passwordDelete, acceptDelete, username;
    private Button deleteButton, cancelButton;

    public ProfileFragment() {
        super(FragmentProfileBinding.class);
    }

    @Override
    protected void addListeners() {
        binding.profileMenuButton.setOnClickListener( view -> showProfileMenu(view));
        setDetailsUser();
    }

    void setDetailsUser() {
        View v = this.getView();
        username = v.findViewById(R.id.usernameText);
        ProfileService.User u = ctrl.getInfoUser();
        if (u != null) {
            username.setText(u.username);
        }
    }

    void enableInput() {
        binding.profileMenuButton.setEnabled(true);
    }

    void setActiveMedal() {
        //Change the active medal
    }

    void setUsername() {

    }


    void showProfileMenu(View v){
        PopupMenu popupMenu = new PopupMenu(this.getContext(), v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.profile_menu);
        popupMenu.show();

    }
    public void createReviewDialog() {
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

        @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.profile_logout:
                ctrl.logoutButtonClick();
                break;
            case R.id.profile_edit:
                ctrl.editButtonClick();
                break;
            case R.id.profile_delete_account:
                createReviewDialog();
                break;
            default:
                break;
        }
        return true;
    }
}