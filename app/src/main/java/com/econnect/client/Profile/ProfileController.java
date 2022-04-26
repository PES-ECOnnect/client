package com.econnect.client.Profile;


import android.content.Intent;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.econnect.API.LoginService;
import com.econnect.API.ProductService;
import com.econnect.API.ProfileService;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;


public class ProfileController {

    private final ProfileFragment fragment;
    private final ActivityResultLauncher<Intent> _activityLauncher;


    ProfileController(ProfileFragment fragment) {
        this.fragment = fragment;
        _activityLauncher = fragment.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::launchDetailsCallback
        );
    }

    private void launchDetailsCallback(ActivityResult result) {
        // Called once the user returns from details screen
        ExecutionThread.nonUI(this::updateProfile);
    }

    private void updateProfile() {
        try {

            ExecutionThread.UI(fragment, () -> {
                //por ahora paso un null pero esto se tiene que cambiar
                fragment.setActiveMedal(null);
                fragment.setUsername(null);
                fragment.setEmail(null);
                fragment.enableInput();
            });
        }
        catch (Exception e) {
            ExecutionThread.UI(fragment, ()->{
                PopupMessage.warning(fragment, "Could not fetch profile:\n" + e.getMessage());
            });
        }
    }

    // Boilerplate for interfacing with the fragment

    public void logoutButtonClick() {
        // Show dialog
        PopupMessage.yesNoDialog(fragment, "Log out", "Are you sure?", (dialog, id) -> {
            // If YES option is selected:
            ExecutionThread.nonUI(() -> {
                // Logout
                LoginService loginService = ServiceFactory.getInstance().getLoginService();
                try {
                    loginService.logout();
                    ExecutionThread.UI(fragment, ()->{
                        fragment.getActivity().finish();
                    });
                }
                catch (Exception e) {
                    // Return to UI for showing errors
                    ExecutionThread.UI(fragment, ()->{
                        // Even if there has been an error, return to login anyways
                        fragment.getActivity().finish();
                    });
                }
            });
        });
    }

    public void editButtonClick() {
        /*
        // This could take some time (and accesses the internet), run on non-UI thread
        ExecutionThread.nonUI(() -> {
            try {
                attemptGetInfo();
            }
            catch (Exception e) {
                // Return to UI for showing errors
                ExecutionThread.UI(fragment, ()->{
                    PopupMessage.warning(fragment, "Could not get user info: " + e.getMessage());
                });
            }
        });

        // Launch new activity PostActivity
        Intent intent = new Intent(fragment.getContext(), EditProfileActivity.class);
        // Pass parameters to activity
        intent.putExtra("username", u.username);
        intent.putExtra("email", u.email);
        intent.putExtra("isPrivate", u.isPrivate);

        _activityLauncher.launch(intent);
        */
    }

    public void getInfoUser() {

        // This could take some time (and accesses the internet), run on non-UI thread
        ExecutionThread.nonUI(() -> {
            try {
                ProfileService profileService = ServiceFactory.getInstance().getProfileService();
                ProfileService.User u = profileService.getInfoUser();

                ExecutionThread.UI(fragment, () -> {
                    fragment.setActiveMedal(u);
                    fragment.setUsername(u);
                    fragment.setEmail(u);
                    fragment.enableInput();
                });
            }
            catch (Exception e) {
                // Return to UI for showing errors
                ExecutionThread.UI(fragment, ()->{
                    PopupMessage.warning(fragment, "Could not get user info: " + e.getMessage());
                });
            }

        });
    }
}
