package com.example.demo.dto.milestone.request;

import java.time.LocalDateTime;

import com.example.demo.model.milestone.Milestone;
import com.example.demo.model.project.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MilestoneCreateRequestDto {
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Milestone toMileStoneEntity(Project project){
        return Milestone.builder()
                .project(project)
                .content(this.getName())
                .startDate(this.getStartDate())
                .endDate(this.getEndDate())
                .expireStatus(false)
                .completeStatus(false)
                .build();
    }
}
