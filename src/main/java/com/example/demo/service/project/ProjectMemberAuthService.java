package com.example.demo.service.project;

import com.example.demo.model.project.ProjectMemberAuth;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProjectMemberAuthService {
    ProjectMemberAuth save(ProjectMemberAuth projectMemberAuth);

    ProjectMemberAuth findProjectMemberAuthById(Long id);

    ProjectMemberAuth findTopByOrderByIdDesc();
}
