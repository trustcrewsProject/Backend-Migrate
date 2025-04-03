package com.example.demo.service.project;

import com.example.demo.constant.ProjectMemberAuth;
import com.example.demo.constant.ProjectMemberStatus;
import com.example.demo.constant.UserProjectHistoryStatus;
import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.dto.position.response.PositionResponseDto;
import com.example.demo.dto.project.request.WithdrawRequestDto;
import com.example.demo.dto.project.setting.request.ProjectSettingCrewAuthUpdReqDto;
import com.example.demo.dto.projectmember.ProjectMemberAuthDto;
import com.example.demo.dto.projectmember.response.ProjectMemberReadCrewDetailResponseDto;
import com.example.demo.dto.projectmember.response.ProjectMemberReadProjectCrewsResponseDto;
import com.example.demo.dto.projectmember.response.ProjectMemberReadTotalProjectCrewsResponseDto;
import com.example.demo.dto.technology_stack.response.TechnologyStackInfoResponseDto;
import com.example.demo.dto.trust_grade.response.TrustGradeResponseDto;
import com.example.demo.dto.user.response.UserCrewDetailResponseDto;
import com.example.demo.dto.user.response.UserReadProjectCrewResponseDto;
import com.example.demo.global.exception.customexception.PageNationCustomException;
import com.example.demo.global.exception.customexception.ProjectCustomException;
import com.example.demo.global.exception.customexception.ProjectMemberCustomException;
import com.example.demo.global.log.PMLog;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.project.alert.crew.AlertCrew;
import com.example.demo.model.technology_stack.TechnologyStack;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserProjectHistory;
import com.example.demo.model.user.UserTechnologyStack;
import com.example.demo.model.work.Work;
import com.example.demo.service.file.AwsS3FileService;
import com.example.demo.service.projectAlert.crew.AlertCrewService;
import com.example.demo.service.trust_score.TrustScoreHistoryService;
import com.example.demo.service.user.UserProjectHistoryService;
import com.example.demo.service.work.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.global.log.PMLog.PROJECT_ALERT;
import static com.example.demo.global.log.PMLog.PROJECT_CREW;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectMemberFacade {
    private final ProjectMemberService projectMemberService;
    private final ProjectService projectService;
    private final WorkService workService;
    private final UserProjectHistoryService userProjectHistoryService;
    private final TrustScoreHistoryService trustScoreHistoryService;
    private final AlertCrewService alertCrewService;
    private final AwsS3FileService awsS3FileService;

    /**
     * 프로젝트 탈퇴
     *
     * @param withdrawRequestDto
     */
    @Transactional
    public void withdraw(WithdrawRequestDto withdrawRequestDto) {

        // 매니저 탈퇴시 대체할 매니저 멤버 없으면 탈퇴 못하도록
        if (withdrawRequestDto.getWMemberAuth().equals(ProjectMemberAuth.PAUTH_1001)) {
            long otherProjectManagersCount = projectMemberService.countOtherProjectManagers(
                    withdrawRequestDto.getProjectId(),
                    withdrawRequestDto.getWMemberId()
            );
            if (otherProjectManagersCount == 0) throw ProjectMemberCustomException.NO_OTHER_PROJECT_MANAGER;
        }

        Project project = projectService.findById(withdrawRequestDto.getProjectId());

        // 프로젝트 멤버 상태 탈퇴로 변경
        ProjectMember projectMember = projectMemberService.findById(withdrawRequestDto.getWMemberId());
        if (projectMember == null) throw ProjectMemberCustomException.NOT_FOUND_PROJECT_MEMBER;

        projectMember.updateStatus(ProjectMemberStatus.WITHDRAW);
        PMLog.i(PROJECT_CREW, "[WITHDRAW] project: {} crew: {}", project.getName(), projectMember.getUser().getNickname());

        // 프로젝트 탈퇴 이력 생성
        User wProjectMemberUserInfo = projectMember.getUser();
        UserProjectHistory userWithdrawalProjectHistory = UserProjectHistory.builder()
                .project(project)
                .user(wProjectMemberUserInfo)
                .status(UserProjectHistoryStatus.PHIST_STAT_004)
                .build();
        userProjectHistoryService.save(userWithdrawalProjectHistory);


        String alertContents = wProjectMemberUserInfo.getNickname() + "님이 프로젝트를 탈퇴했습니다.";
        AlertCrew alertCrew = alertCrewService.toAlertCrewEntity(
                withdrawRequestDto.getProjectId(),
                alertContents
        );

        PMLog.i(PROJECT_ALERT, "[{}] Alert saved : {}", alertCrew.getProjectAlertType().getName(), alertCrew.getAlertContents());
    }


    /**
     * 크루정보 상세 조회
     *
     * @param projectMemberId
     */
    @Transactional(readOnly = true)
    public ProjectMemberReadCrewDetailResponseDto getCrewDetail(Long projectMemberId) {
        ProjectMember projectMember = projectMemberService.findById(projectMemberId);

        int projectCount = projectService.countProjectsByUser(projectMember.getUser());

        // 프로젝트 신뢰등급 - 현재 사용 x, 추후삭제
        TrustGradeResponseDto trustGradeResponseDto =
                TrustGradeResponseDto.of(projectMember.getProject().getTrustGrade());

        // 프로젝트 멤버 포지션
        PositionResponseDto positionResponseDto = PositionResponseDto.of(projectMember.getPosition());

        // 프로젝트 멤버 기술스택
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
                        awsS3FileService.generatePreSignedUrl(projectMember.getUser().getProfileImgSrc()),
                        projectMember.getUser().getTrustScore().getScore(),
                        positionResponseDto,
                        trustGradeResponseDto,
                        technologyStackInfoResponseDtoList);

        // 프로젝트 멤버 권한
        ProjectMemberAuthDto<ProjectMemberAuth> projectMemberAuthDto =
                ProjectMemberAuthDto.of(projectMember.getProjectMemberAuth());

        return ProjectMemberReadCrewDetailResponseDto.of(
                projectMember, projectCount, userCrewDetailResponseDto, projectMemberAuthDto, positionResponseDto);
    }

    @Transactional(readOnly = true)
    public ProjectMemberReadTotalProjectCrewsResponseDto getCrewsByProject(Long projectId) {
        Project project = projectService.findById(projectId);

        // 프로젝트에 참여중인 멤버목록 조회
        List<ProjectMember> projectMembers = projectMemberService.getProjectMembersByProjectAndStatus(project, ProjectMemberStatus.PARTICIPATING);

        // 응답 DTO로 변환
        List<ProjectMemberReadProjectCrewsResponseDto> projectMemberReadProjectCrewsResponseDtos = new ArrayList<>();
        for (ProjectMember projectMember : projectMembers) {
            // 프로젝트 멤버 '사용자' 정보 - 사용자아이디, 닉네임, 이메일, 프로필이미지
            User user = projectMember.getUser();
            UserReadProjectCrewResponseDto userReadProjectCrewResponseDto =
                    UserReadProjectCrewResponseDto.of(user, awsS3FileService.generatePreSignedUrl(user.getProfileImgSrc()));

            // 프로젝트 멤버 권한 정보
            ProjectMemberAuthDto<ProjectMemberAuth> projectMemberAuthDto =
                    ProjectMemberAuthDto.of(projectMember.getProjectMemberAuth());

            // 프로젝트 멤버 포지션 정보
            PositionResponseDto positionResponseDto = PositionResponseDto.of(projectMember.getPosition());

            // 프로젝트 멤버 최근 작업정보
            Work lastCompleteWork = workService.findLastCompleteWork(project, projectMember.getUser());

            ProjectMemberReadProjectCrewsResponseDto projectMemberReadProjectCrewsResponseDto =
                    ProjectMemberReadProjectCrewsResponseDto.of(
                            projectMember,
                            userReadProjectCrewResponseDto,
                            projectMemberAuthDto,
                            positionResponseDto,
                            lastCompleteWork != null ? lastCompleteWork.getCompleteDate() : null);
            projectMemberReadProjectCrewsResponseDtos.add(projectMemberReadProjectCrewsResponseDto);
        }

        return ProjectMemberReadTotalProjectCrewsResponseDto.of(projectMemberReadProjectCrewsResponseDtos);
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


    /**
     * 프로젝트 크루 권한 수정
     *
     * @param userId
     * @param dto
     */
    @Transactional
    public void updateProjectMemberAuth(Long userId, ProjectSettingCrewAuthUpdReqDto dto) {
        validateProjectMember(userId, dto.getProjectId());

        // 수정 권한(프로젝트 설정권한)확인
        if (dto.getAuthMap() == null || !dto.getAuthMap().isConfigYn()) {
            throw ProjectCustomException.NO_PERMISSION_TO_TASK;
        }

        projectMemberService.updateProjectMemberAuth(dto.getProjectMemberId(), dto.getProjectMemberAuth());
    }

    public void validateProjectMember(Long userId, Long projectId) {
        ProjectMember projectMember = projectMemberService.findProjectMemberByPrIdAndUserId(projectId, userId);
        if (projectMember == null) {
            throw ProjectCustomException.ACCESS_NOT_ALLOWED;
        }
    }
}
