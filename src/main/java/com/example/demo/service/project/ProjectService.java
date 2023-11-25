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
import com.example.demo.repository.alert.AlertRepository;
import com.example.demo.repository.position.PositionRepository;
import com.example.demo.repository.project.ProjectMemberAuthRepository;
import com.example.demo.repository.project.ProjectMemberRepository;
import com.example.demo.repository.project.ProjectRepository;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.repository.work.WorkRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final WorkRepository workRepository;
    private final ProjectMemberAuthRepository projectMemberAuthRepository;
    private final PositionRepository positionRepository;
    private final AlertRepository alertRepository;

    /**
     * 내 프로젝트 목록 조회
     * TODO : 현재 유저 가져오기.
     * @return
     */

    @Transactional(readOnly = true)
    public List<ProjectMeResponseDto> getMyProjects() {
        User user = userRepository.findById(1L).orElseThrow(() -> UserCustomException.NOT_FOUND_USER);
        List<Project> projects = projectRepository.findProjectsByUser(user);
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

    @Transactional(readOnly = true)
    public ProjectSpecificDetailResponseDto getDetail(Long projectId) {
        Project project =
                projectRepository
                        .findById(projectId)
                        .orElseThrow(() -> ProjectCustomException.NOT_FOUND_PROJECT);
        TrustGradeResponseDto trustGradeDto = TrustGradeResponseDto.of(project.getTrustGrade());
        UserProjectResponseDto userProjectResponseDto = UserProjectResponseDto.of(project);

        // ProjectMember 부분
        List<ProjectMember> projectMembers =
                projectMemberRepository
                        .findProjectsMemberByProject(project)
                        .orElseThrow(() -> ProjectMemberCustomException.NOT_FOUND_PROJECT_MEMBER);
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
        List<Work> works =
                workRepository
                        .findWorksByProject(project)
                        .orElseThrow(() -> WorkCustomException.NOT_FOUND_WORK);
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
