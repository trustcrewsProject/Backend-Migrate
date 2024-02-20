package com.example.demo.repository.work;

import com.example.demo.dto.common.PaginationResponseDto;
import org.springframework.data.domain.Pageable;

public interface WorkRepositoryCustom {

    // 프로젝트 > 마일스톤 > 업무 리스트 조회 (시작날짜 기준 오름차순 정렬, 페이징)
    PaginationResponseDto findWorkByProjectIdAndMilestoneIdOrderByStartDateAsc(Long projectId, Long milestoneId, Pageable pageable);
}
