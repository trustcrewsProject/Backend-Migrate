package com.example.demo.service.project;

import com.example.demo.constant.ProjectMemberAuth;
import com.example.demo.constant.ProjectMemberStatus;
import com.example.demo.model.position.Position;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public interface ProjectMemberService {

    ProjectMember toProjectMemberEntity(
            Project project,
            User user,
            ProjectMemberAuth projectMemberAuth,
            ProjectMemberStatus projectMemberStatus,
            Position position);

    ProjectMember findById(Long id);

    ProjectMember save(ProjectMember projectMember);

    void delete(ProjectMember projectMember);

    // 프로젝트 정보, 프로젝트 멤버 상태로 멤버 목록 조회
    List<ProjectMember> getProjectMembersByProjectAndStatus(Project project, ProjectMemberStatus status);

    Optional<ProjectMember> findProjectMemberByProjectAndUser(Project project, User user);

    ProjectMember findProjectMemberByPrIdAndUserId(Long projectId, Long userId);

    List<ProjectMember> getProjectMembersByUserAndStatus(User user, ProjectMemberStatus status);

    void updateProjectMemberAuth(Long projectMemberId, ProjectMemberAuth projectMemberAuth);

    Long countOtherProjectManagers(Long projectId, Long projectMemberId);
}
