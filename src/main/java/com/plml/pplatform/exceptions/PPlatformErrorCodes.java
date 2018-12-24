package com.plml.pplatform.exceptions;

public enum PPlatformErrorCodes {
    TOKEN_NOT_EXIST(211), TOKEN_EXPIRED(212), BINDING_EXCEPTION(700), USER_NOT_FOUND(800), OLD_PASSWORD_INVALID(850);
    private int errorCode;

    PPlatformErrorCodes(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
