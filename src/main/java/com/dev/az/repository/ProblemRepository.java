package com.dev.az.repository;

import com.dev.az.model.Algorithm;
import com.dev.az.model.ProblemRank;
import com.dev.az.model.entity.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    Page<Problem> findByAlgorithm(Algorithm algorithm, Pageable pageable);

    Page<Problem> findByProblemRank(ProblemRank problemRank, Pageable pageable);

    Page<Problem> findByProblemRankAndAlgorithm(ProblemRank problemRank, Algorithm algorithm, Pageable pageable);

    Page<Problem> findAll(Pageable pageable);
}
