package com.example.demo.repository.trust_score;

import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.dto.trust_score.ProjectUserHistoryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TrustScoreHistoryRepositoryCustom {
    List<ProjectUserHistoryDto> getProjectUserHistory(Long projectId, Long userId);

    int calculateCurrentScore(Long userId);

    // 특정 프로젝트 멤버의 업무 신뢰점수 이력 목록 조회 (페이징, 최신순 정렬)
    PaginationResponseDto findByProjectAndUserAndWorkIsNotNullOrderByCreateDate(Long projectId, Long userId, Pageable pageable);
}
