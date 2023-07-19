package com.dev.az.service;


import com.dev.az.controller.request.SubmissionCreateRequest;
import com.dev.az.model.SubmissionResult;
import com.dev.az.model.entity.Member;
import com.dev.az.model.entity.Problem;
import com.dev.az.model.entity.Submission;
import com.dev.az.repository.MemberRepository;
import com.dev.az.repository.ProblemRepository;
import com.dev.az.repository.SubmissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class SubmissionService {

    private final SubmissionRepository submissionRepository;

    private final MemberRepository memberRepository;

    private final ProblemRepository problemRepository;

    public SubmissionService(SubmissionRepository submissionRepository, MemberRepository memberRepository, ProblemRepository problemRepository) {
        this.submissionRepository = submissionRepository;
        this.memberRepository = memberRepository;
        this.problemRepository = problemRepository;
    }

    @Transactional
    public void createSubmission(SubmissionCreateRequest submissionCreateRequest, SubmissionResult submissionResult) {
        Member member = memberRepository.getReferenceById(submissionCreateRequest.memberId());
        Problem problem = problemRepository.getReferenceById(submissionCreateRequest.problemId());

        Submission submission = new Submission(problem, member, submissionCreateRequest.language(), submissionCreateRequest.code(), submissionResult);
        submissionRepository.save(submission);
    }
}
