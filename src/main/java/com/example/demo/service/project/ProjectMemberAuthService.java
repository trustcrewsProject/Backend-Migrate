package com.example.demo.service.project;

import com.example.demo.model.project.ProjectMemberAuth;
import org.springframework.stereotype.Service;

@Service
public interface ProjectMemberAuthService {
    public ProjectMemberAuth save(ProjectMemberAuth projectMemberAuth);

    public ProjectMemberAuth findProjectMemberAuthById(Long id);
}
