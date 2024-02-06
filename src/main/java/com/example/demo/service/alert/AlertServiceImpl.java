package com.example.demo.service.alert;

import com.example.demo.constant.AlertType;
import com.example.demo.dto.alert.response.AlertInfoResponseDto;
import com.example.demo.dto.alert.response.AlertSupportedProjectInfoResponseDto;
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

    public PaginationResponseDto findAlertsByProjectIdAndType(Project project, AlertType type, int pageIndex, int itemCount) {
        List<AlertInfoResponseDto> content = alertRepository
                                                .findAlertsByProjectIdOrTypeOrderByCreateDateDesc(project.getId(), type, PageRequest.of(pageIndex, itemCount));

        long totalPages = alertRepository.countAlertsTotalItem(project.getId(), null, type);

        return PaginationResponseDto.of(content, totalPages);
    }

    @Override
    public void updateAlertStatus(Long alertId) {
        Alert alert = alertRepository
                .findById(alertId)
                .orElseThrow(() -> AlertCustomException.NOT_FOUND_ALERT);

        alert.updateCheckedStatusIsTrue();
    }

    @Override
    public PaginationResponseDto findAlertsBySendUserIdAndType(User user, int pageIndex, int itemCount) {
        List<AlertSupportedProjectInfoResponseDto> content = alertRepository
                                                                .findAlertsBySendUserIdAndTypeOrderByCreateDateDesc(user.getId(), PageRequest.of(pageIndex, itemCount));

        long totalPages = alertRepository.countAlertsTotalItem(null, user.getId(), AlertType.RECRUIT);

        return PaginationResponseDto.of(content, totalPages);
    }
}
