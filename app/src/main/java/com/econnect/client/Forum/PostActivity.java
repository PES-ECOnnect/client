package com.econnect.client.Forum;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;

import com.econnect.client.ItemDetails.CompanyDetailsController;
import com.econnect.client.ItemDetails.IDetailsController;
import com.econnect.client.ItemDetails.ProductDetailsController;
import com.econnect.client.ItemDetails.ProductDetailsFragment;
import com.econnect.client.R;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;

import com.econnect.client.R;

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
