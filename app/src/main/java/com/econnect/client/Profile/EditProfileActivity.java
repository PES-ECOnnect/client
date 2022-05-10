package com.econnect.client.Profile;

import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.econnect.Utilities.FragmentContainerActivity;

public class EditProfileActivity extends FragmentContainerActivity {

    public EditProfileActivity() {
        super("Edit profile");
    }

    @Override
    protected Fragment initializeFragment(Intent intent) {
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");
        Boolean isPrivate = intent.getBooleanExtra("isPrivate", false);
        return new EditFragment(username, email, isPrivate);
    }

}
