package com.example.demo.service.project;

import com.example.demo.constant.ProjectMemberStatus;
import com.example.demo.constant.ProjectRole;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
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

    public List<ProjectMember> findProjectsMemberByProject(Project project) {
        return projectMemberRepository
                .findProjectsMemberByProject(project)
                .orElseThrow(() -> ProjectMemberCustomException.NOT_FOUND_PROJECT_MEMBER);
    }

    @Override
    public List<ProjectMember> getProjectMembersByProjectAndStatus(Project project, ProjectMemberStatus status) {
        return projectMemberRepository
                .findByProjectAndStatus(project, status);
    }

    public ProjectMember findProjectMemberByProjectAndUser(Project project, User user) {
        return projectMemberRepository
                .findProjectMemberByProjectAndUser(project, user)
                .orElseThrow(() -> ProjectMemberCustomException.NOT_FOUND_PROJECT_MEMBER);
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
     * 프로젝트 멤버 강제 탈퇴하기.
     *
     * @param projectMemberId
     */
    public void withdrawlForce(Long projectMemberId) {
        ProjectMember projectMember = findById(projectMemberId);
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
    public Map<String, Boolean> getUserAuthMap(Long projectId, Long userId) {
        return getAuthMap(getProjectMemberAuth(projectId, userId));
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

    private static Map<String, Boolean> getAuthMap(ProjectMemberAuth projectMemberAuth) {
        Map<String, Boolean> authMap = new HashMap<>();
        authMap.put("milestoneAuth", projectMemberAuth.isMilestoneChangeYN());
        authMap.put("workAuth", projectMemberAuth.isWorkChangeYN());
        return authMap;
    }

    private ProjectMemberAuth getProjectMemberAuth(Long projectId, Long userId) {
        User findUser = userService.findById(userId);
        Project findProject = projectService.findById(projectId);
        return findProjectMemberByProjectAndUser(findProject, findUser).getProjectMemberAuth();
    }
}
