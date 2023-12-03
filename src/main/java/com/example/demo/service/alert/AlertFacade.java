package com.example.demo.service.alert;

import com.example.demo.dto.alert.AlertCreateRequestDto;
import com.example.demo.model.alert.Alert;
import com.example.demo.model.position.Position;
import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import com.example.demo.model.work.Work;
import com.example.demo.service.position.PositionService;
import com.example.demo.service.project.ProjectService;
import com.example.demo.service.user.UserService;
import com.example.demo.service.work.WorkService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AlertFacade {
    private final AlertService alertService;
    private final ProjectService projectService;
    private final UserService userService;
    private final WorkService workService;
    private final PositionService positionService;

    /**
     * 알림 발송하기. TODO : 이메일 발송 jwt token 사용하기.
     *
     * @param alertCreateRequestDto
     */
    public void send(AlertCreateRequestDto alertCreateRequestDto) {
        Project project = projectService.findById(alertCreateRequestDto.getProjectId());
        User checkUser = userService.findById(1L);
        User sendUser = userService.findById(2L);
        Work work = null;
        Position position = null;
        if (StringUtils.hasText(String.valueOf(alertCreateRequestDto.getWorkId()))) {
            work = workService.findById(alertCreateRequestDto.getWorkId());
        }

        if (StringUtils.hasText(String.valueOf(alertCreateRequestDto.getPositionId()))) {
            position = positionService.findById(alertCreateRequestDto.getPositionId());
        }

        Alert alert =
                alertCreateRequestDto.toAlertEntity(project, checkUser, sendUser, work, position);
        alertService.save(alert);
    }

    public List<Alert> getAllByProject(Long projectId) {
        Project project = projectService.findById(projectId);
        return alertService.findAlertsByProjectId(project);
    }

    public List<Alert> getRecruitsByProject(Long projectId) {
        Project project = projectService.findById(projectId);
        return alertService.findRecruitAlertsByProject(project);
    }

    public List<Alert> getWorksByProject(Long projectId) {
        Project project = projectService.findById(projectId);
        return alertService.findWorkAlertsByProject(project);
    }

    public List<Alert> getCrewsByProject(Long projectId) {
        Project project = projectService.findById(projectId);
        return alertService.findCrewAlertsByProject(project);
    }
}
