package com.example.demo.service.project;

import com.example.demo.dto.position.response.PositionResponseDto;
import com.example.demo.dto.project.response.ProjectMeResponseDto;
import com.example.demo.dto.project.response.ProjectSpecificDetailResponseDto;
import com.example.demo.dto.projectmember.response.MyProjectMemberResponseDto;
import com.example.demo.dto.projectmember.response.ProjectMemberAuthResponseDto;
import com.example.demo.dto.projectmember.response.ProjectMemberDetailResponseDto;
import com.example.demo.dto.trust_grade.response.TrustGradeResponseDto;
import com.example.demo.dto.user.response.UserMyProjectResponseDto;
import com.example.demo.dto.user.response.UserProjectDetailResponseDto;
import com.example.demo.dto.user.response.UserProjectResponseDto;
import com.example.demo.dto.work.response.WorkProjectDetailResponseDto;
import com.example.demo.global.exception.customexception.ProjectCustomException;
import com.example.demo.global.exception.customexception.ProjectMemberCustomException;
import com.example.demo.global.exception.customexception.UserCustomException;
import com.example.demo.global.exception.customexception.WorkCustomException;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.user.User;
import com.example.demo.model.work.Work;
import com.example.demo.service.user.UserService;
import com.example.demo.service.work.WorkService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectFacade {

    private UserService userService;
    private ProjectService projectService;

    private ProjectMemberService projectMemberService;
    private WorkService workService;

    /**
     * 내 프로젝트 목록 조회
     * TODO : 현재 유저 가져오기.
     * @return
     */

    @Transactional(readOnly = true)
    public List<ProjectMeResponseDto> getMyProjects() throws Exception {
        User user = userService.getUserById(1L);

        List<Project> projects = projectService.findProjectsByUser(user);
        List<ProjectMeResponseDto> result = new ArrayList<>();

        for (Project project : projects) {
            TrustGradeResponseDto trustGradeDto = TrustGradeResponseDto.of(project.getTrustGrade());

            List<MyProjectMemberResponseDto> myProjectMemberResponseDtos = new ArrayList<>();
            for (ProjectMember projectMember : project.getProjectMembers()) {
                UserMyProjectResponseDto userMyProjectResponseDto = UserMyProjectResponseDto.of(projectMember.getUser());
                MyProjectMemberResponseDto myProjectMemberResponseDto = MyProjectMemberResponseDto.of(projectMember, userMyProjectResponseDto);
                myProjectMemberResponseDtos.add(myProjectMemberResponseDto);
            }

            ProjectMeResponseDto projectMeResponseDto = ProjectMeResponseDto.of(project, trustGradeDto, myProjectMemberResponseDtos);
            result.add(projectMeResponseDto);
        }

        return result;
    }

    /**
     * 프로젝트 상세 목록
     * @param projectId
     * @return
     */

    @Transactional(readOnly = true)
    public ProjectSpecificDetailResponseDto getDetail(Long projectId) {
        Project project = projectService.findById(projectId);
        TrustGradeResponseDto trustGradeDto = TrustGradeResponseDto.of(project.getTrustGrade());
        UserProjectResponseDto userProjectResponseDto = UserProjectResponseDto.of(project);

        // ProjectMember 부분
        List<ProjectMember> projectMembers = projectMemberService.findProjectsMemberByProject(project);
        List<ProjectMemberDetailResponseDto> projectMemberDetailResponseDtos = new ArrayList<>();

        for (ProjectMember projectMember : projectMembers) {
            UserProjectDetailResponseDto userProjectDetailResponseDto =
                    UserProjectDetailResponseDto.of(projectMember.getUser());
            ProjectMemberAuthResponseDto projectMemberAuthResponseDto =
                    ProjectMemberAuthResponseDto.of(projectMember.getProjectMemberAuth());
            PositionResponseDto positionResponseDto =
                    PositionResponseDto.of(projectMember.getPosition());

            ProjectMemberDetailResponseDto projectMemberDetailResponseDto =
                    ProjectMemberDetailResponseDto.of(
                            projectMember,
                            userProjectDetailResponseDto,
                            projectMemberAuthResponseDto,
                            positionResponseDto);
            projectMemberDetailResponseDtos.add(projectMemberDetailResponseDto);
        }

        // work 부분
        List<Work> works = workService.findWorksByProject(project);
        List<WorkProjectDetailResponseDto> workProjectDetailResponseDtos = new ArrayList<>();
        for (Work work : works) {
            UserProjectDetailResponseDto userProjectDetailResponseDto =
                    UserProjectDetailResponseDto.of(work.getAssignedUserId());
            WorkProjectDetailResponseDto workProjectDetailResponseDto =
                    WorkProjectDetailResponseDto.of(work, userProjectDetailResponseDto);
            workProjectDetailResponseDtos.add(workProjectDetailResponseDto);
        }

        return ProjectSpecificDetailResponseDto.of(
                project,
                trustGradeDto,
                userProjectResponseDto,
                projectMemberDetailResponseDtos,
                workProjectDetailResponseDtos);
    }
}
