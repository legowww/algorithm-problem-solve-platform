package com.dev.az.controller;


import com.dev.az.controller.request.ProblemCreateRequest;
import com.dev.az.controller.response.Response;
import com.dev.az.model.Algorithm;
import com.dev.az.model.ProblemRank;
import com.dev.az.model.dto.ProblemDto;
import com.dev.az.service.ProblemService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/problems")
public class ProblemController {

    private final ProblemService problemService;

    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;

    }

    @PostMapping
    public Response<Void> createProblem(@RequestBody ProblemCreateRequest problemCreateRequest) {
        problemService.createProblem(
                problemCreateRequest.title(),
                problemCreateRequest.content(),
                problemCreateRequest.problemCondition(),
                problemCreateRequest.examples(),
                problemCreateRequest.problemRank(),
                problemCreateRequest.algorithm()
        );

        return Response.success(null);
    }

    @GetMapping
    public Response<List<ProblemDto>> problems(
            @RequestParam Optional<ProblemRank> problemRank,
            @RequestParam Optional<Algorithm> algorithm,
            @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Algorithm searchtAlgorithm = algorithm.orElse(Algorithm.ALL);
        ProblemRank searchProblemRank = problemRank.orElse(ProblemRank.ALL);
        List<ProblemDto> problemDtos = problemService.searchProblems(searchProblemRank, searchtAlgorithm, pageable);

        return Response.success(problemDtos);
    }
}
