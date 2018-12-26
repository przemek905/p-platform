package com.plml.pplatform.http;

public class PPlatformResponse {
    private String message;
    private String error;
    private int errorCode;


    public PPlatformResponse(String message) {
        super();
        this.message = message;
    }

    public PPlatformResponse(String message, String error) {
        super();
        this.message = message;
        this.error = error;
    }

    public PPlatformResponse(String message, String error, int errorCode) {
        super();
        this.message = message;
        this.error = error;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
