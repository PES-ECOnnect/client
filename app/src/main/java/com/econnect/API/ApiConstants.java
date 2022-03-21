package com.econnect.API;

// Namespace for storing all the API constants together.
public interface ApiConstants {
    // PATHS
    public final String BASE_URL = "https://pes-econnect.herokuapp.com";
    public final String LOGIN_PATH = "/account/login";
    public final String LOGOUT_PATH = "/account/logout";
    // Todo: add paths
    
    // PARAMETERS
    public final String TOKEN = "token";
    
    public final String LOGIN_NAME = "email";
    public final String LOGIN_PASSWORD = "password";
    
    
    // RETURN VALUES
    public final String RET_TOKEN = "token";
    
    
    // ERRORS
    public final String ERROR_ATTR_NAME = "error";
    
    public final String ERROR_INVALID_TOKEN = "ERROR_INVALID_USER_TOKEN";
    
    public final String ERROR_USER_NOT_FOUND = "ERROR_USER_NOT_FOUND";
    public final String ERROR_WRONG_PASSWORD = "ERROR_USER_INCORRECT_PASSWORD";
    
}
