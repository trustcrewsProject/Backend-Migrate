package com.example.demo.repository.alert;

import com.example.demo.constant.AlertType;
import com.example.demo.dto.alert.response.AlertInfoResponseDto;
import com.example.demo.dto.alert.response.AlertSupportedProjectInfoResponseDto;
import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.dto.position.response.PositionInfoResponseDto;
import com.example.demo.dto.project.response.ProjectSimpleInfoResponseDto;
import com.example.demo.model.alert.QAlert;
import com.example.demo.model.milestone.QMilestone;
import com.example.demo.model.position.QPosition;
import com.example.demo.model.project.QProject;
import com.example.demo.model.user.QUser;
import com.example.demo.model.work.QWork;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AlertRepositoryImpl implements AlertRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;
    private final QAlert qAlert = QAlert.alert;
    private final QProject qProject = QProject.project;
    private final QPosition qPosition = QPosition.position;
    private final QUser qCheckUser = new QUser("checkUser");
    private final QUser qSendUser = new QUser("sendUser");
    private final QWork qWork = QWork.work;
    private final QMilestone qMilestone = QMilestone.milestone;

    @Override
    public PaginationResponseDto findAlertsByProjectIdOrderByCreateDateDesc(Long projectId, Pageable pageable) {
        List<AlertInfoResponseDto> content = jpaQueryFactory
                .select(
                        Projections.constructor(
                                AlertInfoResponseDto.class,
                                qAlert.id,
                                qProject.id,
                                qCheckUser.id,
                                qSendUser.id,
                                qWork.id,
                                qMilestone.id,
                                Projections.constructor(
                                        PositionInfoResponseDto.class,
                                        qPosition.id,
                                        qPosition.name
                                ),
                                qAlert.content,
                                qAlert.type,
                                qAlert.createDate,
                                qAlert.updateDate
                        )
                )
                .from(qAlert)
                .join(qAlert.project, qProject)
                .leftJoin(qAlert.checkUser, qCheckUser)
                .leftJoin(qAlert.sendUser, qSendUser)
                .leftJoin(qAlert.work, qWork)
                .leftJoin(qAlert.milestone, qMilestone)
                .leftJoin(qAlert.position, qPosition)
                .where(qProject.id.eq(projectId))
                .orderBy(qAlert.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalPages = getAlertsTotalItemCount(projectId, null, null);

        return PaginationResponseDto.of(content, totalPages);
    }

    @Override
    public PaginationResponseDto findAlertsBySendUserIdAndTypeOrderByCreateDateDesc(Long sendUserId, Pageable pageable) {
        List<AlertSupportedProjectInfoResponseDto> content = jpaQueryFactory
                .select(
                        Projections.constructor(
                                AlertSupportedProjectInfoResponseDto.class,
                                Projections.constructor(
                                        ProjectSimpleInfoResponseDto.class,
                                        qProject.id,
                                        qProject.name
                                ),
                                Projections.constructor(
                                        PositionInfoResponseDto.class,
                                        qPosition.id,
                                        qPosition.name
                                ),
                                qAlert.projectConfirmResult
                        )
                )
                .from(qAlert)
                .join(qAlert.project, qProject)
                .join(qAlert.position, qPosition)
                .join(qAlert.sendUser, qSendUser)
                .where(qSendUser.id.eq(sendUserId),
                        qAlert.type.eq(AlertType.RECRUIT))
                .orderBy(qAlert.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalPages = getAlertsTotalItemCount(null, sendUserId, AlertType.RECRUIT);

        return PaginationResponseDto.of(content, totalPages);
    }

    // 알람 전체 개수 조회 (조건)
    private long getAlertsTotalItemCount(Long projectId, Long sendUserId, AlertType alertType) {
        return jpaQueryFactory
                .select(qAlert.count())
                .from(qAlert)
                .where(eq(projectId, sendUserId, alertType))
                .fetchOne();
    }

    private BooleanBuilder eq(Long projectId, Long sendUserId, AlertType alertType) {
        BooleanBuilder builder = new BooleanBuilder();

        if(projectId != null) {
            builder.and(qAlert.project.id.eq(projectId));
        }

        if(sendUserId != null) {
            builder.and(qAlert.sendUser.id.eq(sendUserId));
        }

        if(alertType != null) {
            builder.and(qAlert.type.eq(alertType));
        }

        return builder;
    }
}
