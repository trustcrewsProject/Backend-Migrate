package com.example.demo.repository.project;

import com.example.demo.constant.ProjectMemberStatus;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    Optional<List<ProjectMember>> findProjectsMemberByProject(Project project);

    Optional<ProjectMember> findProjectMemberByProjectAndUser(Project project, User user);

    @Query("select pm from ProjectMember pm where pm.project = :project and pm.status = :status")
    List<ProjectMember> findByProjectAndStatus(Project project, ProjectMemberStatus status);

    @Query("select pm from ProjectMember pm where pm.user = :user")
    List<ProjectMember> findByUserId(Long user);
}
