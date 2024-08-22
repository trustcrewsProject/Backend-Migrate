package com.example.demo.repository.projectAlert.crew;

import com.example.demo.dto.projectAlert.crew.AlertCrewResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AlertCrewRepositoryCustom {
    List<AlertCrewResponseDto> findAlertCrewsByProject_id(Long projectId, Pageable pageable);

    Long countAlertCrewsByProject_id(Long projectId);
}
