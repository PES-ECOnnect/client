package com.econnect.API.Exceptions;

import com.econnect.API.ApiConstants;

public class UsernameExistsException extends ApiException {

    public UsernameExistsException() {
        super(ApiConstants.ERROR_USERNAME_EXISTS);
    }

    @Override
    public String getMessage() {
        return "This username has already been taken";
    }
}
