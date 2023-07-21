package com.dev.az.controller;


import com.dev.az.controller.request.SubmissionCreateRequest;
import com.dev.az.controller.response.Response;
import com.dev.az.model.SubmissionResult;
import com.dev.az.service.ProblemSolvingStateService;
import com.dev.az.service.SubmissionGradingService;
import com.dev.az.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    private final SubmissionGradingService submissionGradingService;

    private final ProblemSolvingStateService problemSolvingStateService;

    @PostMapping
    public ResponseEntity<Response<SubmissionResult>> submit(@RequestBody SubmissionCreateRequest submissionCreateRequest, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        UUID memberId = UUID.fromString(user.getUsername());

        SubmissionResult submissionResult = submissionGradingService.grade(submissionCreateRequest);

        submissionService.createSubmission(memberId, submissionCreateRequest, submissionResult);
        problemSolvingStateService.createState(memberId, submissionCreateRequest, submissionResult);

        return ResponseEntity.ok()
                .body(Response.success(submissionResult));
    }
}
