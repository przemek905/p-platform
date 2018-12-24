package com.plml.pplatform.exceptions;

public class InvalidOldPasswordException extends PPlatformException {
    public InvalidOldPasswordException(String message, int errCode) {
        super(message, errCode);
    }
}
