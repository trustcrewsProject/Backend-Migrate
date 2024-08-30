package com.example.demo.repository.work;

import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.dto.projectmember.response.ProjectMemberWorkWithTrustScoreResponseDto;
import com.example.demo.dto.work.response.WorkAssignedUserInfoResponseDto;
import com.example.demo.dto.work.response.WorkReadResponseDto;
import com.example.demo.model.milestone.QMilestone;
import com.example.demo.model.project.QProject;
import com.example.demo.model.project.QProjectMember;
import com.example.demo.model.trust_score.QTrustScoreHistory;
import com.example.demo.model.trust_score.QTrustScoreType;
import com.example.demo.model.user.QUser;
import com.example.demo.model.work.QWork;
import com.example.demo.model.work.Work;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WorkRepositoryImpl implements WorkRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;
    private final QProject qProject = QProject.project;
    private final QMilestone qMilestone = QMilestone.milestone;
    private final QWork qWork = QWork.work;
    private final QUser qUser = QUser.user;
    private final QProjectMember qLastModifiedProjectMember = QProjectMember.projectMember;
    private final QProjectMember qAssignedProjectMember = QProjectMember.projectMember;
    private final QTrustScoreHistory qTrustScoreHistory = QTrustScoreHistory.trustScoreHistory;

    @Override
    public PaginationResponseDto findWorkByProjectIdAndMilestoneIdOrderByStartDateAsc(Long projectId, Long milestoneId, Pageable pageable) {
        // 업무 목록 조회
        List<WorkReadResponseDto> content = jpaQueryFactory
                .select(
                        Projections.constructor(
                                WorkReadResponseDto.class,
                                qWork.id,
                                qProject.id,
                                qMilestone.id,
                                Projections.constructor(
                                        WorkAssignedUserInfoResponseDto.class,
                                        JPAExpressions
                                                .select(qAssignedProjectMember.id)
                                                .from(qAssignedProjectMember)
                                                .where(qAssignedProjectMember.project.id.eq(projectId),
                                                        qAssignedProjectMember.user.id.eq(qUser.id)),
                                        qUser.nickname
                                ),
                                qLastModifiedProjectMember.user.nickname,
                                qWork.content,
                                qWork.contentDetail,
                                qWork.startDate,
                                qWork.endDate,
                                qWork.progressStatus
                        )
                )
                .from(qWork)
                .join(qWork.project, qProject)
                .join(qWork.milestone, qMilestone)
                .join(qWork.assignedUserId, qUser)
                .join(qWork.lastModifiedMember, qLastModifiedProjectMember)
                .where(
                        eqProjectId(projectId),
                        eqMilestoneId(milestoneId)
                )
                .orderBy(qWork.startDate.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 업무 전체 갯수 조회
        long totalPages = getTotalItemCount(projectId, milestoneId, null);

        return PaginationResponseDto.of(content, totalPages);
    }

    @Override
    public void deleteAllByMilestoneId(Long milestoneId) {
        jpaQueryFactory
                .delete(qWork)
                .where(qWork.milestone.id.eq(milestoneId))
                .execute();
    }

    // 조건에 맞는 업무의 총 개수 조회
    private Long getTotalItemCount(Long projectId, Long milestoneId, Long assignedUserId) {
        return jpaQueryFactory
                .select(qWork.count())
                .from(qWork)
                .where(
                        eqProjectId(projectId),
                        eqMilestoneId(milestoneId),
                        eqAssignedUserId(assignedUserId)
                )
                .fetchOne();
    }

    // 프로젝트 ID 비교
    private BooleanExpression eqProjectId(Long projectId) {
        if(projectId == null) {
            return null;
        }

        return qWork.project.id.eq(projectId);
    }

    // 마일스톤 ID 비교
    private BooleanExpression eqMilestoneId(Long milestoneId) {
        if(milestoneId == null) {
            return null;
        }

        return qWork.milestone.id.eq(milestoneId);
    }

    // 할당자 ID 비교
    private BooleanExpression eqAssignedUserId(Long assignedUserId) {
        if(assignedUserId == null) {
            return null;
        }

        return qWork.assignedUserId.id.eq(assignedUserId);
    }
}
