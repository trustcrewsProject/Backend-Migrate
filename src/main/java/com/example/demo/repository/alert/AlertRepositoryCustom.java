package com.example.demo.repository.alert;

import com.example.demo.constant.AlertType;
import com.example.demo.dto.alert.response.AlertInfoResponseDto;
import com.example.demo.dto.alert.response.AlertSupportedProjectInfoResponseDto;
import com.example.demo.dto.common.PaginationResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AlertRepositoryCustom {

    // 프로젝트 알람 목록 조회(페이징, 최신순 정렬)
    List<AlertInfoResponseDto> findAlertsByProjectIdOrTypeOrderByCreateDateDesc(Long projectId, AlertType type, Pageable pageable);

    // 사용자가 지원한 프로젝트 알람 목록 조회(페이징, 최신순 정렬)
    List<AlertSupportedProjectInfoResponseDto> findAlertsBySendUserIdAndTypeOrderByCreateDateDesc(Long sendUserId, Pageable pageable);

    // 조건별 알림 목록 개수 조회
    Long countAlertsTotalItem(Long projectId, Long sendUserId, AlertType type);
}
