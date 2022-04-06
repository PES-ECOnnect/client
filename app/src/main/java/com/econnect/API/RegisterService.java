package com.econnect.API;

import com.econnect.API.Exceptions.ApiException;
import com.econnect.Utilities.SettingsFile;

import java.util.TreeMap;

public class RegisterService extends Service{

    RegisterService() {};

    // Sets the user token, throws an exception if an error occurs
    // If file is not null, the token is stored for later
    public void register(String email, String password, String name, SettingsFile file) {

        // Add parameters
        TreeMap<String, String> params = new TreeMap<>();
        params.put(ApiConstants.REGISTER_EMAIL, email);
        params.put(ApiConstants.REGISTER_PASSWORD , password);
        params.put(ApiConstants.REGISTER_NAME, name);

        // Call API
        super.needsToken = false;
        JsonResult result;

        try {
            result = post(ApiConstants.REGISTER_PATH, params, null);
        }
        catch (ApiException e) {
            switch (e.getErrorCode()) {
                case ApiConstants.ERROR_USERNAME_EXISTS:
                    throw new RuntimeException("This username has already been taken");
                case ApiConstants.ERROR_EMAIL_EXISTS:
                    throw new RuntimeException("This email already has an account");
                default:
                    throw e;
            }
        }

        String token = result.getAttribute(ApiConstants.RET_TOKEN);
        if (token == null) {
            // This should never happen, the API should always return a token or an error
            throwInvalidResponseError(result, ApiConstants.RET_TOKEN);
        }

        super.setToken(token);

        if (file != null) {
            file.putString(LoginService.STORED_TOKEN_KEY, token);
        }
    }

}
