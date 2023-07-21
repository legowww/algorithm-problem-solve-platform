package com.dev.az.controller.request;

public record MemberJoinRequest(
        String name,
        String email,
        String password) {
}
