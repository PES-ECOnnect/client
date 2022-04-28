package com.econnect.API;

import android.graphics.Bitmap;

import com.econnect.API.Exceptions.ApiException;
import com.econnect.Utilities.BitmapLoader;

import java.util.TreeMap;

public class ProfileService extends Service {

    // Only allow instantiating from ServiceFactory
    ProfileService() {}

    public static class User {

        // Important: The name of these attributes must match the ones in the returned JSON
        public  String username;
        public  String activeMedal;
        public  String email;
        public  String home;
        public  String[] medals;
        //public final String imageUser;

        public User(String username, String activeMedal, String email, String home, String[] medals) {
            this.username = username;
            this.activeMedal = activeMedal;
            this.email = email;
            this.home = home;
            this.medals = medals;

        }
        public static class Medal {
            public  String nameMedal;
            //public final String imageMedal;

            public Medal(String nameMedal) {
                this.nameMedal = nameMedal;
            }
        }
    }

    public User getInfoUser() {

        JsonResult result = null;
        try {
            // Call API
            super.needsToken = true;
            result = get(ApiConstants.PRIVATE_USER_PATH, null);
        } catch (ApiException e) {
            switch (e.getErrorCode()) {
                // This endpoint does not throw any errors
                default:
                    throw e;
            }
        }
        // Parse result
        User user = result.getObject(ApiConstants.RET_RESULT, User.class);
        assertResultNotNull(user, result);
        return user;
    }

    public User updateUsername(String text) {
        // Add parameters
        TreeMap<String, String> params = new TreeMap<>();
        params.put(ApiConstants.NEW_USERNAME, text);

        JsonResult result = null;
        try {
            // Call API
            super.needsToken = true;
            result = get(ApiConstants.PUT_USERNAME, null);
        } catch (ApiException e) {
            switch (e.getErrorCode()) {
                case ApiConstants.ERROR_USERNAME_EXISTS:
                    throw new RuntimeException("This username already exists");
                default:
                    throw e;
            }
        }
        // Parse result
        User user = result.getObject(ApiConstants.RET_RESULT, User.class);
        assertResultNotNull(user, result);
        return user;
    }

    public User updatePassword(String oldP, String newP) {
        // Add parameters
        TreeMap<String, String> params = new TreeMap<>();
        params.put(ApiConstants.OLD_USER_PASSWORD, oldP);
        params.put(ApiConstants.NEW_USER_PASSWORD, newP);
        JsonResult result = null;
        try {
            // Call API
            super.needsToken = true;
            result = get(ApiConstants.PUT_PASSWORD, null);
        } catch (ApiException e) {
            switch (e.getErrorCode()) {
                case ApiConstants.ERROR_USERNAME_EXISTS:
                    throw new RuntimeException("This username already exists");
                default:
                    throw e;
            }
        }
        // Parse result
        User user = result.getObject(ApiConstants.RET_RESULT, User.class);
        assertResultNotNull(user, result);
        return user;
    }

    public User updateEmail(String text) {
        // Add parameters
        TreeMap<String, String> params = new TreeMap<>();
        params.put(ApiConstants.NEW_USER_EMAIL, text);
        JsonResult result = null;
        try {
            // Call API
            super.needsToken = true;
            result = get(ApiConstants.PUT_EMAIL, null);
        } catch (ApiException e) {
            switch (e.getErrorCode()) {
                case ApiConstants.ERROR_EMAIL_EXISTS:
                    throw new RuntimeException("This email already exists");
                default:
                    throw e;
            }
        }
        // Parse result
        User user = result.getObject(ApiConstants.RET_RESULT, User.class);
        assertResultNotNull(user, result);
        return user;
    }

    public User updateActiveMedal(String text) {
        // Add parameters
        TreeMap<String, String> params = new TreeMap<>();
        params.put(ApiConstants.NEW_USER_MEDAL, text);
        JsonResult result = null;
        try {
            // Call API
            super.needsToken = true;
            result = get(ApiConstants.PUT_MEDAL, null);
        } catch (ApiException e) {
            switch (e.getErrorCode()) {
                case ApiConstants.ERROR_INVALID_MEDAL:
                    throw new RuntimeException("This medal is incorrect");
                default:
                    throw e;
            }
        }
        // Parse result
        User user = result.getObject(ApiConstants.RET_RESULT, User.class);
        assertResultNotNull(user, result);
        return user;
    }

    public void deleteAccount(String password) {
        TreeMap<String, String> params = new TreeMap<>();
        params.put(ApiConstants.LOGIN_PASSWORD, password);
        JsonResult result = null;
        try {
            // Call API
            super.needsToken = true;
            result = delete(ApiConstants.REGISTER_PATH, params);
        } catch (ApiException e) {
            switch (e.getErrorCode()) {
                // This endpoint does not throw any errors
                default:
                    throw e;
            }
        }
        // Parse result
        String s = result.getObject(ApiConstants.RET_RESULT, String.class);
        assertResultNotNull(s, result);

    }
}
