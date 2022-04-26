package com.econnect.API;

// Namespace for storing all the API constants together.
public interface ApiConstants {
    // PATHS
    String BASE_URL_DEV = "http://10.0.2.2:5000";
    String BASE_URL_DEPLOY = "https://pes-econnect.herokuapp.com";
    String BASE_URL = BASE_URL_DEPLOY;

    String LOGIN_PATH = "/account/login";
    String LOGOUT_PATH = "/account/logout";
    String REGISTER_PATH = "/account";
    String TYPES_PATH = "/products/types";
    String PRODUCTS_PATH = "/products";
    String COMPANIES_PATH = "/companies";
    String COMPANY_QUESTIONS_PATH = "/companies/questions";
    String POSTS_PATH = "/posts";
    String TAGS_PATH = "/posts/tags";
    String POST_LIKE_PATH = "/posts/%d/like";
    String PRIVATE_USER_PATH = "/account";
    String PUT_USERNAME = "/account/username";
    String PUT_EMAIL = "/account/email";
    String PUT_PASSWORD = "/account/password";
    String PUT_MEDAL = "/account/medal";
    String PUT_IS_PRIVATE = "/account/visibility";


    // PARAMETERS
    // General
    String TOKEN = "token";

    // Login
    String LOGIN_NAME = "email";
    String LOGIN_PASSWORD = "password";

    // Register
    String REGISTER_NAME = "username";
    String REGISTER_PASSWORD = "password";
    String REGISTER_EMAIL = "email";

    // Get/create products
    String PRODUCT_TYPE = "type";

    // Product
    String PRODUCT_ID = "productId";

    // Review
    String REVIEW = "review";

    // Question
    String ANSWER = "answer";
    String QUESTION_INDEX = "questionIndex";
    String CHOSEN_OPTION = "chosenOption";

    // Get/like/delete posts
    String POST_AMOUNT = "n";
    String POST_TAG = "tag";
    String POST_IS_LIKE = "isLike";
    String POST_REMOVE = "remove";
    String POST_TEXT = "text";
    String POST_IMAGE = "image";

    //PUT attributes User
    String NEW_USER_MEDAL = "medalId";
    String NEW_USERNAME = "newUsername";
    String NEW_USER_EMAIL = "newEmail";
    String NEW_USER_PASSWORD = "newPassword";
    String OLD_USER_PASSWORD = "oldPassword";
    String IS_PRIVATE_USER = "isPrivate";


    // RETURN VALUES
    String RET_TOKEN = "token";
    String RET_STATUS = "status";
    String STATUS_OK = "success";
    String RET_ERROR = "error";
    String RET_RESULT = "result";
    
    
    // ERRORS
    // General
    String ERROR_INVALID_TOKEN = "ERROR_INVALID_TOKEN";

    // Login
    String ERROR_USER_NOT_FOUND = "ERROR_USER_NOT_FOUND";
    String ERROR_WRONG_PASSWORD = "ERROR_USER_INCORRECT_PASSWORD";

    // Sign up
    String ERROR_USERNAME_EXISTS = "ERROR_USER_USERNAME_EXISTS";
    String ERROR_EMAIL_EXISTS = "ERROR_USER_EMAIL_EXISTS";

    // Get products/companies
    String ERROR_TYPE_NOT_EXISTS = "ERROR_TYPE_NOT_EXISTS";
    String ERROR_PRODUCT_NOT_EXISTS = "ERROR_INCORRECT_ID_REVIEWABLE";
    String ERROR_COMPANY_NOT_EXISTS = "ERROR_INCORRECT_ID_REVIEWABLE";

    // Delete posts
    String ERROR_POST_NOT_EXISTS = "ERROR_POST_NOT_EXISTS";
    String ERROR_USER_NOT_POST_OWNER = "ERROR_USER_NOT_POST_OWNER";

    //Post posts
    String ERROR_INCORRECT_INSERTION = "ERROR_INCORRECT_INSERTION";

    //PUT attributes user
    String ERROR_INVALID_MEDAL = "ERROR_INVALID_MEDAL";
}
