package com.econnect.client.RegisterLogin;

import android.view.View;

import com.econnect.API.Exceptions.AccountNotFoundException;
import com.econnect.API.LoginService;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;
import com.econnect.Utilities.SettingsFile;
import com.econnect.client.R;
import com.econnect.client.RegisterLogin.IThirdPartyLogin.IThirdPartyLoginCallback;

public class LoginController {

    private final LoginFragment _fragment;
    private final IThirdPartyLogin _googleLogin = new GoogleLogin();

    LoginController(LoginFragment fragment) {
        this._fragment = fragment;
    }

    // Boilerplate for interfacing with the fragment
    View.OnClickListener loginButton() { return view -> loginButtonClick(); }
    View.OnClickListener toRegisterButton() { return view -> registerButtonClick(); }
    View.OnClickListener googleLogin() { return view -> ExecutionThread.nonUI(_googleLogin::buttonPressed); }


    private void loginButtonClick() {
        // Get email and password
        String user_email = _fragment.getEmailText();
        String user_pass = _fragment.getPasswordText();

        // Local validation
        if (user_email.isEmpty() || user_pass.isEmpty()) {
            PopupMessage.warning(_fragment, "You have to fill all the fields");
            return;
        }
        _fragment.enableInput(false);

        // This could take some time (and accesses the internet), run on non-UI thread
        ExecutionThread.nonUI(() -> {
            try {
                attemptLogin(user_email, user_pass);
            }
            catch (Exception e) {
                // Return to UI for showing errors
                ExecutionThread.UI(_fragment, ()->{
                    _fragment.enableInput(true);
                    PopupMessage.warning(_fragment, "Could not login: " + e.getMessage());
                });
            }
        });
    }

    private void registerButtonClick() {
        ExecutionThread.navigate(_fragment, R.id.action_navigate_to_register);
    }

    // Called once the view has been initialized
    void attemptAutoLogin() {
        LoginService loginService = ServiceFactory.getInstance().getLoginService();
        SettingsFile file = new SettingsFile(_fragment);
        boolean success = loginService.autoLogin(file);
        if (success) {
            navigateToMainMenu();
        }
    }

    void initializeThirdPartyLogins() {
        _googleLogin.initialize(_fragment, thirdPartyloginCallback);
    }

    private final IThirdPartyLoginCallback thirdPartyloginCallback = new IThirdPartyLoginCallback() {
        @Override
        public void onLogin(String email, String name, String password) {
            // Try to login. If the user does not exist, sign up

            // This could take some time (and accesses the internet), run on non-UI thread
            ExecutionThread.nonUI(() -> {
                try {
                    attemptLogin(email, password);
                }
                catch (AccountNotFoundException e) {
                    // No account found, create account
                    SettingsFile file = new SettingsFile(_fragment);
                    ServiceFactory.getInstance().getRegisterService().register(email, password, name, file);
                    navigateToMainMenu();
                }
                catch (Exception e) {
                    // Generic error
                    ExecutionThread.UI(_fragment, ()->{
                        _fragment.enableInput(true);
                        PopupMessage.warning(_fragment, "Could not login with google: " + e.getMessage());
                    });
                }
            });
        }

        @Override
        public void printError(String error) {
            PopupMessage.showToast(_fragment, error);
        }
    };

    private void attemptLogin(String email, String password) {
        // Login and store token
        LoginService loginService = ServiceFactory.getInstance().getLoginService();
        SettingsFile file = new SettingsFile(_fragment);
        loginService.login(email, password, file);
        // Success
        navigateToMainMenu();
    }

    private void navigateToMainMenu() {
        ExecutionThread.UI(_fragment, ()->{
            _fragment.enableInput(true);
            ExecutionThread.navigate(_fragment, R.id.action_successful_login);
        });
    }

    public void pingServer() {
        ExecutionThread.nonUI(() -> {
            LoginService service = ServiceFactory.getInstance().getLoginService();
            service.pingServer();
        });
    }
}
