package com.example.demo.service.trust_score;

import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;

public interface TrustScoreHistoryService {

    // 업무 관련 신뢰점수 이력 목록 조회
    PaginationResponseDto getWorkTrustScoreHistories(Project project, User user, int pageIndex, int itemCount);
}
