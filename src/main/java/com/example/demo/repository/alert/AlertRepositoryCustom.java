package com.example.demo.repository.alert;

import com.example.demo.dto.alert.response.AlertInfoResponseDto;
import com.example.demo.dto.common.PaginationResponseDto;
import org.springframework.data.domain.Pageable;

public interface AlertRepositoryCustom {

    // 프로젝트 알람 목록 조회(페이징, 최신순 정렬)
    PaginationResponseDto findAlertsByProjectIdOrderByCreateDateDesc(Long projectId, Pageable pageable);
}
