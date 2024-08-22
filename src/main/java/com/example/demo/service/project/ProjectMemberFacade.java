package com.example.demo.service.project;

import com.example.demo.constant.ProjectMemberStatus;
import com.example.demo.constant.UserProjectHistoryStatus;
import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.dto.position.response.PositionResponseDto;
import com.example.demo.dto.project.request.WithdrawRequestDto;
import com.example.demo.dto.projectmember.response.ProjectMemberAuthResponseDto;
import com.example.demo.dto.projectmember.response.ProjectMemberReadCrewDetailResponseDto;
import com.example.demo.dto.projectmember.response.ProjectMemberReadProjectCrewsResponseDto;
import com.example.demo.dto.projectmember.response.ProjectMemberReadTotalProjectCrewsResponseDto;
import com.example.demo.dto.technology_stack.response.TechnologyStackInfoResponseDto;
import com.example.demo.dto.trust_grade.response.TrustGradeResponseDto;
import com.example.demo.dto.user.response.UserCrewDetailResponseDto;
import com.example.demo.dto.user.response.UserReadProjectCrewResponseDto;
import com.example.demo.global.exception.customexception.PageNationCustomException;
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



    /**
     * 프로젝트 탈퇴
     *
     * @param withdrawRequestDto
     */
    @Transactional
    public void withdraw(WithdrawRequestDto withdrawRequestDto) {
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
                .status(UserProjectHistoryStatus.WITHDRAWAL)
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
