package com.dev.az.model.entity;


import com.dev.az.model.Algorithm;
import com.dev.az.model.ProblemRank;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(
        name = "PROBLEM_TB",
        indexes = {
                @Index(name = "idx_created_at", columnList = "createdAt"),
                @Index(name = "idx_algorithm", columnList = "algorithm"),
                @Index(name = "idx_rank", columnList = "problemRank")
        }
        )
@Getter
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String problemCondition;

    @Type(JsonType.class)
    @Column(columnDefinition = "longtext")
    private Map<String, String> examples;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProblemRank problemRank;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Algorithm algorithm;

    @Column(nullable = false)
    private Long solvedCount = 0L;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    protected Problem() {}

    public Problem(String title, String content, String problemCondition, Map<String, String> examples, ProblemRank problemRank, Algorithm algorithm) {
        this.title = title;
        this.content = content;
        this.problemCondition = problemCondition;
        this.examples = examples;
        this.problemRank = problemRank;
        this.algorithm = algorithm;
    }

    public long getExperience() {
        return problemRank.getExperience();
    }

    public void plusSolvedCount() {
        this.solvedCount++;
    }
}
