package com.example.demo.service.project;

import com.example.demo.constant.ProjectMemberStatus;
import com.example.demo.constant.UserProjectHistoryStatus;
import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.dto.project.ProjectDetailAuthDto;
import com.example.demo.dto.project.request.ProjectInfoUpdateRequestDto;
import com.example.demo.dto.project.response.ProjectMeResponseDto;
import com.example.demo.dto.project.response.ProjectSpecificDetailResponseDto;
import com.example.demo.dto.projectmember.response.MyProjectMemberResponseDto;
import com.example.demo.dto.trust_grade.response.TrustGradeResponseDto;
import com.example.demo.dto.user.response.UserMyProjectResponseDto;
import com.example.demo.global.exception.customexception.PageNationCustomException;
import com.example.demo.global.exception.customexception.ProjectCustomException;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserProjectHistory;
import com.example.demo.service.milestone.MilestoneService;
import com.example.demo.service.user.UserProjectHistoryService;
import com.example.demo.service.user.UserService;
import com.example.demo.service.work.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectFacade {

    private final UserService userService;
    private final ProjectService projectService;
    private final ProjectMemberService projectMemberService;
    private final WorkService workService;
    private final MilestoneService milestoneService;
    private final UserProjectHistoryService userProjectHistoryService;


    @Transactional(readOnly = true)
    public PaginationResponseDto getMyParticipatingProjects(Long userId, int pageIndex, int itemCount) {
        if (pageIndex < 0) {
            throw PageNationCustomException.INVALID_PAGE_NUMBER;
        }

        if (itemCount > 8) {
            throw PageNationCustomException.INVALID_PAGE_ITEM_COUNT;
        }

        User currentUser = userService.findById(userId);

        List<ProjectMember> projects = projectMemberService.getProjectMembersByUserAndStatus(currentUser, ProjectMemberStatus.PARTICIPATING);
        long totalPages = projects.size();

        List<Project> sortedProjects = projects
                .stream()
                .map(ProjectMember::getProject)
                .sorted(Comparator.comparing(Project::getStartDate).reversed())
                .collect(Collectors.toList());

        int startIndex = Math.min(pageIndex * itemCount, sortedProjects.size());
        int endIndex = Math.min(startIndex + itemCount, sortedProjects.size());
        List<Project> slicedProjects = new ArrayList<>(sortedProjects.subList(startIndex, endIndex));

        List<ProjectMeResponseDto> content = new ArrayList<>();
        for (Project project : slicedProjects) {
            List<MyProjectMemberResponseDto> myProjectMembers = project.getProjectMembers().stream()
                    .filter(projectMember -> projectMember.getStatus().equals(ProjectMemberStatus.PARTICIPATING))
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
    public ProjectSpecificDetailResponseDto getDetail(Long userId, Long projectId) {

        validateProjectMember(userId, projectId);

        Project project = projectService.findById(projectId);
        TrustGradeResponseDto trustGradeDto = TrustGradeResponseDto.of(project.getTrustGrade());

        ProjectSpecificDetailResponseDto responseDto = ProjectSpecificDetailResponseDto.of(project, trustGradeDto);

        ProjectDetailAuthDto userAuthMap =
                projectMemberService.getUserAuthMap(projectId, userId);
        responseDto.setAuthMap(userAuthMap);

        return responseDto;
    }

    /**
     * 프로젝트 종료
     * 해당 프로젝트 멤버의 새로운 사용자 프로젝트 이력 추가(프로젝트 완주 이력)
     * 해당 프로젝트와 관련된 업무, 마일스톤, 알림, 멤버, 기술스택 정보 삭제
     *
     * @param userId
     * @param projectId
     */
    @Transactional
    public void endProject(Long userId, Long projectId) {
        User currentUser = userService.findById(userId);
        Project project = projectService.findById(projectId);

        // 프로젝트 매니저 검증
        projectMemberService.verifiedProjectManager(project, currentUser);

        for (ProjectMember projectMember : project.getProjectMembers()) {
            // 프로젝트 멤버 프로젝트 완주 이력 추가
            UserProjectHistory projectFinishHistory = UserProjectHistory.builder()
                    .project(project)
                    .user(projectMember.getUser())
                    .status(UserProjectHistoryStatus.FINISH)
                    .build();
            userProjectHistoryService.save(projectFinishHistory);
        }

        // 프로젝트 멤버 삭제
        project.removeProjectMembers();

        // 프로젝트 기술목록 삭제
        project.removeProjectTechnologies();

        // 프로젝트 모든 업무 삭제
        workService.deleteAllByProject(project);

        // 프로젝트 모든 마일스톤 삭제
        milestoneService.deleteAllByProject(project);

        // 프로젝트 status 종료로 변경
        project.endProject();
    }

    @Transactional
    public void updateProject(Long userId, ProjectInfoUpdateRequestDto updateRequest) {
        User currentUser = userService.findById(userId);
        Project project = projectService.findById(updateRequest.getProjectId());

        // 프로젝트 매니저 검증
        projectMemberService.verifiedProjectManager(project, currentUser);

        // 프로젝트 정보 수정
        project.updateProject(updateRequest.getProjectName(), updateRequest.getSubject(),
                updateRequest.getStartDate(),
                updateRequest.getEndDate());
    }

    public void validateProjectMember(Long userId, Long projectId) {
        ProjectMember projectMember = projectMemberService.findProjectMemberByPrIdAndUserId(projectId, userId);
        if(projectMember == null){
            throw ProjectCustomException.ACCESS_NOT_ALLOWED;
        }
    }
}
