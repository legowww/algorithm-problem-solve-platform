package com.dev.az.model.dto;

import com.dev.az.model.Algorithm;
import com.dev.az.model.ProblemRank;
import com.dev.az.model.entity.Problem;

import java.util.Map;

public record ProblemDto (
        Long id,
        String title,
        String content,
        String problemCondition,
        Map<String, String> examples,
        ProblemRank problemRank,
        Algorithm algorithm) {

    public static ProblemDto from(Problem problem) {
        return new ProblemDto(
                problem.getId(),
                problem.getTitle(),
                problem.getContent(),
                problem.getProblemCondition(),
                problem.getExamples(),
                problem.getProblemRank(),
                problem.getAlgorithm()
        );
    }
}
