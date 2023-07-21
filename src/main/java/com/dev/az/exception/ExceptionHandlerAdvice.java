package com.dev.az.exception;


import com.dev.az.controller.response.Response;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Response<String>> runtimeExceptionHandler(RuntimeException e) {

        return ResponseEntity.badRequest().
                body(Response.error(e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response<String>> illegalArgumentExceptionHandler(IllegalArgumentException e) {

        return ResponseEntity.badRequest()
                .body(Response.error(e.getMessage()));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Response<String>> dataAccessExceptionHandler(DataAccessException e) {

        return ResponseEntity.badRequest()
                .body(Response.error(e.getMessage()));
    }
}


