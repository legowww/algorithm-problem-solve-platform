package com.dev.az.exception;


import com.dev.az.controller.response.Response;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<ErrorStatus> runtimeExceptionHandler() {
        return Response.error(ErrorStatus.UNDEFINED_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<ErrorStatus> dataIntegrityViolationExceptionHandler() {
        return Response.error(ErrorStatus.INPUT_DUPLICATE_EMAIL);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<ErrorStatus> illegalArgumentExceptionHandler() {
        return Response.error(ErrorStatus.INPUT_WRONG_EMAIL_FORMAT);
    }
}
