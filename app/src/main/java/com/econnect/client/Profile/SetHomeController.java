package com.econnect.client.Profile;

import com.econnect.API.HomeService;
import com.econnect.API.ProfileService;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;
import com.econnect.client.R;

import java.util.Arrays;

public class SetHomeController {
    private final SetHomeFragment _fragment;

    SetHomeController(SetHomeFragment fragment) {
        this._fragment = fragment;
    }

    void zipcodeChanged(String zipcode) {
        final int STANDARD_ZIPCODE_LENGTH = 5;
        if (zipcode.length() == STANDARD_ZIPCODE_LENGTH) {
            _fragment.disableInput();
            getStreets(zipcode);
        }
        else {
            // The user removed the zipcode or still has not finished
            _fragment.enableSecondStep(false);
        }
    }

    private void getStreets(String zipcode) {
        ExecutionThread.nonUI(() -> {
            try {
                HomeService homeService = ServiceFactory.getInstance().getHomeService();
                String[] streets = homeService.getCity(zipcode);
                ExecutionThread.UI(_fragment, () -> {
                    _fragment.setStreetsList(streets);
                    _fragment.enableSecondStep(true);
                });
            }
            catch (Exception e) {
                // Return to UI for showing errors
                ExecutionThread.UI(_fragment, ()->{
                    PopupMessage.warning(_fragment, "Could not get city info: " + e.getMessage());
                    _fragment.enableFirstStep(true);
                });
            }
        });
    }
}
