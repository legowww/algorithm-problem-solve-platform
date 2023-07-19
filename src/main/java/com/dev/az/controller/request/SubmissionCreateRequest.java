package com.dev.az.controller.request;

import com.dev.az.model.Language;

import java.util.UUID;

public record SubmissionCreateRequest(
        UUID memberId,
        long problemId,
        Language language,
        String code) {
}
