package com.econnect.client.RegisterLogin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.econnect.client.R;
import com.econnect.client.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginController ctrl = new LoginController(this);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonLogin.setOnClickListener(ctrl.loginButton());
        binding.textLoginToRegister.setOnClickListener(ctrl.toRegisterButton());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    // Called from Controller:

    void enableInput(boolean enabled) {
        binding.textLoginToRegister.setEnabled(enabled);
        binding.buttonLogin.setEnabled(enabled);
    }

    String getEmailText() {
        return binding.editTextTextEmailAddress2.getText().toString();
    }

    String getPasswordText() {
        return binding.editTextTextPassword2.getText().toString();
    }
}