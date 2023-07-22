package com.dev.az.model.dto;

import com.dev.az.model.ProblemRank;
import com.dev.az.model.SubmissionResult;
import com.dev.az.model.entity.Submission;

import java.time.LocalDateTime;

public record SubmissionDto(long problemId, String title, ProblemRank problemRank, SubmissionResult submissionResult, LocalDateTime createdAt) {

    public static SubmissionDto from(Submission submission) {
        return new SubmissionDto(
                submission.getProblem().getId(),
                submission.getProblem().getTitle(),
                submission.getProblem().getProblemRank(),
                submission.getSubmissionResult(),
                submission.getCreatedAt()
        );
    }
}
