package com.econnect.client.Profile;

import com.econnect.API.HomeService;
import com.econnect.API.ProfileService;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;
import com.econnect.client.R;

public class SetHomeController {
    private final SetHomeFragment _fragment;

    SetHomeController(SetHomeFragment fragment) {
        this._fragment = fragment;
    }

    public void getStreets(String zipcode) {

        ExecutionThread.nonUI(() -> {
            try {
                HomeService homeService = ServiceFactory.getInstance().getHomeService();
                _fragment.setStreetsList(homeService.getCity(zipcode));
                ExecutionThread.UI(_fragment, () -> {
                    _fragment.createStreetDialog();
                });
            }
            catch (Exception e) {
                // Return to UI for showing errors
                ExecutionThread.UI(_fragment, ()->{
                    PopupMessage.warning(_fragment, _fragment.getString(R.string.city_not_exists) + e.getMessage());
                });

            }
        });
    }

    public void getBuilding(String zipcode, String street, String num) {
        ExecutionThread.nonUI(() -> {
            try {
                HomeService homeService = ServiceFactory.getInstance().getHomeService();
                HomeService.Homes[] h = homeService.getHomesBuilding(zipcode, street, num);
                ExecutionThread.UI(_fragment, () -> {
                    _fragment.selectHomeDialog(h);
                });
            }
            catch (Exception e) {
                // Return to UI for showing errors
                ExecutionThread.UI(_fragment, ()->{
                    PopupMessage.warning(_fragment, _fragment.getString(R.string.city_not_exists) + e.getMessage());
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
}
