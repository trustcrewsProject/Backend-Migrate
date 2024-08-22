package com.example.demo.repository.projectApply;

import com.example.demo.constant.ProjectApplyStatus;
import com.example.demo.dto.common.ConstantDto;
import com.example.demo.dto.projectApply.ProjectApplyResponseDto;
import com.example.demo.model.position.QPosition;
import com.example.demo.model.project.QProject;
import com.example.demo.model.projectApply.QProjectApply;
import com.example.demo.model.user.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProjectApplyRepositoryImpl implements ProjectApplyRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    private final QProjectApply qProjectApply = QProjectApply.projectApply;

    private final QProject qProject = QProject.project;
    private final QPosition qPosition = QPosition.position;
    private final QUser qUser = QUser.user;

    @Override
    public List<ProjectApplyResponseDto> findProjectAppliesByUserId(Long userId, Pageable pageable) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                ProjectApplyResponseDto.class,
                                qProjectApply.id,
                                qProject.id,
                                qProject.name,
                                qPosition.name,
                                Projections.constructor(
                                        ConstantDto.class,
                                       qProjectApply.status
                                ),
                                qProjectApply.apply_message,
                                qProjectApply.createDate
                        )
                )
                .from(qProjectApply)
                .join(qProjectApply.project, qProject)
                .join(qProjectApply.user, qUser)
                .join(qProjectApply.position, qPosition)
                .where(qProjectApply.user.id.eq(userId))
                .orderBy(qProjectApply.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public Long countProjectAppliesByUserId(Long userId) {
        return jpaQueryFactory
                .select(qProjectApply.count())
                .from(qProjectApply)
                .where(qProjectApply.user.id.eq(userId))
                .fetchOne();
    }

    @Override
    public Long countUserProjectApplying(Long projectId, Long userId) {
        return jpaQueryFactory
                .select(qProjectApply.count())
                .from(qProjectApply)
                .where(qProjectApply.project.id.eq(projectId),
                        qProjectApply.user.id.eq(userId),
                        qProjectApply.status.eq(ProjectApplyStatus.PAS1001))
                .fetchOne();
    }

    @Override
    public void udpateProjectApplyStatus(Long applyId, ProjectApplyStatus projectApplyStatus) {
        jpaQueryFactory
                .update(qProjectApply)
                .set(qProjectApply.status, projectApplyStatus)
                .where(qProjectApply.id.eq(applyId))
                .execute();
    }
}
