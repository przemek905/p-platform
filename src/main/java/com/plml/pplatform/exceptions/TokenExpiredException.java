package com.plml.pplatform.exceptions;

public class TokenExpiredException extends PPlatformException {

    public TokenExpiredException(String message, int errCode) {
        super(message, errCode);
    }
}
