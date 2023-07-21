package com.dev.az.controller.request;

import com.dev.az.model.Language;

public record SubmissionCreateRequest(
        long problemId,
        Language language,
        String code) {
}
