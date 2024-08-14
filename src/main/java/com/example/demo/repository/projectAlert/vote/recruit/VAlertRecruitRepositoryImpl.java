package com.example.demo.repository.projectAlert.vote.recruit;

import com.example.demo.dto.common.ConstantDepthDto;
import com.example.demo.dto.projectAlert.vote.recruit.ProjectRecruitAlertResponseDto;
import com.example.demo.model.project.alert.vote.QVAlertRecruit;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class VAlertRecruitRepositoryImpl implements VAlertRecruitRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    private final QVAlertRecruit qvAlertRecruit = QVAlertRecruit.vAlertRecruit;

    @Override
    public List<ProjectRecruitAlertResponseDto> findProjectRecruitAlertList(Long projectId, Pageable pageable) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                ProjectRecruitAlertResponseDto.class,
                                qvAlertRecruit.id,
                                qvAlertRecruit.voteRecruit.id,
                                qvAlertRecruit.voteRecruit.projectApply.id,
                                Projections.constructor(
                                        ConstantDepthDto.class,
                                        qvAlertRecruit.alertType
                                ),
                                qvAlertRecruit.alertContents,
                                qvAlertRecruit.voteRecruit.voteStatus,
                                qvAlertRecruit.createDate
                        )
                )
                .from(qvAlertRecruit)
                .where(qvAlertRecruit.project_id.eq(projectId))
                .orderBy(qvAlertRecruit.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public Long countProjectRecruitAlerts(Long projectId) {
        return jpaQueryFactory
                .select(qvAlertRecruit.count())
                .from(qvAlertRecruit)
                .where(qvAlertRecruit.project_id.eq(projectId))
                .fetchOne();
    }


}
