package com.dev.az.controller.request;

public record MemberDeleteRequest(
        String email,
        String password
) {
}
