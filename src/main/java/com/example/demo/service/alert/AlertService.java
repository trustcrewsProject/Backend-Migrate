package com.example.demo.service.alert;

import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.model.alert.Alert;
import com.example.demo.model.project.Project;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface AlertService {

    Alert findById(Long id);

    Alert save(Alert alert);

    PaginationResponseDto findAlertsByProjectId(Project project, int pageIndex, int itemCount);

    List<Alert> findRecruitAlertsByProject(Project project);

    List<Alert> findWorkAlertsByProject(Project project);

    List<Alert> findCrewAlertsByProject(Project project);

    void updateAlertStatus(Long alertId);
}