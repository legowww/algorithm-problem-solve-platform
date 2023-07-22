package com.dev.az.model.entity;


import com.dev.az.model.SubmissionResult;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(
        name = "PROBLEM_SOLVING_STATE_TB",
        indexes = @Index(name = "idx_problem_member_id", columnList = "problem_id, member_id")
)
@Getter
public class ProblemSolvingState {

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
    @Setter
    private SubmissionResult submissionResult;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    protected ProblemSolvingState() {}

    public ProblemSolvingState(Problem problem, Member member, SubmissionResult submissionResult) {
        this.problem = problem;
        this.member = member;
        this.submissionResult = submissionResult;
    }
}
