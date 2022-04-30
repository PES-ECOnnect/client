package com.econnect.API;

import com.econnect.API.Exceptions.ApiException;
import com.econnect.API.Exceptions.ProfileIsPrivateException;

import java.util.TreeMap;

public class ProfileService extends Service {

    // Only allow instantiating from ServiceFactory
    ProfileService() {}

    public static class Medal {
        public final String medalname;
        public final int idmedal;
        //public final String imageMedal;

        public Medal (int idmedal, String medalname) {
            this.idmedal = idmedal;
            this.medalname = medalname;
            //this.imageMedal = imageMedal;
        }
    }

    public static class User {
        // Important: The name of these attributes must match the ones in the returned JSON
        public final String username;
        public final Medal[] medals;
        public final int activeMedal;
        public final String home;
        public final Boolean isPrivate;
        public final String email;
        //public final String imageUser;

        public User(String username, int activeMedal, String email, String home, Medal[] medals, Boolean isPrivate) {
            this.username = username;
            this.medals = medals;
            this.activeMedal = activeMedal;
            this.home = home;
            this.email = email;
            this.isPrivate = isPrivate;
        }
    }

    public User getInfoLoggedUser() {
        JsonResult result = null;
        try {
            // Call API
            super.needsToken = true;
            result = get(ApiConstants.ACCOUNT_PATH, null);
        } catch (ApiException e) {
            // This endpoint does not throw any errors
            throw e;
        }
        // Parse result
        User user = result.getObject(ApiConstants.RET_RESULT, User.class);
        assertResultNotNull(user, result);
        return user;
    }

    public User getInfoOtherUser(int userId) {
        JsonResult result = null;
        try {
            // Call API
            super.needsToken = true;
            result = get(ApiConstants.USERS_PATH + "/" + userId, null);
        } catch (ApiException e) {
            switch (e.getErrorCode()) {
                case ApiConstants.ERROR_PRIVATE_USER:
                    throw new ProfileIsPrivateException();
                default:
                    throw e;
            }
        }
        // Parse result
        String username = result.getObject("username", String.class);
        Medal[] medals = result.getArray("medals", Medal[].class);
        assertResultNotNull(username, result);
        assertResultNotNull(medals, result);
        // TODO: get active medal from endpoint
        return new User(username, 1234, null, null, medals, null);
    }

    public void updateUsername(String text) {
        // Add parameters
        TreeMap<String, String> params = new TreeMap<>();
        params.put(ApiConstants.NEW_USERNAME, text);

        JsonResult result = null;
        try {
            // Call API
            super.needsToken = true;
            result = put(ApiConstants.ACCOUNT_USERNAME_PATH, params, null);
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
            result = put(ApiConstants.ACCOUNT_PASSWORD_PATH, params, null);
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
            result = put(ApiConstants.ACCOUNT_EMAIL_PATH, params, null);
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
            result = put(ApiConstants.ACCOUNT_MEDAL_PATH, params, null);
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
            result = put(ApiConstants.ACCOUNT_VISIBILITY_PATH, params, null);
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
        // Add parameters
        TreeMap<String, String> params = new TreeMap<>();
        params.put(ApiConstants.USER_PASSWORD, password);

        JsonResult result = null;
        try {
            // Call API
            super.needsToken = true;
            result = delete(ApiConstants.ACCOUNT_PATH, params);
        } catch (ApiException e) {
            switch (e.getErrorCode()) {
                case ApiConstants.ERROR_DELETE_USER_INCORRECT_PASSWORD:
                    throw new RuntimeException("The entered password was incorrect.");
                default:
                    throw e;
            }
        }
        // Parse result
        super.expectOkStatus(result);
    }
}
