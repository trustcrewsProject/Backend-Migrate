package com.example.demo.repository.trust_score;

import com.example.demo.model.trust_score.QTrustScore;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TrustScoreRepositoryImpl implements TrustScoreRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void updateScore(Long userId, int score) {
        QTrustScore trustScore = QTrustScore.trustScore;
        jpaQueryFactory
                .update(trustScore)
                .set(
                        trustScore.score,
                        new CaseBuilder()
                                .when(trustScore.score.add(score).lt(0))
                                .then(0)
                                .otherwise(trustScore.score.add(score)))
                .where(trustScore.userId.eq(userId))
                .execute();
    }

    @Override
    public int getUserScore(Long userId) {
        QTrustScore trustScore = QTrustScore.trustScore;
        return jpaQueryFactory
                .select(trustScore.score)
                .from(trustScore)
                .where(trustScore.userId.eq(userId))
                .fetchFirst();
    }
}
