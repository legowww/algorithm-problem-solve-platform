package com.dev.az;


import com.dev.az.model.Algorithm;
import com.dev.az.model.Language;
import com.dev.az.model.ProblemRank;
import com.dev.az.model.SubmissionResult;
import com.dev.az.model.entity.*;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Component
public class InsertTest implements InitializingBean {

    private final Init init;

    public InsertTest(Init init) {
        this.init = init;
    }

    @Override
    public void afterPropertiesSet() {
        init.init();
    }

    @Component
    @Transactional
    public static class Init {
        private final EntityManager entityManager;

        public Init(EntityManager entityManager) {
            this.entityManager = entityManager;
        }

        public void init() {
            Member alice = Member.of("Alice", "alice@gmail.com");
            entityManager.persist(alice);
            entityManager.persist(Member.of("David", "david@gmail.com"));


            for (int i=1; i<10; ++i) {
                ProblemRank problemRank;
                if (i % 2 == 0) {
                    problemRank = ProblemRank.GOLD;
                }
                else {
                    problemRank = ProblemRank.SILVER;
                }

                Algorithm algorithm = Algorithm.DP;

                if (i >= 3 && i <= 6) {
                    algorithm = Algorithm.GREEDY;
                }

                if (i >= 7 && i<= 8) {
                    algorithm = Algorithm.DFS;
                }

                HashMap<String, String> map = new HashMap<>();
                map.put("a", "A");
                map.put("b", "B");


                Problem problem = new Problem(
                         "1로 만들기(" + i + ")",
                        "높이가 H인 피라미드 수열은 1, 2, ..., H-1, H, H-1, ..., 2, 1, 2, ... 이다. 즉, 앞의 원소 2H-2개가 무한히 반복해서 나타난다. 높이가 1인 피라미드 수열은 1이 무한히 반복된다.두 자연수 N과 M이 주어졌을 때, 높이가 N인 피라미드 수열과 높이가 M인 피라미드 수열의 각 원소를 순서쌍으로 만든다. 이때, 나오는 순서쌍 종류의 개수를 구하는 프로그램을 작성하시오.",
                        "1 ≤ str1, str2의 길이 ≤ 10",
                        map,
                        problemRank,
                        algorithm
                );
                entityManager.persist(problem);


                if (i >= 5) {
                    entityManager.persist(new Submission(problem, alice, Language.PYTHON, "println(\"123\");", SubmissionResult.SUCCESS));
                    entityManager.persist(new ProblemSolvingState(problem, alice, SubmissionResult.SUCCESS));

                    alice.solveProblem(problem.getExperience());

                    problem.plusSolvedCount();
                }
                else {
                    entityManager.persist(new Submission(problem, alice, Language.PYTHON, "println(\"123\");", SubmissionResult.FAIL));
                    entityManager.persist(new ProblemSolvingState(problem, alice, SubmissionResult.FAIL));
                }
            }
        }
    }
}
