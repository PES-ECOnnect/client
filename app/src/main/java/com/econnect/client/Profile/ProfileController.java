package com.econnect.client.Profile;


import android.content.Intent;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.econnect.API.LoginService;
import com.econnect.API.ProfileService;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;


public class ProfileController {

    private final ProfileFragment _fragment;
    private final ActivityResultLauncher<Intent> _activityLauncher;
    private  ProfileService.User u;


    ProfileController(ProfileFragment fragment) {
        this._fragment = fragment;
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

            ExecutionThread.UI(_fragment, () -> {
                //por ahora paso un null pero esto se tiene que cambiar
                _fragment.setActiveMedal(null);
                _fragment.setUsername(null);
                _fragment.setEmail(null);
                _fragment.enableInput();
            });
        }
        catch (Exception e) {
            ExecutionThread.UI(_fragment, ()->{
                PopupMessage.warning(_fragment, "Could not fetch profile:\n" + e.getMessage());
            });
        }
    }

    // Boilerplate for interfacing with the fragment

    public void logoutButtonClick() {
        // Show dialog
        PopupMessage.yesNoDialog(_fragment, "Log out", "Are you sure?", (dialog, id) -> {
            // If YES option is selected:
            ExecutionThread.nonUI(() -> {
                // Logout
                LoginService loginService = ServiceFactory.getInstance().getLoginService();
                try {
                    loginService.logout();
                    ExecutionThread.UI(_fragment, ()->{
                        _fragment.getActivity().finish();
                    });
                }
                catch (Exception e) {
                    // Return to UI for showing errors
                    ExecutionThread.UI(_fragment, ()->{
                        // Even if there has been an error, return to login anyways
                        _fragment.getActivity().finish();
                    });
                }
            });
        });
    }

    public void editButtonClick() {

        // Launch new activity PostActivity
        Intent intent = new Intent(_fragment.requireContext(), EditProfileActivity.class);
        // Pass parameters to activity
        intent.putExtra("username", u.username);
        intent.putExtra("email", u.email);
        intent.putExtra("isPrivate", u.isPrivate);

        _activityLauncher.launch(intent);
    }

    public void getInfoUser() {

        // This could take some time (and accesses the internet), run on non-UI thread
        ExecutionThread.nonUI(() -> {
            try {
                ProfileService profileService = ServiceFactory.getInstance().getProfileService();
                u = profileService.getInfoUser();

                ExecutionThread.UI(_fragment, () -> {
                    _fragment.setActiveMedal(u);
                    _fragment.setUsername(u);
                    _fragment.setEmail(u);
                    _fragment.setMedals(u);
                    _fragment.enableInput();
                });
            }
            catch (Exception e) {
                // Return to UI for showing errors
                ExecutionThread.UI(_fragment, ()->{
                    PopupMessage.warning(_fragment, "Could not get user info: " + e.getMessage());
                });
            }

        });
    }
}
