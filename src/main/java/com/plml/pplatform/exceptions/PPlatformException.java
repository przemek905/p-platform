package com.plml.pplatform.exceptions;

public abstract class PPlatformException extends RuntimeException {
    protected int errCode;

    public PPlatformException(String message, int errCode) {
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