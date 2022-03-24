package com.econnect.client;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.econnect.API.LoginService;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;
import com.econnect.client.RegisterLogin.LoginController;
import com.econnect.client.databinding.FragmentLoginBinding;
import com.econnect.client.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    
    private FragmentProfileBinding binding;
    private ProfileController ctrl = new ProfileController(this);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //binding.buttonLogout.setOnClickListener(ctrl.logoutButton());
        Button buttonlogout = getView().findViewById(R.id.button_logout);
        buttonlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctrl.logoutButtonClick();
            }
        });
    }

    void enableInput(boolean enabled) {
        binding.buttonLogout.setEnabled(enabled);
    }
}