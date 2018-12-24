package com.plml.pplatform.exceptions;

public class UserNotFoundException extends PPlatformException {
    public UserNotFoundException(String message, int errCode) {
        super(message, errCode);
    }
}
