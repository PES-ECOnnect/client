package com.econnect.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Looper;

import com.econnect.Utilities.PopupMessage;
import com.econnect.client.Products.ProductsFragment;
import com.econnect.client.Profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
        ft.replace(R.id.mainFrameLayout, selectedFragment).commit();

    }

    // Double tap to exit
    private boolean _doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        // If flag has been set, exit
        if (_doubleBackToExitPressedOnce) {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }

        _doubleBackToExitPressedOnce = true;
        PopupMessage.showToast(this, "Please click BACK again to exit");

        // Wait 2 seconds before clearing flag
        android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
        Runnable unsetFlag = () -> _doubleBackToExitPressedOnce=false;
        handler.postDelayed(unsetFlag, 2000);
    }
}