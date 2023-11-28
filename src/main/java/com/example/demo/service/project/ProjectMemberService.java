package com.example.demo.service.project;

import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.project.ProjectMemberAuth;
import com.example.demo.model.user.User;

public interface ProjectMemberService {

    public ProjectMember toProjectMemberEntity(Project project, User user, ProjectMemberAuth projectMemberAuth);
    public ProjectMember save(ProjectMember projectMember);
}
