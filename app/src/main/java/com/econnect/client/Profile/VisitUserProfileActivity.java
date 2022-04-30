package com.econnect.client.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.econnect.client.R;

public class VisitUserProfileActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        // Init Activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);

        Intent intent = getIntent();
        int userId = intent.getIntExtra("username", -1);
        if (userId == -1) {
            throw new RuntimeException("VisitUserProfileActivity requires a userId");
        }

        setTitle("User profile");

        // Enable back arrow in title bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize fragment
        ProfileFragment fragment = new ProfileFragment(userId);

        // Display fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.editProfileMainLayout, fragment).commit();
    }

    // If back arrow in title bar is pressed, finish activity
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
