package com.example.demo.service.project;

import com.example.demo.constant.AlertType;
import com.example.demo.constant.ProjectMemberStatus;
import com.example.demo.constant.UserProjectHistoryStatus;
import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.dto.position.response.PositionResponseDto;
import com.example.demo.dto.project.request.ProjectWithdrawConfirmRequestDto;
import com.example.demo.dto.projectmember.response.*;
import com.example.demo.dto.technology_stack.response.TechnologyStackInfoResponseDto;
import com.example.demo.dto.trust_grade.response.TrustGradeResponseDto;
import com.example.demo.dto.trust_score.AddPointDto;
import com.example.demo.dto.trust_score.response.TrustScoreUpdateResponseDto;
import com.example.demo.dto.user.response.UserCrewDetailResponseDto;
import com.example.demo.dto.user.response.UserReadProjectCrewResponseDto;
import com.example.demo.global.exception.customexception.PageNationCustomException;
import com.example.demo.global.exception.customexception.ProjectCustomException;
import com.example.demo.global.log.PMLog;
import com.example.demo.model.alert.Alert;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.technology_stack.TechnologyStack;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserProjectHistory;
import com.example.demo.model.user.UserTechnologyStack;
import com.example.demo.model.work.Work;
import com.example.demo.service.alert.AlertService;
import com.example.demo.service.trust_score.TrustScoreHistoryService;
import com.example.demo.service.trust_score.TrustScoreService;
import com.example.demo.service.user.UserProjectHistoryService;
import com.example.demo.service.user.UserService;
import com.example.demo.service.work.WorkService;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.global.log.PMLog.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectMemberFacade {

    private final UserService userService;
    private final ProjectMemberService projectMemberService;
    private final AlertService alertService;
    private final ProjectService projectService;
    private final WorkService workService;
    private final UserProjectHistoryService userProjectHistoryService;
    private final TrustScoreService trustScoreService;
    private final TrustScoreHistoryService trustScoreHistoryService;

    /**
     * 프로젝트 멤버 탈퇴 알림 보내기
     *
     * @param projectMemberId
     */
    public void sendWithdrawAlert(Long userId, Long projectMemberId) {
        ProjectMember projectMember = projectMemberService.findById(projectMemberId);
        User user = projectMember.getUser();

        if (!user.getId().equals(userId)) {
            throw ProjectCustomException.NO_PERMISSION_TO_TASK;
        }

        Project project = projectMember.getProject();
        Alert alert =
                Alert.builder()
                        .project(project)
                        .checkUser(project.getUser())
                        .sendUser(user)
                        .content(user.getNickname() + "님이 프로젝트 탈퇴 신청을 했습니다.")
                        .position(projectMember.getPosition())
                        .type(AlertType.WITHDRAWAL)
                        .checked_YN(false)
                        .build();

        alertService.save(alert);
        PMLog.i(PROJECT_ALERT, "[{}] Alert saved : {}", alert.getType(), alert.getContent());
    }

    /**
     * 프로젝트 멤버 강제 탈퇴 알림 보내기
     *
     * @param projectMemberId
     */
    public void sendForceWithdrawAlert(Long projectMemberId) {
        ProjectMember projectMember = projectMemberService.findById(projectMemberId);
        User user = projectMember.getUser();

        Project project = projectMember.getProject();
        Alert alert =
                Alert.builder()
                        .project(project)
                        .checkUser(project.getUser())
                        .sendUser(user)
                        .content(user.getNickname() + "님의 프로젝트 강제 탈퇴가 요청되었습니다.")
                        .position(projectMember.getPosition())
                        .type(AlertType.FORCED_WITHDRAWAL)
                        .checked_YN(false)
                        .build();

        alertService.save(alert);
        PMLog.i(PROJECT_ALERT, "[{}] Alert saved : {}", alert.getType(), alert.getContent());
    }

    /**
     * 프로젝트 탈퇴 컨펌 (수락 / 거절)
     *
     * @param userId
     * @param withdrawConfirmRequest
     */
    @Transactional
    public void withdrawConfirm(Long userId, ProjectWithdrawConfirmRequestDto withdrawConfirmRequest) {
        // 탈퇴신청 알림 정보
        Alert withdrawAlert = alertService.findById(withdrawConfirmRequest.getAlertId());

        User currentUser = userService.findById(userId);
        Project project = withdrawAlert.getProject();

        // 프로젝트 매니저 검증
        projectMemberService.verifiedProjectManager(project, currentUser);

        // 탈퇴 수락인 경우
        if (withdrawConfirmRequest.isWithdrawConfirm()) {
            // 프로젝트 멤버 상태 탈퇴로 변경
            ProjectMember projectMember = projectMemberService.findProjectMemberByProjectAndUser(project, withdrawAlert.getSendUser());
            projectMember.updateStatus(ProjectMemberStatus.WITHDRAW);
            PMLog.i(PROJECT_CREW, "[WITHDRAW] project: {} crew: {}", project.getName(), projectMember.getUser().getNickname());

            User user = projectMember.getUser();

            // 프로젝트 탈퇴 이력 생성
            UserProjectHistory userWithdrawalProjectHistory = UserProjectHistory.builder()
                    .project(project)
                    .user(user)
                    .status(UserProjectHistoryStatus.WITHDRAWAL)
                    .build();
            userProjectHistoryService.save(userWithdrawalProjectHistory);

            // 프로젝트 탈퇴 알림 생성
            Alert alert = Alert.builder()
                    .project(project)
                    .sendUser(currentUser)
                    .content(user.getNickname() + "님이 프로젝트를 탈퇴했습니다.")
                    .type(AlertType.CREW_UPDATE)
                    .checked_YN(false)
                    .build();
            alertService.save(alert);
            PMLog.i(PROJECT_ALERT, "[{}] Alert saved : {}", alert.getType(), alert.getContent());
        }
    }

    /**
     * 프로젝트 멤버 강제탈퇴
     * 사용자 프로젝트 이력에 해당 회원의 강제탈퇴 이력 추가
     *
     * @param projectMemberId
     */
    @Transactional
    public void forcedWithdrawal(Long userId, Long projectMemberId) {
        User currentUser = userService.findById(userId);
        ProjectMember projectMember = projectMemberService.findById(projectMemberId);
        Project project = projectMember.getProject();

        // 프로젝트 매니저 검증
        projectMemberService.verifiedProjectManager(project, currentUser);

        User user = projectMember.getUser();
        // 사용자 프로젝트 이력에 강제탈퇴 이력 추가
        UserProjectHistory forcedWithdrawalHistory = UserProjectHistory.builder()
                .project(project)
                .user(user)
                .status(UserProjectHistoryStatus.FORCED_WITHDRAWAL)
                .build();
        userProjectHistoryService.save(forcedWithdrawalHistory);

        // 강제탈퇴에 따른 신뢰점수 차감
        AddPointDto addPoint = AddPointDto.builder()
                .content(user.getNickname() + "님 " + project.getName() + " 강제탈퇴")
                .userId(user.getId())
                .projectId(project.getId())
                .scoreTypeId(5L)
                .build();

        TrustScoreUpdateResponseDto result = trustScoreService.addPoint(addPoint);
        PMLog.i(
                TRUST_POINT, "[MINUS] user:{}, score:{}, result:{}, content:{}",
                800, user.getNickname(), result.getScoreChange(),
                result.getTotalScore(), addPoint.getContent()
        );

        // 강제탈퇴 알림 생성
        Alert forcedWithdrawalAlert = Alert.builder()
                .project(project)
                .sendUser(currentUser)
                .content(user.getNickname() + "님이 " + project.getName() + "에서 강제탈퇴 처리됐습니다.")
                .type(AlertType.CREW_UPDATE)
                .checked_YN(false)
                .build();
        alertService.save(forcedWithdrawalAlert);
        PMLog.i(PROJECT_ALERT, "[{}] Alert saved: {}",
                forcedWithdrawalAlert.getType(), forcedWithdrawalAlert.getContent());

        // 프로젝트 멤버 상태 탈퇴로 변경
        projectMember.updateStatus(ProjectMemberStatus.FORCE_WITHDRAW);
        PMLog.i(PROJECT_CREW, "[FORCED_WITHDRAW] project: {} crew: {}", project.getName(), projectMember.getUser().getNickname());

    }

    /**
     * 크루정보 상세 페이지 TODO : 프로젝트 신뢰 이력 추가 해야함 유저 정보들, 유저 기술들, 프로젝트 개수, 신뢰점수 이력들
     *
     * @param projectMemberId
     */
    @Transactional(readOnly = true)
    public ProjectMemberReadCrewDetailResponseDto getCrewDetail(Long projectMemberId) {
        ProjectMember projectMember = projectMemberService.findById(projectMemberId);

        int projectCount = projectService.countProjectsByUser(projectMember.getUser());
        TrustGradeResponseDto trustGradeResponseDto =
                TrustGradeResponseDto.of(projectMember.getProject().getTrustGrade());
        PositionResponseDto positionResponseDto =
                PositionResponseDto.of(projectMember.getPosition());
        List<TechnologyStackInfoResponseDto> technologyStackInfoResponseDtoList = new ArrayList<>();

        for (UserTechnologyStack userTechnologyStack : projectMember.getUser().getTechStacks()) {
            TechnologyStack technologyStack = userTechnologyStack.getTechnologyStack();

            TechnologyStackInfoResponseDto technologyStackInfoResponseDto =
                    TechnologyStackInfoResponseDto.of(
                            technologyStack.getId(), technologyStack.getName());
            technologyStackInfoResponseDtoList.add(technologyStackInfoResponseDto);
        }

        UserCrewDetailResponseDto userCrewDetailResponseDto =
                UserCrewDetailResponseDto.of(
                        projectMember.getUser(),
                        projectMember.getUser().getTrustScore().getScore(),
                        positionResponseDto,
                        trustGradeResponseDto,
                        technologyStackInfoResponseDtoList);

        ProjectMemberAuthResponseDto projectMemberAuthResponse =
                ProjectMemberAuthResponseDto.of(projectMember.getProjectMemberAuth());

        return ProjectMemberReadCrewDetailResponseDto.of(
                projectMember, projectCount, userCrewDetailResponseDto, projectMemberAuthResponse, positionResponseDto);
    }

    @Transactional(readOnly = true)
    public ProjectMemberReadTotalProjectCrewsResponseDto getCrewsByProject(Long projectId) {
        Project project = projectService.findById(projectId);

        // 프로젝트에 참여중인 멤버목록 조회
        List<ProjectMember> projectMembers = projectMemberService.getProjectMembersByProjectAndStatus(project, ProjectMemberStatus.PARTICIPATING);

        List<ProjectMemberReadProjectCrewsResponseDto> projectMemberReadProjectCrewsResponseDtos =
                new ArrayList<>();
        for (ProjectMember projectMember : projectMembers) {
            UserReadProjectCrewResponseDto userReadProjectCrewResponseDto =
                    UserReadProjectCrewResponseDto.of(projectMember.getUser());
            ProjectMemberAuthResponseDto projectMemberAuthResponseDto =
                    ProjectMemberAuthResponseDto.of(projectMember.getProjectMemberAuth());
            PositionResponseDto positionResponseDto =
                    PositionResponseDto.of(projectMember.getPosition());
            Work lastCompleteWork =
                    workService.findLastCompleteWork(project, projectMember.getUser());

            ProjectMemberReadProjectCrewsResponseDto projectMemberReadProjectCrewsResponseDto =
                    ProjectMemberReadProjectCrewsResponseDto.of(
                            projectMember,
                            userReadProjectCrewResponseDto,
                            projectMemberAuthResponseDto,
                            positionResponseDto,
                            lastCompleteWork != null ? lastCompleteWork.getCompleteDate() : null);
            projectMemberReadProjectCrewsResponseDtos.add(projectMemberReadProjectCrewsResponseDto);
        }

        ProjectMemberReadTotalProjectCrewsResponseDto result =
                ProjectMemberReadTotalProjectCrewsResponseDto.of(
                        projectMemberReadProjectCrewsResponseDtos);
        return result;
    }

    /**
     * 프로젝트 멤버가 할당된 업무 이력 + 업무 별 신뢰점수 내역 조회
     *
     * @param projectMemberId
     * @param pageIndex
     * @param itemCount
     * @return PaginationResponseDto
     */
    @Transactional(readOnly = true)
    public PaginationResponseDto getCrewWorksWithTrustScoreHistory(Long projectMemberId, int pageIndex, int itemCount) {
        if (pageIndex < 0) {
            throw PageNationCustomException.INVALID_PAGE_NUMBER;
        }

        if (itemCount < 0 || itemCount > 6) {
            throw PageNationCustomException.INVALID_PAGE_ITEM_COUNT;
        }

        ProjectMember projectMember = projectMemberService.findById(projectMemberId);

        // 해당 프로젝트 멤버의 업무 + 업무 별 신뢰점수 내역 & 해당 프로젝트 멤버 업무 총 개수 조회
        PaginationResponseDto result = trustScoreHistoryService
                .getWorkTrustScoreHistories(projectMember.getProject(), projectMember.getUser(), pageIndex, itemCount);

        return result;
    }
}
