package com.example.demo.repository.project;


import com.example.demo.constant.ProjectMemberAuth;

public interface ProjectMemberRepositoryCustom {
    void updateProjectMemberAuth(Long projectMemberId, ProjectMemberAuth projectMemberAuth);

    Long countOtherProjectManagers(Long projectId, Long projectMemberId);
}
