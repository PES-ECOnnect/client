package com.econnect.API.Exceptions;

import com.econnect.API.ApiConstants;

public class AccountNotFoundException extends ApiException {

    public AccountNotFoundException() {
        super(ApiConstants.ERROR_USER_NOT_FOUND);
    }

    @Override
    public String getMessage() {
        return "No account found for this email";
    }
}
