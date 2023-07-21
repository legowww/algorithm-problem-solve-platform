package com.dev.az.model.dto;

import com.dev.az.model.ProblemRank;
import com.dev.az.model.entity.Submission;

public record SubmissionDto(long problemId, String title, ProblemRank problemRank) {

    public static SubmissionDto from(Submission submission) {
        return new SubmissionDto(
                submission.getProblem().getId(),
                submission.getProblem().getTitle(),
                submission.getProblem().getProblemRank()
        );
    }
}
