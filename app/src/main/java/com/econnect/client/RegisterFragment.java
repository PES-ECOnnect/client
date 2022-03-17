package com.econnect.client;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.econnect.client.databinding.FragmentRegisterBinding;
import com.google.android.material.snackbar.Snackbar;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_name = binding.editTextTextPersonName.getText().toString();
                String user_pass = binding.editTextTextPersonName.getText().toString();
                String user_email = binding.editTextTextEmailAddress.getText().toString();
                String user_adress = binding.editTextTextPostalAddress.getText().toString();

                if (!user_adress.isEmpty() && !user_email.isEmpty() && !user_name.isEmpty() && !user_pass.isEmpty()) {
                    //comunicarse con backend
                    NavHostFragment.findNavController(RegisterFragment.this)
                            .navigate(R.id.action_FirstFragment_to_SecondFragment);
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("You have to fill all the fields").setTitle("WARNING");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });

        binding.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(RegisterFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}