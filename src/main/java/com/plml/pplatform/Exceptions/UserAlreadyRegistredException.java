package com.plml.pplatform.Exceptions;

public class UserAlreadyRegistredException extends RuntimeException {

    private int errCode;


    public UserAlreadyRegistredException(String message, int errCode) {
        super(message);
        this.errCode = errCode;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
}
