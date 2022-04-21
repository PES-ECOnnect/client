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

import com.econnect.Utilities.CustomFragment;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.client.R;
import com.econnect.client.databinding.FragmentProductsBinding;
import com.econnect.client.databinding.FragmentProfileBinding;

import org.w3c.dom.Text;

public class ProfileFragment extends CustomFragment<FragmentProfileBinding> implements PopupMenu.OnMenuItemClickListener {
    
    private final ProfileController ctrl = new ProfileController(this);
    private AlertDialog.Builder deleterBuilder;
    private AlertDialog deleter;
    private TextView passwordDelete, acceptDelete;
    private Button deleteButton, cancelButton;

    public ProfileFragment() {
        super(FragmentProfileBinding.class);
    }

    @Override
    protected void addListeners() {
        binding.profileMenuButton.setOnClickListener( view -> showProfileMenu(view));
    }

    void enableInput() {
        binding.profileMenuButton.setEnabled(true);
    }

    void setActiveMedal() {
        //Change the active medal
    }

    void setUsername() {
        //Change the username
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

        deleteButton = (Button) deleterPopupView.findViewById(R.id.deleteAccountButton);
        cancelButton = (Button) deleterPopupView.findViewById(R.id.deleteAccountCancel);

        passwordDelete = (TextView) deleterPopupView.findViewById(R.id.deleteAccountPassword);
        acceptDelete = (TextView) deleterPopupView.findViewById(R.id.deleteAccountText);

        deleterBuilder.setView(deleterPopupView);
        deleter = deleterBuilder.create();
        deleter.show();

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleter.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleter.dismiss();
            }
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
            case R.id.profile_placeholder:

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