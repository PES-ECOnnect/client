package com.econnect.API;

// Namespace for storing all the API constants together.
public interface ApiConstants {
    // PATHS
    public final String BASE_URL = "https://pes-econnect.herokuapp.com";
    public final String LOGIN_PATH = "/account/login";
    public final String LOGOUT_PATH = "/account/logout";
    public final String REGISTER_PATH = "/account";
    // Todo: add paths
    
    // PARAMETERS
    public final String TOKEN = "token";
    
    public final String LOGIN_NAME = "email";
    public final String LOGIN_PASSWORD = "password";

    public final String REGISTER_NAME = "name";
    public final String REGISTER_PASSWORD = "password";
    public final String REGISTER_EMAIL = "email";
    
    
    // RETURN VALUES
    public final String RET_TOKEN = "token";
    
    
    // ERRORS
    public final String ERROR_ATTR_NAME = "error";
    
    public final String ERROR_INVALID_TOKEN = "ERROR_INVALID_USER_TOKEN";
    
    public final String ERROR_USER_NOT_FOUND = "ERROR_USER_NOT_FOUND";
    public final String ERROR_WRONG_PASSWORD = "ERROR_USER_INCORRECT_PASSWORD";

    public final String ERROR_USERNAME_EXISTS = "ERROR_USERNAME_EXISTS";
    public final String ERROR_EMAIL_EXISTS = "ERROR_EMAIL_EXISTS";
}
