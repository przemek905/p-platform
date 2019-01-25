package com.plml.pplatform.interceptors;

import com.plml.pplatform.exceptions.*;
import com.plml.pplatform.http.ConstrainPPlatformResponse;
import com.plml.pplatform.http.PPlatformResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class PPlatformResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(PPlatformResponseEntityExceptionHandler.class);

    @ExceptionHandler({TokenNotExistException.class, TokenExpiredException.class, InvalidOldPasswordException.class})
    public ResponseEntity<?> handlePasswordExceptions(RuntimeException ex, WebRequest request) {
        LOGGER.info("Handle business exception" + ex);
        PPlatformException exception = (PPlatformException) ex;

        PPlatformResponse platformResponse = new PPlatformResponse(exception.getMessage(), "Problem with user password process",
                exception.getErrCode());

        return handleExceptionInternal(
                ex, platformResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        LOGGER.error(ex.getMessage());
        BindingResult bindingResult = ex.getBindingResult();

        List<PPlatformResponse> bindingErrors = bindingResult.getFieldErrors().stream()
                .map(error -> new PPlatformResponse(error.getField(), error.getDefaultMessage(),
                        ConstrainPPlatformCodes.valueOf(error.getField()).getErrorCode()))
                .collect(Collectors.toList());

        ConstrainPPlatformResponse response = new ConstrainPPlatformResponse("Constrain validation exception",
                "Binding exception", PPlatformErrorCodes.BINDING_EXCEPTION.getErrorCode(), bindingErrors);

        return handleExceptionInternal(ex, response, headers, status, request);
    }

}
