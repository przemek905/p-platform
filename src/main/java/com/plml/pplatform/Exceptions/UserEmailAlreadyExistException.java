package com.plml.pplatform.Exceptions;

public class UserEmailAlreadyExistException extends PPlatformException {

    public UserEmailAlreadyExistException(String message, int errCode) {
        super(message, errCode);
    }
}
