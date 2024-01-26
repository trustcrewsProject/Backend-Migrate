package com.example.demo.service.alert;

import com.example.demo.dto.alert.response.AlertInfoResponseDto;
import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.global.exception.customexception.AlertCustomException;
import com.example.demo.model.alert.Alert;
import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import com.example.demo.repository.alert.AlertRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {
    private final AlertRepository alertRepository;

    @Override
    public Alert findById(Long id) {
        return alertRepository.findById(id).orElseThrow(() -> AlertCustomException.NOT_FOUND_ALERT);
    }

    @Override
    public Alert save(Alert alert) {
        return alertRepository.save(alert);
    }

    public PaginationResponseDto findAlertsByProjectId(Project project, int pageIndex, int itemCount) {
        return alertRepository
                .findAlertsByProjectIdOrderByCreateDateDesc(project.getId(), PageRequest.of(pageIndex, itemCount));
    }

    public List<Alert> findRecruitAlertsByProject(Project project) {
        return alertRepository
                .findRecruitAlertsByProject(project)
                .orElseThrow(() -> AlertCustomException.NOT_FOUND_ALERT);
    }

    public List<Alert> findWorkAlertsByProject(Project project) {
        return alertRepository
                .findWorkAlertsByProject(project)
                .orElseThrow(() -> AlertCustomException.NOT_FOUND_ALERT);
    }

    public List<Alert> findCrewAlertsByProject(Project project) {
        return alertRepository
                .findCrewAlertsByProject(project)
                .orElseThrow(() -> AlertCustomException.NOT_FOUND_ALERT);
    }

    @Override
    public void updateAlertStatus(Long alertId) {
        alertRepository.updateAlertStatus(alertId);
    }

    @Override
    public PaginationResponseDto findAlertsBySendUserIdAndType(User user, int pageIndex, int itemCount) {
        return alertRepository
                .findAlertsBySendUserIdAndTypeOrderByCreateDateDesc(user.getId(), PageRequest.of(pageIndex, itemCount));
    }
}
