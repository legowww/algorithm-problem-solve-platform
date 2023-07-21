package com.dev.az.controller;


import com.dev.az.controller.request.MemberDeleteRequest;
import com.dev.az.controller.request.MemberJoinRequest;
import com.dev.az.controller.response.ProblemSolvedHistoryResponse;
import com.dev.az.controller.response.Response;
import com.dev.az.model.dto.SubmissionDto;
import com.dev.az.service.MemberService;
import com.dev.az.service.ProblemSolvingStateService;
import com.dev.az.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final SubmissionService submissionService;

    private final ProblemSolvingStateService problemSolvingStateService;

    @PostMapping
    public ResponseEntity<Response<String>> join(@RequestBody MemberJoinRequest memberJoinRequest) {
        String id = memberService.join(memberJoinRequest.name(), memberJoinRequest.email(), memberJoinRequest.password());

        return ResponseEntity.created(URI.create("/api/v1/members/"+id))
                .body(Response.success(id));
    }

    @DeleteMapping
    public ResponseEntity<Response<String>> deleteMember(@RequestBody MemberDeleteRequest memberDeleteRequest) {
        String id = memberService.delete(memberDeleteRequest.email(), memberDeleteRequest.password());

        return ResponseEntity.ok()
                .body(Response.success(id));
    }

    @GetMapping("/{id}/success")
    public ResponseEntity<Response<ProblemSolvedHistoryResponse>> successProblems(@PathVariable UUID id) {
        List<Long> problemIds = problemSolvingStateService.findSuccessProblems(id);
        ProblemSolvedHistoryResponse problemSolvedHistoryResponse = new ProblemSolvedHistoryResponse(problemIds.size(), problemIds);

        return ResponseEntity.ok()
                .body(Response.success(problemSolvedHistoryResponse));
    }

    @GetMapping("/{id}/fail")
    public ResponseEntity<Response<ProblemSolvedHistoryResponse>> failProblems(@PathVariable UUID id) {
        List<Long> problemIds = problemSolvingStateService.findFailProblems(id);
        ProblemSolvedHistoryResponse problemSolvedHistoryResponse = new ProblemSolvedHistoryResponse(problemIds.size(), problemIds);

        return ResponseEntity.ok()
                .body(Response.success(problemSolvedHistoryResponse));
    }

    @GetMapping("{id}/submissions")
    public ResponseEntity<?> submissions(
            @PathVariable UUID id,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        List<SubmissionDto> submissionDtos = submissionService.findSubmissions(id, pageable);

        return ResponseEntity.ok()
                .body(submissionDtos);
    }
}
