package com.dev.az.controller;


import com.dev.az.controller.request.MemberCreateRequest;
import com.dev.az.controller.response.ProblemSolvedHistoryResponse;
import com.dev.az.controller.response.Response;
import com.dev.az.service.MemberService;
import com.dev.az.service.ProblemSolvingStateService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    private final ProblemSolvingStateService problemSolvingStateService;

    public MemberController(MemberService memberService, ProblemSolvingStateService problemSolvingStateService) {
        this.memberService = memberService;
        this.problemSolvingStateService = problemSolvingStateService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Void> createMember(@RequestBody MemberCreateRequest memberCreateRequest) {
        memberService.createMember(memberCreateRequest.name(), memberCreateRequest.email());

        return Response.success(null);
    }

    @GetMapping("/{id}/success")
    public Response<ProblemSolvedHistoryResponse> successProblems(@PathVariable UUID id) {
        List<Long> problemIds = problemSolvingStateService.findSuccessProblems(id);
        ProblemSolvedHistoryResponse problemSolvedHistoryResponse = new ProblemSolvedHistoryResponse(problemIds.size(), problemIds);

        return Response.success(problemSolvedHistoryResponse);
    }

    @GetMapping("/{id}/fail")
    public Response<ProblemSolvedHistoryResponse> failProblems(@PathVariable UUID id) {
        List<Long> problemIds = problemSolvingStateService.findFailProblems(id);
        ProblemSolvedHistoryResponse problemSolvedHistoryResponse = new ProblemSolvedHistoryResponse(problemIds.size(), problemIds);

        return Response.success(problemSolvedHistoryResponse);
    }
}
