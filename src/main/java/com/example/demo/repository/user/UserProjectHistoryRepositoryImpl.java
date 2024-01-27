package com.example.demo.repository.user;

import static com.example.demo.model.project.QProject.project;
import static com.example.demo.model.user.QUser.user;
import static com.example.demo.model.user.QUserProjectHistory.userProjectHistory;

import com.example.demo.constant.UserProjectHistoryStatus;
import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.dto.user.response.UserProjectHistoryInfoResponseDto;
import com.example.demo.model.user.UserProjectHistory;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserProjectHistoryRepositoryImpl implements UserProjectHistoryRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public PaginationResponseDto findAllByUserIdOrderByUpdateDateDesc(
            Long userId, Pageable pageable) {
        // 요청한 페이지의 회원 프로젝트 이력 목록 조회
        List<UserProjectHistoryInfoResponseDto> content =
                jpaQueryFactory
                        .select(
                                Projections.constructor(
                                        UserProjectHistoryInfoResponseDto.class,
                                        userProjectHistory.id,
                                        userProjectHistory.project.id,
                                        userProjectHistory.status,
                                        userProjectHistory.project.name,
                                        userProjectHistory.updateDate))
                        .from(userProjectHistory)
                        .leftJoin(userProjectHistory.project, project)
                        .leftJoin(userProjectHistory.user, user)
                        .where(userProjectHistory.user.id.eq(userId))
                        .orderBy(userProjectHistory.updateDate.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        // 사용자 프로젝트 이력 총 개수 조회
        long totalPages = countUserProjectHistoryByUserId(userId);

        return PaginationResponseDto.of(content, totalPages);
    }

    @Override
    public Long countUserProjectHistoryByUserId(Long userId) {
        // 회원의 전체 프로젝트 이력 개수 조회
        return jpaQueryFactory
                .select(userProjectHistory.count())
                .from(userProjectHistory)
                .leftJoin(userProjectHistory.user, user)
                .where(userProjectHistory.user.id.eq(userId))
                .fetchOne();
    }

    @Override
    public Long countParticipatesUserProjectHistoryByUserId(Long userId) {
        // 회원 참여중인 프로젝트 이력 개수 조회
        return jpaQueryFactory
                .select(userProjectHistory.count())
                .from(userProjectHistory)
                .where(userProjectHistory.user.id.eq(userId),
                        userProjectHistory.status.eq(UserProjectHistoryStatus.PARTICIPATING))
                .fetchOne();
    }

    @Override
    public List<UserProjectHistory> findAllUserParticipates(Long userId, Pageable pageable) {
        List<UserProjectHistory> results = jpaQueryFactory
                .selectFrom(userProjectHistory)
                .where(userProjectHistory.user.id.eq(userId),
                        userProjectHistory.status.eq(UserProjectHistoryStatus.PARTICIPATING))
                .orderBy(userProjectHistory.startDate.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return results;
    }
}
