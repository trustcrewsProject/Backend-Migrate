package com.example.demo.repository.milestone;

import com.example.demo.model.milestone.Milestone;
import com.example.demo.model.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MileStoneRepository extends JpaRepository<Milestone, Long> {

    Optional<List<Milestone>> findMilestonesByProject(Project project);
}
