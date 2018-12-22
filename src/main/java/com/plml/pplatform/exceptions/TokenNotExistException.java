package com.plml.pplatform.exceptions;

public class TokenNotExistException extends PPlatformException {

    public TokenNotExistException(String message, int errCode) {
        super(message, errCode);
    }
}
