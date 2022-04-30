package com.econnect.API;

import com.econnect.API.Exceptions.ApiException;

import java.util.ArrayList;
import java.util.TreeMap;

public class ProfileService extends Service {

    // Only allow instantiating from ServiceFactory
    ProfileService() {}

    public static class User {

        // Important: The name of these attributes must match the ones in the returned JSON
        public  String username;
        public  int activeMedal;
        public  String email;
        public  String home;
        public Medal[] medals;
        public Boolean isPrivate;
        //public final String imageUser;

        public User(String username, int activeMedal, String email, String home, Medal[] medals, Boolean isPrivate) {
            this.username = username;
            this.activeMedal = activeMedal;
            this.email = email;
            this.home = home;
            this.isPrivate = isPrivate;
            this.medals = medals;
        }

        public static class Medal {
            public  String medalname;
            public int idmedal;
            //public final String imageMedal;

            public Medal (int idmedal, String medalname) {
                this.idmedal = idmedal;
                this.medalname = medalname;
                //this.imageMedal = imageMedal;
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

    public void updateUsername(String text) {
        // Add parameters
        TreeMap<String, String> params = new TreeMap<>();
        params.put(ApiConstants.NEW_USERNAME, text);

        JsonResult result = null;
        try {
            // Call API
            super.needsToken = true;
            result = put(ApiConstants.PUT_USERNAME, params, null);
        } catch (ApiException e) {
            switch (e.getErrorCode()) {
                case ApiConstants.ERROR_USERNAME_EXISTS:
                    throw new RuntimeException("This username already exists");
                default:
                    throw e;
            }
        }
        // Parse result
        super.expectOkStatus(result);
    }

    public void updatePassword(String oldP, String newP) {
        // Add parameters
        TreeMap<String, String> params = new TreeMap<>();
        params.put(ApiConstants.OLD_USER_PASSWORD, oldP);
        params.put(ApiConstants.NEW_USER_PASSWORD, newP);
        JsonResult result = null;
        try {
            // Call API
            super.needsToken = true;
            result = put(ApiConstants.PUT_PASSWORD, params, null);
        } catch (ApiException e) {
            switch (e.getErrorCode()) {
                case ApiConstants.ERROR_USERNAME_EXISTS:
                    throw new RuntimeException("This username already exists");
                default:
                    throw e;
            }
        }
        // Parse result
        super.expectOkStatus(result);
    }

    public void updateEmail(String text) {
        // Add parameters
        TreeMap<String, String> params = new TreeMap<>();
        params.put(ApiConstants.NEW_USER_EMAIL, text);
        JsonResult result = null;
        try {
            // Call API
            super.needsToken = true;
            result = put(ApiConstants.PUT_EMAIL, params, null);
        } catch (ApiException e) {
            switch (e.getErrorCode()) {
                case ApiConstants.ERROR_EMAIL_EXISTS:
                    throw new RuntimeException("This email already exists");
                default:
                    throw e;
            }
        }
        // Parse result
        super.expectOkStatus(result);
    }

    public void updateActiveMedal(int id) {
        // Add parameters
        TreeMap<String, String> params = new TreeMap<>();
        params.put(ApiConstants.NEW_USER_MEDAL, String.valueOf(id));
        JsonResult result = null;
        try {
            // Call API
            super.needsToken = true;
            result = put(ApiConstants.PUT_MEDAL, params, null);
        } catch (ApiException e) {
            switch (e.getErrorCode()) {
                case ApiConstants.ERROR_INVALID_MEDAL:
                    throw new RuntimeException("This medal is incorrect");
                default:
                    throw e;
            }
        }
        // Parse result
        super.expectOkStatus(result);
    }

    public void updatePrivate(Boolean isPrivate) {
        // Add parameters
        TreeMap<String, String> params = new TreeMap<>();
        params.put(ApiConstants.IS_PRIVATE_USER, isPrivate.toString());

        JsonResult result = null;
        try {
            // Call API
            super.needsToken = true;
            result = put(ApiConstants.PUT_IS_PRIVATE, params, null);
        } catch (ApiException e) {
            switch (e.getErrorCode()) {
                default:
                    throw e;
            }
        }
        // Parse result
        super.expectOkStatus(result);
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
        super.expectOkStatus(result);

    }
}
