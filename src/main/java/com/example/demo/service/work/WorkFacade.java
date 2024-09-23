package com.example.demo.service.work;

import com.example.demo.constant.*;
import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.dto.trust_score.AddPointDto;
import com.example.demo.dto.work.request.WorkCompleteRequestDto;
import com.example.demo.dto.work.request.WorkCreateRequestDto;
import com.example.demo.dto.work.request.WorkDeleteRequestDto;
import com.example.demo.dto.work.request.WorkUpdateRequestDto;
import com.example.demo.dto.work.response.WorkReadResponseDto;
import com.example.demo.global.exception.customexception.PageNationCustomException;
import com.example.demo.global.exception.customexception.ProjectCustomException;
import com.example.demo.global.exception.customexception.ProjectMemberCustomException;
import com.example.demo.global.exception.customexception.WorkCustomException;
import com.example.demo.model.milestone.Milestone;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.trust_grade.TrustGrade;
import com.example.demo.model.trust_score.TrustScore;
import com.example.demo.model.user.User;
import com.example.demo.model.work.Work;
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
    private final TrustScoreService trustScoreService;
    private final ProjectMemberService projectMemberService;

    public void create(
            Long userId, WorkCreateRequestDto workCreateRequestDto
    ) {

        int count = workService.countWorksByMilestoneId(workCreateRequestDto.getMilestoneId());
        if (count >= CreateLimitCnt.TASK.getCount()) {
            throw WorkCustomException.CREATE_EXCEEDED_WORK;
        }

        Project project = projectService.findById(workCreateRequestDto.getProjectId());
        Milestone milestone = milestoneService.findById(workCreateRequestDto.getMilestoneId());
        User user = userService.findById(userId);
        Optional<ProjectMember> projectMember =
                projectMemberService.findProjectMemberByProjectAndUser(project, user);

        if (projectMember.isEmpty()) throw ProjectMemberCustomException.NOT_FOUND_PROJECT_MEMBER;

        ProjectMember assignedProjectMember = projectMemberService.findById(workCreateRequestDto.getAssignedUserId());

        Work work = workCreateRequestDto.toWorkEntity(project, milestone, assignedProjectMember.getUser(), projectMember.get());

        workService.save(work);
    }

    @Transactional(readOnly = true)
    public WorkReadResponseDto getOne(Long workId) {
        Work work = workService.findById(workId);
        User assignedUser = work.getAssignedUserId();
        Optional<ProjectMember> projectMember = projectMemberService.findProjectMemberByProjectAndUser(work.getProject(), assignedUser);
        if (projectMember.isEmpty()) throw ProjectMemberCustomException.NOT_FOUND_PROJECT_MEMBER;

        return WorkReadResponseDto.of(work, projectMember.get(), assignedUser);
    }

    @Transactional(readOnly = true)
    public List<WorkReadResponseDto> getAllByProject(Long userId, Long projectId) {
        validateProjectMember(userId, projectId);

        Project project = projectService.findById(projectId);
        List<Work> works = workService.findWorksByProject(project);

        List<WorkReadResponseDto> workReadResponseDtos = new ArrayList<>();
        for (Work work : works) {
            User assignedUser = work.getAssignedUserId();
            Optional<ProjectMember> projectMember = projectMemberService.findProjectMemberByProjectAndUser(project, assignedUser);
            if (projectMember.isEmpty()) throw ProjectMemberCustomException.NOT_FOUND_PROJECT_MEMBER;

            WorkReadResponseDto workReadResponseDto = WorkReadResponseDto.of(work, projectMember.get(), assignedUser);
            workReadResponseDtos.add(workReadResponseDto);
        }

        return workReadResponseDtos;
    }

    @Transactional(readOnly = true)
    public PaginationResponseDto getAllByMilestone(Long projectId, Long milestoneId, int pageIndex, int itemCount) {

        if (pageIndex < 0) {
            throw PageNationCustomException.INVALID_PAGE_NUMBER;
        }

        if (itemCount < 1 || itemCount > 6) {
            throw PageNationCustomException.INVALID_PAGE_ITEM_COUNT;
        }

        PaginationResponseDto workPaginationResponse = workService
                .findWorksByProjectAndMilestone(projectId, milestoneId, PageRequest.of(pageIndex, itemCount));

        return workPaginationResponse;
    }

    /**
     * 업무 수정
     */
    public void update(Long currentUserId, WorkUpdateRequestDto workUpdateRequestDto) {
        Work work = workService.findById(workUpdateRequestDto.getWorkId());

        // 사용자가 프로젝트 멤버인지 확인
        validateProjectMember(currentUserId, work.getProject().getId());

        // 사용자가 업무 담당 멤버 or 매니저 권한인지 확인
        User currentAssignedUser = work.getAssignedUserId();
        if (!currentUserId.equals(currentAssignedUser.getId())
                && !workUpdateRequestDto.getAuthMap().getCode().equals(ProjectMemberAuth.PAUTH_1001.getCode())
        ) {
            throw WorkCustomException.NO_PERMISSION_TO_TASK;
        }

        ProjectMember updatedAssignedProjectMember =
                projectMemberService.findById(workUpdateRequestDto.getAssignedUserId());

        work.update(workUpdateRequestDto, updatedAssignedProjectMember.getUser());
    }

    /**
     * 업무 완료 (신뢰점수 부여 및 신뢰점수 내역 추가)
     *
     * @param requestDto
     */
    public void workComplete(Long currentUserId, WorkCompleteRequestDto requestDto) {
        Work work = workService.findById(requestDto.getWorkId());

        // 사용자가 프로젝트 멤버인지 확인
        validateProjectMember(currentUserId, work.getProject().getId());

        // 사용자가 업무담당 멤버 or 매니저 권한인지 확인
        User currentAssignedUser = work.getAssignedUserId();
        if (!currentUserId.equals(currentAssignedUser.getId())
                && !requestDto.getAuthMap().getCode().equals(ProjectMemberAuth.PAUTH_1001.getCode())
        ) {
            throw WorkCustomException.NO_PERMISSION_TO_TASK;
        }

        // 신뢰점수 부여 DTO
        AddPointDto addPoint = AddPointDto.builder()
                .content(work.getContent())
                .userId(currentAssignedUser.getId())
                .projectId(work.getProject().getId())
                .milestoneId(work.getMilestone().getId())
                .workId(work.getId())
                .scoreTypeId(TrustScoreTypeIdentifier.WORK_COMPLETE)
                .build();

        TrustScore trustScore = trustScoreService.findTrustScoreByUserId(currentAssignedUser.getId());
        TrustGrade trustGrade = trustScore.getTrustGrade();

        // 신뢰점수 부여 및 신뢰점수 내역 추가
        trustScoreService.addPoint(trustGrade, addPoint);

        // 업무상태 '완료'로 변경
        work.updateCompleteStatus(ProgressStatus.PS003);
    }

    @Transactional
    public void deleteWork(Long userId, WorkDeleteRequestDto requestDto) {
        User currentUser = userService.findById(userId);
        Work work = workService.findById(requestDto.getWorkId());
        Project project = work.getProject();

        // 요청한 회원이 해당 프로젝트의 멤버인지 검증
        Optional<ProjectMember> projectMember = projectMemberService.findProjectMemberByProjectAndUser(project, currentUser);
        if (projectMember.isEmpty()) throw WorkCustomException.NO_PERMISSION_TO_TASK;

        // 사용자가 프로젝트 담당 멤버 or 매니저 권한인지 확인
        if (!userId.equals(work.getAssignedUserId().getId())
                && !requestDto.getAuthMap().getCode().equals(ProjectMemberAuth.PAUTH_1001.getCode())) {
            throw WorkCustomException.NO_PERMISSION_TO_TASK;
        }

        // 해당 업무 삭제
        workService.delete(work);
    }

    public void validateProjectMember(Long userId, Long projectId) {
        ProjectMember projectMember = projectMemberService.findProjectMemberByPrIdAndUserId(projectId, userId);
        if (projectMember == null) {
            throw ProjectCustomException.ACCESS_NOT_ALLOWED;
        }
    }


}
