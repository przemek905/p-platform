package com.plml.pplatform.Exceptions;

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
