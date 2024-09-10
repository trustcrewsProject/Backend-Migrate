package com.example.demo.service.project;

import com.example.demo.constant.ProjectMemberStatus;
import com.example.demo.constant.ProjectRole;
import com.example.demo.dto.project.ProjectDetailAuthDto;
import com.example.demo.global.exception.customexception.ProjectMemberAuthCustomException;
import com.example.demo.global.exception.customexception.ProjectMemberCustomException;
import com.example.demo.model.position.Position;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.project.ProjectMemberAuth;
import com.example.demo.model.user.User;
import com.example.demo.repository.project.ProjectMemberRepository;
import com.example.demo.service.user.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectMemberServiceImpl implements ProjectMemberService {
    private final ProjectMemberRepository projectMemberRepository;
    private final UserService userService;

    private final ProjectService projectService;

    public ProjectMember toProjectMemberEntity(
            Project project,
            User user,
            ProjectMemberAuth projectMemberAuth,
            ProjectMemberStatus projectMemberStatus,
            Position position) {
        return ProjectMember.builder()
                .project(project)
                .user(user)
                .projectMemberAuth(projectMemberAuth)
                .status(projectMemberStatus)
                .position(position)
                .build();
    }

    @Override
    public ProjectMember findById(Long id) {
        return projectMemberRepository
                .findById(id)
                .orElseThrow(() -> ProjectMemberCustomException.NOT_FOUND_PROJECT_MEMBER);
    }

    @Override
    public List<ProjectMember> getProjectMembersByProjectAndStatus(Project project, ProjectMemberStatus status) {
        return projectMemberRepository
                .findAllByProjectAndStatus(project, status);
    }

    public Optional<ProjectMember> findProjectMemberByProjectAndUser(Project project, User user) {
        return projectMemberRepository.findProjectMemberByProjectAndUser(project, user);
    }

    @Override
    public ProjectMember save(ProjectMember projectMember) {
        return projectMemberRepository.save(projectMember);
    }

    @Override
    public void delete(ProjectMember projectMember) {
        projectMemberRepository.delete(projectMember);
    }

    /**
     * 사용자의 생성 및 수정 권한 확인하기 (마일스톤, 업무)
     *
     * @param projectId
     * @param userId
     * @return
     */
    @Override
    public ProjectDetailAuthDto getUserAuthMap(Long projectId, Long userId) {
        return getAuthMap(getProjectMemberAuth(projectId, userId));
    }

    @Override
    public ProjectMember findProjectMemberByPrIdAndUserId(Long projectId, Long userId) {
        return projectMemberRepository.findProjectMemberByPrIdAndUserId(projectId, userId);
    }

    @Override
    public void verifiedProjectManager(Project project, User user) {
        ProjectMember member = projectMemberRepository
                .findProjectMemberByProjectAndUser(project, user)
                .orElseThrow(() -> ProjectMemberCustomException.NOT_FOUND_PROJECT_MEMBER);

        // 프로젝트 매니저 확인
        ProjectRole projectRole = ProjectRole.findProjectRole(member.getProjectMemberAuth().getId());
        if(!projectRole.isManager()) {
            throw ProjectMemberAuthCustomException.INSUFFICIENT_PROJECT_AUTH;
        }
    }

    private static ProjectDetailAuthDto getAuthMap(ProjectMemberAuth projectMemberAuth) {
        return new ProjectDetailAuthDto(projectMemberAuth);
    }

    private ProjectMemberAuth getProjectMemberAuth(Long projectId, Long userId) {
        User findUser = userService.findById(userId);
        Project findProject = projectService.findById(projectId);
        return findProjectMemberByProjectAndUser(findProject, findUser)
                .map(ProjectMember::getProjectMemberAuth)
                .orElseThrow(() -> ProjectMemberCustomException.NOT_FOUND_PROJECT_MEMBER);
    }

    @Override
    public List<ProjectMember> getProjectMembersByUserAndStatus(User user, ProjectMemberStatus status) {
        return projectMemberRepository
                .findAllByUserAndStatus(user, status);
    }

    @Override
    public void updateProjectMemberAuth(Long projectMemberId, ProjectMemberAuth projectMemberAuth) {
        projectMemberRepository.updateProjectMemberAuth(projectMemberId, projectMemberAuth);
    }

    @Override
    public Long countOtherProjectManagers(Long projectId, Long projectMemberId) {
        return projectMemberRepository.countOtherProjectManagers(projectId, projectMemberId);
    }

}
