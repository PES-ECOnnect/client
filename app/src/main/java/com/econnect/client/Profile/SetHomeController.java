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

    public void getBuilding(String zipcode, String street, String num) {
        _fragment.disableInput();
        ExecutionThread.nonUI(() -> {
            try {
                HomeService homeService = ServiceFactory.getInstance().getHomeService();
                HomeService.Homes[] homes = homeService.getHomesBuilding(zipcode, street, num);
                ExecutionThread.UI(_fragment, () -> {
                    _fragment.selectHomeDialog(homes);
                    _fragment.enableSecondStep(true);
                });
            }
            catch (Exception e) {
                // Return to UI for showing errors
                ExecutionThread.UI(_fragment, ()->{
                    PopupMessage.showToast(_fragment, "There is no building with this street and number");
                    _fragment.enableSecondStep(true);
                });
            }
        });
    }

    public void setHome(String zipcode, String street, String num, String escala, String pis, String porta) {
        ExecutionThread.nonUI(() -> {
            try {
                HomeService homeService = ServiceFactory.getInstance().getHomeService();
                homeService.setHome(zipcode, street, num, escala, pis, porta);
            }
            catch (Exception e) {
                // Return to UI for showing errors
                ExecutionThread.UI(_fragment, ()->{
                    PopupMessage.warning(_fragment, _fragment.getString(R.string.city_not_exists) + e.getMessage());
                });

            }
        });
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

