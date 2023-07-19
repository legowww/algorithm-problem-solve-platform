package com.dev.az.model;

import java.util.Arrays;
import java.util.function.Predicate;

public enum MemberRank {

    BRONZE((experience) -> experience >= 0 && experience <= 249),

    SILVER((experience) -> experience >= 250 && experience <= 499),

    GOLD((experience) -> experience >= 500 && experience <= 999),

    PLATINUM((experience) -> experience >= 1_000 && experience <= 1_999),

    DIAMOND((experience) -> experience >= 2_000);

    private final Predicate<Long> condition;

    MemberRank(Predicate<Long> condition) {
        this.condition = condition;
    }

    public static MemberRank rankUpdate(long experience) {
        return Arrays.stream(MemberRank.values())
                .filter(rank -> rank.condition.test(experience))
                .findAny()
                .orElse(BRONZE);
    }
}
