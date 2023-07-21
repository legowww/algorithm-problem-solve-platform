package com.dev.az.service;


import com.dev.az.controller.request.SubmissionCreateRequest;
import com.dev.az.model.SubmissionResult;
import com.dev.az.model.dto.SubmissionDto;
import com.dev.az.model.entity.Member;
import com.dev.az.model.entity.Problem;
import com.dev.az.model.entity.Submission;
import com.dev.az.repository.MemberRepository;
import com.dev.az.repository.ProblemRepository;
import com.dev.az.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepository submissionRepository;

    private final MemberRepository memberRepository;

    private final ProblemRepository problemRepository;

    @Transactional
    public void createSubmission(UUID memberId, SubmissionCreateRequest submissionCreateRequest, SubmissionResult submissionResult) {
        Member member = memberRepository.getReferenceById(memberId);
        Problem problem = problemRepository.getReferenceById(submissionCreateRequest.problemId());

        Submission submission = new Submission(problem, member, submissionCreateRequest.language(), submissionCreateRequest.code(), submissionResult);
        submissionRepository.save(submission);
    }

    public List<SubmissionDto> findSubmissions(UUID memberId, Pageable pageable) {
        return submissionRepository.findAllSubmissions(memberId, pageable)
                .map(SubmissionDto::from)
                .toList();
    }
}
