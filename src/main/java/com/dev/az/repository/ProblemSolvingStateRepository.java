package com.dev.az.repository;

import com.dev.az.model.entity.ProblemSolvingState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProblemSolvingStateRepository extends JpaRepository<ProblemSolvingState, Long> {

    Optional<ProblemSolvingState> findByMember_IdAndProblem_Id(UUID memberId, long problemId);

    @Query("select p from ProblemSolvingState p where p.member.id =:id and p.submissionResult = 'SUCCESS' order by p.problem.id")
    List<ProblemSolvingState> findSuccessProblems(@Param("id") UUID id);

    @Query("select p from ProblemSolvingState p where p.member.id =:id and p.submissionResult = 'FAIL' order by p.problem.id")
    List<ProblemSolvingState> findFailProblems(@Param("id") UUID id);
}
