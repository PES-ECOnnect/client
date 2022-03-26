package com.econnect.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Looper;

import com.econnect.Utilities.PopupMessage;
import com.econnect.client.Companies.CompaniesFragment;
import com.econnect.client.Forum.ForumFragment;
import com.econnect.client.Products.ProductsFragment;
import com.econnect.client.Profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView _bottomNavigationView;

    private final Fragment[] _fragments = {new ProductsFragment(), new CompaniesFragment(),
            new ForumFragment(), new ProfileFragment()};

    // Default screen is Products
    private Fragment _selectedFragment = _fragments[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _bottomNavigationView = findViewById(R.id.bottomNav);
        _bottomNavigationView.setOnItemSelectedListener(bottomNavSelected);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        // Add and hide all fragments, show only default fragment
        for (Fragment f : _fragments) {
            ft = ft.add(R.id.mainFrameLayout, f).hide(f);
        }
        ft.show(_selectedFragment).commit();
    }

    private OnItemSelectedListener bottomNavSelected = item -> {
        navigateToScreen(item.getItemId());
        return true;
    };

    private void navigateToScreen(int id) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Hide old selected fragment
        ft = ft.hide(_selectedFragment);

        switch (id) {
            case R.id.products:
                _selectedFragment = _fragments[0];
                super.setTitle(R.string.products_name);
                break;

            case R.id.companies:
                _selectedFragment = _fragments[1];
                super.setTitle(R.string.companies_name);
                break;

            case R.id.forum:
                _selectedFragment = _fragments[2];
                super.setTitle(R.string.forum_name);
                break;

            case R.id.profile:
                _selectedFragment = _fragments[3];
                super.setTitle(R.string.profile_name);
                break;

            default:
                assert(false);
        }
        // Show new selected fragment
        ft.show(_selectedFragment).commit();
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