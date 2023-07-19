package com.dev.az.model;

public enum ProblemRank {

    ALL(0),

    BRONZE(10),

    SILVER(20),

    GOLD(60),

    PLATINUM(200),

    DIAMOND(500);

    private final long experience;

    ProblemRank(long experience) {
        this.experience = experience;
    }

    public long getExperience() {
        return experience;
    }
}
