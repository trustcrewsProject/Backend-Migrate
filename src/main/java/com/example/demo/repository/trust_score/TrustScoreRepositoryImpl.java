package com.example.demo.repository.trust_score;

import static com.example.demo.model.trust_grade.QTrustGrade.*;
import static com.example.demo.model.trust_score.QTrustScore.trustScore;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TrustScoreRepositoryImpl implements TrustScoreRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    /** 0100AM 전체 유저 신뢰등급 업데이트 스케줄 */
    @Scheduled(cron = "0 1 * * *", zone = "Asia/Seoul")
    @Override
    public void updateTrustGradeBatch() {
        updateTrustGrade(null);
    }

    /** 신뢰점수 변경 발생 시, 신뢰등급 업데이트 */
    public void updateUserTrustGrade(Long userId) {
        BooleanExpression builder = trustScore.userId.eq(userId);
        updateTrustGrade(builder);
    }

    private void updateTrustGrade(BooleanExpression builder) {
        jpaQueryFactory
                .update(trustScore)
                .set(
                        trustScore.trustGrade,
                        JPAExpressions.selectFrom(trustGrade)
                                .where(
                                        trustScore
                                                .score
                                                .goe(trustGrade.minimumScore)
                                                .and(
                                                        trustScore.score.loe(
                                                                trustGrade.maximumScore))))
                .where(builder)
                .execute();
    }
}
