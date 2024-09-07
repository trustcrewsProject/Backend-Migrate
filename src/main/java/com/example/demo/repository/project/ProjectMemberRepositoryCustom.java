package com.example.demo.repository.project;

import com.example.demo.model.project.ProjectMemberAuth;

public interface ProjectMemberRepositoryCustom {
    void updateProjectMemberAuth(Long projectMemberId, ProjectMemberAuth projectMemberAuth);
}
