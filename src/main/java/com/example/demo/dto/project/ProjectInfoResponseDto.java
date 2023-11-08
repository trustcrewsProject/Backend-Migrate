package com.example.demo.dto.project;

import com.example.demo.model.Project;
import com.example.demo.model.ProjectMember;
import com.example.demo.model.TrustGrade;
import com.example.demo.model.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

// 프로젝트 정보 응답 DTO
@Getter
@Builder
public class ProjectInfoResponseDto {

    private String projectName;

    private String projectContent;

    private TrustGrade trustGrade;

    private String createProjectDate;

    private User createUser;

    private List<ProjectMember> projectMembers;

    public static ProjectInfoResponseDto of(Project project, List<ProjectMember> projectMembers) {
        return ProjectInfoResponseDto.builder()
                .projectName(project.getName())
                .projectContent(project.getContent())
                .trustGrade(project.getTrustGrade())
                .createProjectDate("")
                .createUser(project.getUser())
                .projectMembers(projectMembers)
                .build();
    }
}
