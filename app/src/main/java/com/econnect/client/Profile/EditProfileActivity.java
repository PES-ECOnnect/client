package com.econnect.client.Profile;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.econnect.client.R;

public class EditProfileActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        // Init Activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);

        // Enable back arrow in title bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize fragment and corresponding controller (depending on type)
        EditFragment fragment = new EditFragment();
        EditProfileController ctrl = new EditProfileController(fragment);

        fragment.setController(ctrl);

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
