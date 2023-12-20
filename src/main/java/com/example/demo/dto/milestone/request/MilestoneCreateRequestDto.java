package com.example.demo.dto.milestone.request;

import com.example.demo.model.milestone.Milestone;
import com.example.demo.model.project.Project;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MilestoneCreateRequestDto {
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;

    public Milestone toMileStoneEntity(Project project) {
        return Milestone.builder()
                .project(project)
                .content(this.getContent())
                .startDate(this.getStartDate())
                .endDate(this.getEndDate())
                .expireStatus(false)
                .completeStatus(false)
                .build();
    }
}
