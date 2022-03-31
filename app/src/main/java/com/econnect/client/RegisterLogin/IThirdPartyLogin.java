package com.econnect.client.RegisterLogin;

import androidx.fragment.app.Fragment;

public interface IThirdPartyLogin {

    interface ThirdPartyLoginCallback {
        void onLogin(String email, String name, String token);
    }

    void initialize(Fragment owner, ThirdPartyLoginCallback callback);
    void buttonPressed();
    void logout();
}
