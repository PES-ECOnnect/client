package com.econnect.client;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.econnect.API.LoginService;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.client.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String user_pass = binding.editTextTextPassword2.getText().toString();
                String user_email = binding.editTextTextEmailAddress2.getText().toString();


                if (user_email.isEmpty() || user_pass.isEmpty()) {
                    showWarning("You have to fill all the fields");
                    return;
                }

                ExecutionThread.nonUI(() -> {
                    LoginService loginService = ServiceFactory.getInstance().getLoginService();
                    try {
                        loginService.login(user_email, user_pass);
                        ExecutionThread.UI(getActivity(), ()->{
                            NavHostFragment.findNavController(LoginFragment.this)
                                    .navigate(R.id.action_SecondFragment_to_mainActivity);
                        });
                    }
                    catch (Exception e) {
                        ExecutionThread.UI(getActivity(), ()->{
                            showWarning("There has been an error: " + e.getMessage());
                        });
                    }
                });
            }
        });

        binding.textLoginToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void showWarning(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(text).setTitle("Warning");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}