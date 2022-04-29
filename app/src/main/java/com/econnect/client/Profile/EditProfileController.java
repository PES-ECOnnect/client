package com.econnect.client.Profile;

import com.econnect.API.ProfileService;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;

public class EditProfileController {

    private final EditFragment fragment;

    EditProfileController(EditFragment fragment) {
        this.fragment = fragment;
    }

    public void changePassword(String oldP, String newP) {

        // This could take some time (and accesses the internet), run on non-UI thread
        ExecutionThread.nonUI(() -> {
            try {
                ProfileService profileService = ServiceFactory.getInstance().getProfileService();
                profileService.updatePassword(oldP, newP);
            }
            catch (Exception e) {
                // Return to UI for showing errors
                ExecutionThread.UI(fragment, ()->{
                    PopupMessage.warning(fragment, "Could not update password: " + e.getMessage());
                });
            }
        });

    }

    public void changeIsPrivate(Boolean isPrivate) {

        // This could take some time (and accesses the internet), run on non-UI thread
        ExecutionThread.nonUI(() -> {
            try {
                ProfileService profileService = ServiceFactory.getInstance().getProfileService();
                profileService.updatePrivate(isPrivate);
            }
            catch (Exception e) {
                // Return to UI for showing errors
                ExecutionThread.UI(fragment, ()->{
                    PopupMessage.warning(fragment, "Could not update private attribute: " + e.getMessage());
                });
            }
        });

    }

    public void changeEmail(String email) {

        // This could take some time (and accesses the internet), run on non-UI thread
        ExecutionThread.nonUI(() -> {
            try {
                ProfileService profileService = ServiceFactory.getInstance().getProfileService();
                profileService.updateEmail(email);
            }
            catch (Exception e) {
                // Return to UI for showing errors
                ExecutionThread.UI(fragment, ()->{
                    PopupMessage.warning(fragment, "Could not update email: " + e.getMessage());
                });
            }
        });
    }

    public void changeUsername(String username) {

        // This could take some time (and accesses the internet), run on non-UI thread
        ExecutionThread.nonUI(() -> {
            try {
                ProfileService profileService = ServiceFactory.getInstance().getProfileService();
                profileService.updateUsername(username);
            }
            catch (Exception e) {
                // Return to UI for showing errors
                ExecutionThread.UI(fragment, ()->{
                    PopupMessage.warning(fragment, "Could not update username: " + e.getMessage());
                });
            }
        });
    }
}
