package com.dev.az.controller.request;

import com.dev.az.model.Algorithm;
import com.dev.az.model.ProblemRank;

import java.util.Map;

public record ProblemCreateRequest(
        String title,
        String content,
        String problemCondition,
        Map<String, String> examples,
        ProblemRank problemRank,
        Algorithm algorithm) {
}
