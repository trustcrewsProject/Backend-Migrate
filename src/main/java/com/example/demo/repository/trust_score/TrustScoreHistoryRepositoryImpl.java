package com.example.demo.repository.trust_score;

import com.example.demo.dto.common.ConstantDto;
import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.dto.projectmember.response.ProjectMemberWorkWithTrustScoreResponseDto;
import com.example.demo.dto.trust_score.ProjectUserHistoryDto;
import com.example.demo.model.trust_score.QTrustScoreHistory;
import com.example.demo.model.work.QWork;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TrustScoreHistoryRepositoryImpl implements TrustScoreHistoryRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QTrustScoreHistory qTrustScoreHistory = QTrustScoreHistory.trustScoreHistory;

    /**
     * 특정 프로젝트에 참여하는 사용자의, 해당 프로젝트 관련 발생한 신뢰점수이력을 DTO 형태로 반환
     *
     * @param projectId
     * @param userId
     * @return List<ProjectUserHistoryDto>
     */
    @Override
    public List<ProjectUserHistoryDto> getProjectUserHistory(Long projectId, Long userId) {
        QTrustScoreHistory trustScoreHistory = QTrustScoreHistory.trustScoreHistory;
        QWork work = QWork.work;
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                ProjectUserHistoryDto.class,
                                trustScoreHistory.workId,
                                trustScoreHistory.score,
                                Projections.constructor(
                                        ConstantDto.class,
                                        work.progressStatus
                                ),
                                work.content,
                                trustScoreHistory.createDate))
                .from(trustScoreHistory)
                .where(
                        trustScoreHistory
                                .projectId
                                .eq(projectId)
                                .and(trustScoreHistory.userId.eq(userId)))
                .join(work)
                .on(trustScoreHistory.workId.eq(work.id))
                .fetch();
    }

    @Override
    public int calculateCurrentScore(Long userId) {
        QTrustScoreHistory trustScoreHistory = QTrustScoreHistory.trustScoreHistory;
        try {
            return jpaQueryFactory
                    .select(trustScoreHistory.score.sum())
                    .from(trustScoreHistory)
                    .where(trustScoreHistory.userId.eq(userId))
                    .fetchFirst();
        } catch (NullPointerException ne) {
            log.error("데이터가 존재하지 않음. userId : {}", userId);
            log.error("error :", ne);
            return 0;
        }
    }

    @Override
    public PaginationResponseDto findByProjectAndUserAndWorkIsNotNullOrderByCreateDate(Long projectId, Long userId, Pageable pageable) {
        List<ProjectMemberWorkWithTrustScoreResponseDto> content = jpaQueryFactory
                .select(
                        Projections.constructor(
                                ProjectMemberWorkWithTrustScoreResponseDto.class,
                                qTrustScoreHistory.id,
                                qTrustScoreHistory.content,
                                qTrustScoreHistory.score,
                                qTrustScoreHistory.createDate
                        )
                )
                .from(qTrustScoreHistory)
                .where(qTrustScoreHistory.projectId.eq(projectId),
                        qTrustScoreHistory.userId.eq(userId),
                        qTrustScoreHistory.workId.isNotNull())
                .orderBy(qTrustScoreHistory.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalPages = countByProjectAndUserAndWorkIsNotNull(projectId, userId);

        return PaginationResponseDto.of(content, totalPages);
    }

    private Long countByProjectAndUserAndWorkIsNotNull(Long projectId, Long userId) {
        return jpaQueryFactory
                .select(qTrustScoreHistory.count())
                .from(qTrustScoreHistory)
                .where(qTrustScoreHistory.projectId.eq(projectId),
                        qTrustScoreHistory.userId.eq(userId),
                        qTrustScoreHistory.workId.isNotNull())
                .fetchOne();
    }
}
