package com.example.demo.repository.work;

import org.springframework.data.domain.Pageable;

public interface WorkRepositoryCustom {

    // 프로젝트 > 마일스톤 > 업무 리스트 조회 (시작날짜 기준 오름차순 정렬, 페이징)
    WorkPaginationResponseDto findWorkByProjectIdAndMilestoneIdOrderByStartDateAsc(Long projectId, Long milestoneId, Pageable pageable);

    // 특정 프로젝트에 할당된 프로젝트 멤버의 업무 내역 + 업무 별 신뢰점수 내역 조회 (시작날짜 기준 최신순 정렬, 페이징)
    ProjectMemberWorksPaginationResponseDto findWorksWithTrustScoreHistoryByProjectIdAndAssignedUserIdOrderByStartDateDesc(Long projectId, Long assignedUserId, Pageable pageable);
}
