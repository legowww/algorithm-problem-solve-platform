package com.dev.az.service;


import com.dev.az.model.Algorithm;
import com.dev.az.model.ProblemRank;
import com.dev.az.model.dto.ProblemDto;
import com.dev.az.model.entity.Problem;
import com.dev.az.repository.ProblemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class ProblemService {

    private final ProblemRepository problemRepository;

    public ProblemService(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    @Transactional
    public void createProblem(String title, String content, String problemCondition, Map<String, String> examples, ProblemRank problemRank, Algorithm algorithm) {
        Problem problem = new Problem(title, content, problemCondition, examples, problemRank, algorithm);
        problemRepository.save(problem);
    }

    public List<ProblemDto> searchProblems(ProblemRank problemRank, Algorithm algorithm, Pageable pageable) {
        //모든 문제 검색
        if (problemRank == ProblemRank.ALL && algorithm == Algorithm.ALL) {
            return problemRepository.findAll(pageable)
                    .map(ProblemDto::from)
                    .toList();
        }

        //특정 랭크 + 특정 알고리즘 검색
        if (problemRank != ProblemRank.ALL && algorithm != Algorithm.ALL) {
            return problemRepository.findByProblemRankAndAlgorithm(problemRank, algorithm, pageable)
                    .map(ProblemDto::from)
                    .toList();
        }

        //특정 랭크의 모든 문제 검색
        if (problemRank != ProblemRank.ALL) {
            return problemRepository.findByProblemRank(problemRank, pageable)
                    .map(ProblemDto::from)
                    .toList();
        }

        //특정 알고리즘의 모든 문제 검색
        return problemRepository.findByAlgorithm(algorithm, pageable)
                .map(ProblemDto::from)
                .toList();
    }
}
