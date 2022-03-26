package com.econnect.API;

import java.util.TreeMap;

import com.econnect.API.Exceptions.ApiException;
import com.econnect.Utilities.SettingsFile;

public class LoginService extends Service {

    final static String STORED_TOKEN_KEY = "LOGIN_SERVICE_USER_TOKEN";
    
    // Only allow instantiating from ServiceFactory
    LoginService() {}
    
    // Sets the user token, throws an exception if an error occurs
    // If file is not null, the token is stored for later
    public void login(String email, String password, SettingsFile file) {
        
        // Add parameters
        TreeMap<String, String> params = new TreeMap<>();
        params.put(ApiConstants.LOGIN_NAME, email);
        params.put(ApiConstants.LOGIN_PASSWORD, password);
        
        // Call API
        super.needsToken = false;
        JsonResult result;
        
        try {
            result = get(ApiConstants.LOGIN_PATH, params);
        }
        catch (ApiException e) {
            switch (e.getErrorCode()) {
                case ApiConstants.ERROR_USER_NOT_FOUND:
                    throw new RuntimeException("No account found for this email");
                case ApiConstants.ERROR_WRONG_PASSWORD:
                    throw new RuntimeException("Incorrect password for this email");
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
            file.putString(STORED_TOKEN_KEY, token);
        }
    }
    
    // Invalidates the user token, throws an exception if an error occurs
    public void logout(SettingsFile file) {
        try {
            // Call API to invalidate in server
            super.needsToken = true;
            get(ApiConstants.LOGOUT_PATH, null);
        }
        finally {
            // Delete local token whether or not the API call succeeded
            if (file != null) file.remove(STORED_TOKEN_KEY);
            super.deleteToken();
        }
    }

    // Attempt to login automatically. Return true if it succeeded
    public boolean autoLogin(SettingsFile file) {
        String token = file.getString(STORED_TOKEN_KEY);

        if (token == null) return false;

        super.setToken(token);
        return true;
    }
}