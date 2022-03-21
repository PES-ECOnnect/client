package com.econnect.API.Exceptions;

import com.econnect.API.ApiConstants;

public class InvalidTokenApiException extends ApiException {
    public InvalidTokenApiException() {
        super(ApiConstants.ERROR_INVALID_TOKEN);
    }
    
    @Override
    public String getMessage() {
        return "This session has expired, please logout and try again";
    }
}
