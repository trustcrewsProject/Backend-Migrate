package com.example.demo.repository.work;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.milestone.Milestone;
import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import com.example.demo.model.work.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRepository extends JpaRepository<Work, Long> {
    Optional<List<Work>> findWorksByProject(Project project);

    Optional<List<Work>> findWorksByProjectAndMilestone(Project project, Milestone milestone);

    Optional<Work> findFirstByProjectAndAssignedUserIdAndOrderByProjectDesc(Project project, User user);
}
