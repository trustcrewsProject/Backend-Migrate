package com.example.demo.repository.projectAlert.vote.fwithdraw;

import com.example.demo.constant.ProjectAlertType;
import com.example.demo.dto.common.ConstantDepthDto;
import com.example.demo.dto.common.ConstantDto;
import com.example.demo.dto.projectAlert.vote.fwithdraw.VAlertFWResponseDto;
import com.example.demo.global.common.ConstantDepth;
import com.example.demo.model.project.alert.vote.QVAlertFWithdraw;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class VAlertFWithdrawRepositoryImpl implements VAlertFWithdrawRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QVAlertFWithdraw qvAlertFWithdraw = QVAlertFWithdraw.vAlertFWithdraw;

    @Override
    public List<VAlertFWResponseDto> findVAlertFWList(Long projectId, Pageable pageable) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                VAlertFWResponseDto.class,
                                qvAlertFWithdraw.id,
                                qvAlertFWithdraw.voteFWithdraw.id,
                                qvAlertFWithdraw.voteFWithdraw.fwMember.id,
                                Projections.constructor(
                                        ConstantDepthDto.class,
                                        qvAlertFWithdraw.alertType
                                ),
                                qvAlertFWithdraw.alertContents,
                                Projections.constructor(
                                        ConstantDto.class,
                                        qvAlertFWithdraw.voteFWithdraw.voteStatus
                                ),
                                qvAlertFWithdraw.createDate
                        )
                )
                .from(qvAlertFWithdraw)
                .where(qvAlertFWithdraw.project_id.eq(projectId))
                .orderBy(qvAlertFWithdraw.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public Long countVAlertFWList(Long projectId) {
        return jpaQueryFactory
                .select(qvAlertFWithdraw.count())
                .from(qvAlertFWithdraw)
                .where(qvAlertFWithdraw.project_id.eq(projectId))
                .fetchOne();
    }
}
