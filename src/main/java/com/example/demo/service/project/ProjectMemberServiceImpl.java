package com.example.demo.service.project;

import com.example.demo.constant.ProjectMemberAuth;
import com.example.demo.constant.ProjectMemberStatus;
import com.example.demo.global.exception.customexception.ProjectMemberCustomException;
import com.example.demo.model.position.Position;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.user.User;
import com.example.demo.repository.project.ProjectMemberRepository;
import com.example.demo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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


    @Override
    public ProjectMember findProjectMemberByPrIdAndUserId(Long projectId, Long userId) {
        return projectMemberRepository.findProjectMemberByPrIdAndUserId(projectId, userId);
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
