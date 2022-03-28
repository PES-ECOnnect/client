package com.econnect.client.ItemDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.econnect.client.R;
import com.econnect.client.databinding.ActivityRegisterBinding;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Init Activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Get parameters
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        int itemId = intent.getIntExtra("id", -1);

        if (type == null || itemId == -1) {
            throw new RuntimeException("Incorrect parameters passed to DetailsActivity");
        }

        // Initialize fragment and corresponding controller (depending on type)
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        IDetailsController ctrl;
        switch (type) {
            case "product":
                setTitle("Product details");
                ctrl = new ProductDetailsController(fragment, itemId);
                break;
            case "company":
                setTitle("Company details");
                ctrl = new CompanyDetailsController(fragment, itemId);
                break;
            default:
                throw new RuntimeException("Unrecognized details type: " + type);
        }
        fragment.setController(ctrl);

        // Display fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.detailsMainLayout, fragment).commit();
    }
}