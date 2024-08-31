package com.example.demo.repository.work;

import com.example.demo.constant.ProgressStatus;
import com.example.demo.model.milestone.Milestone;
import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import com.example.demo.model.work.Work;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WorkRepository extends JpaRepository<Work, Long>, WorkRepositoryCustom {
    Optional<List<Work>> findWorksByProject(Project project);

    Optional<Work> findFirstByProjectAndAssignedUserIdAndProgressStatusOrderByIdDesc(
            Project project, User user, ProgressStatus progressStatus);

    @Query("SELECT COUNT(w) FROM Work w WHERE w.milestone.id = :milestoneId")
    int countWorksByMilestoneId(Long milestoneId);

    void deleteAllByProject(Project project);
}
