package com.dev.az.controller.response;

import com.dev.az.exception.ErrorStatus;

public record Response<T>(
        T responseObject,
        String requestResult) {

    public static <T> Response<T> success(T responseObject) {
        return new Response<>(responseObject, "success");
    }

    public static Response<ErrorStatus> error(ErrorStatus errorStatus) {
        return new Response<>(errorStatus, "fail");
    }
}
