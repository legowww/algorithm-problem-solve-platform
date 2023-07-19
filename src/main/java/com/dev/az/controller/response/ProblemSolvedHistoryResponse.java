package com.dev.az.controller.response;

import java.util.List;

public record ProblemSolvedHistoryResponse (
        long count,
        List<Long> problemIds) {
}
