package com.econnect.client.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.econnect.Utilities.CustomFragment;
import com.econnect.client.R;
import com.econnect.client.databinding.FragmentProductsBinding;
import com.econnect.client.databinding.FragmentProfileBinding;

public class ProfileFragment extends CustomFragment<FragmentProfileBinding> implements PopupMenu.OnMenuItemClickListener {
    
    private final ProfileController ctrl = new ProfileController(this);

    public ProfileFragment() {
        super(FragmentProfileBinding.class);
    }

    @Override
    protected void addListeners() {
        binding.profileMenuButton.setOnClickListener( view -> showProfileMenu(view));
    }


    void showProfileMenu(View v){
        PopupMenu popupMenu = new PopupMenu(this.getContext(), v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.profile_menu);
        popupMenu.show();

    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.profile_logout:
                ctrl.logoutButtonClick();
                break;
            default:
                break;
        }
        return true;
    }
}