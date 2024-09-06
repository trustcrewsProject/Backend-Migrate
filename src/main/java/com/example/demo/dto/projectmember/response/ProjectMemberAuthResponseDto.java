package com.example.demo.dto.projectmember.response;

import com.example.demo.model.project.ProjectMemberAuth;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectMemberAuthResponseDto {
    private Long projectMemberAuthId;
    private String projectMemberAuthName;
    private boolean milestone_change_YN;
    private boolean work_change_YN;
    private boolean vote_YN;
    private boolean config_YN;

    public static ProjectMemberAuthResponseDto of(ProjectMemberAuth projectMemberAuth) {
        return ProjectMemberAuthResponseDto.builder()
                .projectMemberAuthId(projectMemberAuth.getId())
                .projectMemberAuthName(projectMemberAuth.getName())
                .milestone_change_YN(projectMemberAuth.isMilestoneChangeYN())
                .work_change_YN(projectMemberAuth.isWorkChangeYN())
                .vote_YN(projectMemberAuth.isVoteYn())
                .config_YN(projectMemberAuth.isConfigYn())
                .build();
    }
}
