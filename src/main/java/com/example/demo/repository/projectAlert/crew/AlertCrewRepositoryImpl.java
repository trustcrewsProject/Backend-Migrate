package com.example.demo.repository.projectAlert.crew;

import com.example.demo.model.project.alert.crew.AlertCrew;
import com.example.demo.model.project.alert.crew.QAlertCrew;
import com.example.demo.service.projectAlert.crew.AlertCrewService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AlertCrewRepositoryImpl implements AlertCrewRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QAlertCrew qAlertCrew = QAlertCrew.alertCrew;

    @Override
    public List<AlertCrew> findAlertCrewsByProject_id(Long projectId, Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(qAlertCrew)
                .where(qAlertCrew.project_id.eq(projectId))
                .orderBy(qAlertCrew.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public Long countAlertCrewsByProject_id(Long projectId) {
        return jpaQueryFactory
                .select(qAlertCrew.count())
                .from(qAlertCrew)
                .where(qAlertCrew.project_id.eq(projectId))
                .fetchOne();
    }
}
