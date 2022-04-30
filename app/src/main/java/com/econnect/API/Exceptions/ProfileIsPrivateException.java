package com.econnect.API.Exceptions;

import com.econnect.API.ApiConstants;

public class ProfileIsPrivateException extends ApiException {

    public ProfileIsPrivateException() {
        super(ApiConstants.ERROR_PRIVATE_USER);
    }

    @Override
    public String getMessage() {
        return "The profile of this user is private";
    }
}
