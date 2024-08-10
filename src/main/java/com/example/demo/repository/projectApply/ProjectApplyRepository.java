package com.example.demo.repository.projectApply;

import com.example.demo.model.projectApply.ProjectApply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectApplyRepository extends JpaRepository<ProjectApply, Long>, ProjectApplyRepositoryCustom {
    ProjectApply findProjectApplyById(Long applyId);
}
