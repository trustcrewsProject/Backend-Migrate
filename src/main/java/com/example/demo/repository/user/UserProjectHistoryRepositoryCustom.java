package com.example.demo.repository.user;

import java.util.List;

import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.model.user.UserProjectHistory;
import org.springframework.data.domain.Pageable;

public interface UserProjectHistoryRepositoryCustom {

    // 회원 프로젝트 이력 목록 조회 (수정날짜 기준 최신순 정렬, 페이징 offset 방법 활용)
    PaginationResponseDto findAllByUserIdOrderByUpdateDateDesc(
            Long userId, Pageable pageable);

    // 회원 프로젝트 이력 전체 개수 조회
    Long countUserProjectHistoryByUserId(Long userId);

    // 회원 참여중인 프로젝트 이력 개수 조회
    Long countParticipatesUserProjectHistoryByUserId(Long userId);

    // 회원 참여중인 프로젝트 이력 조회 (프로젝트 시작날짜 기준 오름차순 정렬, 페이징 offset 방법 활용)
    List<UserProjectHistory> findAllUserParticipates(Long userId, Pageable pageable);
}
