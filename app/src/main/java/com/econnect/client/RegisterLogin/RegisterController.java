package com.econnect.client.RegisterLogin;

import android.app.AlertDialog;
import android.view.View;

import androidx.navigation.fragment.NavHostFragment;

import com.econnect.API.RegisterService;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;
import com.econnect.client.R;

public class RegisterController {
    private final RegisterFragment fragment;

    RegisterController(RegisterFragment fragment) {
        this.fragment = fragment;
    }

    // Boilerplate for interfacing with the fragment
    View.OnClickListener registerButton() { return view -> registerButtonClick(); }
    View.OnClickListener toLoginButton() { return view -> loginButtonClick(); }

    private void registerButtonClick() {
        // Get text fields
        String user_name = fragment.getUsernameText();
        String user_pass = fragment.getPasswordText();
        String user_email = fragment.getEmailText();
        String user_address = fragment.getPostalAddressText();

        // Local validation
        if (user_address.isEmpty() || user_email.isEmpty() || user_name.isEmpty() || user_pass.isEmpty()) {
            PopupMessage.warning(fragment, "You have to fill all the fields");
            return;
        }
        fragment.enableInput(false);
        // TODO: Call signup endpoint
        ExecutionThread.nonUI(() -> {
            try{
                RegisterService registerService = ServiceFactory.getInstance().getRegisterService();
                registerService.register(user_email, user_pass,user_name);
                ExecutionThread.UI(fragment, ()->{
                    fragment.enableInput(true);
                    ExecutionThread.navigate(fragment, R.id.action_successful_register);
                });

            } catch (Exception e){
                ExecutionThread.UI(fragment, ()->{
                    fragment.enableInput(true);
                    PopupMessage.warning(fragment, "There has been an error: " + e.getMessage());
                });
            }

        });

    }

    private void loginButtonClick() {
        ExecutionThread.navigate(fragment, R.id.action_navigate_to_login);
    }
}
