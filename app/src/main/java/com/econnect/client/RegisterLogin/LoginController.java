package com.econnect.client.RegisterLogin;

import android.view.View;

import com.econnect.API.LoginService;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;
import com.econnect.Utilities.SettingsFile;
import com.econnect.client.R;

public class LoginController {

    private final LoginFragment fragment;

    LoginController(LoginFragment fragment) {
        this.fragment = fragment;
    }

    // Boilerplate for interfacing with the fragment
    View.OnClickListener loginButton() { return view -> loginButtonClick(); }
    View.OnClickListener toRegisterButton() { return view -> registerButtonClick(); }


    private void loginButtonClick() {
        // Get email and password
        String user_email = fragment.getEmailText();
        String user_pass = fragment.getPasswordText();

        // Local validation
        if (user_email.isEmpty() || user_pass.isEmpty()) {
            PopupMessage.warning(fragment, "You have to fill all the fields");
            return;
        }
        fragment.enableInput(false);

        // This could take some time (and accesses the internet), run on non-UI thread
        ExecutionThread.nonUI(() -> {
            // Login and store token
            LoginService loginService = ServiceFactory.getInstance().getLoginService();
            SettingsFile file = new SettingsFile(fragment);
            try {
                loginService.login(user_email, user_pass, file);
                ExecutionThread.UI(fragment, ()->{
                    fragment.enableInput(true);
                    ExecutionThread.navigate(fragment, R.id.action_successful_login);
                });

            }
            catch (Exception e) {
                // Return to UI for showing errors
                ExecutionThread.UI(fragment, ()->{
                    fragment.enableInput(true);
                    PopupMessage.warning(fragment, "There has been an error: " + e.getMessage());
                });
            }
        });

    }

    private void registerButtonClick() {
        ExecutionThread.navigate(fragment, R.id.action_navigate_to_register);
    }

    // Called once the view has been initialized
    void attemptAutoLogin() {
        LoginService loginService = ServiceFactory.getInstance().getLoginService();
        SettingsFile file = new SettingsFile(fragment);
        boolean success = loginService.autoLogin(file);
        if (success) {
            ExecutionThread.navigate(fragment, R.id.action_successful_login);
        }
    }
}
