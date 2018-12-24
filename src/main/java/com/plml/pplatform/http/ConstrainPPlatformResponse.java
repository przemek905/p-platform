package com.plml.pplatform.http;

import java.util.List;

public class ConstrainPPlatformResponse extends PPlatformResponse {

    private List<PPlatformResponse> constrainPPlatformResponsesErrors;

    public ConstrainPPlatformResponse(String message,
                                      List<PPlatformResponse> constrainPPlatformResponsesErrors) {
        super(message);
        this.constrainPPlatformResponsesErrors = constrainPPlatformResponsesErrors;
    }

    public ConstrainPPlatformResponse(String message, String error, int errorCode,
                                      List<PPlatformResponse> constrainPPlatformResponsesErrors) {
        super(message, error, errorCode);
        this.constrainPPlatformResponsesErrors = constrainPPlatformResponsesErrors;
    }

    public List<PPlatformResponse> getConstrainPPlatformResponsesErrors() {
        return constrainPPlatformResponsesErrors;
    }

    public void setConstrainPPlatformResponsesErrors(List<PPlatformResponse> constrainPPlatformResponsesErrors) {
        this.constrainPPlatformResponsesErrors = constrainPPlatformResponsesErrors;
    }
}
