package com.dev.az.model.entity;


import com.dev.az.model.Language;
import com.dev.az.model.SubmissionResult;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "SUBMISSION_TB")
@Getter
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String code;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SubmissionResult submissionResult;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    protected Submission() {}

    public Submission(Problem problem, Member member, Language language, String code, SubmissionResult submissionResult) {
        this.problem = problem;
        this.member = member;
        this.language = language;
        this.code = code;
        this.submissionResult = submissionResult;
    }
}
