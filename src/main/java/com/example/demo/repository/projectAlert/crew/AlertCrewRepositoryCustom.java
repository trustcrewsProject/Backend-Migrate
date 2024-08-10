package com.example.demo.repository.projectAlert.crew;

import com.example.demo.model.project.alert.crew.AlertCrew;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AlertCrewRepositoryCustom {
    List<AlertCrew> findAlertCrewsByProject_id(Long projectId, Pageable pageable);

    Long countAlertCrewsByProject_id(Long projectId);
}
