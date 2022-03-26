package com.econnect.client.Profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import com.econnect.API.LoginService;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;
import com.econnect.Utilities.SettingsFile;

public class ProfileController {

    private final ProfileFragment fragment;

    ProfileController(ProfileFragment fragment) {
        this.fragment = fragment;
    }

    // Boilerplate for interfacing with the fragment
    View.OnClickListener logoutButton() { return view -> logoutButtonClick(); }
    View.OnClickListener testButton() { return view -> testButtonClick(); }

    private void logoutButtonClick() {
        // Show dialog
        PopupMessage.yesNoDialog(fragment, "Log out", "Are you sure?", (dialog, id) -> {
            // If YES option is selected:
            fragment.enableInput(false);
            ExecutionThread.nonUI(() -> {
                // Logout
                LoginService loginService = ServiceFactory.getInstance().getLoginService();
                SettingsFile file = new SettingsFile(fragment);
                try {
                    loginService.logout(file);
                    ExecutionThread.UI(fragment, ()->{
                        fragment.getActivity().finish();
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
        });
    }

    private void testButtonClick() {
        PopupMessage.showToast(fragment, "hi!");
    }

}
