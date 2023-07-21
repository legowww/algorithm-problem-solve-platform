package com.dev.az.service;


import com.dev.az.controller.request.SubmissionCreateRequest;
import com.dev.az.model.SubmissionResult;
import com.dev.az.model.entity.Member;
import com.dev.az.model.entity.Problem;
import com.dev.az.model.entity.ProblemSolvingState;
import com.dev.az.repository.MemberRepository;
import com.dev.az.repository.ProblemRepository;
import com.dev.az.repository.ProblemSolvingStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProblemSolvingStateService {

    private final ProblemSolvingStateRepository problemSolvingStateRepository;

    private final MemberRepository memberRepository;

    private final ProblemRepository problemRepository;

    public List<Long> findSuccessProblems(UUID id) {
        return problemSolvingStateRepository.findSuccessProblems(id)
                .stream()
                .map(problemSolvingState ->
                        problemSolvingState.getProblem().getId()
                )
                .toList();
    }

    public List<Long> findFailProblems(UUID id) {
        return problemSolvingStateRepository.findFailProblems(id)
                .stream()
                .map(problemSolvingState ->
                        problemSolvingState.getProblem().getId()
                )
                .toList();
    }

    @Transactional
    public void createState(UUID memberId, SubmissionCreateRequest submissionCreateRequest, SubmissionResult submissionResult) {
        Member member = memberRepository.getReferenceById(memberId);
        Problem problem = problemRepository.getReferenceById(submissionCreateRequest.problemId());

        Optional<ProblemSolvingState> stateOptional = problemSolvingStateRepository.findByMember_IdAndProblem_Id(member.getId(), problem.getId());

        switch (submissionResult) {
            case FAIL -> {
                if (stateOptional.isEmpty()) {
                    problemSolvingStateRepository.save(new ProblemSolvingState(problem, member, SubmissionResult.FAIL));
                }
            }
            case SUCCESS -> {
                if (stateOptional.isEmpty()) {
                    solveProblem(member, problem);
                    problemSolvingStateRepository.save(new ProblemSolvingState(problem, member, submissionResult));
                }
                else {
                    ProblemSolvingState problemSolvingState = stateOptional.get();

                    if (problemSolvingState.getSubmissionResult() == SubmissionResult.FAIL) {
                        solveProblem(member, problem);
                    }

                    problemSolvingState.setSubmissionResult(SubmissionResult.SUCCESS);
                }
            }
        }
    }

    @Transactional
    public void solveProblem(Member member, Problem problem) {
        long experience = problem.getExperience();
        member.solveProblem(experience);
        problem.plusSolvedCount();
    }
}
