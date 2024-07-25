package com.example.demo.service.work;

import com.example.demo.constant.ProgressStatus;
import com.example.demo.constant.ProjectMemberStatus;
import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.dto.trust_score.AddPointDto;
import com.example.demo.dto.work.request.*;
import com.example.demo.dto.work.response.WorkReadResponseDto;
import com.example.demo.global.exception.customexception.PageNationCustomException;
import com.example.demo.global.exception.customexception.ProjectMemberCustomException;
import com.example.demo.global.exception.customexception.WorkCustomException;
import com.example.demo.model.alert.Alert;
import com.example.demo.model.milestone.Milestone;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.user.User;
import com.example.demo.model.work.Work;
import com.example.demo.service.alert.AlertService;
import com.example.demo.service.milestone.MilestoneService;
import com.example.demo.service.project.ProjectMemberService;
import com.example.demo.service.project.ProjectService;
import com.example.demo.service.trust_score.TrustScoreService;
import com.example.demo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkFacade {
    private final WorkService workService;
    private final ProjectService projectService;
    private final MilestoneService milestoneService;
    private final UserService userService;
    private final AlertService alertService;
    private final TrustScoreService trustScoreService;
    private final ProjectMemberService projectMemberService;

    public void create(
            Long userId, Long projectId, Long milestoneId, WorkCreateRequestDto workCreateRequestDto) {
        Project project = projectService.findById(projectId);
        Milestone milestone = milestoneService.findById(milestoneId);
        User user = userService.findById(userId);
        Optional<ProjectMember> projectMember =
                projectMemberService.findProjectMemberByProjectAndUser(project, user);

        if(projectMember.isEmpty()) throw ProjectMemberCustomException.NOT_FOUND_PROJECT_MEMBER;

        ProjectMember assignedProjectMember = projectMemberService.findById(workCreateRequestDto.getAssignedUserId());

        Work work = workCreateRequestDto.toWorkEntity(project, milestone, assignedProjectMember.getUser(), projectMember.get());

        workService.save(work);
    }

    @Transactional(readOnly = true)
    public WorkReadResponseDto getOne(Long workId) {
        Work work = workService.findById(workId);
        User assignedUser = work.getAssignedUserId();
        Optional<ProjectMember> projectMember = projectMemberService.findProjectMemberByProjectAndUser(work.getProject(), assignedUser);
        if(projectMember.isEmpty()) throw ProjectMemberCustomException.NOT_FOUND_PROJECT_MEMBER;

        return WorkReadResponseDto.of(work, projectMember.get(), assignedUser);
    }

    @Transactional(readOnly = true)
    public List<WorkReadResponseDto> getAllByProject(Long projectId) {
        Project project = projectService.findById(projectId);
        List<Work> works = workService.findWorksByProject(project);

        List<WorkReadResponseDto> workReadResponseDtos = new ArrayList<>();
        for (Work work : works) {
            User assignedUser = work.getAssignedUserId();
            Optional<ProjectMember> projectMember = projectMemberService.findProjectMemberByProjectAndUser(project, assignedUser);
            if(projectMember.isEmpty()) throw ProjectMemberCustomException.NOT_FOUND_PROJECT_MEMBER;

            WorkReadResponseDto workReadResponseDto = WorkReadResponseDto.of(work, projectMember.get(), assignedUser);
            workReadResponseDtos.add(workReadResponseDto);
        }

        return workReadResponseDtos;
    }

    @Transactional(readOnly = true)
    public PaginationResponseDto getAllByMilestone(Long projectId, Long milestoneId, int pageIndex, int itemCount) {
        Project project = projectService.findById(projectId);
        Milestone milestone = milestoneService.findById(milestoneId);

        if(pageIndex < 0) {
            throw PageNationCustomException.INVALID_PAGE_NUMBER;
        }

        if(itemCount < 1 || itemCount > 6) {
            throw PageNationCustomException.INVALID_PAGE_ITEM_COUNT;
        }

        PaginationResponseDto workPaginationResponse = workService
                .findWorksByProjectAndMilestone(project.getId(), milestone.getId(), PageRequest.of(pageIndex, itemCount));

        return workPaginationResponse;
    }

    /**
     * 업무 수정
     *
     * @param workId
     */
    public void update(Long userId, Long workId, WorkUpdateRequestDto workUpdateRequestDto) {
        Work work = workService.findById(workId);
        User user = userService.findById(userId);
        Optional<ProjectMember> projectMember =
                projectMemberService.findProjectMemberByProjectAndUser(work.getProject(), user);
        if(projectMember.isEmpty()) throw ProjectMemberCustomException.NOT_FOUND_PROJECT_MEMBER;

        // 할당된 회원 정보
        ProjectMember assignedUser = projectMemberService.findById(workUpdateRequestDto.getAssignedUserId());

        work.update(workUpdateRequestDto, projectMember.get(), assignedUser.getUser());
    }

    /**
     * 업무 내용 수정 TODO : 마지막 변경자 바꿔줘야 함.
     *
     * @param workId
     * @param workUpdateContentRequestDto
     */
    public void updateContent(
            Long workId, WorkUpdateContentRequestDto workUpdateContentRequestDto) {
        Work work = workService.findById(workId);
        User user = userService.findById(1L);
        Optional<ProjectMember> projectMember =
                projectMemberService.findProjectMemberByProjectAndUser(work.getProject(), user);
        if(projectMember.isEmpty()) throw ProjectMemberCustomException.NOT_FOUND_PROJECT_MEMBER;

        work.updateContent(workUpdateContentRequestDto, projectMember.get());
    }

    /**
     * 업무 완료 여부 수정 TODO : 마지막 변경자 바꿔줘야 함.
     *
     * @param workId
     * @param workUpdateCompleteStatusRequestDto
     */
    public void updateCompleteStatus(
            Long workId, WorkUpdateCompleteStatusRequestDto workUpdateCompleteStatusRequestDto) {
        Work work = workService.findById(workId);
        User user = userService.findById(1L);
        Optional<ProjectMember> projectMember =
                projectMemberService.findProjectMemberByProjectAndUser(work.getProject(), user);
        if(projectMember.isEmpty()) throw ProjectMemberCustomException.NOT_FOUND_PROJECT_MEMBER;

        work.updateCompleteStatus(workUpdateCompleteStatusRequestDto, projectMember.get());
    }

    public void updateAssignUser(
            Long workId, WorkUpdateAssignUserRequestDto workUpdateAssignUserRequestDto) {
        Work work = workService.findById(workId);
        User user = userService.findById(workUpdateAssignUserRequestDto.getAssignUserId());
        Optional<ProjectMember> projectMember =
                projectMemberService.findProjectMemberByProjectAndUser(work.getProject(), user);
        if(projectMember.isEmpty()) throw ProjectMemberCustomException.NOT_FOUND_PROJECT_MEMBER;

        work.updateAssignedUserId(user, projectMember.get());
    }

    /**
     * 업무 컨펌 (프로젝트 등급과 업무 상태에 따른 신뢰점수 부여 및 신뢰점수 내역 추가)
     *
     * @param userId
     * @param workConfirmRequest
     */
    public void workConfirm(Long userId, WorkConfirmRequestDto workConfirmRequest) {
        User currentUser = userService.findById(userId);
        Alert alert = alertService.findById(workConfirmRequest.getAlertId());
        Project project = alert.getProject();

        // 프로젝트 매니저 확인
        projectMemberService.verifiedProjectManager(project, currentUser);

        Work work = alert.getWork();
        ProgressStatus workStatus = work.getProgressStatus();
        // 업무가 기간만료된 상태라면
        if(workStatus.equals(ProgressStatus.EXPIRED)) {
            workConfirmRequest.updateScoreTypeId(22L);
        }

        // 신뢰점수 부여 DTO
        AddPointDto addPoint = AddPointDto.builder()
                .content(work.getContent() + " " +
                        (workStatus.equals(ProgressStatus.COMPLETION) ? (workConfirmRequest.getScoreTypeId().equals(1L) ? "완수" : "미흡") : "만료")
                )
                .userId(alert.getSendUser().getId())
                .projectId(project.getId())
                .milestoneId(alert.getMilestone().getId())
                .workId(work.getId())
                .scoreTypeId(workConfirmRequest.getScoreTypeId())
                .build();

        // 신뢰점수 부여 및 신뢰점수 내역 추가
        trustScoreService.addPoint(addPoint);
    }

    @Transactional
    public void deleteWork(Long userId, Long workId) {
        User currentUser = userService.findById(userId);
        Work work = workService.findById(workId);
        Project project = work.getProject();

        // 요청한 회원이 해당 프로젝트의 멤버인지 검증
        Optional<ProjectMember> projectMember = projectMemberService.findProjectMemberByProjectAndUser(project, currentUser);
        if(projectMember.isEmpty()) throw ProjectMemberCustomException.NOT_FOUND_PROJECT_MEMBER;

        if(!projectMember.get().getStatus().equals(ProjectMemberStatus.PARTICIPATING) ||
                !project.getId().equals(projectMember.get().getProject().getId())) {
            throw WorkCustomException.NO_PERMISSION_TO_TASK;
        }

        // 해당 업무와 관련된 알림목록 삭제
        alertService.deleteAllByWork(work);

        // 해당 업무 삭제
        workService.delete(work);
    }
}
