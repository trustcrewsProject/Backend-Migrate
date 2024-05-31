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
    public List<AlertInfoResponseDto> findAlertsByProjectIdOrTypeOrderByCreateDateDesc(Long projectId, AlertType type, Pageable pageable) {
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
                                qAlert.updateDate,
                                qAlert.checked_YN
                        )
                )
                .from(qAlert)
                .join(qAlert.project, qProject)
                .leftJoin(qAlert.checkUser, qCheckUser)
                .leftJoin(qAlert.sendUser, qSendUser)
                .leftJoin(qAlert.work, qWork)
                .leftJoin(qAlert.milestone, qMilestone)
                .leftJoin(qAlert.position, qPosition)
                .where(eqProjectId(projectId),
                        eqAlertType(type))
                .orderBy(qAlert.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return content;
    }

    @Override
    public List<AlertSupportedProjectInfoResponseDto> findAlertsBySendUserIdAndTypeOrderByCreateDateDesc(Long sendUserId, Pageable pageable) {
        List<AlertSupportedProjectInfoResponseDto> content = jpaQueryFactory
                .select(
                        Projections.constructor(
                                AlertSupportedProjectInfoResponseDto.class,
                                qAlert.id,
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
                .where(eqSendUserId(sendUserId),
                        eqAlertType(AlertType.RECRUIT))
                .orderBy(qAlert.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return content;
    }

    @Override
    // 알람 전체 개수 조회 (조건)
    public Long countAlertsTotalItem(Long projectId, Long sendUserId, AlertType alertType) {
        return jpaQueryFactory
                .select(qAlert.count())
                .from(qAlert)
                .where(eqProjectId(projectId),
                        eqSendUserId(sendUserId),
                        eqAlertType(alertType))
                .fetchOne();
    }

    private BooleanExpression eqProjectId(Long projectId) {
        if(projectId != null) {
            return qAlert.project.id.eq((projectId));
        }

        return null;
    }

    private BooleanExpression eqSendUserId(Long sendUserId) {
        if(sendUserId != null) {
            return qAlert.sendUser.id.eq(sendUserId);
        }

        return null;
    }

    private BooleanExpression eqAlertType(AlertType alertType) {
        if(alertType != null) {
            if (!alertType.equals(AlertType.RECRUIT) && !alertType.equals(AlertType.WORK)) {
                return qAlert.type.eq(AlertType.CREW_UPDATE)
                        .or(qAlert.type.eq(AlertType.ADD))
                        .or(qAlert.type.eq(AlertType.WITHDRAWAL))
                        .or(qAlert.type.eq(AlertType.FORCED_WITHDRAWAL));
            } else {
                return qAlert.type.eq(alertType);
            }
        }

        return null;
    }
}
