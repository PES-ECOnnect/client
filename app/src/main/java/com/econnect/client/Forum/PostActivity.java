package com.econnect.client.Forum;


import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import com.econnect.client.R;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;



public class PostActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        // Init Activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        // Enable back arrow in title bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize fragment and corresponding controller (depending on type)
        AddPostFragment fragment = new AddPostFragment();
        AddPostController ctrl = new AddPostController(fragment);

        fragment.setController(ctrl);

        // Display fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.addpostMainLayout, fragment).commit();
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
