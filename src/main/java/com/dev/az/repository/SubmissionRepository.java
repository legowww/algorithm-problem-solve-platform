package com.dev.az.repository;

import com.dev.az.model.entity.Submission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;


public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    @Query("select s from Submission s join s.problem where s.member.id = :id")
    Page<Submission> findAllSubmissions(@Param("id") UUID id, Pageable pageable);
}
