package com.econnect.API;

import java.util.TreeMap;

import com.econnect.API.Exceptions.ApiException;
import com.econnect.API.Exceptions.AccountNotFoundException;
import com.econnect.Utilities.SettingsFile;

public class LoginService extends Service {

    final static String STORED_TOKEN_KEY = "LOGIN_SERVICE_USER_TOKEN";
    private static SettingsFile settingsFile = null;
    
    // Only allow instantiating from ServiceFactory
    LoginService() {}
    
    // Sets the user token, throws an exception if an error occurs
    // If file is not null, the token is stored for later
    public void login(String email, String password, SettingsFile file) {

        // Store file for logout
        settingsFile = file;

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
                    throw new AccountNotFoundException();
                case ApiConstants.ERROR_WRONG_PASSWORD:
                    throw new RuntimeException("Incorrect password for this email");
                case ApiConstants.ERROR_BANNED:
                    throw new RuntimeException("Your account has been suspended!");
                default:
                    throw e;
            }
        }
        
        String token = result.getAttribute(ApiConstants.RET_TOKEN);
        assertResultNotNull(token, result);
        Service.setToken(token);

        if (file != null) {
            file.putString(STORED_TOKEN_KEY, token);
        }
    }
    
    // Invalidates the user token, throws an exception if an error occurs
    public void logout() {
        try {
            // Call API to invalidate in server
            super.needsToken = true;
            get(ApiConstants.LOGOUT_PATH, null);
        }
        finally {
            // Delete local token whether or not the API call succeeded. Ignore errors
            localLogout();
        }
    }

    // Logout without calling API. Ignore errors
    public void localLogout() {
        if (settingsFile != null) settingsFile.remove(STORED_TOKEN_KEY);
        try {
            Service.deleteToken();
        }
        catch (IllegalStateException e) {
            // Do nothing
        }
    }

    // Attempt to login automatically. Return true if it succeeded
    public boolean autoLogin(SettingsFile file) {

        // Store file for logout
        settingsFile = file;

        String token = file.getString(STORED_TOKEN_KEY);

        if (token == null) return false;

        try {
            Service.setToken(token);
        }
        catch (IllegalStateException e) {
            // Token already set, ignore
        }
        return true;
    }

    // Wake up the server
    public void pingServer() {
        // Call API (no parameters needed)
        final String EXPECTED_RESPONSE = "PES Econnect Root!";
        String result = null;
        try {
            result = getRaw(ApiConstants.BASE_URL, null);
        }
        catch (Exception e) {
            System.err.println("Error sending ping to server:\n" + e.getMessage());
            return;
        }

        if (result == null) {
            System.err.println("Warning: Server is not responding to ping");
            return;
        }
        if (!result.equals(EXPECTED_RESPONSE)) {
            System.err.println("Warning: Server returned invalid response to ping '" + result + "'");
            System.err.println("(expected '" + EXPECTED_RESPONSE + "')");
        }
    }
}
