package com.dev.az.controller;


import com.dev.az.controller.request.SubmissionCreateRequest;
import com.dev.az.controller.response.Response;
import com.dev.az.model.SubmissionResult;
import com.dev.az.service.ProblemSolvingStateService;
import com.dev.az.service.SubmissionGradingService;
import com.dev.az.service.SubmissionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/submissions")
public class SubmissionController {

    private final SubmissionService submissionService;

    private final SubmissionGradingService submissionGradingService;

    private final ProblemSolvingStateService problemSolvingStateService;

    public SubmissionController(SubmissionService submissionService, SubmissionGradingService submissionGradingService, ProblemSolvingStateService problemSolvingStateService) {
        this.submissionService = submissionService;
        this.submissionGradingService = submissionGradingService;
        this.problemSolvingStateService = problemSolvingStateService;
    }

    @PostMapping
    public Response<SubmissionResult> submit(@RequestBody SubmissionCreateRequest submissionCreateRequest) {
        SubmissionResult submissionResult = submissionGradingService.grade(submissionCreateRequest);

        submissionService.createSubmission(submissionCreateRequest, submissionResult);
        problemSolvingStateService.createState(submissionCreateRequest, submissionResult);

        return Response.success(submissionResult);
    }
}
