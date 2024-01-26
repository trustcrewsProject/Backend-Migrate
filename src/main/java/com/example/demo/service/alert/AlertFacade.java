package com.example.demo.service.alert;

import com.example.demo.dto.alert.AlertCreateRequestDto;
import com.example.demo.dto.alert.response.AlertInfoResponseDto;
import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.global.exception.customexception.PageNationCustomException;
import com.example.demo.model.alert.Alert;
import com.example.demo.model.milestone.Milestone;
import com.example.demo.model.position.Position;
import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import com.example.demo.model.work.Work;
import com.example.demo.service.milestone.MilestoneService;
import com.example.demo.service.position.PositionService;
import com.example.demo.service.project.ProjectService;
import com.example.demo.service.user.UserService;
import com.example.demo.service.work.WorkService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlertFacade {
    private final AlertService alertService;
    private final ProjectService projectService;
    private final UserService userService;
    private final WorkService workService;
    private final PositionService positionService;
    private final MilestoneService milestoneService;

    /**
     * 알림 발송하기. TODO : 이메일 발송 jwt token 사용하기.
     *
     * @param alertCreateRequestDto
     */
    public void send(AlertCreateRequestDto alertCreateRequestDto) {
        Project project = projectService.findById(alertCreateRequestDto.getProjectId());
        User checkUser = userService.findById(alertCreateRequestDto.getCheckUserId());
        User sendUser = userService.findById(alertCreateRequestDto.getSendUserId());
        Work work = null;
        Milestone milestone = null;
        Position position = null;
        if (alertCreateRequestDto.getWorkId() != null) {
            work = workService.findById(alertCreateRequestDto.getWorkId());
        }

        if (alertCreateRequestDto.getMilestoneId() != null) {
            milestone = milestoneService.findById(alertCreateRequestDto.getMilestoneId());
        }

        if (alertCreateRequestDto.getPositionId() != null) {
            position = positionService.findById(alertCreateRequestDto.getPositionId());
        }

        Alert alert =
                alertCreateRequestDto.toAlertEntity(
                        project, checkUser, sendUser, work, milestone, position);
        alertService.save(alert);
    }

    public PaginationResponseDto getAllByProject(Long projectId, int pageIndex, int itemCount) {
        Project project = projectService.findById(projectId);

        if(pageIndex < 0) {
            throw PageNationCustomException.INVALID_PAGE_NUMBER;
        }

        if(itemCount < 0 || itemCount > 8) {
            throw PageNationCustomException.INVALID_PAGE_ITEM_COUNT;
        }

        PaginationResponseDto alerts = alertService.findAlertsByProjectId(project, pageIndex, itemCount);

        return alerts;
    }

    public List<AlertInfoResponseDto> getRecruitsByProject(Long projectId) {
        Project project = projectService.findById(projectId);
        List<Alert> alerts = alertService.findRecruitAlertsByProject(project);
        List<AlertInfoResponseDto> alertInfoResponseDtos = new ArrayList<>();

        for (Alert alert : alerts) {
            alertInfoResponseDtos.add(AlertInfoResponseDto.of(alert, alert.getPosition()));
        }

        return alertInfoResponseDtos;
    }

    public List<AlertInfoResponseDto> getWorksByProject(Long projectId) {
        Project project = projectService.findById(projectId);
        List<Alert> alerts = alertService.findWorkAlertsByProject(project);
        List<AlertInfoResponseDto> alertInfoResponseDtos = new ArrayList<>();

        for (Alert alert : alerts) {
            alertInfoResponseDtos.add(AlertInfoResponseDto.of(alert, alert.getPosition()));
        }

        return alertInfoResponseDtos;
    }

    public List<AlertInfoResponseDto> getCrewsByProject(Long projectId) {
        Project project = projectService.findById(projectId);
        List<Alert> alerts = alertService.findCrewAlertsByProject(project);
        List<AlertInfoResponseDto> alertInfoResponseDtos = new ArrayList<>();

        for (Alert alert : alerts) {
            alertInfoResponseDtos.add(AlertInfoResponseDto.of(alert, alert.getPosition()));
        }

        return alertInfoResponseDtos;
    }

    @Transactional(readOnly = true)
    public PaginationResponseDto getSupportedProjects(Long userId, int pageIndex, int itemCount) {
        if(pageIndex < 0) {
            throw PageNationCustomException.INVALID_PAGE_NUMBER;
        }

        User currentUser = userService.findById(userId);

        return alertService.findAlertsBySendUserIdAndType(currentUser, pageIndex, itemCount);
    }
}
