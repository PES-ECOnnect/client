package com.econnect.client;

import android.view.View;

import com.econnect.API.LoginService;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;

public class ProfileController {

    private final ProfileFragment fragment;

    ProfileController(ProfileFragment fragment) {
        this.fragment = fragment;
    }

    // Boilerplate for interfacing with the fragment
    View.OnClickListener logoutButton() {
        return view -> logoutButtonClick(); }

    public void logoutButtonClick() {

        //PopupMessage.warning(fragment, "You have to fill all the fields");

        fragment.enableInput(false);
        ExecutionThread.nonUI(() -> {
            // Logout
            LoginService loginService = ServiceFactory.getInstance().getLoginService();
            try {
                loginService.logout();
                ExecutionThread.navigate(fragment, R.id.macaco);
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

}
