package com.econnect.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity {


    static String token;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnItemSelectedListener(bottomNavSelected);


        //posem token a null de primeres

        token = null;

        // Default screen is Products
        navigateToScreen(R.id.products);
    }

    private OnItemSelectedListener bottomNavSelected = item -> {
        navigateToScreen(item.getItemId());
        return true;
    };

    public void setToken(String newtoken){
        token = newtoken;
    }

    public void cerrarSession(){
        token = null;
        //navegar a login
    }

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
}