package com.example.demo.dto.milestone.request;

import com.example.demo.constant.ProjectMemberAuth;
import com.example.demo.model.milestone.Milestone;
import com.example.demo.model.project.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MilestoneCreateRequestDto {
    private Long projectId;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectMemberAuth authMap;

    public Milestone toMileStoneEntity(Project project) {
        return Milestone.builder()
                .project(project)
                .content(this.getContent())
                .startDate(this.getStartDate())
                .endDate(this.getEndDate())
                .build();
    }
}
