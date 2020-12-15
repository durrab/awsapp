package com.h2app.aws;

import com.amazonaws.AmazonServiceException;


public class ApiGatewayException extends AmazonServiceException {
    public ApiGatewayException(String errorMessage) {
        super(errorMessage);
    }

    public ApiGatewayException(String errorMessage, Exception cause) {
        super(errorMessage, cause);
    }
}
