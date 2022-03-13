package com.econnect.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnItemSelectedListener(bottomNavSelected);

        // Default screen is Products
        navigateToScreen(R.id.products);
    }

    private OnItemSelectedListener bottomNavSelected = item -> {
        navigateToScreen(item.getItemId());
        return true;
    };

    private void navigateToScreen(int id) {
        Fragment selectedFragment = null;
        switch (id) {
            case R.id.products:
                selectedFragment = new ProductsFragment();
                super.setTitle(R.string.products_name);
                break;
            case R.id.companies:
                selectedFragment = new CompaniesFragment();
                super.setTitle(R.string.companies_name);
                break;
            case R.id.forum:
                selectedFragment = new ForumFragment();
                super.setTitle(R.string.forum_name);
                break;
            case R.id.profile:
                selectedFragment = new ProfileFragment();
                super.setTitle(R.string.profile_name);
                break;
            default:
                assert(false);
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //ft.replace(R.id.mainFrameLayout, selectedFragment).commit();
        ft.replace(R.id.mainFrameLayout, new InicialFragment()).commit();
    }
}