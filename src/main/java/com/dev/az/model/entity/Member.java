package com.dev.az.model.entity;


import com.dev.az.model.MemberRank;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "MEMBER_TB")
@Getter
public class Member {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.AUTO)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, length = 20)
    private String name;

    @Embedded
    @Setter
    private Email email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRank memberRank = MemberRank.BRONZE;

    @Column(nullable = false)
    private Long experience = 0L;

    @Column(nullable = false)
    private Long solvedCount = 0L;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    protected Member() {}

    private Member(String name) {
        this.name = name;
    }

    public static Member of(String name, String email) {
        Member member = new Member(name);
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
