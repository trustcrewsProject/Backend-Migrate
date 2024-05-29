package com.example.demo.service.alert;

import com.example.demo.constant.AlertType;
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
import com.example.demo.service.project.ProjectMemberService;
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
    private final ProjectMemberService projectMemberService;
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
        User checkUser = null;
        User sendUser = null;
        Work work = null;
        Milestone milestone = null;
        Position position = null;

        if (alertCreateRequestDto.getCheckUserId() != null) {
            checkUser = userService.findById(alertCreateRequestDto.getCheckUserId());
        }

        if(alertCreateRequestDto.getSendUserId() != null) {
            // 클라이언트로부터 해당 회원의 project_member_id 정보를 요청받음
            sendUser = projectMemberService.findById(alertCreateRequestDto.getSendUserId()).getUser();
        }

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
        verifiedPageIndex(pageIndex);
        verifiedItemCount(itemCount);

        Project project = projectService.findById(projectId);
        PaginationResponseDto alerts = alertService.findAlertsByProjectIdAndType(project, null, pageIndex, itemCount);

        return alerts;
    }

    public PaginationResponseDto getRecruitsByProject(Long projectId, int pageIndex, int itemCount) {
        verifiedPageIndex(pageIndex);
        verifiedItemCount(itemCount);

        Project project = projectService.findById(projectId);
        PaginationResponseDto alerts = alertService
                .findAlertsByProjectIdAndType(project, AlertType.RECRUIT, pageIndex, itemCount);

        return alerts;
    }

    public PaginationResponseDto getWorksByProject(Long projectId, int pageIndex, int itemCount) {
        verifiedPageIndex(pageIndex);
        verifiedItemCount(itemCount);

        Project project = projectService.findById(projectId);
        PaginationResponseDto alerts = alertService
                .findAlertsByProjectIdAndType(project, AlertType.WORK, pageIndex, itemCount);

        return alerts;
    }

    public PaginationResponseDto getCrewsByProject(Long projectId, int pageIndex, int itemCount) {
        verifiedPageIndex(pageIndex);
        verifiedItemCount(itemCount);

        Project project = projectService.findById(projectId);
        PaginationResponseDto alerts = alertService
                .findAlertsByProjectIdAndType(project, AlertType.CREW_UPDATE, pageIndex, itemCount);

        return alerts;
    }

    @Transactional(readOnly = true)
    public PaginationResponseDto getSupportedProjects(Long userId, int pageIndex, int itemCount) {
        if(pageIndex < 0) {
            throw PageNationCustomException.INVALID_PAGE_NUMBER;
        }

        User currentUser = userService.findById(userId);

        return alertService.findAlertsBySendUserIdAndType(currentUser, pageIndex, itemCount);
    }

    private void verifiedPageIndex(int pageIndex) {
        if(pageIndex < 0) {
            throw PageNationCustomException.INVALID_PAGE_NUMBER;
        }
    }

    private void verifiedItemCount(int itemCount) {
        if(itemCount < 0 || itemCount > 8) {
            throw PageNationCustomException.INVALID_PAGE_ITEM_COUNT;
        }
    }
}
