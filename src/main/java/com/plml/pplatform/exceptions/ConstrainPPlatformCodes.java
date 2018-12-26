package com.plml.pplatform.exceptions;

public enum ConstrainPPlatformCodes {
    username(711), email(712);

    private int errorCode;

    ConstrainPPlatformCodes(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
