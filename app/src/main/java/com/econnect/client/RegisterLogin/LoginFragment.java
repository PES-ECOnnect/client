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
import com.econnect.client.databinding.FragmentLoginBinding;

public class LoginFragment extends CustomFragment<FragmentLoginBinding> {

    private final LoginController ctrl = new LoginController(this);

    public LoginFragment() {
        super(FragmentLoginBinding.class);
    }

    @Override
    protected void addListeners() {
        binding.buttonLogin.setOnClickListener(ctrl.loginButton());
        binding.textLoginToRegister.setOnClickListener(ctrl.toRegisterButton());

        ctrl.attemptAutoLogin();
    }

    void enableInput(boolean enabled) {
        binding.textLoginToRegister.setEnabled(enabled);
        binding.buttonLogin.setEnabled(enabled);
    }

    String getEmailText() {
        return binding.emailTextInput.getText().toString();
    }

    String getPasswordText() {
        return binding.passwordTextInput.getText().toString();
    }
}