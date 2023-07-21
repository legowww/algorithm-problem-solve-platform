package com.dev.az.controller.response;

public record Response<T>(
        T response,
        String result) {

    public static <T> Response<T> success(T response) {
        return new Response<>(response, "success");
    }

    public static Response<String> error(String errorCode) {
        return new Response<>(errorCode, "fail");
    }
}
