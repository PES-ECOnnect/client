package com.econnect.client;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CtrlVistes {

    private CompaniesFragment companiesFragment;
    private ForumFragment forumFragment;
    private LogInActivity logInActivity;
    private ProfileFragment profileFragment;
    private RegisterActivity registerActivity;
    private ProductsFragment productsFragment;
    private BottomNavigationView bottomNavigationView;
    private AppCompatActivity appCompatActivity;

    CtrlVistes(AppCompatActivity appCompatActivity){
        companiesFragment = new CompaniesFragment(this);
        forumFragment = new ForumFragment(this);
        logInActivity = new LogInActivity(this);
        profileFragment = new ProfileFragment(this);
        registerActivity = new RegisterActivity(this);
        productsFragment = new ProductsFragment(this);
        this.appCompatActivity = appCompatActivity;
    }

    public void RegisterToLogin(){
        appCompatActivity.setContentView(R.layout.fragment_log_in);
    }

    public void inciar() {
        appCompatActivity.setContentView(R.layout.fragment_register);
    }
}
