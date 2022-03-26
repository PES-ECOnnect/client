package com.econnect.client.RegisterLogin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.econnect.Utilities.CustomFragment;
import com.econnect.client.R;
import com.econnect.client.databinding.FragmentRegisterBinding;

public class RegisterFragment extends CustomFragment<FragmentRegisterBinding> {

    private final RegisterController ctrl = new RegisterController(this);

    public RegisterFragment() {
        super(FragmentRegisterBinding.class);
    }

    @Override
    protected void addListeners() {
        binding.buttonRegister.setOnClickListener(ctrl.registerButton());
        binding.textRegisterToLogin.setOnClickListener(ctrl.toLoginButton());
    }


    String getUsernameText() {
        return binding.editTextTextPersonName.getText().toString();
    }

    String getPasswordText() {
        return binding.editTextTextPassword3.getText().toString();
    }

    String getEmailText() {
        return binding.editTextTextEmailAddress.getText().toString();
    }

    String getPostalAddressText() {
        return binding.editTextTextPostalAddress.getText().toString();
    }

    public void enableInput(boolean b) {
        binding.buttonRegister.setEnabled(b);
        binding.textRegisterToLogin.setEnabled(b);
    }
}