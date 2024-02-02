package com.example.demo.service.alert;

import com.example.demo.constant.AlertType;
import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.model.alert.Alert;
import com.example.demo.model.project.Project;
import java.util.List;

import com.example.demo.model.user.User;
import org.springframework.stereotype.Service;

@Service
public interface AlertService {

    Alert findById(Long id);

    Alert save(Alert alert);

    PaginationResponseDto findAlertsByProjectIdAndType(Project project, AlertType type, int pageIndex, int itemCount);

    void updateAlertStatus(Long alertId);

    PaginationResponseDto findAlertsBySendUserIdAndType(User user, int pageIndex, int itemCount);
}