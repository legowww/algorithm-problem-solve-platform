package com.dev.az.service;


import com.dev.az.controller.request.SubmissionCreateRequest;
import com.dev.az.model.Language;
import com.dev.az.model.SubmissionResult;
import org.springframework.stereotype.Component;

@Component
public class SubmissionGradingService {

    public SubmissionResult grade(SubmissionCreateRequest submissionCreateRequest) {
        if (submissionCreateRequest.code().length() % 2 == 0 && submissionCreateRequest.language() == Language.JAVA) {
            return SubmissionResult.SUCCESS;
        }

        return SubmissionResult.FAIL;
    }
}
