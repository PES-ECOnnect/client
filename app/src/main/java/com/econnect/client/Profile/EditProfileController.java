package com.econnect.client.Profile;

import static com.econnect.Utilities.BitmapLoader.fromURL;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.econnect.API.ImageUpload.ImageService;
import com.econnect.API.ProfileService;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.FileUtils;
import com.econnect.Utilities.PopupMessage;

import java.io.File;
import java.net.URL;

public class EditProfileController {

    private final EditFragment _fragment;

    EditProfileController(EditFragment fragment) {
        this._fragment = fragment;
    }

    public void changePassword(String oldP, String newP) {

        // This could take some time (and accesses the internet), run on non-UI thread
        ExecutionThread.nonUI(() -> {
            try {
                ProfileService profileService = ServiceFactory.getInstance().getProfileService();
                profileService.updatePassword(oldP, newP);
                ExecutionThread.UI(_fragment, ()->{
                    PopupMessage.showToast(_fragment, "Password updated successfully");
                    _fragment.clearPasswordFields();
                });
            }
            catch (Exception e) {
                // Return to UI for showing errors
                ExecutionThread.UI(_fragment, ()->{
                    PopupMessage.warning(_fragment, "Could not update password: " + e.getMessage());
                });
            }
        });

    }

    public void changeIsPrivate(Boolean isPrivate) {

        // This could take some time (and accesses the internet), run on non-UI thread
        ExecutionThread.nonUI(() -> {
            try {
                ProfileService profileService = ServiceFactory.getInstance().getProfileService();
                profileService.updateAccountVisibility(isPrivate);
            }
            catch (Exception e) {
                // Return to UI for showing errors
                ExecutionThread.UI(_fragment, ()->{
                    PopupMessage.warning(_fragment, "Could not update private attribute: " + e.getMessage());
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
                _fragment.updateEmail(email);
            }
            catch (Exception e) {
                // Return to UI for showing errors
                ExecutionThread.UI(_fragment, ()->{
                    PopupMessage.warning(_fragment, "Could not update email: " + e.getMessage());
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
                _fragment.updateUsername(username);
            }
            catch (Exception e) {
                // Return to UI for showing errors
                ExecutionThread.UI(_fragment, ()->{
                    PopupMessage.warning(_fragment, "Could not update username: " + e.getMessage());
                });
            }
        });
    }

    public void changeAbout(String about) {
        // This could take some time (and accesses the internet), run non-UI thread
        ExecutionThread.nonUI(() -> {
            try {
                ProfileService profileService = ServiceFactory.getInstance().getProfileService();
                profileService.updateAbout(about);
                _fragment.updateAbout(about);
            }
            catch (Exception e) {
                // Return to UI for showing errors
                ExecutionThread.UI(_fragment, ()->{
                    PopupMessage.warning(_fragment, "Could not update username" + e.getMessage());
                });
            }
        });
    }

    public void changeProfilePicture() {
        // Get picture url
        ExecutionThread.nonUI(() ->{
            String url = getImageUrl();
            System.out.println(url);
            // Call Service
            ProfileService profileService = ServiceFactory.getInstance().getProfileService();
            profileService.updatePicture(url);
        });
    }

    private String getImageUrl() {
        Uri image = _fragment.getSelectedImageUri();
        if (image == null)
            return "";
        File tempFile;
        try {
            tempFile = FileUtils.from(_fragment.requireContext(), image);
        } catch (Exception e) {
            throw new RuntimeException("Could not convert URI to File " + e.getMessage(), e);
        }

        final ImageService service = new ImageService();
        String url = service.uploadImageToUrl(tempFile);
        if (!isValidURL(url)) {
            throw new RuntimeException("The generated URL is invalid" + url);
        }
        System.out.println(url);
        return url;
    }

    private static boolean isValidURL (String urlString) {
        try {
            URL url = new URL(urlString);
            url.toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
