package com.dev.az.model.entity;


import com.dev.az.model.MemberRank;
import com.dev.az.model.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "MEMBER_TB")
@Getter
public class Member {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 100)
    private String password;

    @Embedded
    @Setter
    private Email email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRank memberRank = MemberRank.BRONZE;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.ROLE_USER;

    @Column(nullable = false)
    private Long experience = 0L;

    @Column(nullable = false)
    private Long solvedCount = 0L;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    List<Submission> submissionList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    List<ProblemSolvingState> problemSolvingStates;

    protected Member() {}

    private Member(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public static Member of(String name, String email, String password) {
        Member member = new Member(name, password);
        member.setEmail(Email.from(email));

        return member;
    }

    public void solveProblem(long experience) {
        addExperience(experience);
        updateRank();
        plusSolvedCount();
    }

    private void addExperience(long experience) {
        this.experience += experience;
    }

    private void plusSolvedCount() {
        this.solvedCount++;
    }

    private void updateRank() {
        MemberRank updatedRank = MemberRank.rankUpdate(this.experience);

        if (this.memberRank != updatedRank) {
            this.memberRank = updatedRank;
        }
    }
}
