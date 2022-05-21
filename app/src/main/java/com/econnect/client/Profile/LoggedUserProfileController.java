package com.econnect.client.Profile;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;

import com.econnect.API.LoginService;
import com.econnect.API.ProfileService;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;
import com.econnect.Utilities.SettingsFile;
import com.econnect.client.RegisterLogin.LoginController;
import com.econnect.client.RegisterLogin.RegisterActivity;

import java.util.Locale;


public class LoggedUserProfileController extends ProfileController {

    private final ActivityResultLauncher<Intent> _activityLauncher;
    // Store user to initialize edit fields without calling API again
    private ProfileService.User u;


    LoggedUserProfileController(ProfileFragment fragment) {
        super(fragment, -1);
        _activityLauncher = fragment.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::launchDetailsCallback
        );
    }

    @Override
    protected ProfileService.User getUser() {
        ProfileService profileService = ServiceFactory.getInstance().getProfileService();
        u = profileService.getInfoLoggedUser();
        return u;
    }

    private void launchDetailsCallback(ActivityResult result) {
        // Called once the user returns from edit screen
        // No need to refresh data, it's reloaded every time that the fragment is selected
    }

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
                        _fragment.requireActivity().finish();
                    });
                }
                catch (Exception e) {
                    // Return to UI for showing errors
                    ExecutionThread.UI(_fragment, ()->{
                        // Even if there has been an error, return to login anyways
                        _fragment.requireActivity().finish();
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
        intent.putExtra("about", u.about);
        intent.putExtra("isPrivate", u.isPrivate);
        intent.putExtra("pictureURL", u.pictureURL);

        _activityLauncher.launch(intent);
    }

    public void changeActiveMedal(int id) {

        // This could take some time (and accesses the internet), run on non-UI thread
        ExecutionThread.nonUI(() -> {
            try {
                ProfileService profileService = ServiceFactory.getInstance().getProfileService();
                profileService.updateActiveMedal(id);
            }
            catch (Exception e) {
                // Return to UI for showing errors
                ExecutionThread.UI(_fragment, ()->{
                    PopupMessage.warning(_fragment, "Could not update the active medal: " + e.getMessage());
                });
            }
        });
    }

    public void deleteAccount() {
        ExecutionThread.nonUI(() -> {
            try {
                // Delete account
                ProfileService profileService = ServiceFactory.getInstance().getProfileService();
                profileService.deleteAccount();
                // Delete stored token
                LoginService loginService = ServiceFactory.getInstance().getLoginService();
                loginService.localLogout();

                ExecutionThread.UI(_fragment, ()->{
                    PopupMessage.showToast(_fragment, "Account deleted successfully.\nReturning to login...");
                    _fragment.requireActivity().finish();
                });
            }
            catch (Exception e) {
                // Return to UI for showing errors
                ExecutionThread.UI(_fragment, ()->{
                    PopupMessage.warning(_fragment, "Could not delete account: " + e.getMessage());
                });
            }
        });
    }

    public void changeLanguage(String language) {
        final SettingsFile file = new SettingsFile(_fragment);

        // ISO 639 language codes
        final String ENGLISH_CODE = "en";
        final String SPANISH_CODE = "es";
        final String CATALAN_CODE = "ca";

        switch (language) {
            case "english":
                file.putString(LoginController.CUSTOM_LANGUAGE_KEY, ENGLISH_CODE);
                break;
            case "spanish":
                file.putString(LoginController.CUSTOM_LANGUAGE_KEY, SPANISH_CODE);
                break;
            case "catalan":
                file.putString(LoginController.CUSTOM_LANGUAGE_KEY, CATALAN_CODE);
                break;
            default:
                throw new RuntimeException("Unrecognized language");
        }

        // Restart app
        Context context = _fragment.requireContext();
        // RegisterActivity is the startup activity of the app
        Intent mStartActivity = new Intent(context, RegisterActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(context, mPendingIntentId,
                mStartActivity, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager mgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);
    }
}
