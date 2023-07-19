package com.dev.az.repository;

import com.dev.az.model.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}
