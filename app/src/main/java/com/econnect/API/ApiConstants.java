package com.econnect.API;

// Namespace for storing all the API constants together.
public interface ApiConstants {
    // PATHS
    public final String BASE_URL = "https://pes-econnect.herokuapp.com";
    public final String LOGIN_PATH = "/account/login";
    public final String LOGOUT_PATH = "/account/logout";
    public final String REGISTER_PATH = "/account";
    public final String TYPES_PATH = "/products/types";
    public final String PRODUCTS_PATH = "/products";
    public final String COMPANIES_PATH = "/companies";
    public final String COMPANY_QUESTIONS_PATH = "/companies/questions";
    
    // PARAMETERS
    // General
    public final String TOKEN = "token";

    // Login
    public final String LOGIN_NAME = "email";
    public final String LOGIN_PASSWORD = "password";

    // Register
    public final String REGISTER_NAME = "username";
    public final String REGISTER_PASSWORD = "password";
    public final String REGISTER_EMAIL = "email";

    // Get/create products
    public final String PRODUCT_TYPE = "type";

    // Product
    public final String PRODUCT_ID = "productId";

    // Review
    public final String REVIEW = "review";

    // Question
    public final String ANSWER = "answer";
    public final String QUESTION_INDEX = "questionIndex";
    public final String CHOSEN_OPTION = "chosenOption";
    
    // RETURN VALUES
    public final String RET_TOKEN = "token";
    public final String RET_STATUS = "status";
    public final String STATUS_OK = "success";
    public final String RET_ERROR = "error";
    public final String RET_RESULT = "result";
    
    
    // ERRORS
    // General
    public final String ERROR_INVALID_TOKEN = "ERROR_INVALID_USER_TOKEN";

    // Login
    public final String ERROR_USER_NOT_FOUND = "ERROR_USER_NOT_FOUND";
    public final String ERROR_WRONG_PASSWORD = "ERROR_USER_INCORRECT_PASSWORD";

    // Sign up
    public final String ERROR_USERNAME_EXISTS = "ERROR_USERNAME_EXISTS";
    public final String ERROR_EMAIL_EXISTS = "ERROR_EMAIL_EXISTS";

    // Get products
    public final String ERROR_TYPE_NOT_EXISTS = "ERROR_TYPE_NOT_EXISTS";
    public final String ERROR_PRODUCT_NOT_EXISTS = "ERROR_PRODUCT_NOT_EXISTS";
}
