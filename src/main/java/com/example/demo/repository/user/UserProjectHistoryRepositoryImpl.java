package com.example.demo.repository.user;

import static com.example.demo.model.user.QUser.user;
import static com.example.demo.model.user.QUserProjectHistory.userProjectHistory;
import static com.example.demo.model.project.QProject.project;

import com.example.demo.dto.user.response.UserProjectHistoryInfoResponseDto;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserProjectHistoryRepositoryImpl implements UserProjectHistoryRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long countUserProjectHistoryByUserId(Long userId) {
        // 회원의 전체 프로젝트 이력 개수 조회
        return  jpaQueryFactory
                .select(userProjectHistory.count())
                .from(userProjectHistory)
                .leftJoin(userProjectHistory.user, user)
                .where(userProjectHistory.user.id.eq(userId))
                .fetchOne();
    }

    @Override
    public List<UserProjectHistoryInfoResponseDto> findAllByUserIdOrderByUpdateDateDesc(Long userId, Pageable pageable) {
        // 요청한 페이지의 회원 프로젝트 이력 목록 조회
        List<UserProjectHistoryInfoResponseDto> content = jpaQueryFactory
                .select(
                        Projections.constructor(
                                UserProjectHistoryInfoResponseDto.class,
                                userProjectHistory.id,
                                userProjectHistory.status,
                                userProjectHistory.project.name,
                                Expressions.stringTemplate("DATE_FORMAT(userProjectHistory.updateDate, '%Y %b %d %H:%i:%s')", userProjectHistory.updateDate)
                        )
                )
                .from(userProjectHistory)
                .leftJoin(userProjectHistory.project, project)
                .leftJoin(userProjectHistory.user, user)
                .where(userProjectHistory.user.id.eq(userId))
                .orderBy(userProjectHistory.updateDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return content;
    }
}
