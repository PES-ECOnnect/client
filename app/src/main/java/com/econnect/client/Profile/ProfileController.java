package com.econnect.client.Profile;


import com.econnect.API.ProfileService;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;


public class ProfileController {

    protected final ProfileFragment _fragment;
    private final int _userId;

    ProfileController(ProfileFragment fragment, int userId) {
        this._fragment = fragment;
        _userId = userId;
    }

    void getInfoUser() {
        // This could take some time (and accesses the internet), run on non-UI thread
        ExecutionThread.nonUI(() -> {
            try {
                // Call API
                ProfileService.User u = getUser();
                ExecutionThread.UI(_fragment, () -> {
                    _fragment.updateUI(u);
                    _fragment.enableInput(true);
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

    // Override this method to change which user to show data from
    protected ProfileService.User getUser() {
        ProfileService service = ServiceFactory.getInstance().getProfileService();
        return service.getInfoOtherUser(_userId);
    }
}
