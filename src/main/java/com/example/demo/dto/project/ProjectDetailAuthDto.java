package com.example.demo.dto.project;

import com.example.demo.model.project.ProjectMemberAuth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProjectDetailAuthDto {
    private final boolean milestoneAuth;
    private final boolean workAuth;
    private final boolean voteAuth;

    public ProjectDetailAuthDto(ProjectMemberAuth projectMemberAuth){
        this.milestoneAuth = projectMemberAuth.isMilestoneChangeYN();
        this.workAuth = projectMemberAuth.isWorkChangeYN();
        this.voteAuth = projectMemberAuth.isVoteYn();
    }

}
