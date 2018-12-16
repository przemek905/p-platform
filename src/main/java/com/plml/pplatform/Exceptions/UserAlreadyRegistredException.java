package com.plml.pplatform.Exceptions;

public class UserAlreadyRegistredException extends PPlatformException {


    public UserAlreadyRegistredException(String message, int errCode) {
        super(message, errCode);
    }

}
