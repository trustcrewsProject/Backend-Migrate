package com.example.demo.repository.project;

import com.example.demo.constant.ProjectMemberStatus;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    Optional<List<ProjectMember>> findProjectsMemberByProject(Project project);

    Optional<ProjectMember> findProjectMemberByProjectAndUser(Project project, User user);

    @Query("select pm from ProjectMember pm where pm.project = :project and pm.status = :status")
    List<ProjectMember> findAllByProjectAndStatus(Project project, ProjectMemberStatus status);

    @Query("select pm from ProjectMember pm where pm.user = :user and pm.status = :status")
    List<ProjectMember> findAllByUserAndStatus(User user, ProjectMemberStatus status);

    @Query("SELECT COUNT(pm) from ProjectMember pm WHERE pm.projectMemberAuth.id != 4L AND pm.project = :project")
    int countVotableProjectMember(Project project);

    @Query("SELECT COUNT(pm) from ProjectMember pm WHERE pm.id != :fwMemberId AND pm.project.id = :projectId")
    int countFWVotableProjectMember(Long fwMemberId, Long projectId);

    @Query("SELECT pm from ProjectMember pm WHERE pm.project.id = :projectId AND pm.user.id = :userId")
    ProjectMember findProjectMemberByPrIdAndUserId(Long projectId, Long userId);
}
