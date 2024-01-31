package com.example.demo.service.project;

import com.example.demo.constant.AlertType;
import com.example.demo.constant.ProjectMemberStatus;
import com.example.demo.constant.UserProjectHistoryStatus;
import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.dto.project.request.ProjectConfirmRequestDto;
import com.example.demo.dto.project.request.ProjectParticipateRequestDto;
import com.example.demo.dto.project.response.ProjectMeResponseDto;
import com.example.demo.dto.project.response.ProjectSpecificDetailResponseDto;
import com.example.demo.dto.projectmember.response.MyProjectMemberResponseDto;
import com.example.demo.dto.trust_grade.response.TrustGradeResponseDto;
import com.example.demo.dto.user.response.UserMyProjectResponseDto;
import com.example.demo.global.exception.customexception.CommonCustomException;
import com.example.demo.global.exception.customexception.PageNationCustomException;
import com.example.demo.global.exception.customexception.ProjectCustomException;
import com.example.demo.global.exception.customexception.UserCustomException;
import com.example.demo.model.alert.Alert;
import com.example.demo.model.position.Position;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.project.ProjectMemberAuth;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserProjectHistory;
import com.example.demo.service.alert.AlertService;
import com.example.demo.service.milestone.MilestoneService;
import com.example.demo.service.position.PositionService;
import com.example.demo.service.user.UserProjectHistoryService;
import com.example.demo.service.user.UserService;
import com.example.demo.service.work.WorkService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectFacade {

    private final UserService userService;
    private final ProjectService projectService;
    private final ProjectMemberService projectMemberService;
    private final WorkService workService;
    private final PositionService positionService;
    private final AlertService alertService;
    private final ProjectMemberAuthService projectMemberAuthService;
    private final MilestoneService milestoneService;
    private final UserProjectHistoryService userProjectHistoryService;

    @Transactional(readOnly = true)
    public PaginationResponseDto getMyProjectsParticipates(Long userId, int pageIndex, int itemCount) {
        User user = userService.findById(userId);

        if(pageIndex < 0) {
            throw PageNationCustomException.INVALID_PAGE_NUMBER;
        }

        if(itemCount < 1 && itemCount > 6) {
            throw PageNationCustomException.INVALID_PAGE_ITEM_COUNT;
        }

        // 참여중인 프로젝트 개수 조회
        long totalPages = userProjectHistoryService.getUserProjectHistoryTotalCount(user.getId(), UserProjectHistoryStatus.PARTICIPATING);

        // 내가 참여중인 프로젝트 이력 목록 불러오기 (정렬, 페이징)
        List<UserProjectHistory> projectHistories = userProjectHistoryService.getUserProjectHistoryListParticipates(userId, pageIndex, itemCount);

        // 내가 참여중인 프로젝트 목록
        List<Project> projects = projectHistories.stream()
                .map(userProjectHistory -> userProjectHistory.getProject())
                .collect(Collectors.toList());

        List<ProjectMeResponseDto> content = new ArrayList<>();
        for(Project project : projects) {
            List<MyProjectMemberResponseDto> myProjectMembers = project.getProjectMembers().stream()
                    .map(projectMember -> MyProjectMemberResponseDto.of(projectMember, UserMyProjectResponseDto.of(projectMember.getUser())))
                    .collect(Collectors.toList());

            content.add(ProjectMeResponseDto.of(project, TrustGradeResponseDto.of(project.getTrustGrade()), myProjectMembers));
        }

        return PaginationResponseDto.of(content, totalPages);
    }

    /**
     * 내 프로젝트 목록 조회
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<ProjectMeResponseDto> getMyProjects(Long userId) {
        User user = userService.findById(userId);

        List<Project> projects = projectService.findProjectsByUser(user);
        List<ProjectMeResponseDto> result = new ArrayList<>();

        for (Project project : projects) {
            TrustGradeResponseDto trustGradeDto = TrustGradeResponseDto.of(project.getTrustGrade());

            List<MyProjectMemberResponseDto> myProjectMemberResponseDtos = new ArrayList<>();
            for (ProjectMember projectMember : project.getProjectMembers()) {
                UserMyProjectResponseDto userMyProjectResponseDto =
                        UserMyProjectResponseDto.of(projectMember.getUser());
                MyProjectMemberResponseDto myProjectMemberResponseDto =
                        MyProjectMemberResponseDto.of(projectMember, userMyProjectResponseDto);
                myProjectMemberResponseDtos.add(myProjectMemberResponseDto);
            }

            ProjectMeResponseDto projectMeResponseDto =
                    ProjectMeResponseDto.of(project, trustGradeDto, myProjectMemberResponseDtos);
            result.add(projectMeResponseDto);
        }

        return result;
    }

    /**
     * 프로젝트 상세 목록
     *
     * @param projectId
     * @return
     */
    @Transactional(readOnly = true)
    public ProjectSpecificDetailResponseDto getDetail(Long projectId) {
        Project project = projectService.findById(projectId);
        TrustGradeResponseDto trustGradeDto = TrustGradeResponseDto.of(project.getTrustGrade());

        return ProjectSpecificDetailResponseDto.of(project, trustGradeDto);
    }

    /**
     * 참여하기 참여하는 경우 알림보내기
     *
     * @param projectId
     * @param projectParticipateRequestDto
     */
    public void sendParticipateAlert(
            Long userId,
            Long projectId,
            ProjectParticipateRequestDto projectParticipateRequestDto) {
        Project project = projectService.findById(projectId);
        User user = userService.findById(userId);
        Position position = positionService.findById(projectParticipateRequestDto.getPositionId());
        Alert alert =
                Alert.builder()
                        .project(project)
                        .checkUser(project.getUser())
                        .sendUser(user)
                        .work(null)
                        .milestone(null)
                        .content("프로젝트 지원했습니다.")
                        .position(position)
                        .type(AlertType.RECRUIT)
                        .checked_YN(false)
                        .build();

        alertService.save(alert);
    }

    /**
     * 프로젝트 참여 수락/거절
     * @param userId
     * @param projectConfirmRequestDto
     */
    @Transactional
    public void confirm(
            Long userId, ProjectConfirmRequestDto projectConfirmRequestDto) {
        User currentUser = userService.findById(userId);

        // 참여지원 알림
        Alert supportedAlert = alertService.findById(projectConfirmRequestDto.getAlertId());
        Project project = supportedAlert.getProject();
        // project null -> 예외처리

        // 프로젝트 매니저 확인
        projectMemberService.verifiedProjectManager(project, currentUser);

        // 프로젝트 참여 수락
        if(projectConfirmRequestDto.isConfirmResult()) {
            // 지원 알림의 confirm 필드 수락으로 변경
            supportedAlert.updateProjectConfirmResult(projectConfirmRequestDto.isConfirmResult());

            User sendUser = supportedAlert.getSendUser();
            ProjectMemberAuth projectMemberAuth = projectMemberAuthService.findTopByOrderByIdDesc();

            // 프로젝트 멤버 등록
            ProjectMember projectMember =
                    projectMemberService.toProjectMemberEntity(
                            project,
                            sendUser,
                            projectMemberAuth,
                            ProjectMemberStatus.PARTICIPATING,
                            supportedAlert.getPosition());
            projectMemberService.save(projectMember);

            // 프로젝트 합류 알림 생성
            Alert participationAlert = Alert.builder()
                    .project(project)
                    .sendUser(currentUser)
                    .content(sendUser.getNickname() + "님이 " + project.getName() + "에 합류했습니다.")
                    .type(AlertType.ADD)
                    .build();
            alertService.save(participationAlert);
            return;
        }

        // 프로젝트 참여 거절
        supportedAlert.updateProjectConfirmResult(false);
    }
}
