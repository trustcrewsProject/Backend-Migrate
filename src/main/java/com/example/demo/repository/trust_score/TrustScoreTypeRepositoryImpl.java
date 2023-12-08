package com.example.demo.repository.trust_score;

import com.example.demo.model.project.QProject;
import com.example.demo.model.trust_grade.QTrustGrade;
import com.example.demo.model.trust_score.QTrustScoreType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TrustScoreTypeRepositoryImpl implements TrustScoreTypeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public int getScore(Long trustScoreTypeId) {
        // QClass 선언
        QTrustScoreType trustScoreType = QTrustScoreType.trustScoreType;
        // 조건 설정
        BooleanBuilder condition = new BooleanBuilder();
        condition.and(trustScoreType.id.eq(trustScoreTypeId));
        // 쿼리 수행
        return jpaQueryFactory
                .select(trustScoreType.score)
                .from(trustScoreType)
                .where(condition)
                .fetchFirst();
    }

    /**
     * project와 trustScoreType을 통해 요청에 맞는 신뢰점수 조회
     * @param projectId
     * @param trustScoreTypeId
     * @return
     */

    @Override
    public int getScoreByProject(Long projectId, Long trustScoreTypeId) {
        QTrustScoreType trustScoreType = QTrustScoreType.trustScoreType;
        QTrustGrade trustGrade = QTrustGrade.trustGrade;
        QProject project = QProject.project;
        return jpaQueryFactory
                .select(
                        new CaseBuilder()
                                .when(trustScoreType.gubunCode.eq("M"))
                                .then(trustScoreType.score.multiply(-1))
                                .otherwise(trustScoreType.score))
                .from(project)
                .join(trustGrade)
                .on(trustGrade.id.eq(project.trustGrade.id))
                .join(trustScoreType)
                .on(
                        trustScoreType
                                .upTrustScoreType.id
                                .eq(trustScoreTypeId)
                                .and(trustScoreType.trustGradeName.eq(trustGrade.name)))
                .where(project.id.eq(projectId))
                .fetchFirst();
    }

    @Override
    public List<Long> findAllUpScoreTypeId() {
        QTrustScoreType trustScoreType = QTrustScoreType.trustScoreType;
        return jpaQueryFactory
                .select(trustScoreType.id)
                .from(trustScoreType)
                .where(trustScoreType.upTrustScoreType.isNull())
                .fetch();
    }
}
