package com.example.demo.repository.projectApply;

import com.example.demo.model.projectApply.ProjectApply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectApplyRepository extends JpaRepository<ProjectApply, Long>, ProjectApplyRepositoryCustom {

}
